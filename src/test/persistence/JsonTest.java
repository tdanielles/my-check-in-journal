package persistence;

import model.Entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkEntry(String title, String content, String date, Entry entry) {
        assertEquals(title, entry.getTitle());
        assertEquals(title + " (" + date + ")", entry.getTitleAndDate());
        assertEquals(title + " (" + date + ")\n" + content, entry.getEntry());
    }
}
