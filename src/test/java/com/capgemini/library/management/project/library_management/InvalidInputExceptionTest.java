package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.library.management.project.library_management.exception.InvalidInputException;
import org.junit.jupiter.api.Test;

public class InvalidInputExceptionTest {

    @Test
    void testInvalidInputException() {
        String resourceName = "Book";
        String fieldName = "title";
        String fieldValue = "null";

        InvalidInputException exception = new InvalidInputException(resourceName, fieldName, fieldValue);

        // Test the exception message
        String expectedMessage = String.format("%s not valid with %s : '%s'", resourceName, fieldName, fieldValue);
        assertEquals(expectedMessage, exception.getMessage());

        // Test the getters
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(fieldValue, exception.getFieldValue());
    }
}