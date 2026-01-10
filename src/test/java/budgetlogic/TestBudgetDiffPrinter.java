package budgetlogic;

import static org.junit.jupiter.api.Assertions.*;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBudgetDiffPrinter {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUpStreams() {
        // Redirect System.out to capture printed output for assertions
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testFormatDiffPositiveNegativeZero() throws Exception {
        // Use reflection to test the private formatDiff method
        var method = BudgetDiffPrinter.class.getDeclaredMethod("formatDiff", BigDecimal.class, BigDecimal.class);
        method.setAccessible(true);

        String pos = (String) method.invoke(null, BigDecimal.valueOf(10), BigDecimal.valueOf(15));
        String neg = (String) method.invoke(null, BigDecimal.valueOf(20), BigDecimal.valueOf(15));
        String zero = (String) method.invoke(null, BigDecimal.valueOf(5), BigDecimal.valueOf(5));

        // Positive difference should contain "+5" and green ANSI code
        assertTrue(pos.contains("+5"));
        assertTrue(pos.contains("32m")); // green ANSI

        // Negative difference should contain "-5" and red ANSI code
        assertTrue(neg.contains("-5"));
        assertTrue(neg.contains("31m")); // red ANSI

        // Zero difference should return empty string
        assertEquals("", zero);
    }

    @Test
    void testCompareRevenuesPrintsChanges() {
        Map<String, Eggrafi> revBefore = new LinkedHashMap<>();
        Map<String, Eggrafi> revAfter = new LinkedHashMap<>();
        Map<String, Eggrafi> expBefore = new LinkedHashMap<>();
        Map<String, Eggrafi> expAfter = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        // Add a revenue record that changes
        revBefore.put("1", new Eggrafi("1", "Revenue 1", BigDecimal.valueOf(100)));
        revAfter.put("1", new Eggrafi("1", "Revenue 1", BigDecimal.valueOf(150)));

        // Add a RESULT record in expenses so compareResult doesn't fail
        expBefore.put("R", new Eggrafi("R", "ΑΠΟΤΕΛΕΣΜΑ", BigDecimal.valueOf(50)));
        expAfter.put("R", new Eggrafi("R", "ΑΠΟΤΕΛΕΣΜΑ", BigDecimal.valueOf(50)));

        // Create Budget objects
        Budget before = new Budget(revBefore, expBefore, ministries);
        Budget after = new Budget(revAfter, expAfter, ministries);

        // Call the method to print revenue differences
        BudgetDiffPrinter.printDiffRevenues(before, after);

        // Verify output contains expected information
        String output = outContent.toString();
        assertTrue(output.contains("Revenue 1"), "Output should contain Revenue 1");
        assertTrue(output.contains("100 →"), "Output should contain old value 100");
        assertTrue(output.contains("150"), "Output should contain new value 150");
        assertTrue(output.contains("ΑΠΟΤΕΛΕΣΜΑ"), "Output should contain the RESULT record");
    }

    @Test
    void testCompareMinistriesPrintsChanges() {
        Map<String, Eggrafi> rev = new LinkedHashMap<>();
        Map<String, Eggrafi> exp = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> minBefore = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> minAfter = new LinkedHashMap<>();

        // Create a ministry record with a change in taktikos
        Ypourgeio oldMin = new Ypourgeio(1, "Ministry A",
                                          BigDecimal.valueOf(1000),
                                          BigDecimal.valueOf(200),
                                          BigDecimal.valueOf(1200));
        Ypourgeio newMin = new Ypourgeio(1, "Ministry A",
                                          BigDecimal.valueOf(1100),
                                          BigDecimal.valueOf(200),
                                          BigDecimal.valueOf(1300));
        minBefore.put(1, oldMin);
        minAfter.put(1, newMin);

        Budget before = new Budget(rev, exp, minBefore);
        Budget after = new Budget(rev, exp, minAfter);

        // Capture output of ministries comparison
        String captured = BudgetDiffPrinter.captureMinistryDiff(before, after);

        assertTrue(captured.contains("Ministry A"));
        assertTrue(captured.contains("1000 →")); // old Taktikos
        assertTrue(captured.contains("1100"));    // new Taktikos
        assertTrue(captured.contains("1300"));    // new Synolo
    }

    @Test
    void testPrintRevenuesAndExpenses() {
        Map<String, Eggrafi> rev = new LinkedHashMap<>();
        Map<String, Eggrafi> exp = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        rev.put("1", new Eggrafi("1", "Revenue 1", BigDecimal.valueOf(100)));
        exp.put("1", new Eggrafi("1", "Expense 1", BigDecimal.valueOf(50)));

        Budget budget = new Budget(rev, exp, ministries);

        // Call printing methods
        BudgetDiffPrinter.printRevenues(budget);
        BudgetDiffPrinter.printExpenses(budget);

        String output = outContent.toString();
        assertTrue(output.contains("Revenue 1"), "Output should contain Revenue 1");
        assertTrue(output.contains("Expense 1"), "Output should contain Expense 1");
    }

    @Test
    void testPrintMinistries() {
        Map<String, Eggrafi> rev = new LinkedHashMap<>();
        Map<String, Eggrafi> exp = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        // Add one ministry
        Ypourgeio yp = new Ypourgeio(1, "Ministry A",
                                      BigDecimal.valueOf(1000),
                                      BigDecimal.valueOf(200),
                                      BigDecimal.valueOf(1200));
        ministries.put(1, yp);

        Budget budget = new Budget(rev, exp, ministries);

        BudgetDiffPrinter.printMinistries(budget);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry A"), "Output should contain Ministry A");
        assertTrue(output.contains("1000"), "Output should contain Taktikos value");
        assertTrue(output.contains("200"), "Output should contain Ependyseis value");
        assertTrue(output.contains("1200"), "Output should contain Synolo value");
    }
}
