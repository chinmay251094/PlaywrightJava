package com.driver;

import com.exceptions.TestException;
import com.microsoft.playwright.*;
import com.utils.EnvironmentUtils;

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
        try {
            playwright = Playwright.create();
            DriverManager.setPlaywright(playwright);

            Browser browser = switch (browserName.toLowerCase().trim()) {
                case "chrome" ->
                        DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
                case "edge" ->
                        DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless));
                case "firefox" ->
                        DriverManager.getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                case "safari" ->
                        DriverManager.getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
                default ->
                        DriverManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            };

            String name = browser.browserType().name();
            String version = browser.version();
            EnvironmentUtils.setBrowser(name);
            EnvironmentUtils.setVersion(version);
            EnvironmentUtils.setIsHeadlessMode(headless);

            DriverManager.setBrowser(browser);

            BrowserContext browserContext = DriverManager.getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(screenWidth, screenHeight).setScreenSize(screenWidth, screenHeight));
            DriverManager.setBrowserContext(browserContext);

            Page page = DriverManager.getBrowserContext().newPage();
            DriverManager.setPage(page);

            // Navigate to the URL
            if (url != null && !url.isEmpty()) {
                page.navigate(url);
            }

            // Maximize the browser window
            maximizeBrowserOnWindow();

            return DriverManager.getPage();
        } catch (PlaywrightException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
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

