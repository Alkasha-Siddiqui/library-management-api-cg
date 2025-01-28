package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.library.management.project.library_management.model.ErrorDTO;
import com.capgemini.library.management.project.library_management.model.GenreResponseWithErrorsDTO;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class GenreResponseWithErrorsDTOTest {

    @Test
    void testGenreResponseWithErrorsDTO() {
        GenreResponseWithErrorsDTO genreResponse1 = new GenreResponseWithErrorsDTO();
        GenreResponseWithErrorsDTO genreResponse2 = new GenreResponseWithErrorsDTO();
        GenreResponseWithErrorsDTO genreResponse3 = new GenreResponseWithErrorsDTO();

        genreResponse1.setId(1L);
        genreResponse1.setName("Fantasy");
        genreResponse1.setDescription("Fantasy genre");

        ErrorDTO error1 = new ErrorDTO();
        error1.setCode("GENRE_NOT_FOUND");
        error1.setMessage("Genre not found");
        error1.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        genreResponse1.setError(error1);

        genreResponse2.setId(1L);
        genreResponse2.setName("Fantasy");
        genreResponse2.setDescription("Fantasy genre");

        ErrorDTO error2 = new ErrorDTO();
        error2.setCode("GENRE_NOT_FOUND");
        error2.setMessage("Genre not found");
        error2.setTimestamp(error1.getTimestamp());
        genreResponse2.setError(error2);

        genreResponse3.setId(2L);
        genreResponse3.setName("Science Fiction");
        genreResponse3.setDescription("Sci-Fi genre");

        ErrorDTO error3 = new ErrorDTO();
        error3.setCode("GENRE_NOT_FOUND");
        error3.setMessage("Genre not found");
        error3.setTimestamp(OffsetDateTime.now(ZoneOffset.UTC));
        genreResponse3.setError(error3);

        assertEquals(genreResponse1, genreResponse2);

        assertNotEquals(genreResponse1, genreResponse3);
        assertNotEquals(genreResponse2, genreResponse3);

        assertEquals(genreResponse1.hashCode(), genreResponse2.hashCode());
        assertNotEquals(genreResponse1.hashCode(), genreResponse3.hashCode());

        assertEquals(1L, genreResponse1.getId());
        assertEquals("Fantasy", genreResponse1.getName());
        assertEquals("Fantasy genre", genreResponse1.getDescription());
        assertEquals(error1, genreResponse1.getError());

        genreResponse1.setName("Science Fiction");
        assertEquals("Science Fiction", genreResponse1.getName());

        assertNotEquals(genreResponse1, null);
        assertNotEquals(genreResponse1, new Object());
    }
}