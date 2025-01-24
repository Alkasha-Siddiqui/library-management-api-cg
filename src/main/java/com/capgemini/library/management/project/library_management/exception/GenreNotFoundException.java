package com.capgemini.library.management.project.library_management.exception;

import java.util.List;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(List<Long> genreIds) {
        super(String.format("Genre not found with ids: %s", genreIds));
    }
}