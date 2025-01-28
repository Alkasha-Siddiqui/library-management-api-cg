package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.BookCopies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookCopiesTest {

    private BookCopies bookCopies;
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");

        bookCopies = new BookCopies();
        bookCopies.setId(1L);
        bookCopies.setBook(book);
        bookCopies.setTotalCopies(5);
        bookCopies.setAvailableCopies(3);
    }

    @Test
    void testGetId() {
        assertEquals(1L, bookCopies.getId());
    }

    @Test
    void testSetId() {
        bookCopies.setId(2L);
        assertEquals(2L, bookCopies.getId());
    }

    @Test
    void testGetBook() {
        assertEquals(book, bookCopies.getBook());
    }

    @Test
    void testSetBook() {
        Book newBook = new Book();
        newBook.setId(2L);
        newBook.setTitle("New Test Book");

        bookCopies.setBook(newBook);
        assertEquals(newBook, bookCopies.getBook());
    }

    @Test
    void testGetTotalCopies() {
        assertEquals(5, bookCopies.getTotalCopies());
    }

    @Test
    void testSetTotalCopies() {
        bookCopies.setTotalCopies(10);
        assertEquals(10, bookCopies.getTotalCopies());
    }

    @Test
    void testGetAvailableCopies() {
        assertEquals(3, bookCopies.getAvailableCopies());
    }

    @Test
    void testSetAvailableCopies() {
        bookCopies.setAvailableCopies(7);
        assertEquals(7, bookCopies.getAvailableCopies());
    }
}