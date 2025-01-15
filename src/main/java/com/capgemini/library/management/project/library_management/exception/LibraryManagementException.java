package com.capgemini.library.management.project.library_management.exception;

import java.io.Serial;

public class LibraryManagementException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public LibraryManagementException(String message) {
        super(message);

    }
}
