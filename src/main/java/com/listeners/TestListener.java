package com.listeners;

import com.annotations.TestInformation;
import com.enums.ConfigProperties;
import com.exceptions.BrowserInvocationFailedException;
import com.exceptions.TestException;
import com.exceptions.TestSkipException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.utils.CaptureUtils;
import com.utils.LogUtils;
import com.utils.PropertyUtils;
import org.testng.*;

import java.time.Duration;

import static com.reports.FrameworkReportLogger.*;
import static com.reports.FrameworkReports.*;

public class TestListener implements ITestListener, ISuiteListener {
    public static void sleep() {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(1));
    }

    @Override
    public void onStart(ISuite suite) {
        initReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        flushReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("*********" + result.getName() + "*********");
        String testDescription = result.getMethod().getDescription();
        createTest(testDescription);
        TestInformation testInfo = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestInformation.class);
        addAuthors(testInfo.author());
        addCategory(testInfo.category());
        setBrowserInfo();
        setExecutionEnvironmentInfo();
        setExecutionModeInfo();
        setUrlInfo();
        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep();
            CaptureUtils.startRecord(testDescription);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String description = result.getMethod().getDescription();
        pass("<span style='background-color: #008000; color: white;'><b>" + description + " has passed successfully. 😊</b></span>");
        if (PropertyUtils.get(ConfigProperties.PASSEDSCREENSHOT).equals("yes")) {
            CaptureUtils.takeScreenshot(result.getName());
        }
        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep();
            CaptureUtils.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String description = result.getMethod().getDescription();
        String throwableMessage = result.getThrowable().getMessage();
        if (result.getThrowable() instanceof TestException || result.getThrowable() instanceof BrowserInvocationFailedException) {
            fail("<span style='background-color: red; color: white;'><b>" + throwableMessage + " 😔</b></span>", true);
        } else {
            fail("<span style='background-color: red; color: white;'><b>" + description + " has failed the test. 😔</span></b>", true);
            failWithDetails("<b><font color='red'> Reasons for test failure stated below</font></b>", result.getThrowable());
        }
        if (PropertyUtils.get(ConfigProperties.FAILEDSCREENSHOT).equals("yes")) {
            CaptureUtils.takeScreenshot(description);
        }
        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep();
            CaptureUtils.stopRecord();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getStatus() == ITestResult.SKIP) {
            String message = result.getThrowable().getMessage();
            if (result.getThrowable() instanceof TestSkipException) {
                skip("<span style='background-color: #FF8C00; color: white;'><b>" + message + "</b></span> 👋", true);
            } else {
                skip("<span style='color: #FF8C00'><b>" + result.getMethod().getDescription() + " has been skipped.</b></span>", true);
            }
        }
        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep();
            CaptureUtils.stopRecord();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        /* Not in use for now */

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        /* Not in use for now */

    }

    @Override
    public void onStart(ITestContext context) {
        /* Not in use for now */

    }

    @Override
    public void onFinish(ITestContext context) {
        /* Not in use for now */

    }
}
