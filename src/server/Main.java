package server;

import java.io.*;
import java.net.*;

public class Main {
//    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server started!");
            while (true) {
                try {
                    Socket clientSocket = server.accept();
                    DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                    RequestManager manager = new RequestManager(input, output);
                    manager.start();
                } catch (RuntimeException e3) {
                    System.out.println("The client shutdown server.");
                    break;
                } catch (EOFException e2) {
                    System.out.println("The client disconnected.");
                } catch (SocketException e3) {
                    System.out.println("The connection was broken.");
                } catch (IOException e) {
                    System.out.println("Error! " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error! " + e.getMessage());
        }
    }
}