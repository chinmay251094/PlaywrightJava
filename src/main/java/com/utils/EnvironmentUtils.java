package com.utils;

import lombok.Getter;

public final class EnvironmentUtils {
    @Getter
    private static String mode;
    @Getter
    private static String url;
    @Getter
    private static String browser;
    private static String isHeadlessMode;
    @Getter
    private static String version;

    private EnvironmentUtils() {
    }

    public static void setVersion(String version) {
        EnvironmentUtils.version = version;
    }

    public static void setMode(String mode) {
        EnvironmentUtils.mode = mode;
    }

    public static void setUrl(String url) {
        EnvironmentUtils.url = url;
    }

    public static String getBrowserIcon(boolean isVersionNeeded) {
        if (browser.contains("chrome") || browser.contains("chromium")) {
            if (isVersionNeeded) {
                return "<i class='fa fa-chrome fa-lg' aria-hidden='true'></i>" + " <b>CHROME</b> ";
            } else {
                return "<i class='fa fa-chrome' aria-hidden='true'></i>" + " CHROME: ";
            }
        } else if (browser.contains("firefox")) {
            if (isVersionNeeded) {
                return "<i class='fa fa-firefox fa-lg' aria-hidden='true'></i>" + " <b>FIREFOX</b> ";
            } else {
                return "<i class='fa fa-firefox' aria-hidden='true'></i>" + " FIREFOX: ";
            }
        } else if (browser.contains("edge")) {
            if (isVersionNeeded) {
                return "<i class='fas fa-edge fa-lg'></i>" + " <b>EDGE</b> ";
            } else {
                return "<i class='fas fa-edge'></i>" + " EDGE: ";
            }
        } else {
            return "Unknown browser";
        }
    }

    public static String getOSInfo() {
        return System.getProperty("os.name");
    }

    public static void setBrowser(String browser) {
        EnvironmentUtils.browser = browser;
    }

    public static String isIsHeadlessMode() {
        return isHeadlessMode;
    }

    public static void setIsHeadlessMode(boolean isHeadlessMode) {
        if (isHeadlessMode) {
            EnvironmentUtils.isHeadlessMode = "\uD83D\uDEAB️" + " NO USER INTERFACE mode";
        } else {
            EnvironmentUtils.isHeadlessMode = "\uD83D\uDCBB" + " USER INTERFACE mode";
        }
    }
}
