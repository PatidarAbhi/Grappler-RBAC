package com.grapplermodule1.GrapplerEnhancement.customexception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
