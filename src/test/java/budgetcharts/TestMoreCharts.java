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

    private List<BasicRecord> mockBasicRecords;

    @BeforeEach
    void setUp() {
        mockBasicRecords = new ArrayList<>();
        mockBasicRecords.add(new BasicRecord("01", "ΕΣΟΔΑ", new BigDecimal("10000000000")));
        mockBasicRecords.add(new BasicRecord("02", "ΕΞΟΔΑ", new BigDecimal("8000000000")));
        mockBasicRecords.add(new BasicRecord("03", "ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)", new BigDecimal("2000000000")));
    }

    @Test
    void testPieChartEsodaExoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test γιατί το περιβάλλον δεν υποστηρίζει GUI");

        try {
            MoreCharts.pieChartEsodaExoda(mockBasicRecords);
            assertNotNull(mockBasicRecords, "Mock data δεν πρέπει να είναι null");
        } catch (Exception e) {
            throw new AssertionError("pieChartEsodaExoda threw an exception: " + e.getMessage());
        }
    }

    @Test
    void testLineChartEsodaExoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test γιατί το περιβάλλον δεν υποστηρίζει GUI");

        try {
            MoreCharts.lineChartEsodaExoda();
        } catch (Exception e) {
            throw new AssertionError("lineChartEsodaExoda threw an exception: " + e.getMessage());
        }
    }
}
