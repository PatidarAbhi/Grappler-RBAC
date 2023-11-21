package com.grapplermodule1.GrapplerEnhancement.customexception;

public class TeamAlreadyPresentInTheProjectException extends RuntimeException{
    public TeamAlreadyPresentInTheProjectException(String message) {
        super(message);
    }
}
