package com.grapplermodule1.GrapplerEnhancement.customexception;

public class PasswordNotMatchException extends RuntimeException{
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
