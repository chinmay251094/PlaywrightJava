package com.pom;

import com.actions.PlaywrightPageActions;
import com.driver.DriverManager;
import com.microsoft.playwright.Locator;

public class LoginPage extends PlaywrightPageActions {
    Locator textBoxUsername = fetchElement().getByPlaceholder("Username").first();
    Locator textBoxPassword = fetchElement().getByPlaceholder("Password").first();
    Locator buttonLogin = fetchElement().locator("[type=submit]");

    private LoginPage() {
    }

    public static LoginPage accessLoginPage() {
        return new LoginPage();
    }

    public void loginToOrangeHRM(String uname, String pwd) {
        DriverManager.getPage().waitForLoadState();
        fill(textBoxUsername, uname, "Username");
        fill(textBoxPassword, pwd, "Password");
        click(buttonLogin);
    }
}
