package com.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.constants.FrameworkConstants;
import com.driver.PlaywrightManager;
import com.enums.TestCategory;
import com.enums.Tester;
import com.microsoft.playwright.Page;
import com.utils.EnvironmentUtils;
import com.utils.SystemUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static com.utils.EnvironmentUtils.isIsHeadlessMode;

public final class FrameworkReports {
    private static ExtentReports extentReports;

    private FrameworkReports() {
    }

    public static void initReports() {
        if (extentReports == null) {
            extentReports = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportFilepath());
            extentReports.attachReporter(spark);
            configureSparkReporter(spark);
        }
    }

    private static void configureSparkReporter(ExtentSparkReporter spark) {
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Test Automation Reports");
        spark.config().setReportName("Playwright Reports");
    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
        FrameworkReportManager.unload();
        openReportInBrowser();
    }

    private static void openReportInBrowser() {
        try {
            Desktop.getDesktop().browse(new File(FrameworkConstants.getExtentReportFilepath()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testcaseName) {
        FrameworkReportManager.setExtent(extentReports.createTest(testcaseName));
    }

    public static void addAuthors(Tester[] authors) {
        for (Tester tester : authors) {
            FrameworkReportManager.getExtent().assignAuthor(tester.toString());
        }
    }

    public static void addCategory(TestCategory[] categories) {
        for (TestCategory category : categories) {
            FrameworkReportManager.getExtent().assignCategory(category.toString());
        }
    }

    public static void setBrowserInfo() {
        FrameworkReportManager.getExtent().assignDevice(EnvironmentUtils.getBrowser() + EnvironmentUtils.getVersion());
    }

    public static void setExecutionEnvironmentInfo() {
        String osName = System.getProperty("os.name").replace(" ", "_");
        String browserIcon = EnvironmentUtils.getBrowserIcon(true);
        String infoMessage = "<font color='blue'><b>" + "\uD83D\uDCBB " + osName + "</b></font> --------- " + browserIcon;
        FrameworkReportManager.getExtent().info(infoMessage);
    }

    public static void setExecutionModeInfo() {
        String executionModeMessage = "<font color='blue'><b>" + isIsHeadlessMode() + "</b></font>";
        FrameworkReportManager.getExtent().info(executionModeMessage);
    }

    public static void setUrlInfo() {
        String urlMessage = "<font color='blue'><b>" + "\uD83C\uDF10 " + EnvironmentUtils.getUrl() + "</b></font>";
        FrameworkReportManager.getExtent().info(urlMessage);
    }

    public static void addScreenShot(String message) {
        String encodedScreenshot = takeScreenshotAndEncode();
        FrameworkReportManager.getExtent().log(Status.FAIL, message, MediaEntityBuilder.createScreenCaptureFromBase64String(encodedScreenshot).build());
    }

    private static String takeScreenshotAndEncode() {
        String screenshotPath = takeScreenshot();
        return encodeToBase64(screenshotPath);
    }

    private static String takeScreenshot() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        String path = SystemUtils.getCurrentDir() + "media/Screenshots/" + File.separator + "ExtentReport_" + dateFormat.format(new Date()) + ".png";
        PlaywrightManager.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(false));
        return path;
    }

    private static String encodeToBase64(String originalString) {
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
}
