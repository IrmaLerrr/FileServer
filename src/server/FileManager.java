package server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class FileManager {
    private static final String STORAGE_FILE = "./src/server/data/map_data.ser";
    private static final String PATH = "./src/server/data/";
    //    private static final String PATH = "C:/src/server/data/";
    private static final Map<Integer, String> map;

    static {
        map = loadMap();
        System.out.println("Current map is " + map);
    }

    private static Map<Integer, String> loadMap() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(STORAGE_FILE))) {
            return (Map<Integer, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error!");
            return new HashMap<>();
        }
    }

    public static void saveMap() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(STORAGE_FILE))) {
            oos.writeObject(map);
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
        }
    }

    public Integer createFile(String fileName, byte[] fileContent) {
        Random random = new Random();
        Integer id;
        do {
            id = random.nextInt(101);
        } while (map.containsKey(id));

        if (fileName == null || fileName.isBlank()) {
            fileName = String.valueOf(id);
        }

        Path filePath = Paths.get(PATH + fileName);
        if (Files.exists(filePath)) return null;
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, fileContent);
            map.put(id, fileName);
            saveMap();
            return id;
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }

    public byte[] getFile(String option, String fileId) {
        try {
            Path filePath;
            switch (option) {
                case "BY_NAME" -> filePath = Paths.get(PATH + fileId);
                case "BY_ID" -> {
                    String fileName = map.get(Integer.parseInt(fileId));
                    filePath = Paths.get(PATH + fileName);
                }
                default -> {
                    System.out.println("Error! Option is " + option);
                    return null;
                }
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }

    public String deleteFile(String option, String fileId) {
        try {
            Path filePath;
            switch (option) {
                case "BY_NAME" -> filePath = Paths.get(PATH + fileId);
                case "BY_ID" -> {
                    String fileName = map.get(Integer.parseInt(fileId));
                    filePath = Paths.get(PATH + fileName);
                }
                default -> {
                    System.out.println("Error! Option is " + option);
                    return null;
                }
            }
            Files.delete(filePath);
            return "200";
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }
}
