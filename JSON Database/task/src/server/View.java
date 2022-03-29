package server;

public class View {
    private static final View INSTANCE = new View();

    private View() {}

    public static View getInstance() {
        return INSTANCE;
    }

    public void printMenu() {
        System.out.println("set index text - set text by index of db.\n" +
                "get index - get text by index of db.\n" +
                "delete index - delete text by index of db.");
    }

    public void printSet(boolean set) {
        System.out.println(set ? "OK" : "ERROR");
    }

    public void printGet(String cell) {
        System.out.println(cell);
    }

    public void printDelete(boolean delete) {
        System.out.println(delete ? "OK" : "ERROR");
    }

    public void printUnknownCommand() {
        System.out.println("Unknown command.");
    }
}
