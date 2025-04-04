package ISPITNI.NETWORKING.ZAD1;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class ClientHandler extends Thread {
    Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());

            String line = "";
            boolean flag = false;

            HashMap<String, Integer> words = new HashMap<>();
            StringBuilder stringBuilder = new StringBuilder();
            while (!(line = bufferedReader.readLine()).isEmpty()) {
                if (line.equals("0x79cc_jkaax_*"))
                    break;

                if (line.equals("HANDSHAKE")) {
                    flag = true;
                    System.out.println("HANDSHAKE");
                }

                if (flag) {
                    words.putIfAbsent(line, 0);
                    words.put(line, words.get(line) + 1);
                    if (words.get(line) == 1) {
                        printWriter.println("NEMA");
                        stringBuilder.append(line).append(" ").append(LocalDateTime.now()).append(" ").append(socket.getLocalAddress()).append("\n");
                    }

                    else
                        printWriter.println("IMA");
                    printWriter.flush();
                }

            }

            String relativePath = String.format("%s.txt",socket.getPort());
            System.out.println(relativePath);

            File file = new File(relativePath);

            FileWriter writer = new FileWriter(file);

            writer.write(stringBuilder.toString());
            writer.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
