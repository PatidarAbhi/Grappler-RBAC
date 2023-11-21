package com.grapplermodule1.GrapplerEnhancement.customexception;

public class DataNotPresent extends RuntimeException{
    public DataNotPresent(String message)
    {
        super(message);
    }
}
