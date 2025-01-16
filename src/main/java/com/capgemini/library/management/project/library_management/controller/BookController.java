package com.capgemini.library.management.project.library_management.controller;

import com.capgemini.library.management.project.library_management.api.BooksApi;
import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.LibraryManagementException;
import com.capgemini.library.management.project.library_management.exception.ResourceNotFoundException;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.service.BookAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestController
public class BookController implements BooksApi {

    @Autowired
    BookAllocationService bookAllocationService;


//    @Override
//    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
//        try {
//            BookDTO addedBookDTO = bookAllocationService.addBook(bookDTO);
//            return new ResponseEntity<>(addedBookDTO, HttpStatus.CREATED);
//        } catch (LibraryManagementException ex) {
//            if (ex instanceof DuplicateISBNException) {
//                BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
//                ErrorDTO error = new ErrorDTO();
//                error.setCode("DUPLICATE_ISBN");
//                error.setMessage("ISBN already exists in the library");
//                error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
//                errorResponseDTO.setError(error);
//                return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
//            } else {
//
//                ex.printStackTrace();
//                BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
//                ErrorDTO error = new ErrorDTO();
//                error.setCode("INTERNAL_SERVER_ERROR"); // Set appropriate error code
//                error.setMessage("Internal server error occurred");
//                error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
//                errorResponseDTO.setError(error);
//                return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//    }

    @Override
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        try {
            BookDTO addedBookDTO = bookAllocationService.addBook(bookDTO);
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
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        try {
            BookDTO bookDTO = bookAllocationService.getBookById(id);
            return ResponseEntity.ok(bookDTO);
        } catch (ResourceNotFoundException ex) {
            BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
            ErrorDTO error = new ErrorDTO();
            error.setCode("RESOURCE_NOT_FOUND"); // Use appropriate error code
            error.setMessage("Book not found with ID: " + id);
            error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
            errorResponseDTO.setError(error);
            return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
        }
    }
}

    //    @Override
//    public ResponseEntity<PageResponseDTO> getAllBooks(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//
//        PageResponseDTO pageResponseDTO = (PageResponseDTO) bookAllocationService.getAllBooks(page, size);
//        return new ResponseEntity<>(pageResponseDTO.pageSize(), pageResponseDTO);
//    }
//        @Override
//    public ResponseEntity<PageResponseDTO<BookDTO>> getAllBooks(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        Page<Book> bookPage = bookAllocationService.getAllBooks(pageRequest);
//
//        List<BookDTO> responseList = bookPage.getContent().stream()
//                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishYear(), book.getGenreIds().stream().map(Long::valueOf).collect(Collectors.toList())))
//                .collect(Collectors.toList());
//
//        PageResponseDTO<BookDTO> pageResponse = new PageResponseDTO<>(
//                responseList, bookPage.getTotalPages(), bookPage.getTotalElements());
//
//        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
//    }

