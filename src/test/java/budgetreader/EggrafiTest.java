package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Eggrafi class.
 */

public class EggrafiTest {

        /**
     * Tests that the constructor correctly assigns the fields.
     */
    @Test
    public void testConstructorAndGetters() {
        Eggrafi e = new Eggrafi("001", "Test entry", 100.5);

        assertEquals("001", e.getKodikos());
        assertEquals("Test entry", e.getPerigrafi());
        assertEquals(100.5, e.getPoso());
    }

    /**
     * Tests that toString returns a non-empty value.
     */
    @Test
    public void testToString() {
        Eggrafi e = new Eggrafi("002", "Example", 50.0);

        String text = e.toString();

        /* Basic check that toString works */
        assertEquals(true, text.contains("002"));
    }
}
