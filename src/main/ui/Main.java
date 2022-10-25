package ui;

import java.io.FileNotFoundException;

// EFFECTS: main class used to run journal application
public class Main {
    public static void main(String[] args) {
        try {
            new JournalApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: File not found");
        }
    }
}
