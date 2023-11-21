package com.grapplermodule1.GrapplerEnhancement.customexception;

public class MemberNotPresentInTeamException extends RuntimeException{
    public MemberNotPresentInTeamException(String message) {
        super(message);
    }
}
