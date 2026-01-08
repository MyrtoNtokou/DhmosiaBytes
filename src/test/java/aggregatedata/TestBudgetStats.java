package aggregatedata;

import budgetreader.Eggrafi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for BudgetStats class */
class TestBudgetStats {

    private Eggrafi rev1, rev2, exp1, exp2;
    private List<Eggrafi> maxRevenues, minRevenues, maxExpenses, minExpenses;
    private List<BigDecimal> maxRevPercentages, maxExpPercentages;
    private BudgetStats budgetStats;

    @BeforeEach
    void setUp() {
        // Create sample Eggrafi objects
        rev1 = new Eggrafi("R1", "Revenue 1", new BigDecimal("1000"));
        rev2 = new Eggrafi("R2", "Revenue 2", new BigDecimal("2000"));
        exp1 = new Eggrafi("E1", "Expense 1", new BigDecimal("500"));
        exp2 = new Eggrafi("E2", "Expense 2", new BigDecimal("300"));

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
        List<Eggrafi> result = budgetStats.getMaxRevenues();
        assertEquals(1, result.size());
        assertEquals("R2", result.get(0).getKodikos());

        // Ensure internal list is not modifiable
        result.add(new Eggrafi("R3", "Revenue 3", BigDecimal.ONE));
        assertEquals(1, budgetStats.getMaxRevenues().size());
    }

    @Test
    void testGetMinRevenues() {
        List<Eggrafi> result = budgetStats.getMinRevenues();
        assertEquals(1, result.size());
        assertEquals("R1", result.get(0).getKodikos());
    }

    @Test
    void testGetMaxExpenses() {
        List<Eggrafi> result = budgetStats.getMaxExpenses();
        assertEquals(1, result.size());
        assertEquals("E1", result.get(0).getKodikos());
    }

    @Test
    void testGetMinExpenses() {
        List<Eggrafi> result = budgetStats.getMinExpenses();
        assertEquals(1, result.size());
        assertEquals("E2", result.get(0).getKodikos());
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
