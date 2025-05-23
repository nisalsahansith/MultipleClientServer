package Main;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String input;
            while ((input = in.readLine()) != null) {
                System.out.println("Command from client: " + input);
                switch (input.toUpperCase()) {
                    case "TIME":
                        out.println("Main.Server Time: " + LocalTime.now());
                        break;
                    case "DATE":
                        out.println("Today's Date: " + LocalDate.now());
                        break;
                    case "UPTIME":
                        long uptime = (System.currentTimeMillis() - ApplicationServer.serverStartTime) / 1000;
                        out.println("Main.Server Uptime: " + uptime + " seconds");
                        break;
                    case "HELP":
                        out.println("Available Commands: TIME, DATE, UPTIME, BYE, HELP");
                        break;
                    case "BYE":
                        out.println("Connection closed.");
                        socket.close();
                        return;
                    default:
                        out.println("Unknown command. Type HELP.");
                }
            }

        } catch (IOException e) {
            System.out.println("Main.Client disconnected.");
        }
    }
}
