package com.actions;

import com.driver.DriverManager;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.reports.FrameworkReportLogger.info;

public class PlaywrightPageActions {
    private static final int STEP_TIME = 1000; // Adjust this value as needed

    // Click action
    public static void click(String selector) {
        sleep();
        getElement(selector).click();
        info("Click element " + selector);
    }

    public static void click(Locator locator) {
        locator.click();
        info("Click element " + locator);
    }

    // Fill text action
    public static void fill(String selector, String value) {
        sleep();
        getElement(selector).fill(value);
        info("Fill text " + value + " on element " + selector);
    }

    public static void fill(Locator locator, String value, String locatorType) {
        locator.fill(value);
        info("Fill text " + value + " on element " + locatorType);
    }

    // Utility method to get ElementHandle
    private static ElementHandle getElement(String selector) {
        return DriverManager.getPage().locator(selector).elementHandle();
    }

    // Double-click action
    public static void doubleClick(String selector) {
        sleep();
        getElement(selector).dblclick();
        info("Double-click element " + selector);
    }

    // Hover action
    public static void hover(String selector) {
        sleep();
        getElement(selector).hover();
        info("Hover over element " + selector);
    }

    // Get text from element
    public static String getText(String selector) {
        sleep();
        String text = getElement(selector).innerText();
        info("Get text from element " + selector + ": " + text);
        return text;
    }

    // Check if element is visible
    public static boolean isElementVisible(String selector) {
        sleep();
        return getElement(selector).isVisible();
    }

    // Check if element is enabled
    public static boolean isElementEnabled(String selector) {
        sleep();
        return getElement(selector).isEnabled();
    }

    // Check if element is present
    public static boolean isElementPresent(String selector) {
        sleep();
        return getElement(selector).isEnabled();
    }

    public static Page fetchElement() {
        try (Page page = DriverManager.getPage()) {
            return page;
        }
    }

    // Utility method for sleep
    private static void sleep() {
        try {
            Thread.sleep(PlaywrightPageActions.STEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
