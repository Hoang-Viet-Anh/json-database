package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {
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
            sent = Application.startJSONDB(received);
            if (received.contains("exit")) {
                System.out.println("Sent: " + sent);
                output.writeUTF(sent);
                socket.close();
                Main.loop = false;
            } else {
                System.out.println("Sent: " + sent);
                output.writeUTF(sent);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
