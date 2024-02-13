package com.driver;

import com.exceptions.TestException;
import com.microsoft.playwright.*;
import com.utils.EnvironmentUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlaywrightDriver {
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int screenHeight = (int) screenSize.getHeight();
    private static final int screenWidth = (int) screenSize.getWidth();

    private PlaywrightDriver() {
    }

    public static Page createBrowser(String browserName, String url, boolean headless) {
        try {
            Playwright playwright = Playwright.create();
            PlaywrightManager.setPlaywright(playwright);

            Browser browser = launchBrowser(browserName, headless);

            EnvironmentUtils.setBrowser(browser.browserType().name());
            EnvironmentUtils.setVersion(browser.version());
            EnvironmentUtils.setIsHeadlessMode(headless);

            BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(screenWidth, screenHeight).setScreenSize(screenWidth, screenHeight));
            PlaywrightManager.setBrowserContext(browserContext);

            Page page = browserContext.newPage();
            PlaywrightManager.setPage(page);

            // Navigate to the URL
            if (url != null && !url.isEmpty()) {
                page.navigate(url);
            }

            // Maximize the browser window
            maximizeBrowserOnWindow();

            return page;
        } catch (PlaywrightException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    private static Browser launchBrowser(String browserName, boolean headless) {
        return switch (browserName.toLowerCase().trim()) {
            case "chrome" ->
                    PlaywrightManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(headless));
            case "edge" ->
                    PlaywrightManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(headless));
            case "firefox" ->
                    PlaywrightManager.getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            case "safari" ->
                    PlaywrightManager.getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(headless));
            default ->
                    PlaywrightManager.getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        };
    }

    private static void maximizeBrowserOnWindow() {
        try {
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_WINDOWS);
            rb.keyPress(KeyEvent.VK_UP);
            rb.keyRelease(KeyEvent.VK_UP);
            rb.keyRelease(KeyEvent.VK_WINDOWS);
        } catch (AWTException e) {
            throw new TestException("Unable to maximize browser window.");
        }
    }
}

