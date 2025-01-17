package com.capgemini.library.management.project.library_management.controller;

import com.capgemini.library.management.project.library_management.api.BooksApi;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.service.BookAllocationService;
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

@Slf4j
@RestController
public class BookController implements BooksApi {

    @Autowired
    BookAllocationService bookAllocationService;

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
}