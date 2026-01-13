package aggregatedata;

import budgetreader.BasicRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for BudgetStatsPrinter class */
class TestBudgetStatsPrinter {

    private BudgetStats budgetStats;
    private BasicRecord rev1, rev2, exp1, exp2;

    @BeforeEach
    void setUp() {
        // Create sample BasicRecord objects
        rev1 = new BasicRecord("R1", "Revenue 1", new BigDecimal("1000"));
        rev2 = new BasicRecord("R2", "Revenue 2", new BigDecimal("2000"));
        exp1 = new BasicRecord("E1", "Expense 1", new BigDecimal("500"));
        exp2 = new BasicRecord("E2", "Expense 2", new BigDecimal("300"));

        // Create lists
        List<BasicRecord> maxRevenues = new ArrayList<>();
        maxRevenues.add(rev2);

        List<BasicRecord> minRevenues = new ArrayList<>();
        minRevenues.add(rev1);

        List<BasicRecord> maxExpenses = new ArrayList<>();
        maxExpenses.add(exp1);

        List<BasicRecord> minExpenses = new ArrayList<>();
        minExpenses.add(exp2);

        List<BigDecimal> maxRevPercentages = new ArrayList<>();
        maxRevPercentages.add(new BigDecimal("50"));

        List<BigDecimal> maxExpPercentages = new ArrayList<>();
        maxExpPercentages.add(new BigDecimal("30"));

        budgetStats = new BudgetStats(maxRevenues, minRevenues, maxExpenses, minExpenses,
                                      maxRevPercentages, maxExpPercentages);
    }

    @Test
    void testPrintRevenues() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertDoesNotThrow(() -> BudgetStatsPrinter.printRevenues(budgetStats));

        String output = outContent.toString();
        assertTrue(output.contains("Revenue 1"));
        assertTrue(output.contains("Revenue 2"));
        assertTrue(output.contains("50%"));
    }

    @Test
    void testPrintExpenses() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertDoesNotThrow(() -> BudgetStatsPrinter.printExpenses(budgetStats));

        String output = outContent.toString();
        assertTrue(output.contains("Expense 1"));
        assertTrue(output.contains("Expense 2"));
        assertTrue(output.contains("30%"));
    }
}
