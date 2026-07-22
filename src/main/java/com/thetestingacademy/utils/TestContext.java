package com.thetestingacademy.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe context holder for sharing runtime variables across different test modules.
 */
public class TestContext {
    private static final Map<String, Object> context = new ConcurrentHashMap<>();

    public static void set(String key, Object value) {
        context.put(key, value);
    }

    public static Object get(String key) {
        return context.get(key);
    }

    public static void setCreatedCountryName(String countryName) {
        set("createdCountryName", countryName);
    }

    public static String getCreatedCountryName() {
        Object val = get("createdCountryName");
        return val != null ? val.toString() : null;
    }

    public static void clear() {
        context.clear();
    }
}
