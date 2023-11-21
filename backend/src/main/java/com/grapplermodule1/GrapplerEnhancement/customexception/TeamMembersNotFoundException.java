package com.grapplermodule1.GrapplerEnhancement.customexception;

public class TeamMembersNotFoundException extends RuntimeException{
    public TeamMembersNotFoundException(String message) {
        super(message);
    }
}
