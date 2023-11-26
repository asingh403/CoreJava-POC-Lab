package com.poc.files;

import com.poc.constants.Constants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;

public class FileRenamer {
    public static void main(String[] args) {
        File inputDirectory = new File(Constants.baseFileLocation);
        File outputDirectory = new File(Constants.outputDirectoryPath);

        if (!isDirectoryEmpty(outputDirectory)) {
            System.out.println("Output directory is not empty. Clearing the directory...");
            clearDirectory(outputDirectory);
        }

        if (inputDirectory.isDirectory()) {
            File[] files = inputDirectory.listFiles();

            if (files != null && files.length > 0) {
                File originalFile = files[0];
                String originalFileName = originalFile.getName();

                String[] parts = originalFileName.split("\\.");
                String prefix = parts[0] + "." + parts[1] + "." + parts[2].substring(0, 3);
                String suffix = parts[3];
                int numberPart = Integer.parseInt(parts[2].substring(3));

                for (int i = 0; i < Constants.createNumberOfNewFiles; i++) {
                    numberPart++;
                    String newFileName = prefix + String.format("%05d", numberPart) + "." + suffix;
                    File newFile = new File(Constants.baseFileLocation, newFileName);

                    try {
                        Files.copy(originalFile.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Files.move(newFile.toPath(), Path.of(Constants.outputDirectoryPath, newFileName), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File created and moved: " + newFileName);
                    } catch (IOException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("No files found in the input directory.");
            }
        } else {
            System.out.println("Input path is not a directory.");
        }
    }

    private static boolean isDirectoryEmpty(File directory) {
        if (directory.isDirectory()) {
            String[] files = directory.list();
            return files == null || files.length == 0;
        }
        return false;
    }

    private static void clearDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
}
