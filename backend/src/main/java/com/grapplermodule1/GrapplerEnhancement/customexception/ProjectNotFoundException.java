package com.grapplermodule1.GrapplerEnhancement.customexception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String message)
    {
        super(message);
    }
}
