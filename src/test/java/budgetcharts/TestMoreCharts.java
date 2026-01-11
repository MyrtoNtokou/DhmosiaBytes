package budgetcharts;

import java.awt.GraphicsEnvironment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetreader.BasicRecord;

public class TestMoreCharts {

    private List<BasicRecord> mockEggrafes;

    @BeforeEach
    void setUp() {
        mockEggrafes = new ArrayList<>();
        mockEggrafes.add(new BasicRecord("01", "ΕΣΟΔΑ", new BigDecimal("10000000000")));
        mockEggrafes.add(new BasicRecord("02", "ΕΞΟΔΑ", new BigDecimal("8000000000")));
        mockEggrafes.add(new BasicRecord("03", "ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)", new BigDecimal("2000000000")));
    }

    @Test
    void testPieChartEsodaExoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test γιατί το περιβάλλον δεν υποστηρίζει GUI");

        try {
            MoreCharts.pieChartEsodaExoda(mockEggrafes);
            assertNotNull(mockEggrafes, "Mock data δεν πρέπει να είναι null");
        } catch (Exception e) {
            throw new AssertionError("pieChartEsodaExoda threw an exception: " + e.getMessage());
        }
    }

    @Test
    void testLineChartEsodaExoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test γιατί το περιβάλλον δεν υποστηρίζει GUI");

        try {
            // Προσοχή: χρειάζεται CSV files με ονόματα "proypologismos2020.csv" ... "proypologismos2026.csv"
            // Αν δεν υπάρχουν, μπορούμε να mockάρουμε ReadBudget με Mockito
            MoreCharts.lineChartEsodaExoda();
        } catch (Exception e) {
            throw new AssertionError("lineChartEsodaExoda threw an exception: " + e.getMessage());
        }
    }
}
