package ispitni2.ex1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class ClientLoginHandler extends Thread {
    Socket socket;
    public ClientLoginHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;

        try {
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        HashMap<String, Integer> word_occurance = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();

        while (true) {

            try {
                String clientWord = bufferedReader.readLine();
                if (clientWord.equals("EXIT")) {
                    break;
                }
                if (clientWord.equals("HANDSHAKE")) {
                    System.out.printf("Client LOGIN: client address: %s client port: %s\n", socket.getInetAddress(), socket.getPort());
                    flag = true;
                    printWriter.println("HANDSHAKE with server");
                    printWriter.flush();
                    continue;

                }

                if (flag) {
                    word_occurance.putIfAbsent(clientWord, 0);
                    word_occurance.put(clientWord, word_occurance.get(clientWord) + 1);
                    if (word_occurance.get(clientWord) == 1)
                        printWriter.println("NEMA");
                    else
                        printWriter.println("IMA");

                    printWriter.flush();

                    stringBuilder.append(clientWord).append("\n");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String directoryPath = "chats";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }


        String relativePath = String.format("chats/%s.txt",socket.getPort());

        File file = new File(relativePath);

        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(stringBuilder.toString());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }







    }
}
