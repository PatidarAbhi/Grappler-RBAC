package com.grapplermodule1.GrapplerEnhancement.customexception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
