package aggregatedata;

import budgetreader.Ministry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for MinistryStatsPrinter */
class TestMinistryStatsPrinter {

    private MinistryStats stats;

    @BeforeEach
    void setUp() {
        List<Ministry> maxRegularBudget = new ArrayList<>();
        maxRegularBudget.add(new Ministry(5, "Ministry A", new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("150")));

        List<Ministry> maxPublicInvestments = new ArrayList<>();
        maxPublicInvestments.add(new Ministry(10, "Ministry B", new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("300")));

        List<Ministry> maxTotalBudget = new ArrayList<>();
        maxTotalBudget.add(new Ministry(15, "Ministry C", new BigDecimal("50"), new BigDecimal("50"), new BigDecimal("100")));

        List<BigDecimal> taktPercent = new ArrayList<>();
        taktPercent.add(new BigDecimal("100.00"));

        List<BigDecimal> ependPercent = new ArrayList<>();
        ependPercent.add(new BigDecimal("100.00"));

        List<BigDecimal> totalBudgetPercent = new ArrayList<>();
        totalBudgetPercent.add(new BigDecimal("100.00"));

        stats = new MinistryStats(
                maxRegularBudget,
                maxPublicInvestments,
                maxTotalBudget,
                taktPercent,
                ependPercent,
                totalBudgetPercent
        );
    }

    @Test
    void testPrintRegularBudget() {
        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MinistryStatsPrinter.printRegularBudget(stats);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry A"), "Output should contain Ministry A");
        assertTrue(output.contains("100"), "Output should contain the RegularBudget value");

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    void testPrintPublicInvestments() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MinistryStatsPrinter.printPublicInvestments(stats);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry B"), "Output should contain Ministry B");
        assertTrue(output.contains("100"), "Output should contain the PublicInvestments value");

        System.setOut(System.out);
    }

    @Test
    void testPrintTotalBudget() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MinistryStatsPrinter.printTotalBudget(stats);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry C"), "Output should contain Ministry C");
        assertTrue(output.contains("100"), "Output should contain the TotalBudget value");

        System.setOut(System.out);
    }
}
