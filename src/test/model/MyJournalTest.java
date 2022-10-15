package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MyJournalTest {
    private Entry first;
    private Entry second;
    private Entry third;
    private Entry testEntry;
    private MyJournal testJournal;

    @BeforeEach
    public void runBefore() {
        first = new Entry("My first entry", "I'm so happy!", "October 1, 2022");
        second = new Entry("My second entry", "I'm so sad.", "October 2, 2022");
        third = new Entry("My third entry", "I feel awesome!", "October 3, 2022");
        testEntry = new Entry("My first entry", "Hi", "Bye");

        testJournal = new MyJournal();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testJournal.getNumEntries());
    }

    @Test
    public void testGetNumEntries() {
        testJournal.addEntry(first);
        assertEquals(1, testJournal.getNumEntries());
        testJournal.addEntry(second);
        assertEquals(2, testJournal.getNumEntries());
    }

    @Test
    public void testAddEntry() {
        assertTrue(testJournal.addEntry(first));
        assertEquals(1, testJournal.getNumEntries());

        assertFalse(testJournal.addEntry(testEntry));
        assertEquals(1, testJournal.getNumEntries());
    }

    @Test
    public void testDeleteEntry() {
        assertTrue(testJournal.addEntry(first));
        assertTrue(testJournal.addEntry(second));

        assertFalse(testJournal.deleteEntry("My third entry"));

        assertTrue(testJournal.deleteEntry("My first entry"));
        assertEquals(1, testJournal.getNumEntries());

        assertTrue(testJournal.deleteEntry("My second entry"));
        assertEquals(0, testJournal.getNumEntries());
    }

    @Test
    public void testGetSpecificEntry() {
        assertEquals(null, testJournal.getSpecificEntry("Title that's not in journal"));

        testJournal.addEntry(first);
        assertEquals(first, testJournal.getSpecificEntry("My first entry"));

        testJournal.addEntry(second);
        testJournal.addEntry(third);
        assertEquals(third, testJournal.getSpecificEntry("My third entry"));
    }

    @Test
    public void testGetAllEntries() {
        ArrayList<Entry> sampleJournal = new ArrayList<>();

        assertEquals(sampleJournal, testJournal.getAllEntries());

        sampleJournal.add(first);
        assertTrue(testJournal.addEntry(first));
        assertEquals(sampleJournal, testJournal.getAllEntries());

        sampleJournal.add(second);
        assertTrue(testJournal.addEntry(second));
        assertEquals(sampleJournal, testJournal.getAllEntries());
    }
}
