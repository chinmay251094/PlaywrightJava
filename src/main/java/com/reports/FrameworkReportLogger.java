package com.reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.driver.PlaywrightManager;
import com.enums.ConfigProperties;
import com.microsoft.playwright.Page;
import com.utils.SystemUtils;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.reports.FrameworkReportManager.getExtent;
import static com.utils.PropertyUtils.get;

public final class FrameworkReportLogger {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

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

    public static void pass(String message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
            addScreenshotWithCondition(message, ConfigProperties.PASSEDSCREENSHOT.toString());
        } else {
            pass(message);
        }
    }

    public static void info(String message, boolean isScreenshotNeeded) {
        if (isScreenshotNeeded) {
            addScreenshotWithCondition(message, ConfigProperties.PASSEDSCREENSHOT.toString());
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
        if (isScreenshotNeeded) {
            addScreenshotWithCondition(message, ConfigProperties.SKIPPPEDSCREENSHOT.toString());
        } else {
            skip(message);
        }
    }

    private static void addScreenshotWithCondition(String message, String screenshotProperty) {
        if (get(ConfigProperties.valueOf(screenshotProperty)).equalsIgnoreCase("yes")) {
            getExtent().pass(message, MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        } else {
            pass(message);
        }
    }

    public static String takeScreenshot() {
        synchronized (DATE_FORMAT) {
            String currentTime = DATE_FORMAT.format(new Date());
            String path = SystemUtils.getCurrentDir() + "media/Screenshots/ExtentReport_" + currentTime + ".png";
            PlaywrightManager.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(false));
            return path;
        }
    }
}
