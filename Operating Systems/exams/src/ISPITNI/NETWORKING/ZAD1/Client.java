package ISPITNI.NETWORKING.ZAD1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{

    int serverPort;
    String serverName;

    public Client(String serverName, int serverPort) {
        this.serverPort = serverPort;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            socket = new Socket(InetAddress.getByName(this.serverName), this.serverPort);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String line = scanner.nextLine();
                if (line.equals("EXIT")) {
                    printWriter.println("0x79cc_jkaax_*");
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
        Client client = new Client("localhost", 7777);
        client.start();
    }
}
