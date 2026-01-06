package budgetcharts;

import budgetreader.Eggrafi;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for MoreCharts class.
 * Tests mainly check that methods run without throwing exceptions.
 */
class TestMoreCharts {

    @Test
    void testPieChartEsodaExoda_runsWithoutException() {
        List<Eggrafi> records = new ArrayList<>();
        records.add(new Eggrafi("1,001", "ΕΣΟΔΑ", new BigDecimal("1000000000")));
        records.add(new Eggrafi("2,001", "ΕΞΟΔΑ", new BigDecimal("500000000")));

        // Run method, should not throw exception
        MoreCharts.pieChartEsodaExoda(records);
    }

    @Test
    void testPieChartElleimma_runsWithoutException() {
        List<Eggrafi> records = new ArrayList<>();
        records.add(new Eggrafi("1,001", "ΕΣΟΔΑ", new BigDecimal("1000000000")));
        records.add(new Eggrafi("2,001", "ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)", new BigDecimal("500000000")));

        // Run method, should not throw exception
        MoreCharts.pieChartElleimma(records);
    }

    @Test
    void testLineChartEsodaExoda_runsWithoutException() {
        // Run method, should not throw exception
        // Note: This method reads CSV files, so you may want to mock ReadBudget
        // or ensure test CSV files exist, otherwise it may throw FileNotFoundException
        MoreCharts.lineChartEsodaExoda();
    }
}
