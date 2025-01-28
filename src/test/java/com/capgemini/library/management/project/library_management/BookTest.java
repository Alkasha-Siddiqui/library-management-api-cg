package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.entity.Book;
import com.capgemini.library.management.project.library_management.entity.Genre;
import com.capgemini.library.management.project.library_management.entity.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;
    private List<Genre> genres;
    private Set<Loan> loans;

    @BeforeEach
    void setUp() {
        book = new Book();
        genres = new ArrayList<>();
        genres.add(new Genre());

        loans = new HashSet<>();
        loans.add(new Loan());
    }

    @Test
    void testGetTitle() {
        book.setTitle("Test Title");
        assertEquals("Test Title", book.getTitle());
    }

    @Test
    void testGetAuthor() {
        book.setAuthor("Test Author");
        assertEquals("Test Author", book.getAuthor());
    }

    @Test
    void testGetIsbn() {
        book.setIsbn("123-4567890123");
        assertEquals("123-4567890123", book.getIsbn());
    }

    @Test
    void testGetPublishYear() {
        book.setPublishYear(2023);
        assertEquals(2023, book.getPublishYear());
    }

    @Test
    void testGetGenreIds() {
        List<Long> genreIds = new ArrayList<>();
        genreIds.add(1L);
        book.setGenreIds(genreIds);
        assertEquals(genreIds, book.getGenreIds());
    }

    @Test
    void testGetGenres() {
        book.setGenres(genres);
        assertEquals(genres, book.getGenres());
    }

    @Test
    void testSetGenres() {
        book.setGenres(genres);
        assertEquals(genres, book.getGenres());
    }

    @Test
    void testGetLoans() {
        book.setLoans(loans);
        assertEquals(loans, book.getLoans());
    }

    @Test
    void testSetLoans() {
        book.setLoans(loans);
        assertEquals(loans, book.getLoans());
    }

    @Test
    void testGetAddedDateTime() {
        OffsetDateTime addedDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        book.setAddedDateTime(addedDateTime);
        assertEquals(addedDateTime, book.getAddedDateTime());
    }

    @Test
    void testGetUpdatedDateTime() {
        OffsetDateTime updatedDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        book.setUpdatedDateTime(updatedDateTime);
        assertEquals(updatedDateTime, book.getUpdatedDateTime());
    }
}