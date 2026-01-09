package aggregatedata;

import budgetreader.Ypourgeio;
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
        List<Ypourgeio> maxTaktikos = new ArrayList<>();
        maxTaktikos.add(new Ypourgeio(5, "Ministry A", new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("150")));

        List<Ypourgeio> maxEpendyseis = new ArrayList<>();
        maxEpendyseis.add(new Ypourgeio(10, "Ministry B", new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("300")));

        List<Ypourgeio> maxSynolo = new ArrayList<>();
        maxSynolo.add(new Ypourgeio(15, "Ministry C", new BigDecimal("50"), new BigDecimal("50"), new BigDecimal("100")));

        List<BigDecimal> taktPercent = new ArrayList<>();
        taktPercent.add(new BigDecimal("100.00"));

        List<BigDecimal> ependPercent = new ArrayList<>();
        ependPercent.add(new BigDecimal("100.00"));

        List<BigDecimal> synoloPercent = new ArrayList<>();
        synoloPercent.add(new BigDecimal("100.00"));

        stats = new MinistryStats(
                maxTaktikos,
                maxEpendyseis,
                maxSynolo,
                taktPercent,
                ependPercent,
                synoloPercent
        );
    }

    @Test
    void testPrintTaktikos() {
        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MinistryStatsPrinter.printTaktikos(stats);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry A"), "Output should contain Ministry A");
        assertTrue(output.contains("100"), "Output should contain the Taktikos value");

        // Restore System.out
        System.setOut(System.out);
    }

    @Test
    void testPrintEpendyseis() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MinistryStatsPrinter.printEpendyseis(stats);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry B"), "Output should contain Ministry B");
        assertTrue(output.contains("100"), "Output should contain the Ependyseis value");

        System.setOut(System.out);
    }

    @Test
    void testPrintSynolo() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        MinistryStatsPrinter.printSynolo(stats);

        String output = outContent.toString();
        assertTrue(output.contains("Ministry C"), "Output should contain Ministry C");
        assertTrue(output.contains("100"), "Output should contain the Synolo value");

        System.setOut(System.out);
    }
}
