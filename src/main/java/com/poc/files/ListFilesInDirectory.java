import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ListFilesInDirectory {

    public static void main(String[] args) {
        String folderPath = "your/directory/path"; // Replace with your actual directory path

        try {
            List<File> filesInFolder = Files.walk(Paths.get(folderPath))
                                            .filter(Files::isRegularFile)
                                            .map(Path::toFile)
                                            .collect(Collectors.toList());

            for (File file : filesInFolder) {
                System.out.println(file.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
