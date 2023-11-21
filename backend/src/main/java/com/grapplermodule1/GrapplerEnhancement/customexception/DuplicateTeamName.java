package com.grapplermodule1.GrapplerEnhancement.customexception;

public class DuplicateTeamName extends RuntimeException{
    public DuplicateTeamName(String message) {
        super(message);
    }
}
