package ispitni2.ex1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientLogin extends Thread{

    int port;
    String serverName;

    public ClientLogin(int port, String serverName) {
        this.port = port;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        while(true) {
            String input = scanner.nextLine();
            if (input.equals("EXIT") || input.isEmpty()) {
                printWriter.println("EXIT");
                printWriter.flush();
            }

            printWriter.println(input);
            printWriter.flush();

            String serverResponse = null;
            try {
                serverResponse = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            System.out.println(serverResponse);
        }

    }

    public static void main(String[] args) {
        ClientLogin clientLogin = new ClientLogin(7887, "localhost");
        clientLogin.start();
    }
}
