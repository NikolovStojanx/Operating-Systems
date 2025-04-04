package ispitni2.ex1;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLogin extends Thread{
    int port;

    public ServerLogin(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("listening...");
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            new ClientLoginHandler(clientSocket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        ServerLogin serverLogin = new ServerLogin(7887);
        serverLogin.start();
    }
}
