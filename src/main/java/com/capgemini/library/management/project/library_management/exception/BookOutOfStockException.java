package com.capgemini.library.management.project.library_management.exception;

public class BookOutOfStockException extends RuntimeException {
    public BookOutOfStockException(Long id) {
        super(String.format("Book is Out Of Stock with ID: %d", id));
    }
}