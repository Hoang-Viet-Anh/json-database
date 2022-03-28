package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static final int PORT = 34522;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            Session session = new Session(server.accept());
            session.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
