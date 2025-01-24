package com.capgemini.library.management.project.library_management.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super(String.format("Book not found with ID: %d", id));
    }
}
