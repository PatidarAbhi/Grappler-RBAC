package com.grapplermodule1.GrapplerEnhancement.customexception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(String message) {
        super(message);
    }
}
