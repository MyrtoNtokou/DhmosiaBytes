package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

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
        Eggrafi e = new Eggrafi("001", "Test entry", new BigDecimal("100.5"));

        assertEquals("001", e.getKodikos());
        assertEquals("Test entry", e.getPerigrafi());
        assertEquals(new BigDecimal("100.5"), e.getPoso());
    }

    /**
     * Tests that toString returns a non-empty value.
     */
    @Test
    public void testToString() {
        Eggrafi e = new Eggrafi(
            "002",
            "Example",
            new BigDecimal(50.0));

        String text = e.toString();

        /* Basic check that toString works */
        assertEquals(true, text.contains("002"));
    }

    /**
     * Tests that setters correctly update the fields.
     */
    @Test
    public void testSetters() {
        Eggrafi e = new Eggrafi(
            "001",
            "Old",
            new BigDecimal("10"));

        e.setKodikos("002");
        e.setPerigrafi("New");
        e.setPoso(new BigDecimal("20.50"));

        assertEquals("002", e.getKodikos());
        assertEquals("New", e.getPerigrafi());
        assertEquals(new BigDecimal("20.50"), e.getPoso());
    }

    @Test
    public void testToStringWithDecimalAmount() {
        Eggrafi e = new Eggrafi(
            "004",
            "Decimal",
            new BigDecimal("1234.56"));

        assertEquals("004 | Decimal | 1.235", e.toString());
}
}
