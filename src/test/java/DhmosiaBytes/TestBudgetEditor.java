package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetreader.Ministry;

class TestBudgetEditor {

    private BudgetEditor editor;

    @BeforeEach
    void setUp() {
        editor = new BudgetEditor();
    }

    @Test
    void testMinistryBudgetUpdate() {
        Ministry ministry = new Ministry(
                1,
                "Υπουργείο Δοκιμής",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(1500)
        );

        ministry.setRegularBudget(ministry.getRegularBudget().add(BigDecimal.valueOf(200)));
        assertEquals(BigDecimal.valueOf(1200), ministry.getRegularBudget());
        assertEquals(BigDecimal.valueOf(1700), ministry.getTotalBudget());

        ministry.setPublicInvestments(ministry.getPublicInvestments().add(BigDecimal.valueOf(100)));
        assertEquals(BigDecimal.valueOf(600), ministry.getPublicInvestments());
        assertEquals(BigDecimal.valueOf(1800), ministry.getTotalBudget());
    }

    @Test
    void testAllocationEntries() {
        Ministry ministry = new Ministry(2, "Υπουργείο Δοκιμής 2", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        ministry.setAllocationEntry("Κατηγορία Α", BigDecimal.valueOf(0.4));
        ministry.setAllocationEntry("Κατηγορία Β", BigDecimal.valueOf(0.6));

        Map<String, BigDecimal> allocation = ministry.getAllocation();
        assertEquals(2, allocation.size());
        assertEquals(BigDecimal.valueOf(0.4), allocation.get("Κατηγορία Α"));
        assertEquals(BigDecimal.valueOf(0.6), allocation.get("Κατηγορία Β"));
    }

    @Test
    void testToString() {
        Ministry ministry = new Ministry(3, "Υπουργείο Δοκιμής 3", BigDecimal.valueOf(500), BigDecimal.valueOf(300), BigDecimal.valueOf(800));
        String expected = "3 | Υπουργείο Δοκιμής 3 | 500 | 300 | 800";
        assertEquals(expected, ministry.toString());
    }
}
