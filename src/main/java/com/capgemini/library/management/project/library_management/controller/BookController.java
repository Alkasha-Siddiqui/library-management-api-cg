package com.capgemini.library.management.project.library_management.controller;

import com.capgemini.library.management.project.library_management.api.BooksApi;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.LoanNotIssuedException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.service.BookAllocationService;
import com.capgemini.library.management.project.library_management.service.GenreAllocationService;
import com.capgemini.library.management.project.library_management.service.LoanAllocationService;
import com.capgemini.library.management.project.library_management.service.MemberAllocationService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@RestController
public class BookController implements BooksApi {

    @Autowired
    BookAllocationService bookAllocationService;

    @Autowired
    GenreAllocationService genreAllocationService;

    @Autowired
    MemberAllocationService memberAllocationService;

    @Autowired
    LoanAllocationService loanAllocationService;

    public BookController(BookAllocationService bookAllocationService,
                          GenreAllocationService genreAllocationService,
                          MemberAllocationService memberAllocationService,
                          LoanAllocationService loanAllocationService) {
        this.bookAllocationService = bookAllocationService;
        this.genreAllocationService = genreAllocationService;
        this.memberAllocationService = memberAllocationService;
        this.loanAllocationService = loanAllocationService;
    }

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Override
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookDTO) {
        try {
            BookResponseDTO addedBookDTO = bookAllocationService.addBook(bookDTO);
            return new ResponseEntity<>(addedBookDTO, HttpStatus.CREATED);
        } catch (DuplicateISBNException ex) {
            BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("DUPLICATE_ISBN");
            error.setMessage("ISBN already exists in the library");
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        try {
            BookResponseDTO bookDTO = this.bookAllocationService.getBookById(id);
            return ResponseEntity.ok(bookDTO);
        } catch (ResourceNotFoundException ex) {
            BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("RESOURCE_NOT_FOUND");
            error.setMessage("Book not found with ID: " + id);
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO bookDTO) {
        try {
            BookResponseDTO updatedBookDTO = this.bookAllocationService.updateBook(id, bookDTO);
            return ResponseEntity.ok(updatedBookDTO);
        } catch (ResourceNotFoundException ex) {
            BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("RESOURCE_NOT_FOUND");
            error.setMessage("Book not found with ID: " + id);
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }
    }

//    @Override
//    public ResponseEntity<Void> removeBook(@PathVariable("id") Long id) {
//        bookAllocationService.removeBook(id);
//        return ResponseEntity.noContent().build();
//    }

    @Override
    public ResponseEntity<Void> removeBook(@PathVariable("id") Long id) {
        try {
            this.bookAllocationService.removeBook(id);
            log.info("Book with ID: {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<PageResponseDTO> getAllBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        PageResponseDTO pageResponseDTO = this.bookAllocationService.getAllBooks(page, size);
        return new ResponseEntity<PageResponseDTO>(pageResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResponseDTO> searchBooks(@Valid @RequestBody BookSearchRequestDTO bookSearchRequestDTO) {
        return ResponseEntity.ok(bookAllocationService.searchBooks(bookSearchRequestDTO));
    }

    @Override
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO genreRequestDTO) {
            GenreResponseDTO addedGenreDTO = genreAllocationService.createGenre(genreRequestDTO);
            return new ResponseEntity<>(addedGenreDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres(){
        List<GenreResponseDTO> genreResponseDTO = this.genreAllocationService.getAllGenres();
        return new ResponseEntity<>(genreResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MemberDTO> registerMember( @Valid @RequestBody MemberDTO memberDTO){
        if (memberDTO.getEmail() == null || memberDTO.getEmail().isEmpty() ||
                memberDTO.getFirstName() == null || memberDTO.getFirstName().isEmpty() ||
                memberDTO.getLastName() == null || memberDTO.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Required fields (email, firstName, lastName) cannot be empty");
        }

        MemberDTO memberResponseDTO = memberAllocationService.registerMember(memberDTO);
        return new ResponseEntity<>(memberResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<MemberDTO>> getAllMembers(@Valid @RequestParam(required = false) String status) {
        List<MemberDTO> memberDTOs = memberAllocationService.getAllMembers(status);
        return new ResponseEntity<>(memberDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoanDTO> issueLoan(@Valid @RequestBody LoanDTO loanDTO) {
        LoanDTO loanResponseDTO = loanAllocationService.issueLoan(loanDTO);
        return new ResponseEntity<>(loanResponseDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<LoanDTO>> getAllLoans(@Valid @RequestParam(required = false) String status) {
        List<LoanDTO> loanDTOs = loanAllocationService.getAllLoans(status);
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoanDTO> returnBook(@PathVariable("id") Long id){
        try {
            LoanDTO updatedLoan = loanAllocationService.returnBook(id);
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            LoanResponseWithErrorsDTO errorResponseDTO = new LoanResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("BAD_REQUEST");
            error.setMessage("Loan not found with ID: " + id);
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }catch (LoanNotIssuedException ex) {
            LoanResponseWithErrorsDTO errorResponseDTO = new LoanResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("LOAN_NOT_ISSUED"); // Use a specific code for this exception
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST); // Consider using a more appropriate status code (e.g., 400)
        }
    }


    @Override
    public ResponseEntity<List<LoanDTO>> getOverdueLoans(){
        List<LoanDTO> loanDTOs = loanAllocationService.getOverdueLoans();
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }
}