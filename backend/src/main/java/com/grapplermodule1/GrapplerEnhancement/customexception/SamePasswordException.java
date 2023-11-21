package com.grapplermodule1.GrapplerEnhancement.customexception;

public class SamePasswordException extends RuntimeException{
    public SamePasswordException(String message) {
        super(message);
    }
}
