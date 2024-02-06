package com.pom;

import com.actions.PlaywrightPageActions;
import com.microsoft.playwright.Locator;

public class DashboardPage extends PlaywrightPageActions {
    private Locator textDashboard = fetchElement().getByText("Dashboard");

    private DashboardPage() {
    }

    public static DashboardPage accessDashboardPage() {
        return new DashboardPage();
    }

    public String getDashboard() {
        return textDashboard.textContent();
    }
}
