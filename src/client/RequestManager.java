package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestManager {
    private final DataInputStream input;
    private final DataOutputStream output;

    public RequestManager(DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
    }

    public void sendStopRequest() {
        try {
            output.writeUTF("STOP");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] sendGetRequest(String option, String fileName) {
        try {
            output.writeUTF("GET");
            output.writeUTF(option);
            output.writeUTF(fileName);
            System.out.println("The request was sent.");

            String msgIn = input.readUTF();
            switch (msgIn) {
                case "200" -> {
                    int length = input.readInt();
                    byte[] content = new byte[length];
                    input.readFully(content, 0, content.length);
                    return content;
                }
                case "404" -> {
                    System.out.println("The response says that this file is not found!");
                    return null;
                }
                default -> {
                    System.out.println("Error!");
                    return null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPutRequest(String fileName, byte[] content) {
        try {
            output.writeUTF("PUT");
            output.writeUTF(fileName);
            output.writeInt(content.length);
            output.write(content);
            System.out.println("The request was sent.");

            String msgIn = input.readUTF();
            switch (msgIn) {
                case "200" -> {
                    System.out.print("Response says that file is saved! ID = ");
                    System.out.println(input.readUTF());
                }
                case "403" -> System.out.println("The response says that creating the file was forbidden!");
                default -> System.out.println("Error!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDeleteRequest(String option, String fileName) {
        try {
            output.writeUTF("DELETE");
            output.writeUTF(option);
            output.writeUTF(fileName);
            System.out.println("The request was sent.");
            String msgIn = input.readUTF();
            switch (msgIn) {
                case "200" -> System.out.println("The response says that the file was successfully deleted!");
                case "404" -> System.out.println("The response says that the file was not found!");
                default -> System.out.println("Error!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
