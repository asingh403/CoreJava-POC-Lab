import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class FileUpdater {

    public static void main(String[] args) {
        String directoryPath = "/path/to/your/directory"; // Replace with your directory path
        File dir = new File(directoryPath);

        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                try {
                    processFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
    }

    private static void processFile(File file) throws IOException {
        Path path = file.toPath();
        Path tempFile = Files.createTempFile(file.getParentFile().toPath(), "temp", ".txt");

        try (BufferedReader reader = Files.newBufferedReader(path);
             BufferedWriter writer = Files.newBufferedWriter(tempFile)) {

            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 3) {
                    line = updateLine(line);
                }
                writer.write(line);
                writer.newLine();
            }
        }

        Files.move(tempFile, tempFile.resolveSibling(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }

    private static String updateLine(String line) {
        if (line.contains("C22") && line.contains("C27")) {
            // Update the line as per your requirement
            // Example: Replace 'C22' to 'C27' with random 4-digit number
            line = line.replace("C22", randomDigits(4))
                       .replace("C27", randomDigits(4));
        }
        return line;
    }

    private static String randomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Append a random digit
        }
        return sb.toString();
    }
}
