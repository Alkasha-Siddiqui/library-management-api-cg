package com.capgemini.library.management.project.library_management.model;

public class SuccessBookResponse extends BookResponseWithErrorsDTO {

    public SuccessBookResponse(Integer bookId) {
        this.setId(Long.valueOf(bookId));
    }
}
