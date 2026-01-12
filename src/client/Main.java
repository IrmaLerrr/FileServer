package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())

        ) {
            System.out.println("Client started!");
            RequestManager requestManager = new RequestManager(input, output);
            ClientService service = new ClientService(requestManager);
            service.start();
        } catch (IOException e) {
            System.out.println("Unable to connect server!");
        }
    }
}

