package com.exceptions;

import org.testng.SkipException;

public class TestSkipException extends SkipException {
    public TestSkipException(String message) {
        super(message);
    }
}

