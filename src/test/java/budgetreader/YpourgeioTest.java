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
     * Tests that the setters for kodikos and onoma work correctly.
     */
    @Test
    public void testSettersKodikosAndOnoma() {
        Ypourgeio y = new Ypourgeio(1, "Old",
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        y.setKodikos(2);
        y.setOnoma("New");

        assertEquals(2, y.getKodikos());
        assertEquals("New", y.getOnoma());
    }

    /**
     * Tests that updating taktikos and ependyseis
     * correctly recalculates synolo.
     */
    @Test
    public void testRecalcSynoloAfterSetters() {
        Ypourgeio y = new Ypourgeio(
            1,
            "Ministry",
            new BigDecimal("10"),
            new BigDecimal("5"),
            new BigDecimal("15"));

        y.setTaktikos(new BigDecimal("20"));
        y.setEpendyseis(new BigDecimal("30"));

        assertEquals(new BigDecimal("50"), y.getSynolo());
    }

    /**
     * Tests that setting synolo directly works.
     */
    @Test
    public void testSetSynoloDirectly() {
        Ypourgeio y = new Ypourgeio(1, "Ministry",
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        y.setSynolo(new BigDecimal("999"));
        assertEquals(new BigDecimal("999"), y.getSynolo());
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

    /**
     * Tests that allocation entries are added correctly.
     */
    @Test
    public void testAllocationEntry() {
        Ypourgeio y = new Ypourgeio(
            1,
            "Ministry",
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            BigDecimal.ZERO);

        y.setAllocationEntry("Health", new BigDecimal("25.5"));
        assertEquals(1, y.getAllocation().size());
        assertEquals(new BigDecimal("25.5"), y.getAllocation().get("Health"));
}



}
