import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class FileOperation {

    public static void main(String[] args) throws IOException {
        String sourcePath = "src/main/java/resources/originalFile.pm"; // original file path
        String destinationPath = "src/main/java/basefile/copiedFile.pm"; // new file path

        // Step 1: Copy the file
        copyFile(sourcePath, destinationPath);

        // Step 2: Rename the file
        String renamedFilePath = renameFile(destinationPath);

        // Step 3: Update the file content
        updateFileContent(renamedFilePath);
    }

    private static void copyFile(String sourcePath, String destinationPath) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
    }

    private static String renameFile(String filePath) {
        String newName = filePath.replace("copiedFile", "updatedFile"); // Create a new name
        File file = new File(filePath);
        File renamedFile = new File(newName);
        if (file.renameTo(renamedFile)) {
            System.out.println("File renamed successfully");
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
