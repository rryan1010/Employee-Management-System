package src;

public class Main {
    public static void main(String[] args) {
        Database.createTables();
        new LoginGUI();
    }
}