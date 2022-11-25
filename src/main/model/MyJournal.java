package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a journal containing entries
public class MyJournal implements Writable {
    private ArrayList<Entry> entries;

    // EFFECTS: Constructs a new empty journal
    public MyJournal() {
        this.entries = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds an entry to the journal if title is not in journal; otherwise, return false
    public boolean addEntry(Entry e) {
        for (Entry e1 : this.entries) {
            if (e1.getTitle().equals(e.getTitle())) {
                return false;
            }
        }
        this.entries.add(e);
        String title = e.getTitle();
        EventLog.getInstance().logEvent(new Event("Entry with title " + title + " added to journal!"));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Removes the entry from the journal if it is in the journal and return true;
    // otherwise, do nothing and return false
    public boolean deleteEntry(String title) {
        EventLog.getInstance().logEvent(new Event("Entry with title " + title + " deleted from journal!"));
        for (Entry e : this.entries) {
            if (e.getTitle().equals(title)) {
                this.entries.remove(e);
                return true;
            }
        }

        return false;
    }

    // EFFECTS: Returns an Entry object from the journal given the title; otherwise, return null
    public Entry getSpecificEntry(String title) {
        for (Entry e : this.entries) {
            if (e.getTitle().equals(title)) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: Returns the number of entries in the journal
    public int getNumEntries() {
        return this.entries.size();
    }

    // EFFECTS: Returns the list of entries in the journal
    public ArrayList<Entry> getAllEntries() {
        return this.entries;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("entries", entriesToJson());
        return json;
    }

    // EFFECTS: Returns the entries in this journal as a JSON array
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Entry e : entries) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }
}
