package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryTest {
    private Entry testEntry;

    @BeforeEach
    public void runBefore() {
        testEntry = new Entry("A Test Entry", "I'm testing my program!", "October 11, 2022");
    }

    @Test
    public void testConstructor() {
        assertEquals("A Test Entry", testEntry.getTitle());
        assertEquals("A Test Entry (October 11, 2022)\nI'm testing my program!", testEntry.getEntry());
        assertEquals("A Test Entry (October 11, 2022)", testEntry.getTitleAndDate());
    }

}