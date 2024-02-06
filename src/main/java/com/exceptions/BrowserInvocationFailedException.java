package com.exceptions;

public class BrowserInvocationFailedException extends TestException {

    public BrowserInvocationFailedException(String message) {
        super(message);
    }

    public BrowserInvocationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
