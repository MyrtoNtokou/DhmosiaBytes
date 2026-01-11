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

/** Unit tests for InvestmentPrinter */
class TestInvestmentPrinter {

    private List<Ministry> ministries;
    private List<InvestmentRatio> ratios;

    @BeforeEach
    void setUp() {
        ministries = new ArrayList<>();
        ministries.add(new Ministry(5, "Ministry A", new BigDecimal("800"), new BigDecimal("200"), new BigDecimal("1000")));
        ministries.add(new Ministry(10, "Ministry B", new BigDecimal("1000"), new BigDecimal("1000"), new BigDecimal("2000")));

        ratios = new ArrayList<>();
        ratios.add(new InvestmentRatio("Ministry A", new BigDecimal("0.20"), new BigDecimal("20.00")));
        ratios.add(new InvestmentRatio("Ministry B", new BigDecimal("0.50"), new BigDecimal("50.00")));
    }

    @Test
    void testPrintDoesNotThrowAndContainsOutput() {
        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Ensure print method does not throw any exception
        assertDoesNotThrow(() -> InvestmentPrinter.print(ministries, ratios));

        String output = outContent.toString();

        // Check that output contains ministry names
        assertTrue(output.contains("Ministry A"));
        assertTrue(output.contains("Ministry B"));

        // Check that output contains investments and total
        assertTrue(output.contains("200"));   // Ministry A investment
        assertTrue(output.contains("1000"));  // Ministry A total
        assertTrue(output.contains("1000"));  // Ministry B investment
        assertTrue(output.contains("2000"));  // Ministry B total

        // Check that output contains percentages
        assertTrue(output.contains("20.00%"));
        assertTrue(output.contains("50.00%"));
    }

    @Test
    void testPrintWithEmptyLists() {
        // Ensure method handles empty lists without exceptions
        assertDoesNotThrow(() -> InvestmentPrinter.print(new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    void testPrintIgnoresMissingMinistries() {
        // Add a ratio for a ministry not in the list
        List<InvestmentRatio> ratiosWithMissing = new ArrayList<>(ratios);
        ratiosWithMissing.add(new InvestmentRatio("Ministry X", new BigDecimal("0.10"), new BigDecimal("10.00")));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        assertDoesNotThrow(() -> InvestmentPrinter.print(ministries, ratiosWithMissing));

        String output = outContent.toString();

        // Ministry X should not appear in the output
        assertFalse(output.contains("Ministry X"));
    }
}
