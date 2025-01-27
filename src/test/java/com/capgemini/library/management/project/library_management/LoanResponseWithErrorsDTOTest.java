package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.capgemini.library.management.project.library_management.model.ErrorDTO;
import com.capgemini.library.management.project.library_management.model.LoanResponseWithErrorsDTO;
import org.junit.jupiter.api.Test;

 class LoanResponseWithErrorsDTOTest {

    @Test
    void testGetError() {
        LoanResponseWithErrorsDTO dto = new LoanResponseWithErrorsDTO();
        ErrorDTO error = new ErrorDTO();
        error.setCode("ERROR_CODE");
        error.setMessage("Error message");

        dto.setError(error);

        assertEquals(error, dto.getError());
    }

    @Test
    void testSetError() {
        LoanResponseWithErrorsDTO dto = new LoanResponseWithErrorsDTO();
        ErrorDTO error = new ErrorDTO();
        error.setCode("ERROR_CODE");
        error.setMessage("Error message");

        dto.setError(error);

        assertEquals("ERROR_CODE", dto.getError().getCode());
        assertEquals("Error message", dto.getError().getMessage());
    }

}