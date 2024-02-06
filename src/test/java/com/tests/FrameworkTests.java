package com.tests;

import com.annotations.TestInformation;
import com.base.BaseTest;
import com.enums.TestCategory;
import com.enums.Tester;
import com.microsoft.playwright.Locator;
import lombok.SneakyThrows;
import org.testng.annotations.Test;

import java.util.Map;

import static com.driver.DriverManager.getPage;

public class FrameworkTests extends BaseTest {
    @SneakyThrows
    @Test
    @TestInformation(author = Tester.CHINMAY, category = {TestCategory.SMOKE, TestCategory.SANITY})
    void launchGoogle(Map<String, String> map) {
        System.out.println(getPage().title());
        Locator textBox = getPage().locator("[name=q]").first();
//        textBox.fill(map.get("searchText"));
        textBox.fill("searchText");
        getPage().press("[name=q]", "Enter");
    }
}
