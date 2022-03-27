package client;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Application {
    public static void startJSONDB() {
        Database database = Database.getInstance();
        View view = View.getInstance();

        Scanner scanner = new Scanner(System.in);
        Scanner scanner1;

        String line = "";
        String[] command;
        String text = null;
        String choice = null;
        int index = -1;
        boolean success = false;
        view.printMenu();
        while (true) {
            line = scanner.nextLine();

            try {
                scanner1 = new Scanner(line);
                choice = scanner1.next();

                switch (choice) {
                    case "set":
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
                        view.printSet(success);
                        break;
                    case "get":
                        if (scanner1.hasNextInt()) {
                            index = scanner1.nextInt();
                            view.printGet(database.getCell(index));
                        } else {
                            view.printGet("ERROR");
                        }
                        break;
                    case "delete":
                        if (scanner1.hasNextInt()) {
                            index = scanner1.nextInt();
                            success = database.deleteCell(index);
                        } else {
                            success = false;
                        }
                        view.printDelete(success);
                        break;
                    case "exit":
                        System.exit(0);
                    default:
                        view.printUnknownCommand();
                        break;
                }
            } catch (NoSuchElementException exc) {
                view.printUnknownCommand();
            }
        }
    }
}
