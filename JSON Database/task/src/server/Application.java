package server;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {
    private static Database database;
    private static View view;

    public static String startJSONDB(String command) {
        database = Database.getInstance();
        view = View.getInstance();

        return database.parseCommand(command);
    }
}
