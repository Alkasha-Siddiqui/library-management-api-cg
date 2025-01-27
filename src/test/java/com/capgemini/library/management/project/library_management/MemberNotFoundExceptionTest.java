package com.capgemini.library.management.project.library_management;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.capgemini.library.management.project.library_management.exception.MemberNotFoundException;
import org.junit.jupiter.api.Test;

class MemberNotFoundExceptionTest {

    @Test
    void testMemberNotFoundException() {
        Long memberId = 1L;
        String expectedMessage = String.format("Member not found with ID: %d", memberId);

        MemberNotFoundException exception = assertThrows(MemberNotFoundException.class, () -> {
            throw new MemberNotFoundException(memberId);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }
}