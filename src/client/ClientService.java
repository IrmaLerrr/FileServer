package client;

import java.util.Scanner;

public class ClientService {
    private final Scanner scanner = new Scanner(System.in);
    private final FileManager fileManager = new FileManager();
    private final RequestManager requestManager;

    public ClientService(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void start() {
        while (true) {
            System.out.println("--- MENU ---");
            System.out.print("Enter action (1 - get a file, 2 - create a file, 3 - delete a file, 4 - exit, 5 - stop server): > ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> processGet();
                case "2" -> processPut();
                case "3" -> processDelete();
                case "4" -> {
                    return;
                }
                case "5" -> {
                    requestManager.sendStopRequest();
                    return;
                }
                default -> System.out.println("Error! Please, try again!");
            }
        }
    }

    private void processGet() {
        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): > ");
        String input = scanner.nextLine().trim();
        String option;
        String fileId;
        switch (input) {
            case "1" -> {
                option = "BY_NAME";
                System.out.print("Enter name: > ");
                fileId = scanner.nextLine().trim();
            }
            case "2" -> {
                option = "BY_ID";
                System.out.print("Enter id: > ");
                fileId = scanner.nextLine().trim();
            }
            default -> {
                System.out.println("Error!");
                return;
            }
        }
        byte[] content = requestManager.sendGetRequest(option, fileId);
        if (content != null) {
            System.out.print("The file was downloaded! Specify a name for it: > ");
            String fileName = scanner.nextLine().trim();
            fileManager.createFile(fileName, content);
            System.out.println("File saved on the hard drive!");
        }
    }

    private void processPut() {
        System.out.print("Enter name of the file: > ");
        String clientFileName = scanner.nextLine().trim();
        System.out.print("Enter name of the file to be saved on server: > ");
        String serverFileName = scanner.nextLine().trim();
        byte[] content = fileManager.getFile(clientFileName);
        requestManager.sendPutRequest(serverFileName, content);
    }

    private void processDelete() {
        System.out.print("Do you want to delete the file by name or by id (1 - name, 2 - id): > ");
        String input = scanner.nextLine().trim();
        String option;
        switch (input) {
            case "1" -> {
                option = "BY_NAME";
                System.out.print("Enter name: > ");
            }
            case "2" -> {
                option = "BY_ID";
                System.out.print("Enter id: > ");
            }
            default -> {
                System.out.println("Error!");
                return;
            }
        }
        String serverFileIdOrName = scanner.nextLine().trim();
        requestManager.sendDeleteRequest(option, serverFileIdOrName);
    }

}
