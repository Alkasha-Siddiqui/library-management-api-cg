package com.capgemini.library.management.project.library_management.model;

public class LoanResponseWithErrorsDTO extends LoanDTO{

    private ErrorDTO error;

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }
}