package persistence;

import model.Entry;
import model.MyJournal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MyJournal mj = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            MyJournal mj = reader.read();
            assertEquals(0, mj.getNumEntries());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            MyJournal mj = reader.read();
            ArrayList<Entry> entries = mj.getAllEntries();
            assertEquals(2, mj.getNumEntries());

            checkEntry("First Entry", "This is my first entry. So exciting!",
                    "October 25, 2022", entries.get(0));

            checkEntry("Second Entry", "This is my second entry. Whoo!",
                    "October 26, 2022", entries.get(1));
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }
}
