package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyJournalTest {
    private Entry first;
    private Entry second;
    private Entry third;
    private MyJournal testJournal;

    @BeforeEach
    void runBefore() {
        first = new Entry("My first entry", "I'm so happy!", "October 1, 2022");
        second = new Entry("My second entry", "I'm so sad.", "October 2, 2022");
        third = new Entry("My third entry", "I feel awesome!", "October 3, 2022");
        testJournal = new MyJournal();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testJournal.getNumEntries());
    }

    @Test
    void testGetNumEntries() {
        testJournal.addEntry(first);
        assertEquals(1, testJournal.getNumEntries());
        testJournal.addEntry(second);
        assertEquals(2, testJournal.getNumEntries());
    }

    @Test
    void testAddEntry() {
        testJournal.addEntry(first);
        assertEquals(1, testJournal.getNumEntries());
        testJournal.addEntry(second);
        assertEquals(2, testJournal.getNumEntries());
    }

    @Test
    void testDeleteEntry() {
        testJournal.addEntry(first);
        testJournal.addEntry(second);

        assertFalse(testJournal.deleteEntry(third));

        assertTrue(testJournal.deleteEntry(first));
        assertEquals(1, testJournal.getNumEntries());

        assertTrue(testJournal.deleteEntry(second));
        assertEquals(0, testJournal.getNumEntries());
    }
}
