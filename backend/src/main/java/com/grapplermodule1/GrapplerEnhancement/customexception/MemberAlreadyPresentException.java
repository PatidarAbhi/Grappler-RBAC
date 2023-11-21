package com.grapplermodule1.GrapplerEnhancement.customexception;

public class MemberAlreadyPresentException extends RuntimeException{
    public MemberAlreadyPresentException(String message) {
        super(message);
    }
}
