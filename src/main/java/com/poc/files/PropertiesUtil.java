package com.fileutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties properties = new Properties();
    private static PropertiesUtil instance = null;

    // Private constructor to prevent direct instantiation
    private PropertiesUtil(String fileName) throws IOException {
        String filePath = fileName + ".properties";
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
    }

    // Static method to get the singleton instance
    public static PropertiesUtil getInstance(String fileName) throws IOException {
        if (instance == null) {
            instance = new PropertiesUtil(fileName);
        }
        return instance;
    }

    // Method to get a property value by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Optional: Method to print all properties
    public static void printAllProperties() {
        properties.forEach((key, value) -> System.out.println(key + " = " + value));
    }

    public static Map<String, String> getAllPropertiesAsMap() {
        Map<String, String> map = new HashMap<>();
        properties.forEach((key, value) -> map.put((String) key, (String) value));
        return map;
    }
}

