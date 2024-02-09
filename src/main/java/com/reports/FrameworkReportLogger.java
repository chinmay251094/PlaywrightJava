package com.reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.driver.DriverManager;
import com.enums.ConfigProperties;
import com.microsoft.playwright.Page;
import com.utils.SystemUtils;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.reports.FrameworkReportManager.getExtent;
import static com.reports.FrameworkReports.addScreenShot;
import static com.utils.PropertyUtils.get;

public final class FrameworkReportLogger {
    private FrameworkReportLogger() {
    }

    public static void pass(String message) {
        getExtent().pass(message);
    }

    public static void fail(String message) {
        getExtent().fail(message);
    }

    public static void skip(String message) {
        getExtent().skip(message);
    }

    public static void info(String message) {
        getExtent().info(message);
    }

    public static void pass(String message, String validationReason) {
        String combinedMessage = message + " - " + validationReason;
        getExtent().pass(combinedMessage);
    }

    public static void pass(String message, boolean isScreenshotNeeded) {
        if (get(ConfigProperties.PASSEDSCREENSHOT).equalsIgnoreCase("yes")
                && isScreenshotNeeded) {
            addScreenShot(message);
        } else {
            pass(message);
        }
    }

    public static void info(String message, boolean isScreenshotNeeded) {
        if (get(ConfigProperties.PASSEDSCREENSHOT).equalsIgnoreCase("yes")
                && isScreenshotNeeded) {
            addScreenShot(message);
        } else {
            info(message);
        }
    }

    public static void fail(String message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
            getExtent().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        } else {
            fail(message);
        }
    }

    public static void failWithDetails(String message, Throwable throwable) {
        ExtentTest test = getExtent();
        test.log(Status.FAIL, message);
        test.log(Status.FAIL, MarkupHelper.createLabel("Details:", ExtentColor.RED));
        test.log(Status.FAIL, throwable);
    }

    public static void skip(String message, boolean isScreenshotNeeded) {
        if (get(ConfigProperties.SKIPPPEDSCREENSHOT).equalsIgnoreCase("yes")
                && isScreenshotNeeded) {
            getExtent().skip(message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        } else {
            skip(message);
        }
    }

    public static String takeScreenshot() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String path = SystemUtils.getCurrentDir() + "media/Screenshots/" + File.separator + "ExtentReport_" + dateFormat.format(new Date()) + ".png";
        DriverManager.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(false));
        return path;
    }
}
