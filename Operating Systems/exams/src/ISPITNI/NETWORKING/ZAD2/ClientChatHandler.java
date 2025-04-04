package ISPITNI.NETWORKING.ZAD2;

import java.io.*;
import java.net.Socket;

public class ClientChatHandler extends Thread{
    Socket socket;
    public ClientChatHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while (!(line = bufferedReader.readLine()).isEmpty()) {
                if (line.equals("xx_xxcax00")) {
                    break;
                }

                if (line.equals("history")) {
                    break;
                }
                printWriter.println(line);
                printWriter.flush();

                System.out.println(line);
                stringBuilder.append(line).append("\n");
            }

            String directoryPath = "chats";
            File directory = new File(directoryPath);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String relativePath = String.format("chats/%s.txt",socket.getPort());
            File file = new File(relativePath);

            FileWriter writer = new FileWriter(file);

            writer.write(stringBuilder.toString());
            writer.flush();

            if (line.equals("history")) {
                System.out.printf("%s, %s", file.getName(), file.length());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
