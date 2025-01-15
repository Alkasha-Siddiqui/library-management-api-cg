package com.capgemini.library.management.project.library_management.controller;

import com.capgemini.library.management.project.library_management.api.BooksApi;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.exception.LibraryManagementException;
import com.capgemini.library.management.project.library_management.model.*;
import com.capgemini.library.management.project.library_management.service.BookAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
//        Integer bookId = bookAllocationService.addBook(bookRequestDTO);
//        BookResponseDTO responseDTO = new BookResponseDTO();
//        responseDTO.setId(bookId);
//        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) throws LibraryManagementException {
        try {
            Integer bookId = bookAllocationService.addBook(bookRequestDTO);
            BookResponseDTO responseDTO = new BookResponseDTO();

            responseDTO.setId(bookId);
            responseDTO.setTitle(bookRequestDTO.getTitle());
            responseDTO.setAuthor(bookRequestDTO.getAuthor());
            responseDTO.setIsbn(bookRequestDTO.getIsbn());
            responseDTO.setPublishYear(bookRequestDTO.getPublishYear());
            responseDTO.setGenreIds(bookRequestDTO.getGenreIds());
            responseDTO.setAddedDate(OffsetDateTime.now(ZoneOffset.UTC));
            responseDTO.setUpdatedDate(OffsetDateTime.now(ZoneOffset.UTC));

            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (LibraryManagementException ex) {
            if (ex instanceof DuplicateISBNException) {
                BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
                ErrorDTO error = new ErrorDTO();
                error.setCode("DUPLICATE_ISBN");
                error.setMessage("ISBN already exists in the library");
                error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
                errorResponseDTO.setError(error);
                return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
            } else {
                // Handle other LibraryManagementException types
                // You can log the exception for further analysis
                ex.printStackTrace();
                BookResponseWithErrorsDTO errorResponseDTO = new BookResponseWithErrorsDTO();
                ErrorDTO error = new ErrorDTO();
                error.setCode("INTERNAL_SERVER_ERROR"); // Set appropriate error code
                error.setMessage("Internal server error occurred");
                error.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
                errorResponseDTO.setError(error);
                return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

//        @Override
//    public ResponseEntity<PageResponseDTO<BookResponseDTO>> getAllBooks(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer size) {
//
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        Page<Book> bookPage = bookAllocationService.getAllBooks(pageRequest);
//
//        List<BookResponseDTO> responseList = bookPage.getContent().stream()
//                .map(book -> new BookResponseDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishYear(), book.getGenreIds().stream().map(Long::valueOf).collect(Collectors.toList())))
//                .collect(Collectors.toList());
//
//        PageResponseDTO<BookResponseDTO> pageResponse = new PageResponseDTO<>(
//                responseList, bookPage.getTotalPages(), bookPage.getTotalElements());
//
//        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
//    }

//    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
//        List<BookResponseDTO> BookDTOs = bookAllocationService.getAllBooks();
//        return new ResponseEntity<>(BookDTOs, HttpStatus.OK);
//    }
}
