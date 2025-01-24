package com.capgemini.library.management.project.library_management.exception;

public class UserSuspendedException extends RuntimeException {
    public UserSuspendedException(Long id) {
        super(String.format("User is suspended with ID: %d", id));
    }
}