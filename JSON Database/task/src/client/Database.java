package client;

import java.util.ArrayList;

public class Database {
    private static final Database INSTANCE = new Database();
    ArrayList<String> database = new ArrayList<>(100);

    private Database() {
        for (int i = 0; i < 100; i++) {
            database.add(null);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public boolean setCell(String text, int index) {
        try {
            if (!text.equals("")) {
                database.set(index - 1, text);
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException exc) {
            return false;
        }
    }

    public String getCell(int index) {
        try {
            String cell = database.get(index - 1);
            return cell == null ? "ERROR" : cell;
        } catch (IndexOutOfBoundsException exc) {
            return "ERROR";
        }
    }

    public boolean deleteCell(int index) {
        try {
            database.set(index - 1, null);
            return true;
        } catch (IndexOutOfBoundsException exc) {
            return false;
        }
    }
}
