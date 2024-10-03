package com.naveen.rentread.exception;

public class ValidationException extends RuntimeException {

    // Constructor with a message only
    public ValidationException(String message) {
        super(message);
    }

    // Constructor with a message and a cause
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Optionally, add other constructors for more flexibility if needed
}
