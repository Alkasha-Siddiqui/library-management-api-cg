package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.model.BookResponseWithErrorsDTO;
import com.capgemini.library.management.project.library_management.model.ErrorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookResponseWithErrorsDTOTest {

    private BookResponseWithErrorsDTO bookResponseWithErrorsDTO;
    private ErrorDTO errorDTO;

    @BeforeEach
    void setUp() {
        bookResponseWithErrorsDTO = new BookResponseWithErrorsDTO();
        errorDTO = new ErrorDTO();
        errorDTO.setCode("404");
        errorDTO.setMessage("Not Found");
        bookResponseWithErrorsDTO.setError(errorDTO);
    }

    @Test
    void testGetError() {
        assertEquals(errorDTO, bookResponseWithErrorsDTO.getError());
    }

    @Test
    void testSetError() {
        ErrorDTO newErrorDTO = new ErrorDTO();
        newErrorDTO.setCode("500");
        newErrorDTO.setMessage("Internal Server Error");
        bookResponseWithErrorsDTO.setError(newErrorDTO);
        assertEquals(newErrorDTO, bookResponseWithErrorsDTO.getError());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(bookResponseWithErrorsDTO, bookResponseWithErrorsDTO);
    }

    @Test
    void testEqualsNullObject() {
        assertNotEquals(null, bookResponseWithErrorsDTO);
    }


    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(bookResponseWithErrorsDTO, new Object());
    }

    @Test
    void testEqualsDifferentError() {
        BookResponseWithErrorsDTO anotherDTO = new BookResponseWithErrorsDTO();
        ErrorDTO differentErrorDTO = new ErrorDTO();
        differentErrorDTO.setCode("400");
        differentErrorDTO.setMessage("Bad Request");
        anotherDTO.setError(differentErrorDTO);
        assertNotEquals(anotherDTO, bookResponseWithErrorsDTO);
    }

    @Test
    void testEqualsSameError() {
        BookResponseWithErrorsDTO anotherDTO = new BookResponseWithErrorsDTO();
        anotherDTO.setError(errorDTO);
        assertEquals(bookResponseWithErrorsDTO, anotherDTO);
    }

    @Test
    void testEqualsSuperNotEqual() {
        BookResponseWithErrorsDTO anotherDTO = new BookResponseWithErrorsDTO();
        anotherDTO.setError(errorDTO);
        anotherDTO.setId(2L);
        assertNotEquals( anotherDTO,bookResponseWithErrorsDTO);
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        BookResponseWithErrorsDTO dto1 = new BookResponseWithErrorsDTO();
        dto1.setError(new ErrorDTO());

        // Test with null
        assertFalse(dto1.equals(null));

        // Test with different class
        assertFalse(dto1.equals(new Object()));
    }

    @Test
    void testHashCode() {
        BookResponseWithErrorsDTO anotherDTO = new BookResponseWithErrorsDTO();
        anotherDTO.setError(errorDTO);
        assertEquals(bookResponseWithErrorsDTO.hashCode(), anotherDTO.hashCode());
    }
}