import java.io.*;
import java.nio.file.*;
import java.util.Random;
import java.util.regex.*;

public class FileOperation {

    public static void main(String[] args) throws IOException {
        String sourceDirPath = "src/main/java/resources/"; // Source directory
        String destinationDirPath = "src/main/java/basefile/"; // Destination directory

        // Delete existing payment files in the destination directory
        deleteExistingPaymentFiles(destinationDirPath, "BDD.BACS18");

        // Find the file in the source directory
        File sourceDir = new File(sourceDirPath);
        FilenameFilter filter = (dir, name) -> name.startsWith("BDD.BACS18");
        File[] files = sourceDir.listFiles(filter);

        if (files == null || files.length == 0) {
            System.out.println("No files found in the source directory.");
            return;
        }

        // Assuming you want to process the first file that matches the criteria
        File fileToProcess = files[0];
        String filePathToProcess = fileToProcess.getPath();

        // Step 1: Copy the file
        String copiedFilePath = copyFile(filePathToProcess, destinationDirPath);

        // Step 2: Rename the file with random digits
        String renamedFilePath = renameFile(copiedFilePath);

        // Step 3: Update the file content
        updateFileContent(renamedFilePath);
    }

    private static void deleteExistingPaymentFiles(String destinationDirPath, String filePrefix) {
        File destinationDir = new File(destinationDirPath);
        FilenameFilter filter = (dir, name) -> name.startsWith(filePrefix);
        File[] files = destinationDir.listFiles(filter);

        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    System.out.println("Failed to delete file: " + file.getPath());
                }
            }
        }
    }

    private static String copyFile(String sourcePath, String destinationDir) throws IOException {
        Path source = Paths.get(sourcePath);
        String fileName = source.getFileName().toString();
        Path destination = Paths.get(destinationDir + fileName);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }

    private static String renameFile(String filePath) {
        String newName = filePath.replaceAll("ABC\\d{5}", "ABC" + getRandomFiveDigits());
        File file = new File(filePath);
        File renamedFile = new File(newName);
        if (file.renameTo(renamedFile)) {
            System.out.println("File renamed successfully to: " + newName);
        } else {
            System.out.println("Failed to rename file");
        }
        return newName;
    }

    private static void updateFileContent(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String content = new String(Files.readAllBytes(path));
        String newContent = content.replaceAll("BTBACS000\\d{5}", "BTBACS000" + getRandomFiveDigits());
        Files.write(path, newContent.getBytes());
    }

    private static String getRandomFiveDigits() {
        Random random = new Random();
        int number = 10000 + random.nextInt(90000);
        return String.valueOf(number);
    }
}
