package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.capgemini.library.management.project.library_management.exception.BookOutOfStockException;
import org.junit.jupiter.api.Test;

class BookOutOfStockExceptionTest {

    @Test
    void testBookOutOfStockException() {
        Long bookId = 1L;
        String expectedMessage = String.format("Book is Out Of Stock with ID: %d", bookId);

        BookOutOfStockException exception = assertThrows(BookOutOfStockException.class, () -> {
            throw new BookOutOfStockException(bookId);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}