package com.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.driver.DriverManager;
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
import java.util.Objects;

import static com.constants.FrameworkConstants.getExtentReportFilepath;
import static com.utils.EnvironmentUtils.getUrl;
import static com.utils.EnvironmentUtils.isIsHeadlessMode;

public final class FrameworkReports {
    private static ExtentReports extentReports;

    private FrameworkReports() {
    }

    public static void initReports() {
        if (Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(getExtentReportFilepath());
            extentReports.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Test Automation Reports");
            spark.config().setReportName("Playwright Reports");
        }
    }

    public static void flushReports() {
        if (Objects.nonNull(extentReports)) {
            extentReports.flush();
        }
        FrameworkReportManager.unload();

        try {
            Desktop.getDesktop().browse(new File(getExtentReportFilepath()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testcasename) {
        FrameworkReportManager.setExtent(extentReports.createTest(EnvironmentUtils.getBrowserIcon(false) + " " + testcasename));
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
        FrameworkReportManager.getExtent().info("<font color='blue'><b>" + "\uD83D\uDCBB "
                + System.getProperty("os.name").replace(" ", "_") + "</b></font>" + " --------- " + EnvironmentUtils.getBrowserIcon(true));
    }

    public static void setUrlInfo() {
        FrameworkReportManager.getExtent().info("<font color='blue'><b>" + "\uD83C\uDF10 " + getUrl() + "</b></font>");
    }

    public static void setExecutionModeInfo() {
        FrameworkReportManager.getExtent().info("<font color='blue'><b>" + isIsHeadlessMode() + "</b></font>");
    }

    public static void addScreenShot(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        Page.ScreenshotOptions screenshotOptions = new Page.ScreenshotOptions();

        String base64Image = DriverManager.getPage().screenshot(screenshotOptions.setPath(Paths.get(SystemUtils.getCurrentDir() + "media/Screenshots/" + File.separator + "ExtentReport_" + dateFormat.format(new Date()) + ".png"))).toString();
        String encodedString = encodeToBase64(base64Image);
        FrameworkReportManager.getExtent().log(Status.FAIL, message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(encodedString).build());
    }

    private static String encodeToBase64(String originalString) {
        byte[] encodedBytes = Base64.getEncoder().encode(originalString.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
}
