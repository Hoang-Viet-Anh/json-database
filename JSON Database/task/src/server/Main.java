package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static final int PORT = 34522;
    public static boolean loop = true;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {

            while (loop) {
                System.out.println("Server started!");
                Session session = new Session(server.accept());
                session.start();
                session.join();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
