package aggregatedata;

import budgetreader.BasicRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for BudgetStats class */
class TestBudgetStats {

    private BasicRecord rev1, rev2, exp1, exp2;
    private List<BasicRecord> maxRevenues, minRevenues, maxExpenses, minExpenses;
    private List<BigDecimal> maxRevPercentages, maxExpPercentages;
    private BudgetStats budgetStats;

    @BeforeEach
    void setUp() {
        // Create sample BasicRecord objects
        rev1 = new BasicRecord("R1", "Revenue 1", new BigDecimal("1000"));
        rev2 = new BasicRecord("R2", "Revenue 2", new BigDecimal("2000"));
        exp1 = new BasicRecord("E1", "Expense 1", new BigDecimal("500"));
        exp2 = new BasicRecord("E2", "Expense 2", new BigDecimal("300"));

        // Initialize lists
        maxRevenues = new ArrayList<>();
        maxRevenues.add(rev2);

        minRevenues = new ArrayList<>();
        minRevenues.add(rev1);

        maxExpenses = new ArrayList<>();
        maxExpenses.add(exp1);

        minExpenses = new ArrayList<>();
        minExpenses.add(exp2);

        maxRevPercentages = new ArrayList<>();
        maxRevPercentages.add(new BigDecimal("50"));

        maxExpPercentages = new ArrayList<>();
        maxExpPercentages.add(new BigDecimal("30"));

        // Create BudgetStats instance
        budgetStats = new BudgetStats(maxRevenues, minRevenues, maxExpenses, minExpenses,
                                      maxRevPercentages, maxExpPercentages);
    }

    @Test
    void testGetMaxRevenues() {
        List<BasicRecord> result = budgetStats.getMaxRevenues();
        assertEquals(1, result.size());
        assertEquals("R2", result.get(0).getCode());

        // Ensure internal list is not modifiable
        result.add(new BasicRecord("R3", "Revenue 3", BigDecimal.ONE));
        assertEquals(1, budgetStats.getMaxRevenues().size());
    }

    @Test
    void testGetMinRevenues() {
        List<BasicRecord> result = budgetStats.getMinRevenues();
        assertEquals(1, result.size());
        assertEquals("R1", result.get(0).getCode());
    }

    @Test
    void testGetMaxExpenses() {
        List<BasicRecord> result = budgetStats.getMaxExpenses();
        assertEquals(1, result.size());
        assertEquals("E1", result.get(0).getCode());
    }

    @Test
    void testGetMinExpenses() {
        List<BasicRecord> result = budgetStats.getMinExpenses();
        assertEquals(1, result.size());
        assertEquals("E2", result.get(0).getCode());
    }

    @Test
    void testGetMaxRevenuePercentages() {
        List<BigDecimal> result = budgetStats.getMaxRevenuePercentages();
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("50"), result.get(0));
    }

    @Test
    void testGetMaxExpensePercentages() {
        List<BigDecimal> result = budgetStats.getMaxExpensePercentages();
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("30"), result.get(0));
    }
}
