package com.pom;

import com.actions.PlaywrightPageActions;
import com.microsoft.playwright.Locator;

public class LoginPage extends PlaywrightPageActions {
    Locator textBoxUsername = fetchElement().getByPlaceholder("Username");
    Locator textBoxPassword = fetchElement().getByPlaceholder("Password");
    Locator buttonLogin = fetchElement().locator("[type=submit]");
    private LoginPage() {
    }

    public static LoginPage accessLoginPage() {
        return new LoginPage();
    }

    public void loginToOrangeHRM(String uname, String pwd) {
        fill(textBoxUsername, uname, "Username");
        fill(textBoxPassword, pwd, "Password");
        click(buttonLogin);
    }
}
