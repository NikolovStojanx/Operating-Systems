package ISPITNI.NETWORKING.ZAD2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChat extends Thread {
    int port;

    public ServerChat(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Waiting for connection");
                clientSocket = serverSocket.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client connected");
            new ClientChatHandler(clientSocket).start();

        }

    }

    public static void main(String[] args) {
        ServerChat serverChat = new ServerChat(8877);
        serverChat.start();
    }

}
