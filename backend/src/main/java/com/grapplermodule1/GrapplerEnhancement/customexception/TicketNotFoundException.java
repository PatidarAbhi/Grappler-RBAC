package com.grapplermodule1.GrapplerEnhancement.customexception;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String message) {
        super(message);
    }
}
