package com.capgemini.library.management.project.library_management;


import com.capgemini.library.management.project.library_management.exception.LoanAlreadyReturnedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanAlreadyReturnedExceptionTest {

    @Test
    void testLoanAlreadyReturnedException() {
        String resourceName = "Loan";
        String fieldName = "id";
        Long fieldValue = 123L;
        LoanAlreadyReturnedException exception = new LoanAlreadyReturnedException(resourceName, fieldName, fieldValue);

        assertNotNull(exception);
        assertEquals("Loan is already returned with id : '123'", exception.getMessage());
    }
}