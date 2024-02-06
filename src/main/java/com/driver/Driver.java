package com.driver;

import com.exceptions.TestException;
import com.microsoft.playwright.*;
import com.reports.FrameworkReportLogger;
import com.utils.EnvironmentUtils;
import com.utils.LogUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Driver {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int screenHeight = ((int) screenSize.getHeight());
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected static Page page;
    private static int screenWidth = ((int) screenSize.getWidth());

    private Driver() {
    }

    public static Page createBrowser(String browserName, String url, boolean headless) {
        playwright = Playwright.create();
        DriverManager.setPlaywright(playwright);

        switch (browserName.toLowerCase().trim()) {
            case "chromium":
                browser = (DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "chrome":
                browser = (DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless)));
                break;
            case "edge":
                browser = (DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless)));
                break;
            case "firefox":
                browser = (DriverManager.getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            case "safari":
                browser = (DriverManager.getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
            default:
                browser = (DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless)));
                break;
        }

        String name = browser.browserType().name();
        String version = browser.version();
        EnvironmentUtils.setBrowser(name);
        EnvironmentUtils.setVersion(version);
        EnvironmentUtils.setIsHeadlessMode(headless);

        DriverManager.setBrowser(browser);

        screenWidth = screenWidth - ((screenWidth * 5) / 100);

        browserContext = DriverManager.getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(screenWidth, screenHeight).setScreenSize(screenWidth, screenHeight));

        DriverManager.setBrowserContext(browserContext);
        page = DriverManager.getBrowserContext().newPage();

        DriverManager.setPage(page);
        navigate(url);
        maximizeBrowserOnWindow();
        return DriverManager.getPage();
    }

    private static void navigate(String url) {
        DriverManager.getPage().navigate(url);
        LogUtils.info("Navigate to URL: " + url);
        FrameworkReportLogger.info("Navigate to URL: " + url);
    }

    private static void maximizeBrowserOnWindow() {
        Robot rb = null;
        try {
            rb = new Robot();
            rb.keyPress(KeyEvent.VK_WINDOWS);
            rb.keyPress(KeyEvent.VK_UP);
            rb.keyRelease(KeyEvent.VK_UP);
            rb.keyRelease(KeyEvent.VK_WINDOWS);
        } catch (AWTException e) {
            throw new TestException("Unable to maximize browser window.");
        }
    }
}

