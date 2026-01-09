package budgetcharts;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.GraphicsEnvironment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

public class TestBarcharts {

    private List<Eggrafi> mockEggrafes;
    private List<Ypourgeio> mockYpourgeia;

    @BeforeEach
    void setUp() {
        mockEggrafes = new ArrayList<>();
        mockEggrafes.add(new Eggrafi("1,01", "Φόροι", new BigDecimal("10000000000")));
        mockEggrafes.add(new Eggrafi("2,01", "Μισθοί", new BigDecimal("8000000000")));

        mockYpourgeia = new ArrayList<>();
        mockYpourgeia.add(
            new Ypourgeio(
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

        Barcharts.chartEsoda(mockEggrafes);
        assertNotNull(mockEggrafes);
    }

    @Test
    void testChartExoda_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test σε headless περιβάλλον");

        Barcharts.chartExoda(mockEggrafes);
        assertNotNull(mockEggrafes);
    }

    @Test
    void testChartMinistry_DisplaySuccess() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(),
                "Παράλειψη test σε headless περιβάλλον");

        Barcharts.chartMinistry(mockYpourgeia);
        assertNotNull(mockYpourgeia);
    }
}
