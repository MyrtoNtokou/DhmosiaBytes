package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Ypourgeio class.
 */
public class YpourgeioTest {

    /**
     * Tests that the constructor sets all fields correctly.
     */
    @Test
    public void testConstructorAndGetters() {
        Ypourgeio y = new Ypourgeio(10, "Test Ministry",
                50.0, 30.0, 80.0);

        assertEquals(10, y.getKodikos());
        assertEquals("Test Ministry", y.getOnoma());
        assertEquals(50.0, y.getTaktikos());
        assertEquals(30.0, y.getEpendysewn());
        assertEquals(80.0, y.getSynolo());
    }

    /**
     * Tests that toString returns something meaningful.
     */
    @Test
    public void testToString() {
        Ypourgeio y = new Ypourgeio(1, "X", 10.0, 5.0, 15.0);

        String text = y.toString();

        /* Simple check */
        assertEquals(true, text.contains("1"));
    }
}
