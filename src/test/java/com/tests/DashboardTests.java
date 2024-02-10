package com.tests;

import com.annotations.TestInformation;
import com.base.BaseTest;
import com.enums.TestCategory;
import com.enums.Tester;
import com.pom.DashboardPage;
import com.utils.VerificationUtils;
import lombok.SneakyThrows;
import org.testng.annotations.Test;

import java.util.Map;

import static com.pom.LoginPage.accessLoginPage;

public class DashboardTests extends BaseTest {
    private DashboardTests() {
    }

    @SneakyThrows
    @Test
    @TestInformation(author = Tester.CHINMAY, category = {TestCategory.SMOKE, TestCategory.SANITY})
    void testNavigatedToDashboard(Map<String, String> map) {
        accessLoginPage().loginToOrangeHRM(map.get("username"), map.get("password"));

        String dashboard = DashboardPage.accessDashboardPage().getDashboard();

        VerificationUtils.validate(dashboard, "Dashboard", "User is navigated to dashboard post login.");
    }
}
