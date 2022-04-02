package server;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Database {
    private static final Database INSTANCE = new Database();
    private JsonObject jsonDB;
    private final String FILE_PATH = "src/server/data/db.json";
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private Database() {

    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public String parseCommand(String json) {
        JsonObject cmd = JsonParser.parseString(json).getAsJsonObject();
        String type = cmd.getAsJsonPrimitive("type").getAsString();

        switch (type) {
            case "set":
                return setCell(cmd);
            case "get":
                return getCell(cmd);
            case "delete":
                return deleteCell(cmd);
            case "exit":
                return closeServer(cmd);
            default:
                break;
        }
        JsonObject response = new JsonObject();
        response.addProperty("response", "ERROR");
        response.addProperty("reason", "Unknown command");
        return response.toString();
    }

    public synchronized String setCell(JsonObject cmd) {
        if (jsonDB == null) {
            readFile();
        }

        String key = cmd.getAsJsonPrimitive("key").getAsString();
        String value = cmd.getAsJsonPrimitive("value").getAsString();
        jsonDB.addProperty(key, value);
        writeFile();

        JsonObject response = new JsonObject();
        response.addProperty("response", "OK");
        return response.toString();
    }

    public String getCell(JsonObject cmd) {
        if (jsonDB == null) {
            readFile();
        }

        String key = cmd.getAsJsonPrimitive("key").getAsString();
        JsonObject response = new JsonObject();
        if (jsonDB.has(key)) {
                JsonElement jsonElement = jsonDB.getAsJsonPrimitive(key);
                String value = jsonElement.getAsString();
                response.addProperty("response", "OK");
                response.addProperty("value", value);
                return response.toString();
        } else {
                response.addProperty("response", "ERROR");
                response.addProperty("reason", "No such key");
                return response.toString();
        }
    }

    public synchronized String deleteCell(JsonObject cmd) {
        if (jsonDB == null) {
            readFile();
        }

        String key = cmd.getAsJsonPrimitive("key").getAsString();
        JsonObject response = new JsonObject();
        if (jsonDB.remove(key) != null) {
            response.addProperty("response", "OK");
            writeFile();
        } else {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        }
        return response.toString();
    }

    public synchronized String closeServer(JsonObject cmd) {
        JsonObject response = new JsonObject();
        if (cmd.getAsJsonPrimitive("type").getAsString().equals("exit")) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
        }
        return response.toString();
    }

    public synchronized void readFile() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            jsonDB = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    public synchronized void writeFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(jsonDB, writer);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
