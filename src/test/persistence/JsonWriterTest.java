package persistence;

import model.Entry;
import model.MyJournal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            MyJournal mj = new MyJournal();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            MyJournal mj = new MyJournal();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(mj);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            mj = reader.read();
            assertEquals(0, mj.getNumEntries());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            MyJournal mj = new MyJournal();

            mj.addEntry(new Entry("Third Entry", "My third entry! It's for a test.",
                    "October 27, 2022"));
            mj.addEntry(new Entry("Fourth Entry", "My fourth entry! It's for another test.",
                    "October 27, 2022"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(mj);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            mj = reader.read();
            ArrayList<Entry> entries = mj.getAllEntries();
            assertEquals(2, mj.getNumEntries());

            checkEntry("Third Entry", "My third entry! It's for a test.",
                    "October 27, 2022", entries.get(0));

            checkEntry("Fourth Entry", "My fourth entry! It's for another test.",
                    "October 27, 2022", entries.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
