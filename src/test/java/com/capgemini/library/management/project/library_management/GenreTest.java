package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    private Genre genre;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        books = new ArrayList<>();
        books.add(new Book());
    }

    @Test
    void testGetName() {
        genre.setName("Fiction");
        assertEquals("Fiction", genre.getName());
    }

    @Test
    void testGetDescription() {
        genre.setDescription("A genre of speculative fiction");
        assertEquals("A genre of speculative fiction", genre.getDescription());
    }

    @Test
    void testSetDescription() {
        genre.setDescription("A genre of speculative fiction");
        assertEquals("A genre of speculative fiction", genre.getDescription());
    }

    @Test
    void testGetBooks() {
        genre.setBooks(books);
        assertEquals(books, genre.getBooks());
    }

    @Test
    void testSetBooks() {
        genre.setBooks(books);
        assertEquals(books, genre.getBooks());
    }
}