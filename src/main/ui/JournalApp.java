package ui;

import model.Entry;
import model.MyJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Journal application
public class JournalApp {
    private static final String JSON_STORE = "./data/journal.json";
    private MyJournal journal;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs the journal application
    public JournalApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        journal = new MyJournal();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input
    private void runJournal() {
        boolean continueProgram = true;
        String command = null;
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (continueProgram) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                continueProgram = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: Processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doViewJournal();
        } else if (command.equals("s")) {
            doSeeEntry();
        } else if (command.equals("a")) {
            doAddEntry();
        } else if (command.equals("d")) {
            doDeleteEntry();
        } else if (command.equals("z")) {
            doSaveEntry();
        } else if (command.equals("l")) {
            doLoadEntry();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: Displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view the titles of all entries in your journal");
        System.out.println("\ts -> view a specific entry in your journal");
        System.out.println("\ta -> add an entry to your journal");
        System.out.println("\td -> delete an entry from your journal");
        System.out.println("\tz -> save your entries to file");
        System.out.println("\tl -> load your entries from file");
        System.out.println("\tq -> exit the program");
    }

    // EFFECTS: Displays the title and date per entry in the journal except if the journal is empty
    public void doViewJournal() {
        if (journal.getNumEntries() == 0) {
            System.out.println("Sorry, it looks like your journal doesn't have any entries.");
        } else {
            ArrayList<Entry> entries = journal.getAllEntries();
            for (Entry e : entries) {
                System.out.println(e.getTitleAndDate());
            }
        }
    }

    // EFFECTS: Allows user to see a specific entry they choose
    public void doSeeEntry() {
        System.out.println("What is the title of the entry you want to view?");
        String title = input.next();

        if (journal.getSpecificEntry(title) != null) {
            Entry e = journal.getSpecificEntry(title);
            System.out.println(e.getEntry());
        } else {
            System.out.println("Sorry, this entry does not exist.");
        }
    }

    // EFFECTS: Returns an entry of the user's specifications
    public Entry createEntry() {
        System.out.println("What title would you want to give this entry?");
        String title = input.next();
        System.out.println("What date is it today? Please input the date in Month Day, Year format "
                + "(e.g. October 11, 2022).");
        String date = input.next();
        System.out.println("What's on your mind today?");
        String content = input.next();

        return new Entry(title, content, date);
    }

    // MODIFIES: this
    // EFFECTS: Adds entry to journal if entry title doesn't exist yet
    public void doAddEntry() {
        Entry e = createEntry();
        if (!journal.addEntry(e)) {
            System.out.println("Sorry, an entry with the same title already exists in your journal.");
        } else {
            System.out.println("You have successfully added a new entry!");
        }
    }

    // MODIFIES: this
    // EFFECTS: If entry is in journal, delete entry; otherwise, do nothing
    public void doDeleteEntry() {
        System.out.println("What is the title of the entry you want to delete?");
        String title = input.next();

        if (!journal.deleteEntry(title)) {
            System.out.println("There is no entry in the journal with title, " + title + ".");
        } else {
            System.out.println("You have successfully deleted an entry!");
        }
    }

    // EFFECTS: saves keychain to file
    private void doSaveEntry() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Successfully saved journal to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the journal from file
    private void doLoadEntry() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded your entries from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
