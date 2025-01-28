package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.controller.BookController;
import com.capgemini.library.management.project.library_management.exception.*;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.service.BookAllocationService;
import com.capgemini.library.management.project.library_management.service.GenreAllocationService;
import com.capgemini.library.management.project.library_management.service.LoanAllocationService;
import com.capgemini.library.management.project.library_management.service.MemberAllocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookAllocationService bookAllocationService;

    @Mock
    private GenreAllocationService genreAllocationService;

    @Mock
    private MemberAllocationService memberAllocationService;

    @Mock
    private LoanAllocationService loanAllocationService;

    @InjectMocks
    private BookController bookController;

    private BookRequestDTO bookRequestDTO;
    private BookResponseDTO bookResponseDTO;
    private BookResponseWithErrorsDTO bookResponseWithErrorsDTO;

    @BeforeEach
    void setUp() {
        bookRequestDTO = new BookRequestDTO();
        bookResponseDTO = new BookResponseDTO();
        bookResponseWithErrorsDTO = new BookResponseWithErrorsDTO();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode("BAD_REQUEST");
        errorDTO.setMessage("Already exists");
        errorDTO.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        bookResponseWithErrorsDTO.setError(errorDTO);
    }

    @Test
    void testAddBook() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitle("The Lord of the Rings");
        bookRequestDTO.setAuthor("J. R. R. Tolkien");
        bookRequestDTO.setIsbn("9780261102694");
        bookRequestDTO.setPublishYear(1954);
        bookRequestDTO.setGenreIds(Arrays.asList(1L, 2L, 3L));

        when(bookAllocationService.addBook(bookRequestDTO)).thenReturn(bookResponseDTO);

        ResponseEntity<BookResponseDTO> response = bookController.addBook(bookRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookResponseDTO, response.getBody());

        verify(bookAllocationService, times(1)).addBook(bookRequestDTO);
    }

    @Test
    void testGetBookById() {
        Long id = 1L;
        when(bookAllocationService.getBookById(id)).thenReturn(bookResponseDTO);

        ResponseEntity<BookResponseDTO> response = bookController.getBookById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookResponseDTO, response.getBody());

        verify(bookAllocationService, times(1)).getBookById(id);
    }

    @Test
    void testUpdateBook() {
        Long id = 1L;
        when(bookAllocationService.updateBook(id, bookRequestDTO)).thenReturn(bookResponseDTO);

        ResponseEntity<BookResponseDTO> response = bookController.updateBook(id, bookRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookResponseDTO, response.getBody());

        verify(bookAllocationService, times(1)).updateBook(id, bookRequestDTO);
    }

    @Test
    void testRemoveBook() {
        Long id = 1L;

        ResponseEntity<Void> response = bookController.removeBook(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(bookAllocationService, times(1)).removeBook(id);
    }

    @Test
    void testRemoveBookResourceNotFoundException() {
        Long id = 1L;
        doThrow(new ResourceNotFoundException("Book", "id", id)).when(bookAllocationService).removeBook(id);

        ResponseEntity<Void> response = bookController.removeBook(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(bookAllocationService, times(1)).removeBook(id);
    }

    @Test
    void testGetAllBooks() {
        PageResponseDTO pageResponseDTO = new PageResponseDTO();
        when(bookAllocationService.getAllBooks(0, 10)).thenReturn(pageResponseDTO);

        ResponseEntity<PageResponseDTO> response = bookController.getAllBooks(0, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageResponseDTO, response.getBody());

        verify(bookAllocationService, times(1)).getAllBooks(0, 10);
    }

    @Test
    void testSearchBooks() {
        BookSearchRequestDTO bookSearchRequestDTO = new BookSearchRequestDTO();
        PageResponseDTO pageResponseDTO = new PageResponseDTO();
        when(bookAllocationService.searchBooks(bookSearchRequestDTO)).thenReturn(pageResponseDTO);

        ResponseEntity<PageResponseDTO> response = bookController.searchBooks(bookSearchRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pageResponseDTO, response.getBody());

        verify(bookAllocationService, times(1)).searchBooks(bookSearchRequestDTO);
    }

    @Test
    void testCreateGenre() {
        // Ensure the genreRequestDTO has the required field
        GenreRequestDTO genreRequestDTO = new GenreRequestDTO();
        genreRequestDTO.setName("Fantasy");

        GenreResponseDTO genreResponseDTO = new GenreResponseDTO();
        when(genreAllocationService.createGenre(genreRequestDTO)).thenReturn(genreResponseDTO);

        ResponseEntity<GenreResponseDTO> response = bookController.createGenre(genreRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(genreResponseDTO, response.getBody());

        verify(genreAllocationService, times(1)).createGenre(genreRequestDTO);
    }

    @Test
    void testGetAllGenres() {
        List<GenreResponseDTO> genreResponseDTOs = Arrays.asList(new GenreResponseDTO());
        when(genreAllocationService.getAllGenres()).thenReturn(genreResponseDTOs);

        ResponseEntity<List<GenreResponseDTO>> response = bookController.getAllGenres();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(genreResponseDTOs, response.getBody());

        verify(genreAllocationService, times(1)).getAllGenres();
    }

    @Test
    void testRegisterMember() {
        // Ensure the memberDTO has all required fields
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setFirstName("John");
        memberDTO.setLastName("Doe");
        memberDTO.setEmail("john.doe@example.com");

        MemberDTO memberResponseDTO = new MemberDTO();
        memberResponseDTO.setFirstName("John");
        memberResponseDTO.setLastName("Doe");
        memberResponseDTO.setEmail("john.doe@example.com");

        when(memberAllocationService.registerMember(memberDTO)).thenReturn(memberResponseDTO);

        ResponseEntity<MemberDTO> response = bookController.registerMember(memberDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(memberResponseDTO, response.getBody());

        verify(memberAllocationService, times(1)).registerMember(memberDTO);
    }

    @Test
    void testGetAllMembers() {
        List<MemberDTO> memberDTOs = Arrays.asList(new MemberDTO());
        when(memberAllocationService.getAllMembers(null)).thenReturn(memberDTOs);

        ResponseEntity<List<MemberDTO>> response = bookController.getAllMembers(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(memberDTOs, response.getBody());

        verify(memberAllocationService, times(1)).getAllMembers(null);
    }

    @Test
    void testIssueLoan() {
        // Ensure the loanDTO has all required fields
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setBookId(1L);
        loanDTO.setMemberId(2L);
        loanDTO.setIssueDate(LocalDate.of(2023, 1, 22));
        loanDTO.setDueDate(LocalDate.of(2023, 2, 5));
        loanDTO.setStatus(LoanDTO.StatusEnum.ISSUED);

        LoanDTO loanResponseDTO = new LoanDTO();
        loanResponseDTO.setBookId(1L);
        loanResponseDTO.setMemberId(2L);
        loanResponseDTO.setIssueDate(LocalDate.of(2023, 1, 22));
        loanResponseDTO.setDueDate(LocalDate.of(2023, 2, 5));
        loanResponseDTO.setStatus(LoanDTO.StatusEnum.ISSUED);

        when(loanAllocationService.issueLoan(loanDTO)).thenReturn(loanResponseDTO);

        ResponseEntity<LoanDTO> response = bookController.issueLoan(loanDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(loanResponseDTO, response.getBody());

        verify(loanAllocationService, times(1)).issueLoan(loanDTO);
    }

    @Test
    void testGetAllLoans() {
        List<LoanDTO> loanDTOs = Arrays.asList(new LoanDTO());
        when(loanAllocationService.getAllLoans(null)).thenReturn(loanDTOs);

        ResponseEntity<List<LoanDTO>> response = bookController.getAllLoans(null);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loanDTOs, response.getBody());

        verify(loanAllocationService, times(1)).getAllLoans(null);
    }

    @Test
    void testReturnBook() {
        Long id = 1L;
        LoanDTO loanDTO = new LoanDTO();
        when(loanAllocationService.returnBook(id)).thenReturn(loanDTO);

        ResponseEntity<LoanDTO> response = bookController.returnBook(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loanDTO, response.getBody());

        verify(loanAllocationService, times(1)).returnBook(id);
    }

    @Test
    void testReturnBookResourceNotFoundException() {
        Long id = 1L;
        LoanResponseWithErrorsDTO loanResponseWithErrorsDTO = new LoanResponseWithErrorsDTO();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode("RESOURCE_NOT_FOUND");
        errorDTO.setMessage("Loan not found");
        errorDTO.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        loanResponseWithErrorsDTO.setError(errorDTO);

        when(loanAllocationService.returnBook(id)).thenThrow(new ResourceNotFoundException("Loan", "id", id));

        ResponseEntity<LoanDTO> response = bookController.returnBook(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(loanResponseWithErrorsDTO, response.getBody());

        verify(loanAllocationService, times(1)).returnBook(id);
    }

    @Test
    void testGetOverdueLoans() {
        List<LoanDTO> loanDTOs = Arrays.asList(new LoanDTO());
        when(loanAllocationService.getOverdueLoans()).thenReturn(loanDTOs);

        ResponseEntity<List<LoanDTO>> response = bookController.getOverdueLoans();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loanDTOs, response.getBody());

        verify(loanAllocationService, times(1)).getOverdueLoans();
    }


    @Test
    void testAddBookWithValidFields() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setTitle("The Lord of the Rings");
        bookRequestDTO.setAuthor("J. R. R. Tolkien");
        bookRequestDTO.setIsbn("9780261102694");

        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        when(bookAllocationService.addBook(bookRequestDTO)).thenReturn(bookResponseDTO);

        ResponseEntity<BookResponseDTO> response = bookController.addBook(bookRequestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookResponseDTO, response.getBody());

        verify(bookAllocationService, times(1)).addBook(bookRequestDTO);
    }
}