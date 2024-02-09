package com.utils;

import java.io.File;

public class SystemUtils {
    public static String getCurrentDir() {
        return System.getProperty("user.dir") + File.separator;
    }
}
