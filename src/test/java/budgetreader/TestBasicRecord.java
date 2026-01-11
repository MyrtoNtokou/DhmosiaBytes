package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * Tests for the BasicRecord class.
 */

public class TestBasicRecord {

        /**
     * Tests that the constructor correctly assigns the fields.
     */
    @Test
    public void testConstructorAndGetters() {
        BasicRecord e = new BasicRecord("001", "Test entry", new BigDecimal("100.5"));

        assertEquals("001", e.getCode());
        assertEquals("Test entry", e.getDescription());
        assertEquals(new BigDecimal("100.5"), e.getAmount());
    }

    /**
     * Tests that toString returns a non-empty value.
     */
    @Test
    public void testToString() {
        BasicRecord e = new BasicRecord(
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
        BasicRecord e = new BasicRecord(
            "001",
            "Old",
            new BigDecimal("10"));

        e.setCode("002");
        e.setDescription("New");
        e.setAmount(new BigDecimal("20.50"));

        assertEquals("002", e.getCode());
        assertEquals("New", e.getDescription());
        assertEquals(new BigDecimal("20.50"), e.getAmount());
    }
    
    /**
     * Tests that toString formats amount with decimals correctly.
     */
    @Test
    public void testToStringWithDecimalAmount() {
        BasicRecord e = new BasicRecord(
            "004",
            "Decimal",
            new BigDecimal("1234.56"));

        String text = e.toString();
        assertTrue(text.matches("004 \\| Decimal \\| 1[.,]235"));
    }
}
