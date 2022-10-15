package model;

// Represents a journal entry having a title, content, and date
public class Entry {
    private String title;
    private String content;
    private String date;

    // REQUIRES: title, content, and date to have non-zero lengths
    // EFFECTS: Creates a new entry
    public Entry(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    // EFFECTS: Returns the title of the entry
    public String getTitle() {
        return this.title;
    }

    // EFFECTS: Returns the formatted title and date of the entry
    public String getTitleAndDate() {
        return this.title + " (" + this.date + ")";
    }

    // EFFECTS: Returns the whole entry formatted
    public String getEntry() {
        return this.title + " (" + this.date + ")\n" + this.content;
    }
}
