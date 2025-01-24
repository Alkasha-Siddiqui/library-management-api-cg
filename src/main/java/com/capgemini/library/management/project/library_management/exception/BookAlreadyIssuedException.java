package com.capgemini.library.management.project.library_management.exception;

public class BookAlreadyIssuedException extends RuntimeException {
    public BookAlreadyIssuedException(Long id) {
        super(String.format("Book already issued with ID: %d", id));
    }
}