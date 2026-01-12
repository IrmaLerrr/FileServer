package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestManager {
    private final FileManager fileManager;
    private final DataInputStream input;
    private final DataOutputStream output;

    public RequestManager(DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
        this.fileManager = new FileManager();
    }

    public void start() throws IOException {
        while (true) {
            String msgIn = input.readUTF();
            switch (msgIn) {
                case "STOP" -> throw new RuntimeException("Shutdown requested!");
                case "GET" -> processGet();
                case "PUT" -> processPut();
                case "DELETE" -> processDelete();
                default -> System.out.println("Error!");
            }
        }
    }

    private void processGet() throws IOException {
        String option = input.readUTF();
        String file = input.readUTF();
        byte[] msgOut = fileManager.getFile(option, file);
        System.out.print("GET " + option + " " + file.trim());
        if (msgOut == null) {
            output.writeUTF("404");
            System.out.println(" Result: 404");
        }
        else {
            output.writeUTF("200");
            output.writeInt(msgOut.length);
            output.write(msgOut);
            System.out.println(" Result: 200");
        }
    }

    private void processPut() throws IOException {
        String fileName = input.readUTF();
        int length = input.readInt();
        byte[] content = new byte[length];
        input.readFully(content, 0, content.length);

        Integer msgOut = fileManager.createFile(fileName, content);
        System.out.print("PUT " + fileName.trim());
        if (msgOut == null) {
            output.writeUTF("403");
            System.out.println(" Result: 403");
        }
        else {
            output.writeUTF("200");
            output.writeUTF(String.valueOf(msgOut));
            System.out.println(" Result: 200, ID: " + msgOut);
        }
    }

    private void processDelete() throws IOException {
        String option = input.readUTF();
        String file = input.readUTF();
        String msgOut = fileManager.deleteFile(option, file);
        System.out.print("DELETE " + option + " " + file);
        if (msgOut == null) {
            output.writeUTF("404");
            System.out.println(" Result: 404");
        }
        else {
            output.writeUTF("200");
            System.out.println(" Result: 200");
        }
    }

}
