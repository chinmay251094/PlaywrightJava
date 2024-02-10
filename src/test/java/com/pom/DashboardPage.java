package com.pom;

import com.actions.PlaywrightPageActions;
import com.driver.DriverManager;
import com.microsoft.playwright.Locator;

public class DashboardPage extends PlaywrightPageActions {
    private final Locator textDashboard = DriverManager.getPage().getByText("Dashboard").first();

    private DashboardPage() {
    }

    public static DashboardPage accessDashboardPage() {
        return new DashboardPage();
    }

    public String getDashboard() {
        return textDashboard.textContent();
    }
}
