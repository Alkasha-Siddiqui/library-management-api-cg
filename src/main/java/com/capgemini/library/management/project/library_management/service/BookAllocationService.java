package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.model.BookRequestDTO;
import com.capgemini.library.management.project.library_management.model.BookResponseDTO;
import com.capgemini.library.management.project.library_management.model.PageResponseDTO;

public interface BookAllocationService {
    BookResponseDTO addBook(BookRequestDTO bookResquestDTO) throws DuplicateISBNException;

    BookResponseDTO getBookById(Long id);

    BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO);

    void removeBook(Long id);

    PageResponseDTO getAllBooks(Integer page, Integer size);
}
