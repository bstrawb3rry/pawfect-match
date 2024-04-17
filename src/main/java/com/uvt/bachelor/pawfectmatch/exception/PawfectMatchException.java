package com.uvt.bachelor.pawfectmatch.exception;

public class PawfectMatchException extends RuntimeException {

    private String errorCode;

    public PawfectMatchException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public PawfectMatchException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
