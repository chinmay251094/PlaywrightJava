package com.listeners;

import com.annotations.TestInformation;
import com.enums.ConfigProperties;
import com.exceptions.BrowserInvocationFailedException;
import com.exceptions.TestException;
import com.exceptions.TestSkipException;
import com.utils.CaptureUtils;
import com.utils.LogUtils;
import com.utils.PropertyUtils;
import org.testng.*;

import static com.reports.FrameworkReportLogger.*;
import static com.reports.FrameworkReports.*;

public class TestListener implements ITestListener, ISuiteListener {
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStart(ISuite suite) {
        try {
            initReports();
        } catch (Exception e) {
            throw new TestException("Problem with reports creation.", e);
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            flushReports();
        } catch (Exception e) {
            throw new TestException("Problem with generating reports and mailing them.");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("*********" + result.getName() + "*********");
        String testDescription = result.getMethod().getDescription();
        createTest(testDescription);
        addAuthors(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestInformation.class).author());
        addCategory(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(TestInformation.class).category());
        setBrowserInfo();
        setExecutionEnvironmentInfo();
        setUrlInfo();
        setExecutionModeInfo();
        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep(0.5);
            CaptureUtils.startRecord(testDescription);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        pass("<span style='background-color: #008000; color: white;'><b>" + result.getMethod().getDescription() + " has passed successfully. 😊</b></span>");
        if (PropertyUtils.get(ConfigProperties.PASSEDSCREENSHOT).equals("yes")) {
            CaptureUtils.takeScreenshot(result.getName());
        }

        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep(0.5);
            CaptureUtils.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            if (result.getThrowable() instanceof TestException || result.getThrowable() instanceof BrowserInvocationFailedException) {
                // Handle the FrameworkException differently
                String message = result.getThrowable().getMessage();
                fail("<span style='background-color: red; color: white;'><b>" + message + " 😔</b></span>", true);
            } else {
                fail("<span style='background-color: red; color: white;'><b>"
                        + result.getMethod().getDescription() + " has failed the test. 😔</span></b>", true);
                failWithDetails("<b><font color='red'> Reasons for test failure stated below</font></b>", result.getThrowable());
            }

            if (PropertyUtils.get(ConfigProperties.FAILEDSCREENSHOT).equals("yes")) {
                CaptureUtils.takeScreenshot(result.getMethod().getDescription());
            }

            if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
                sleep(0.5);
                CaptureUtils.stopRecord();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getStatus() == ITestResult.SKIP) {
            if (result.getThrowable() instanceof TestSkipException) {
                String message = result.getThrowable().getMessage();
                skip("<span style='background-color: #FF8C00; color: white;'><b>" + message + "</b></span> 👋", true);

            } else {
                skip("<span style='color: #FF8C00'><b>" + result.getMethod().getDescription() + " has been skipped.</b></span>", true);

            }
        }
        if (PropertyUtils.get(ConfigProperties.VIDEO_RECORD).equals("yes")) {
            sleep(0.5);
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
