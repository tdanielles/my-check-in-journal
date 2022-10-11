package model;

import java.util.ArrayList;

// Represents a journal containing entries
public class MyJournal {
    private ArrayList<Entry> journal;

    // EFFECTS: Constructs a new empty journal
    public MyJournal() {
        this.journal = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds an entry to the journal
    public void addEntry(Entry e) {
        this.journal.add(e);
    }

    // REQUIRES: Journal is not empty
    // MODIFIES: this
    // EFFECTS: Removes the entry from the journal if it is in the journal
    public boolean deleteEntry(Entry e) {
        if (this.journal.contains(e)) {
            this.journal.remove(e);
            return true;
        } else {
            return false;
        }
    }

    public int getNumEntries() {
        return this.journal.size();
    }

    public ArrayList<Entry> getAllEntries() {
        return this.journal;
    }
}
