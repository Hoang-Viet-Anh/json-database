package server;

import com.google.gson.*;


public class Database {
    private static final Database INSTANCE = new Database();
    private final JsonObject jsonDB;
    private JsonObject cmd;

    private Database() {
        jsonDB = new JsonObject();
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public String setCell(JsonObject cmd) {
        JsonObject response = new JsonObject();
        String key = cmd.getAsJsonPrimitive("key").getAsString();
        if (jsonDB.has(key)) {
            jsonDB.remove("key");
        }
        JsonElement jsonElement = jsonDB.getAsJsonPrimitive(key);
        String value = cmd.getAsJsonPrimitive("value").getAsString();
        jsonDB.addProperty(key, value);

        response.addProperty("response", "OK");
        return response.toString();
    }

    public String parseCommand(String json) {
        cmd = JsonParser.parseString(json).getAsJsonObject();
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

    public String getCell(JsonObject cmd) {
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

    public String deleteCell(JsonObject cmd) {
        String key = cmd.getAsJsonPrimitive("key").getAsString();
        JsonObject response = new JsonObject();
        if (jsonDB.remove(key) != null) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        }
        return response.toString();
    }

    public String closeServer(JsonObject cmd) {
        JsonObject response = new JsonObject();
        if (cmd.getAsJsonPrimitive("type").getAsString().equals("exit")) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
        }
        return response.toString();
    }
}
