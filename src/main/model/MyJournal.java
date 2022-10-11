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
    // EFFECTS: Adds an entry to the journal if title is not in journal
    public boolean addEntry(Entry e) {
        for (Entry e1 : this.journal) {
            if (e1.getTitle().equals(e.getTitle())) {
                return false;
            }
        }
        this.journal.add(e);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Removes the entry from the journal if it is in the journal
    public boolean deleteEntry(String title) {
        for (Entry e : this.journal) {
            if (e.getTitle().equals(title)) {
                this.journal.remove(e);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns an Entry object from the journal given the title; otherwise, returns null
    public Entry getSpecificEntry(String title) {
        for (Entry e : this.journal) {
            if (e.getTitle().equals(title)) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: Returns the number of entries in the journal
    public int getNumEntries() {
        return this.journal.size();
    }

    // EFFECTS: Returns the list of entries in the journal
    public ArrayList<Entry> getAllEntries() {
        return this.journal;
    }
}
