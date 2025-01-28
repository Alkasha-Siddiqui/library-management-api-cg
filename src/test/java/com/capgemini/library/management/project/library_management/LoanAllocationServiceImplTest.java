package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.BookCopies;
import com.capgemini.library.management.project.library_management.entity.Loan;
import com.capgemini.library.management.project.library_management.entity.Member;
import com.capgemini.library.management.project.library_management.exception.BookOutOfStockException;
import com.capgemini.library.management.project.library_management.exception.LoanAlreadyReturnedException;
import com.capgemini.library.management.project.library_management.exception.UserSuspendedException;
import com.capgemini.library.management.project.library_management.model.LoanDTO;
import com.capgemini.library.management.project.library_management.repository.BookCopiesRepository;
import com.capgemini.library.management.project.library_management.repository.BookRepository;
import com.capgemini.library.management.project.library_management.repository.LoanRepository;
import com.capgemini.library.management.project.library_management.repository.MemberRepository;
import com.capgemini.library.management.project.library_management.service.LoanAllocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanAllocationServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookCopiesRepository bookCopiesRepository;

    @InjectMocks
    private LoanAllocationServiceImpl loanAllocationService;

    private Loan loan;
    private LoanDTO loanDTO;
    private Member member;
    private Book book;
    private BookCopies bookCopies;

    @BeforeEach

    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setStatus(Member.MemberStatus.ACTIVE);

        book = new Book();
        book.setId(1L);

        bookCopies = new BookCopies();
        bookCopies.setBook(book);
        bookCopies.setAvailableCopies(1);

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
        loan.setMember(member);
        loan.setIssueDate(LocalDate.of(2023, 1, 1));
        loan.setDueDate(LocalDate.of(2023, 1, 15));
        loan.setStatus(Loan.StatusEnum.ISSUED);

        loanDTO = new LoanDTO();
        loanDTO.setId(1L);
        loanDTO.setBookId(1L);
        loanDTO.setMemberId(1L);
        loanDTO.setIssueDate(LocalDate.of(2023, 1, 1));
        loanDTO.setDueDate(LocalDate.of(2023, 1, 15));
        loanDTO.setStatus(LoanDTO.StatusEnum.ISSUED);
    }

    @Test
    void testIssueLoan() {
        when(memberRepository.findById(loanDTO.getMemberId())).thenReturn(Optional.of(member));
        when(bookRepository.findById(loanDTO.getBookId())).thenReturn(Optional.of(book));
        when(bookCopiesRepository.findByBookId(book.getId())).thenReturn(Optional.of(bookCopies));
        when(modelMapper.map(loanDTO, Loan.class)).thenReturn(loan);
        when(loanRepository.save(loan)).thenReturn(loan);
        when(modelMapper.map(loan, LoanDTO.class)).thenReturn(loanDTO);

        LoanDTO result = loanAllocationService.issueLoan(loanDTO);

        assertNotNull(result);
        assertEquals(loanDTO.getId(), result.getId());
        assertEquals(loanDTO.getBookId(), result.getBookId());
        assertEquals(loanDTO.getMemberId(), result.getMemberId());
        assertEquals(loanDTO.getIssueDate(), result.getIssueDate());
        assertEquals(loanDTO.getDueDate(), result.getDueDate());
        assertEquals(loanDTO.getStatus(), result.getStatus());

        verify(bookCopiesRepository, times(1)).save(bookCopies);
        verify(loanRepository, times(1)).save(loan);
        verify(modelMapper, times(1)).map(loanDTO, Loan.class);
        verify(modelMapper, times(1)).map(loan, LoanDTO.class);
    }

    @Test
    void testGetAllLoansWithoutStatus() {
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan));
        when(modelMapper.map(loan, LoanDTO.class)).thenReturn(loanDTO);

        List<LoanDTO> result = loanAllocationService.getAllLoans(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(loanDTO.getId(), result.get(0).getId());
        assertEquals(loanDTO.getBookId(), result.get(0).getBookId());
        assertEquals(loanDTO.getMemberId(), result.get(0).getMemberId());
        assertEquals(loanDTO.getIssueDate(), result.get(0).getIssueDate());
        assertEquals(loanDTO.getDueDate(), result.get(0).getDueDate());
        assertEquals(loanDTO.getStatus(), result.get(0).getStatus());

        verify(loanRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(loan, LoanDTO.class);
    }

    @Test
    void testReturnBook() {
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
        when(bookCopiesRepository.findByBookId(book.getId())).thenReturn(Optional.of(bookCopies));
        when(loanRepository.save(loan)).thenReturn(loan);
        when(modelMapper.map(loan, LoanDTO.class)).thenReturn(loanDTO);

        LoanDTO result = loanAllocationService.returnBook(loan.getId());

        assertNotNull(result);
        assertEquals(loanDTO.getId(), result.getId());
        assertEquals(loanDTO.getBookId(), result.getBookId());
        assertEquals(loanDTO.getMemberId(), result.getMemberId());
        assertEquals(loanDTO.getIssueDate(), result.getIssueDate());
        assertEquals(loanDTO.getDueDate(), result.getDueDate());
        assertEquals(loanDTO.getStatus(), result.getStatus());

        verify(bookCopiesRepository, times(1)).save(bookCopies);
        verify(loanRepository, times(1)).save(loan);
        verify(modelMapper, times(1)).map(loan, LoanDTO.class);
    }

    @Test
    void testGetOverdueLoans() {
        when(loanRepository.findByStatus(Loan.StatusEnum.OVERDUE)).thenReturn(Arrays.asList(loan));
        when(modelMapper.map(loan, LoanDTO.class)).thenReturn(loanDTO);

        List<LoanDTO> result = loanAllocationService.getOverdueLoans();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(loanDTO.getId(), result.get(0).getId());
        assertEquals(loanDTO.getBookId(), result.get(0).getBookId());
        assertEquals(loanDTO.getMemberId(), result.get(0).getMemberId());
        assertEquals(loanDTO.getIssueDate(), result.get(0).getIssueDate());
        assertEquals(loanDTO.getDueDate(), result.get(0).getDueDate());
        assertEquals(loanDTO.getStatus(), result.get(0).getStatus());

        verify(loanRepository, times(1)).findByStatus(Loan.StatusEnum.OVERDUE);
        verify(modelMapper, times(1)).map(loan, LoanDTO.class);
    }

    @Test
    void testGetAllLoansWithStatus() {
        loan.setStatus(Loan.StatusEnum.ISSUED);
        when(loanRepository.findByStatus(any(Loan.StatusEnum.class))).thenReturn(Arrays.asList(loan));
        when(modelMapper.map(any(Loan.class), eq(LoanDTO.class))).thenReturn(loanDTO);

        List<LoanDTO> result = loanAllocationService.getAllLoans("ISSUED");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(loanRepository, times(1)).findByStatus(any(Loan.StatusEnum.class));
    }

    @Test
    void testLoanAlreadyReturnedException() {
        loan.setStatus(Loan.StatusEnum.RETURNED);
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));

        LoanAlreadyReturnedException exception = assertThrows(LoanAlreadyReturnedException.class, () -> {
            loanAllocationService.returnBook(loan.getId());
        });

        assertEquals("Loan is already returned with id : '1'", exception.getMessage());
    }

    @Test
    void testBookOutOfStockException() {
        member.setStatus(Member.MemberStatus.ACTIVE);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member)); // Ensure member is found
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book)); // Ensure book is found
        when(bookCopiesRepository.findByBookId(anyLong())).thenReturn(Optional.of(bookCopies)); // Ensure book copies are found

        bookCopies.setAvailableCopies(0);

        BookOutOfStockException exception = assertThrows(BookOutOfStockException.class, () -> {
            loanAllocationService.issueLoan(loanDTO);
        });

        assertEquals("Book is Out Of Stock with ID: " + book.getId(), exception.getMessage());
    }

    @Test
    void testUserSuspendedException() {
        member.setStatus(Member.MemberStatus.SUSPENDED);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member)); // Ensure member is found

        UserSuspendedException exception = assertThrows(UserSuspendedException.class, () -> {
            loanAllocationService.issueLoan(loanDTO);
        });

        assertEquals("User is suspended with ID: " + loanDTO.getMemberId(), exception.getMessage());
    }
}