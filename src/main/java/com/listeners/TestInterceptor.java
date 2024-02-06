package com.listeners;

import com.constants.FrameworkConstants;
import com.utils.JsonUtils;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TestInterceptor implements IMethodInterceptor {
    private static final List<String> ordered = new ArrayList<>();

    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        List<ConcurrentHashMap<String, String>> list = getSourceData();
        List<IMethodInstance> result = new ArrayList<>();

        for (IMethodInstance method : methods) {
            String methodName = method.getMethod().getMethodName();
            list.stream()
                    .filter(hashMap -> methodName.equalsIgnoreCase(hashMap.get("Test Name"))
                            && "yes".equalsIgnoreCase(hashMap.get("Execute")))
                    .findFirst()
                    .ifPresent(hashMap -> {
                        method.getMethod().setDescription(hashMap.get("Test Description"));
                        method.getMethod().setInvocationCount(Integer.parseInt(hashMap.get("Count")));
                        method.getMethod().setPriority(Integer.parseInt(hashMap.get("Priority")));
                        result.add(method);
                    });
        }

        Comparator<IMethodInstance> comparator = Comparator.comparingInt(m -> m.getMethod().getPriority());

        result.sort(comparator);

//        for (IMethodInstance m : result) {
//            System.out.println(m.getMethod().getDescription());
//        }

        for (IMethodInstance m : methods) {
            String clazz = m.getMethod().getInstance().getClass().getName() + ".";
            ordered.add(clazz + m.getMethod().getMethodName());
        }
        return result;
    }

    private List<ConcurrentHashMap<String, String>> getSourceData() {
        try {
            return JsonUtils.readJsonFile(FrameworkConstants.getJsonspath("TestCases.json"));
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while fetching data source.");
        }
    }
}

