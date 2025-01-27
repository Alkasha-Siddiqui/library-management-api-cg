package com.capgemini.library.management.project.library_management.controller;

import com.capgemini.library.management.project.library_management.api.BooksApi;
import com.capgemini.library.management.project.library_management.exception.*;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.service.BookAllocationService;
import com.capgemini.library.management.project.library_management.service.GenreAllocationService;
import com.capgemini.library.management.project.library_management.service.LoanAllocationService;
import com.capgemini.library.management.project.library_management.service.MemberAllocationService;
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

@RestController
public class BookController implements BooksApi {

    private final BookAllocationService bookAllocationService;
    private final GenreAllocationService genreAllocationService;
    private final MemberAllocationService memberAllocationService;
    private final LoanAllocationService loanAllocationService;
    private static final String REQUIRED_FIELDS = "required fields";
    private static final String BAD_REQUEST = "BAD_REQUEST";


    @Autowired
    public BookController(BookAllocationService bookAllocationService,
                       GenreAllocationService genreAllocationService,
                       MemberAllocationService memberAllocationService,
                       LoanAllocationService loanAllocationService) {
        this.bookAllocationService = bookAllocationService;
        this.genreAllocationService = genreAllocationService;
        this.memberAllocationService = memberAllocationService;
        this.loanAllocationService = loanAllocationService;
    }

    @Override
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookDTO) {
        try {
            if (bookDTO.getTitle().isEmpty() || bookDTO.getAuthor().isEmpty() || bookDTO.getIsbn().isEmpty()) {
                throw new InvalidInputException("Book", REQUIRED_FIELDS, "title, author, isbn");
            }

            BookResponseDTO addedBookDTO = bookAllocationService.addBook(bookDTO);
            return new ResponseEntity<>(addedBookDTO, HttpStatus.CREATED);
        } catch (AlreadyExistsException | GenreNotFoundException | InvalidInputException ex) {
            BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode(BAD_REQUEST);
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
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
            error.setMessage(ex.getMessage());
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
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> removeBook(@PathVariable("id") Long id) {
        try {
            bookAllocationService.removeBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException ex) {
            ErrorDTO error = new ErrorDTO();
            error.setCode("RESOURCE_NOT_FOUND");
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<PageResponseDTO> getAllBooks(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        PageResponseDTO pageResponseDTO = this.bookAllocationService.getAllBooks(page, size);
        return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageResponseDTO> searchBooks(@Valid @RequestBody BookSearchRequestDTO bookSearchRequestDTO) {
        return ResponseEntity.ok(bookAllocationService.searchBooks(bookSearchRequestDTO));
    }

    @Override
    public ResponseEntity<GenreResponseDTO> createGenre(@Valid @RequestBody GenreRequestDTO genreRequestDTO) {
        try {
            if (genreRequestDTO.getName().isEmpty()) {
                throw new InvalidInputException("Genre", REQUIRED_FIELDS, "name");
            }

            GenreResponseDTO addedGenreDTO = genreAllocationService.createGenre(genreRequestDTO);
            return new ResponseEntity<>(addedGenreDTO, HttpStatus.CREATED);
        } catch (InvalidInputException ex) {
            GenreResponseWithErrorsDTO errorResponseDTO = new GenreResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode(BAD_REQUEST);
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<GenreResponseDTO>> getAllGenres(){
        List<GenreResponseDTO> genreResponseDTO = this.genreAllocationService.getAllGenres();
        return new ResponseEntity<>(genreResponseDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MemberDTO> registerMember( @Valid @RequestBody MemberDTO memberDTO){
        try{
            if (memberDTO.getFirstName().isEmpty() ||
                   memberDTO.getLastName().isEmpty() ||
                   memberDTO.getEmail().isEmpty()) {
                throw new InvalidInputException("Member", REQUIRED_FIELDS, "firstName, lastName, email");
            }

            MemberDTO memberResponseDTO = memberAllocationService.registerMember(memberDTO);
            return new ResponseEntity<>(memberResponseDTO, HttpStatus.CREATED);
        }catch (InvalidInputException ex) {
            MemberResponseWithErrorsDTO errorResponseDTO = new MemberResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode(BAD_REQUEST);
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<MemberDTO>> getAllMembers(@Valid @RequestParam(required = false) String status) {
        List<MemberDTO> memberDTOs = memberAllocationService.getAllMembers(status);
        return new ResponseEntity<>(memberDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoanDTO> issueLoan(@Valid @RequestBody LoanDTO loanDTO) {
        try {
            if (loanDTO.getBookId() == null || loanDTO.getMemberId() == null) {
                throw new InvalidInputException("Loan", REQUIRED_FIELDS, "BookId, MemberId");
            }
            LoanDTO loanResponseDTO = loanAllocationService.issueLoan(loanDTO);
            return new ResponseEntity<>(loanResponseDTO, HttpStatus.CREATED);
        } catch (MemberNotFoundException| ResourceNotFoundException | BookOutOfStockException | UserSuspendedException | InvalidInputException ex) {
            LoanResponseWithErrorsDTO errorResponse = new LoanResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("INVALID_INPUT");
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponse.setError(error);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
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
            LoanResponseWithErrorsDTO errorResponse = new LoanResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("RESOURCE_NOT_FOUND");
            error.setMessage(ex.getMessage());
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponse.setError(error);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<LoanDTO>> getOverdueLoans(){
        List<LoanDTO> loanDTOs = loanAllocationService.getOverdueLoans();
        return new ResponseEntity<>(loanDTOs, HttpStatus.OK);
    }
}