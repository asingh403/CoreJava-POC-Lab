package com.fileutils;

import java.io.IOException;
import java.util.Map;

import static com.fileutils.PropertiesUtil.getAllPropertiesAsMap;

public class PropFileAccess {

    public static void main(String[] args) throws IOException {
        String fileName = "config-test";
        String filePath  = System.getProperty("user.dir")+"/src/main/java/com/fileutils/"+ fileName;
        PropertiesUtil.getInstance(filePath);
        /*String keyValue = PropertiesUtil.getProperty("projectCode");
        System.out.println(keyValue);*/

       /* System.out.println("\n********");
        PropertiesUtil.printAllProperties();*/

        Map<String, String> result = getAllPropertiesAsMap();
        System.out.println(result.get("projectCode"));
        System.out.println(result.get("roleId"));
    }
}
