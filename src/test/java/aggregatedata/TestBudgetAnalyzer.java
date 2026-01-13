package aggregatedata;

import budgetreader.BasicRecord;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestBudgetAnalyzer {

    @Test
    void testAnalyze() {
        // Create sample BasicRecord records
        BasicRecord rev1 = new BasicRecord("1,001", "Revenue 1", new BigDecimal("100.00"));
        BasicRecord rev2 = new BasicRecord("1,002", "Revenue 2", new BigDecimal("200.00"));
        BasicRecord rev3 = new BasicRecord("1,003", "Revenue 3", new BigDecimal("50.00"));
        BasicRecord rev4 = new BasicRecord("1,004", "Revenue 4", new BigDecimal("300.00"));

        BasicRecord exp1 = new BasicRecord("2,001", "Expense 1", new BigDecimal("80.00"));
        BasicRecord exp2 = new BasicRecord("2,002", "Expense 2", new BigDecimal("150.00"));
        BasicRecord exp3 = new BasicRecord("2,003", "Expense 3", new BigDecimal("120.00"));
        BasicRecord exp4 = new BasicRecord("2,004", "Expense 4", new BigDecimal("50.00"));

        List<BasicRecord> allRecords = Arrays.asList(rev1, rev2, rev3, rev4, exp1, exp2, exp3, exp4);

        // Execute analysis
        BudgetStats stats = BudgetAnalyzer.analyze(allRecords);

        // Check maximum revenues
        assertEquals(3, stats.getMaxRevenues().size());
        assertEquals(rev4, stats.getMaxRevenues().get(0));
        assertEquals(rev2, stats.getMaxRevenues().get(1));
        assertEquals(rev1, stats.getMaxRevenues().get(2));

        // Check minimum revenues
        assertEquals(3, stats.getMinRevenues().size());
        assertEquals(rev3, stats.getMinRevenues().get(0));
        assertEquals(rev1, stats.getMinRevenues().get(1));
        assertEquals(rev2, stats.getMinRevenues().get(2));

        // Check maximum expenses
        assertEquals(3, stats.getMaxExpenses().size());
        assertEquals(exp2, stats.getMaxExpenses().get(0));
        assertEquals(exp3, stats.getMaxExpenses().get(1));
        assertEquals(exp1, stats.getMaxExpenses().get(2));

        // Check minimum expenses
        assertEquals(3, stats.getMinExpenses().size());
        assertEquals(exp4, stats.getMinExpenses().get(0));
        assertEquals(exp1, stats.getMinExpenses().get(1));
        assertEquals(exp3, stats.getMinExpenses().get(2));

        // Check percentages
        BigDecimal totalRevenues = new BigDecimal("650.00");
        BigDecimal totalExpenses = new BigDecimal("400.00");

        assertEquals(new BigDecimal("46.15"), stats.getMaxRevenuePercentages().get(0));
        assertEquals(new BigDecimal("30.77"), stats.getMaxRevenuePercentages().get(1));
        assertEquals(new BigDecimal("15.38"), stats.getMaxRevenuePercentages().get(2));

        assertEquals(new BigDecimal("37.50"), stats.getMaxExpensePercentages().get(0));
        assertEquals(new BigDecimal("30.00"), stats.getMaxExpensePercentages().get(1));
        assertEquals(new BigDecimal("20.00"), stats.getMaxExpensePercentages().get(2));
    }
}
