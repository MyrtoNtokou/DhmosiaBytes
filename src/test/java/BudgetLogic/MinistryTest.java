package budgetlogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Map;

class MinistryTest {

    @Test
    void testConstructorAndGetters() {
        Ministry m = new Ministry(
                10,
                "Υπουργείο Test",
                new BigDecimal("100"),
                new BigDecimal("200"),
                new BigDecimal("300")
        );

        assertEquals(10, m.getKodikos());
        assertEquals("Υπουργείο Test", m.getOnoma());
        assertEquals(new BigDecimal("100"), m.getTaktikos());
        assertEquals(new BigDecimal("200"), m.getPde());
        assertEquals(new BigDecimal("300"), m.getSynolo());
    }

    @Test
    void testSetTaktikosRecalculatesSynolo() {
        Ministry m = new Ministry(
                1, "Test",
                new BigDecimal("50"),
                new BigDecimal("50"),
                new BigDecimal("100")
        );

        m.setTaktikos(new BigDecimal("80"));

        assertEquals(new BigDecimal("80"), m.getTaktikos());
        assertEquals(new BigDecimal("130"), m.getSynolo()); // 80 + 50
    }

    @Test
    void testSetPdeRecalculatesSynolo() {
        Ministry m = new Ministry(
                1, "Test",
                new BigDecimal("40"),
                new BigDecimal("60"),
                new BigDecimal("100")
        );

        m.setPde(new BigDecimal("90"));

        assertEquals(new BigDecimal("90"), m.getPde());
        assertEquals(new BigDecimal("130"), m.getSynolo()); // 40 + 90
    }

    @Test
    void testSetSynoloOverridesCalculatedValue() {
        Ministry m = new Ministry(
                1, "Test",
                new BigDecimal("10"),
                new BigDecimal("20"),
                new BigDecimal("30")
        );

        m.setSynolo(new BigDecimal("999"));

        assertEquals(new BigDecimal("999"), m.getSynolo());
    }

    @Test
    void testAllocationSetterAndCopyGetter() {
        Ministry m = new Ministry(
                1, "Test",
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );

        m.setAllocationEntry("A1", new BigDecimal("10"));
        m.setAllocationEntry("A2", new BigDecimal("20"));

        Map<String, BigDecimal> copy = m.getAllocation();

        assertEquals(2, copy.size());
        assertEquals(new BigDecimal("10"), copy.get("A1"));

        // Modify copy → original must NOT change
        copy.clear();

        assertEquals(2, m.getAllocation().size());
    }

    @Test
    void testRecalcSynoloHandlesNulls() {
        Ministry m = new Ministry(
                1, "Test",
                null,
                null,
                BigDecimal.ZERO
        );

        // Force recalculation:
        m.setTaktikos(null); // set null -> recalcSynolo runs

        assertEquals(BigDecimal.ZERO, m.getTaktikos());
        assertEquals(BigDecimal.ZERO, m.getPde());
        assertEquals(BigDecimal.ZERO, m.getSynolo());
    }
}
