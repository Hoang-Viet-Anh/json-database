package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread{
    private final Socket socket;

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    public void run() {
        String received = null;
        String sent = null;

        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            received = input.readUTF();
            System.out.println("Received: " + received);
            if (received.contains("Give me a record # ")) {
                received = received.substring(received.indexOf("#") + 2);
                sent = String.format("A record # %s was sent!", received);
            } else {
                sent = "Unknown command.";
            }
            System.out.println("Sent: " + sent);
            output.writeUTF(sent);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
