package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.model.BookDTO;

public interface BookAllocationService {
    BookDTO addBook(BookDTO bookDTO) throws DuplicateISBNException;

    BookDTO getBookById(Long id);

//    public List<Book> getAllBooks(int page, int size);
}
