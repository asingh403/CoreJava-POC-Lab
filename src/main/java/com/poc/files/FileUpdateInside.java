import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FileUpdateInside {

    public static void main(String[] args) {
        String folderLocation = "/Users/ashutoshsingh/eclipse-workspace/Selenium-Practice-Concept/src/test/resources/";
        updateFilesInDirectory(folderLocation);
    }

    public static void updateFilesInDirectory(String folderPath) {
        try {
            List<File> filesInFolder = Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());

            for (File file : filesInFolder) {
                updateFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateFile(File file) {
        StringBuilder content = new StringBuilder();
        Random random = new Random();
        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber <= 3 && line.contains("TEST")) {
                    String randomString = "TEST" + (10000 + random.nextInt(90000));  // Generate random 5-digit number
                    line = line.replaceAll("TEST\\d{5}", randomString);
                }
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
