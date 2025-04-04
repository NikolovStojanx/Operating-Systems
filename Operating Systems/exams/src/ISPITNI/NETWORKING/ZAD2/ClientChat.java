package ISPITNI.NETWORKING.ZAD2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientChat extends Thread{

    String serverName;
    int serverPort;

    public ClientChat(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName(serverName), serverPort);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {

                String line = scanner.nextLine();
                if (line.equals("EXIT")) {
                    printWriter.println("xx_xxcax00");
                    printWriter.flush();
                    break;
                }
                printWriter.println(line);
                printWriter.flush();

                String serverResponse = bufferedReader.readLine();
                System.out.println(serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ClientChat clientChat = new ClientChat("localhost", 8877);
        clientChat.start();
    }
}
