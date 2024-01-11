package com.poc.files;

import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Stream;

public class FileChecker {

    public static void main(String[] args) {
        String folderPath = "testdata/base-file"; // Replace with your folder path

        // Check for a single file and print its path
        String filePath = foundFile(folderPath);
        if (filePath != null) {
            System.out.println("Found file: " + filePath);
        }

        // Delete files in the directory
        String deleteStatus = deleteFiles(folderPath);
        System.out.println(deleteStatus);
    }

    public static String foundFile(String folderPath) {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return "Path is not a directory or does not exist.";
        }

        try (Stream<Path> files = Files.list(path)) {
            long count = files.count();
            if (count == 1) {
                try (Stream<Path> singleFile = Files.list(path)) {
                    return singleFile.findFirst()
                            .map(p -> folderPath + File.separator + p.getFileName().toString())
                            .orElse("File not found.");
                }
            } else if (count > 1) {
                return "More than one file found.";
            } else {
                return "No files found.";
            }
        } catch (IOException e) {
            return "An error occurred while accessing the directory.";
        }
    }

    public static String deleteFiles(String folderPath) {
        Path path = Paths.get(folderPath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return "Path is not a directory or does not exist.";
        }

        try (Stream<Path> files = Files.list(path)) {
            files.forEach(file -> {
                try {
                    Files.delete(file);
                    System.out.println("Deleted: " + file);
                } catch (IOException e) {
                    System.err.println("Failed to delete: " + file);
                }
            });
            return "File deletion process completed.";
        } catch (IOException e) {
            return "An error occurred while accessing or deleting files in the directory.";
        }
    }
}


