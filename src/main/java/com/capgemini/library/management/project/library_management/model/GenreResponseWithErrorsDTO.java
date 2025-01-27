package com.capgemini.library.management.project.library_management.model;

import java.util.Objects;

public class GenreResponseWithErrorsDTO extends GenreResponseDTO{

    private ErrorDTO error;

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GenreResponseWithErrorsDTO that = (GenreResponseWithErrorsDTO) o;
        return Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), error);
    }
}