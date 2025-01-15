package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.model.BookDTO;
import com.capgemini.library.management.project.library_management.model.BookDTO;

import java.util.List;

public interface BookAllocationService {
    Integer addBook(BookDTO bookDTO) throws DuplicateISBNException;

//    public List<Book> getAllBooks(int page, int size);

    public BookDTO getBookById(Integer id) throws DuplicateISBNException;
}
