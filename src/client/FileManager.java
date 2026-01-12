package client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileManager {
    private static final String PATH = "./src/client/data/";
//    private static final String PATH = "C:/src/client/data/";

    public void createFile(String fileName, byte[] fileContent) {
        Path filePath = Paths.get(PATH + fileName);
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, fileContent,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public byte[] getFile(String fileName) {
        Path filePath = Paths.get(PATH + fileName);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
