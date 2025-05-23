package Main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationServer {
    private static final int PORT = 12345;
    public static long serverStartTime = System.currentTimeMillis();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Main.Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
