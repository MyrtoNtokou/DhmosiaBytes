package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

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
                new BigDecimal("50.0"),
                new BigDecimal("30.0"),
                new BigDecimal("80.0"));

        assertEquals(10, y.getKodikos());
        assertEquals("Test Ministry", y.getOnoma());
        assertEquals(new BigDecimal("50.0"), y.getTaktikos());
        assertEquals(new BigDecimal("30.0"), y.getEpendyseis());
        assertEquals(new BigDecimal("80.0"), y.getSynolo());
    }

    /**
     * Tests that toString returns something meaningful.
     */
    @Test
    public void testToString() {
        Ypourgeio y = new Ypourgeio(1, "X", new BigDecimal("10.0"), 
        new BigDecimal("5.0"), new BigDecimal("15.0"));

        String text = y.toString();

        /* Simple check */
        assertEquals(true, text.contains("1"));
    }
}
