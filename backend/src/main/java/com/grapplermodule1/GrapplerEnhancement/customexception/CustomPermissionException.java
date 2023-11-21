package com.grapplermodule1.GrapplerEnhancement.customexception;

public class CustomPermissionException extends RuntimeException{
    public CustomPermissionException(String message) {
        super(message);
    }
}
