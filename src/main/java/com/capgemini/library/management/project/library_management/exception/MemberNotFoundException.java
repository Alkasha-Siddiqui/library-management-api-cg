package com.capgemini.library.management.project.library_management.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long id) {
        super(String.format("Member not found with ID: %d", id));
    }
}