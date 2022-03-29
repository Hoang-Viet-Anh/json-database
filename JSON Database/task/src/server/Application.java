package server;

import server.Database;
import server.View;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {
    private static Database database;
    private static View view;

    public static String startJSONDB(String command) {
        database = Database.getInstance();
        view = View.getInstance();

        Scanner scanner1;

        String line = "";
        String choice = null;

       // view.printMenu();

       // while (true) {
            line = command;

            try {
                scanner1 = new Scanner(line);
                choice = scanner1.next();

                switch (choice) {
                    case "set":
                        return setCommand(scanner1);
                    case "get":
                        return getCommand(scanner1);
                    case "delete":
                        return deleteCommand(scanner1);
                    case "exit":
                        return "exit";
                    default:
                        return "Unknown command.";
                }
            } catch (NoSuchElementException exc) {
                view.printUnknownCommand();
            }
        //}
        return "";
    }

    private static String setCommand(Scanner scanner1) {
        String text;
        int index;
        boolean success;

        if (scanner1.hasNextInt()) {
            index = scanner1.nextInt();
            if (scanner1.hasNext()) {
                text = scanner1.nextLine().trim();
                success = database.setCell(text, index);
            } else {
                success = false;
            }
        } else {
            success = false;
        }
        return success ? "OK" : "ERROR";
    }

    private static String getCommand(Scanner scanner1) {
        int index;

        if (scanner1.hasNextInt()) {
            index = scanner1.nextInt();
            return  database.getCell(index);
        } else {
            return "ERROR";
        }
    }

    private static String deleteCommand(Scanner scanner1) {
        int index;
        boolean success;

        if (scanner1.hasNextInt()) {
            index = scanner1.nextInt();
            success = database.deleteCell(index);
        } else {
            success = false;
        }
        return success ? "OK" : "ERROR";
    }
}
