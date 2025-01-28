package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.BookCopies;
import com.capgemini.library.management.project.library_management.entity.Loan;
import com.capgemini.library.management.project.library_management.entity.Member;
import com.capgemini.library.management.project.library_management.exception.*;
import com.capgemini.library.management.project.library_management.model.LoanDTO;
import com.capgemini.library.management.project.library_management.repository.BookCopiesRepository;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import com.capgemini.library.management.project.library_management.repository.LoanRepository;
import com.capgemini.library.management.project.library_management.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "loanService")
@Transactional
public class LoanAllocationServiceImpl implements LoanAllocationService {

    private final LoanRepository loanRepository;
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookCopiesRepository bookCopiesRepository;

    @Autowired
    public LoanAllocationServiceImpl(LoanRepository loanRepository, ModelMapper modelMapper,
                       MemberRepository memberRepository, BookRepository bookRepository,
                       BookCopiesRepository bookCopiesRepository) {
        this.loanRepository = loanRepository;
        this.modelMapper = modelMapper;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.bookCopiesRepository = bookCopiesRepository;
    }

    @Override
    public LoanDTO issueLoan(LoanDTO loanDTO) {
        Member member = memberRepository.findById(loanDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(loanDTO.getMemberId()));

        if (member.getStatus() == Member.MemberStatus.SUSPENDED) {
            throw new UserSuspendedException(loanDTO.getMemberId());
        }

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", loanDTO.getBookId()));

        BookCopies bookCopies = bookCopiesRepository.findByBookId(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id",book.getId()));
        if (bookCopies.getAvailableCopies() <= 0) {
            throw new BookOutOfStockException(book.getId());
        }

        bookCopies.setAvailableCopies(bookCopies.getAvailableCopies() - 1);
        bookCopiesRepository.save(bookCopies);

        Loan loan = modelMapper.map(loanDTO, Loan.class);
        Loan savedLoan = loanRepository.save(loan);
        return modelMapper.map(savedLoan, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getAllLoans(String status) {
        List<Loan> loans;
        if (status == null) {
            loans = loanRepository.findAll();
        } else {
            loans = loanRepository.findByStatus(Loan.StatusEnum.valueOf(status));
        }
        return loans.stream().map(loan -> modelMapper.map(loan, LoanDTO.class)).collect(Collectors.toList());
    }

    @Override
    public LoanDTO returnBook(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id));

        if (loan.getStatus().equals(Loan.StatusEnum.RETURNED)) {
            throw new LoanAlreadyReturnedException("Loan", "id", loan.getId());
        }

        final Long bookId = loan.getBook().getId();

        loan.setStatus(Loan.StatusEnum.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loan = loanRepository.save(loan);

        BookCopies bookCopies = bookCopiesRepository.findByBookId(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book","id",bookId));
        bookCopies.setAvailableCopies(bookCopies.getAvailableCopies() + 1);
        bookCopiesRepository.save(bookCopies);

        return modelMapper.map(loan, LoanDTO.class);
    }

    @Override
    public List<LoanDTO> getOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findByStatus(Loan.StatusEnum.OVERDUE);
        return overdueLoans.stream()
                .map(loan -> modelMapper.map(loan, LoanDTO.class))
                .collect(Collectors.toList());
    }
}