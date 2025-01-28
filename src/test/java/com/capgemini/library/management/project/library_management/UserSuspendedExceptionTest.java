package com.capgemini.library.management.project.library_management;

import com.capgemini.library.management.project.library_management.exception.UserSuspendedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSuspendedExceptionTest {

    @Test
    void testUserSuspendedException() {
        Long userId = 123L;
        UserSuspendedException exception = new UserSuspendedException(userId);

        assertNotNull(exception);
        assertEquals("User is suspended with ID: 123", exception.getMessage());
    }
}