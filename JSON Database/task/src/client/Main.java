/*
    You should execute server.Main first then client.Main after
 */

package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) {
        String msg = null;
        Command cmd = new Command();
        JCommander.newBuilder()
                .addObject(cmd)
                .build()
                .parse(args);
        Gson gson = new GsonBuilder()
              //  .setPrettyPrinting()
                .create();
        if (cmd.fileName != null) {
            try (FileReader reader = new FileReader("JSON Database/task/src/client/data/" + cmd.fileName)) {
                msg = gson.toJson(JsonParser.parseReader(reader));
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            msg = gson.toJson(cmd);
        }

        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {

            System.out.println("Client started!");

            System.out.println("Sent: " + msg);
            output.writeUTF(msg);
            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
