package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBudgetType {

    @Test
    void testGetTypeCode() {
        assertEquals(1, BudgetType.TAKTIKOS.getTypeCode());
        assertEquals(2, BudgetType.DHMOSION_EPENDYSEON.getTypeCode());
    }

    @Test
    void testGetTypeDescription() {
        assertEquals("Τακτικός Προυπολογισμός",
                BudgetType.TAKTIKOS.getTypeDescription());

        assertEquals("Προϋπολογισμός Δημοσίων Επενδύσεων",
                BudgetType.DHMOSION_EPENDYSEON.getTypeDescription());
    }

    @Test
    void testFromCodeValid() {
        assertEquals(BudgetType.TAKTIKOS, BudgetType.fromCode(1));
        assertEquals(BudgetType.DHMOSION_EPENDYSEON, BudgetType.fromCode(2));
    }

    @Test
    void testFromCodeInvalidThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> BudgetType.fromCode(99));

        assertTrue(ex.getMessage().contains("Μη έγκυρη επιλογή"));
    }
}
