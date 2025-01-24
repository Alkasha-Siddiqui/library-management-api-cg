package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.Loan;
import com.capgemini.library.management.project.library_management.entity.Member;
import com.capgemini.library.management.project.library_management.exception.*;
import com.capgemini.library.management.project.library_management.model.LoanDTO;
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

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public LoanDTO issueLoan(LoanDTO loanDTO) {
        // Check if member exists
        Member member = memberRepository.findById(loanDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(loanDTO.getMemberId()));

        // Check if member is suspended
        if (member.getStatus() == Member.MemberStatus.SUSPENDED) {
            throw new UserSuspendedException(loanDTO.getMemberId());
        }

        // Check if book exists
        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new BookNotFoundException(loanDTO.getBookId()));

        // Check if book is already issued
        if (loanRepository.existsByBookIdAndStatus(loanDTO.getBookId(), Loan.StatusEnum.ISSUED)) {
            throw new BookAlreadyIssuedException(loanDTO.getBookId());
        }

        Loan loan = modelMapper.map(loanDTO, Loan.class);
        Loan savedLoan = loanRepository.save(loan);
        LoanDTO loanResponseDTO = modelMapper.map(savedLoan, LoanDTO.class);
        return loanResponseDTO;
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

        loan.setStatus(Loan.StatusEnum.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loan = loanRepository.save(loan);

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