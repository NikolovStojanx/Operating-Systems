package ISPITNI.NETWORKING.ZAD1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LastWord extends Thread {
    int port;


    public LastWord(int port) throws IOException {
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

        System.out.println("Server started");
        System.out.println("Waiting for connections");

        while (true) {
            Socket clientSocket = null;

            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Client connected");
            new ClientHandler(clientSocket).start();
        }

    }

    public static void main(String[] args) {
        try {
            LastWord lastWord = new LastWord(7777);
            lastWord.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
