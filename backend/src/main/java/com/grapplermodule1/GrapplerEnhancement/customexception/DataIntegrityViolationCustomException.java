package com.grapplermodule1.GrapplerEnhancement.customexception;

public class DataIntegrityViolationCustomException extends RuntimeException{
    public DataIntegrityViolationCustomException(String message) {
        super(message);
    }
}
