package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * Tests for the Ministry class.
 */
public class TestMinistry {

    /**
     * Tests that the constructor sets all fields correctly.
     */
    @Test
    public void testConstructorAndGetters() {
        Ministry y = new Ministry(10, "Test Ministry",
                new BigDecimal("50.0"),
                new BigDecimal("30.0"),
                new BigDecimal("80.0"));

        assertEquals(10, y.getcode());
        assertEquals("Test Ministry", y.getName());
        assertEquals(new BigDecimal("50.0"), y.getRegularBudget());
        assertEquals(new BigDecimal("30.0"), y.getPublicInvestments());
        assertEquals(new BigDecimal("80.0"), y.getTotalBudget());
    }

    /**
     * Tests that the setters for code and name work correctly.
     */
    @Test
    public void testSetterscodeAndName() {
        Ministry y = new Ministry(1, "Old",
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        y.setcode(2);
        y.setName("New");

        assertEquals(2, y.getcode());
        assertEquals("New", y.getName());
    }

    /**
     * Tests that updating regularBudget and publicInvestments
     * correctly recalculates totalBudget.
     */
    @Test
    public void testRecalcTotalBudgetAfterSetters() {
        Ministry y = new Ministry(
            1,
            "Ministry",
            new BigDecimal("10"),
            new BigDecimal("5"),
            new BigDecimal("15"));

        y.setRegularBudget(new BigDecimal("20"));
        y.setPublicInvestments(new BigDecimal("30"));

        assertEquals(new BigDecimal("50"), y.getTotalBudget());
    }

    /**
     * Tests that setting totalBudget directly works.
     */
    @Test
    public void testSetTotalBudgetDirectly() {
        Ministry y = new Ministry(1, "Ministry",
            BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        y.setTotalBudget(new BigDecimal("999"));
        assertEquals(new BigDecimal("999"), y.getTotalBudget());
    }
    /**
     * Tests that toString returns something meaningful.
     */
    @Test
    public void testToString() {
        Ministry y = new Ministry(1, "X", new BigDecimal("10.0"), 
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
        Ministry y = new Ministry(
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
