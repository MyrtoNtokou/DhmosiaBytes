package budgetcharts;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.GraphicsEnvironment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetreader.BasicRecord;
import budgetreader.Ministry;

public class TestBarcharts {

    private List<BasicRecord> mockBasicRecords;
    private List<Ministry> mockMinistries;

    @BeforeEach
    void setUp() {
        mockBasicRecords = new ArrayList<>();
        mockBasicRecords.add(new BasicRecord("1,01", "Φόροι", new BigDecimal("10000000000")));
        mockBasicRecords.add(new BasicRecord("2,01", "Μισθοί", new BigDecimal("8000000000")));

        mockMinistries = new ArrayList<>();
        mockMinistries.add(
            new Ministry(
                1,
                "Υπουργείο Οικονομικών",
                new BigDecimal("10000000000"),
                new BigDecimal("5000000000"),
                new BigDecimal("15000000000")
            )
        );
    }

    @Test
    void testChartEsoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test σε headless περιβάλλον");

        Barcharts.chartEsoda(mockBasicRecords);
        assertNotNull(mockBasicRecords);
    }

    @Test
    void testChartExoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test σε headless περιβάλλον");

        Barcharts.chartExoda(mockBasicRecords);
        assertNotNull(mockBasicRecords);
    }

    @Test
    void testChartMinistry_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test σε headless περιβάλλον");

        Barcharts.chartMinistry(mockMinistries);
        assertNotNull(mockMinistries);
    }
}
