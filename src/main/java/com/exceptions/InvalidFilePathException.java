package com.exceptions;

@SuppressWarnings("serial")
public class InvalidFilePathException extends TestException {
    public InvalidFilePathException(String message) {
        super(message);
    }

    public InvalidFilePathException(String message, Throwable cause) {
        super(message, cause);
    }
}
