package com.base;

import com.annotations.MustExtendBaseTest;
import com.driver.DriverManager;
import com.exceptions.TestException;
import com.utils.EnvironmentUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Map;

import static com.driver.Driver.createBrowser;

@MustExtendBaseTest
public class BaseTest {
    @BeforeMethod
    public void setUp(Object[] data) {
        try {
            Map<String, String> map = (Map<String, String>) data[0];
            String url = map.get("url");
            EnvironmentUtils.setUrl(url);
            createBrowser(map.get("browser"), url, Boolean.parseBoolean(map.get("headless")));
        } catch (Exception e) {
            throw new TestException("Unable to set up the browser for execution.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closePage() {
        DriverManager.closePage();
        DriverManager.closeBrowser();
    }
}
