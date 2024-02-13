package com.listeners;

import com.enums.ConfigProperties;
import com.utils.PropertyUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTests implements IRetryAnalyzer {

    private int count = 0;

    public boolean retry(ITestResult result) {
        boolean value = false;
        try {
            if (PropertyUtils.get(ConfigProperties.RETRYFAILEDTEST).equalsIgnoreCase("yes")) {
                int retries = 1;
                value = count < retries;
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
