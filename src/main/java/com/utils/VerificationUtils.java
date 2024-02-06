package com.utils;

import org.testng.Assert;

public final class VerificationUtils {
    private VerificationUtils() {
    }

    public static void runAndVerifyMandatoryPass(Runnable action, String failureMessage) {
        try {
            action.run();
        } catch (Exception e) {
            // Log or print the exception details if needed
            e.printStackTrace();

            // Fail the test explicitly using TestNG or JUnit
            Assert.fail(failureMessage);
        }
    }

    public static void validate(Object actual, Object expected, String message) {
        try {
            logFile(message);
            logFile(actual, expected);
            Assert.assertEquals(actual, expected, message);
        } catch (AssertionError assertionError) {
            Assert.fail(message);
        }
    }

    private static void logFile(Object actual, Object expected) {
        LogUtils.info("Actual: " + actual);
        LogUtils.info("Expected: " + expected);
    }

    private static void logFile(String message) {
        LogUtils.info(message);
    }
}
