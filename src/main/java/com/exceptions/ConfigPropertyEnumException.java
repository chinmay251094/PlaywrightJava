package com.exceptions;

@SuppressWarnings("serial")
public class ConfigPropertyEnumException extends TestException {
    public ConfigPropertyEnumException(String message) {
        super(message);
    }

    public ConfigPropertyEnumException(String message, Throwable cause) {
        super(message, cause);
    }
}
