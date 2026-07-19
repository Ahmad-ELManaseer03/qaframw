package com.thetestingacademy.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads configuration values from the data.properties file.
 * All test data and environment config is externalized here —
 * never hardcode values in test methods.
 */
public class PropertiesReader {

    private static final String PROPERTIES_PATH = "src/main/resources/data.properties";

    public static String readKey(String key) {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(PROPERTIES_PATH);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties file: " + PROPERTIES_PATH, e);
        }
        return properties.getProperty(key);
    }
}
