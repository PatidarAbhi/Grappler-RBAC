package com.grapplermodule1.GrapplerEnhancement.customexception;

public class CustomResponseMessage {
    private boolean status;
    private String message;

    public CustomResponseMessage() {
    }

    public CustomResponseMessage(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
