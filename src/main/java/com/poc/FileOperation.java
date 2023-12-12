import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class FileOperation {

    public static void main(String[] args) throws IOException {
        String sourcePath = "src/main/java/resources/BDD.BACS18.ABC00012.PM"; // Example original file path
        String destinationPath = "src/main/java/basefile/"; // Destination directory

        // Step 1: Copy the file
        String copiedFilePath = copyFile(sourcePath, destinationPath);

        // Step 2: Rename the file with random digits
        String renamedFilePath = renameFile(copiedFilePath);

        // Step 3: Update the file content
        updateFileContent(renamedFilePath);
    }

    private static String copyFile(String sourcePath, String destinationDir) throws IOException {
        Path source = Paths.get(sourcePath);
        String fileName = source.getFileName().toString();
        Path destination = Paths.get(destinationDir + fileName);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }

    private static String renameFile(String filePath) {
        String newName = filePath.replaceAll("ABC\\d{5}", "ABC" + getRandomFiveDigits()); // Replaces the last 5 digits
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
        int number = 10000 + random.nextInt(90000); // Generate a random 5-digit number
        return String.valueOf(number);
    }
}
