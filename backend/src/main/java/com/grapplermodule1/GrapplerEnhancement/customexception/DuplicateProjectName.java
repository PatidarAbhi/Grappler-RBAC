package com.grapplermodule1.GrapplerEnhancement.customexception;

public class DuplicateProjectName extends RuntimeException{
    public DuplicateProjectName(String message)
    {
        super(message);
    }
}
