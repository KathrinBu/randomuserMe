package com.example.randomuser.exception;

public class UserExportException extends RuntimeException {
    public UserExportException(String message) {
        super(message);
    }

    public UserExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
