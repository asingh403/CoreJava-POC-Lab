package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropFileMap {

    private static final String NAME_OF_PROPERTY_FILE = "config";
    private static final String FILE_PATH = "/Users/ashutoshsingh/Documents/IntellijSpring/CoreJava-POC-Lab/testdata/config-data/";
    private static Map<String, String> propertiesMap;

    private PropFileMap() {
        // Private constructor to prevent instantiation
    }

    public static Map<String, String> getProperties() {
        if (propertiesMap == null) {
            synchronized (PropFileMap.class) {
                if (propertiesMap == null) {
                    propertiesMap = new HashMap<>();
                    Properties properties = new Properties();
                    try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH + NAME_OF_PROPERTY_FILE + ".properties")) {
                        properties.load(fileInputStream);
                        for (String key : properties.stringPropertyNames()) {
                            String value = properties.getProperty(key);
                            propertiesMap.put(key, value);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null; // Add this line to handle the IOException case
                    }
                }
            }
        }
        return propertiesMap;
    }
}
