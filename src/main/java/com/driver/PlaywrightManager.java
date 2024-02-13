package com.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightManager {
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    private PlaywrightManager() {
    }

    public static Browser getBrowser() {
        return browserThreadLocal.get();
    }

    public static void setBrowser(Browser browser) {
        browserThreadLocal.set(browser);
    }

    public static BrowserContext getBrowserContext() {
        return browserContextThreadLocal.get();
    }

    public static void setBrowserContext(BrowserContext browserContext) {
        browserContextThreadLocal.set(browserContext);
    }

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }

    public static void setPlaywright(Playwright playwright) {
        playwrightThreadLocal.set(playwright);
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static void setPage(Page page) {
        pageThreadLocal.set(page);
    }

    public static void closePage() {
        if (pageThreadLocal.get() != null) {
            pageThreadLocal.get().close();
            pageThreadLocal.remove();
        }
    }

    public static void closeBrowser() {
        if (browserThreadLocal.get() != null) {
            browserThreadLocal.get().close();
            browserThreadLocal.remove();
        }
        if (playwrightThreadLocal.get() != null) {
            playwrightThreadLocal.get().close();
            playwrightThreadLocal.remove();
        }
    }
}