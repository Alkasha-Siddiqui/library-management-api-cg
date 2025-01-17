package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.model.BookDTO;
import com.capgemini.library.management.project.library_management.model.PageResponseDTO;

public interface BookAllocationService {
    BookDTO addBook(BookDTO bookDTO) throws DuplicateISBNException;

    BookDTO getBookById(Long id);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void removeBook(Long id);

    PageResponseDTO getAllBooks(Integer page, Integer size);
}
