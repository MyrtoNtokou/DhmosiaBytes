package budgetcharts;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Unit tests for Barcharts class */
class TestBarcharts {

    @Test
    void testChartEsoda_runsWithoutException() {
        List<Eggrafi> records = new ArrayList<>();
        records.add(new Eggrafi("1,001", "Income A", new BigDecimal("1000000000")));
        records.add(new Eggrafi("1,002", "Income B", new BigDecimal("2000000000")));
        records.add(new Eggrafi("2,001", "Expense A", new BigDecimal("500000000")));

        // Should run without throwing exceptions
        Barcharts.chartEsoda(records);
    }

    @Test
    void testChartExoda_runsWithoutException() {
        List<Eggrafi> records = new ArrayList<>();
        records.add(new Eggrafi("2,001", "Expense A", new BigDecimal("500000000")));
        records.add(new Eggrafi("2,002", "Expense B", new BigDecimal("1500000000")));
        records.add(new Eggrafi("1,001", "Income A", new BigDecimal("1000000000")));

        // Should run without throwing exceptions
        Barcharts.chartExoda(records);
    }

    @Test
    void testChartMinistry_runsWithoutException() {
        List<Ypourgeio> ministries = new ArrayList<>();
        ministries.add(new Ypourgeio(5, "Υπουργείο Α", new BigDecimal("1000000000"), new BigDecimal("500000000"), new BigDecimal("1500000000")));
        ministries.add(new Ypourgeio(6, "Some Other", new BigDecimal("500000000"), new BigDecimal("250000000"), new BigDecimal("750000000")));

        // Should run without throwing exceptions
        Barcharts.chartMinistry(ministries);
    }

    // Optional: tests for ByYear methods
    @Test
    void testChartEsodaByYear_runsWithoutException() {
        // Just test that method runs (does not read actual files in this test)
        Barcharts.chartEsodaByYear(1); 
    }

    @Test
    void testChartExodaByYear_runsWithoutException() {
        Barcharts.chartExodaByYear(1); 
    }

    @Test
    void testChartMinistryByYear_runsWithoutException() {
        Barcharts.chartMinistryByYear(1); 
    }
}
