package server;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class Database {
    private static final Database INSTANCE = new Database();
    private JsonObject jsonDB;
    private final String FILE_PATH = "JSON Database/task/src/server/data/db.json";
    private final Gson gson = new GsonBuilder()
            //.setPrettyPrinting()
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

        JsonObject response = new JsonObject();
        if (cmd.get("key").isJsonArray()) {
            JsonArray array = cmd.getAsJsonArray("key");
            JsonObject object = jsonDB;
            String lastKey = array.get(array.size() - 1).getAsString();
            for (int i = 0; i < array.size() - 1; i++) {
                String keys = array.get(i).getAsString();
                if (object.has(keys)) {
                    if (object.get(keys).isJsonObject()) {
                        object = object.getAsJsonObject(keys);
                    } else {
                        object.add(keys, new JsonObject());
                        object = object.getAsJsonObject(keys);
                    }

                } else {
                    object.add(keys, new JsonObject());
                    object = object.getAsJsonObject(keys);
                }
            }
            object.add(lastKey, cmd.get("value"));
            response.addProperty("response", "OK");
            return gson.toJson(response);
        } else if (cmd.get("key").isJsonPrimitive()) {
            String key = cmd.getAsJsonPrimitive("key").getAsString();
            jsonDB.add(key, cmd.get("value"));
            response.addProperty("response", "OK");
            return response.toString();
        } else {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
            return response.toString();
        }
    }

    public String getCell(JsonObject cmd) {
        JsonElement jsonElement;
        if (jsonDB == null) {
            readFile();
        }

        JsonObject response = new JsonObject();
        if (cmd.get("key").isJsonArray()) {
            JsonArray array = cmd.getAsJsonArray("key");
            JsonObject object = jsonDB;
            String lastKey = array.get(array.size() - 1).getAsString();
            for (int i = 0; i < array.size() - 1; i++) {
                String keys = array.get(i).getAsString();
                if (object.has(keys)) {
                    object = object.get(keys).isJsonObject() ?
                            object.getAsJsonObject(keys) : object;

                } else {
                    object = null;
                    break;
                }
            }

            if (object != null && object.has(lastKey)) {
                response.addProperty("response", "OK");
                response.add("value", object.get(lastKey));
                return gson.toJson(response);
            }
        } else {
            String key = cmd.getAsJsonPrimitive("key").getAsString();
            if (jsonDB.has(key)) {
                jsonElement = jsonDB.getAsJsonPrimitive(key);
                response.addProperty("response", "OK");
                response.add("value", jsonElement);
                return response.toString();
            }
        }
        response.addProperty("response", "ERROR");
        response.addProperty("reason", "No such key");
        return response.toString();
    }

    public synchronized String deleteCell(JsonObject cmd) {
        if (jsonDB == null) {
            readFile();
        }

        JsonObject response = new JsonObject();
        if (cmd.get("key").isJsonArray()) {
            JsonArray array = cmd.getAsJsonArray("key");
            JsonObject object = jsonDB;
            String lastKey = array.get(array.size() - 1).getAsString();
            for (int i = 0; i < array.size() - 1; i++) {
                String keys = array.get(i).getAsString();
                if (object.has(keys)) {
                    object = object.get(keys).isJsonObject() ?
                            object.getAsJsonObject(keys) : object;

                } else {
                    object = null;
                    break;
                }
            }

            if (object != null && object.has(lastKey)) {
                object.remove(lastKey);
                response.addProperty("response", "OK");
                return gson.toJson(response);
            }
        } else {
            String key = cmd.getAsJsonPrimitive("key").getAsString();
            if (jsonDB.remove(key) != null) {
                writeFile();
                response.addProperty("response", "OK");
                return response.toString();
            }
        }
        response.addProperty("response", "ERROR");
        response.addProperty("reason", "No such key");
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
