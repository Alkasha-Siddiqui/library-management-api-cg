package com.capgemini.library.management.project.library_management.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException(String resourceName, String fieldName, Long fieldValue) {
        super(String.format("%s is already returned with %s : '%s'", resourceName, fieldName, fieldValue));
    }
}