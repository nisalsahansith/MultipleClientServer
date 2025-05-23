package Main;

import java.io.*;
import java.net.Socket;

public class ApplicationClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Thread receiver = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Main.Server: " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });

            receiver.start();

            String command;
            while ((command = userInput.readLine()) != null) {
                out.println(command);
                if (command.equalsIgnoreCase("BYE")) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
