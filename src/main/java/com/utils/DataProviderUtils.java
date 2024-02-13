package com.utils;

import com.constants.FrameworkConstants;
import com.exceptions.TestException;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DataProviderUtils {
    private DataProviderUtils() {
    }

    @DataProvider(parallel = true)
    public static Object[] fetchDataUsingJson(Method method) {
        String name = method.getName();
        List<Map<String, String>> executeMethodsList = new ArrayList<>();

        try {
            List<ConcurrentHashMap<String, String>> jsonList = JsonUtils.readJsonFile(FrameworkConstants.getJsonspath("TestData.json"));

            for (ConcurrentHashMap<String, String> hashMap : jsonList) {
                String testName = hashMap.get("Test Name");
                String executeFlag = hashMap.get("Execute");

                // Check if the test name matches and execute flag is "yes"
                if (name.equalsIgnoreCase(testName) && "yes".equalsIgnoreCase(executeFlag)) {
                    // Create a new HashMap to copy the data
                    Map<String, String> map = new HashMap<>(hashMap);
                    executeMethodsList.add(map);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();  // Add this line for logging
            throw new TestException("An error occurred while fetching data source.");
        }

        return executeMethodsList.toArray();
    }
}