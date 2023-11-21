package com.grapplermodule1.GrapplerEnhancement.customexception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
