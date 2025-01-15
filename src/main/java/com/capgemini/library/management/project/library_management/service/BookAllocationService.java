package com.capgemini.library.management.project.library_management.service;

import com.capgemini.library.management.project.library_management.exception.DuplicateISBNException;
import com.capgemini.library.management.project.library_management.model.BookRequestDTO;
import com.capgemini.library.management.project.library_management.model.BookResponseDTO;

import java.util.List;

public interface BookAllocationService {
    Integer addBook(BookRequestDTO bookRequestDTO) throws DuplicateISBNException;

}
