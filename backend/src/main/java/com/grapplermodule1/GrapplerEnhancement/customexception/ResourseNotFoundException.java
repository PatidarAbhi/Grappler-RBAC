package com.grapplermodule1.GrapplerEnhancement.customexception;

public class ResourseNotFoundException extends RuntimeException{
    public ResourseNotFoundException(String message) {
        super(message);
    }
}
