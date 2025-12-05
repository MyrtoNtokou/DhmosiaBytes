package dhmosiabytes;

import org.junit.jupiter.api.Test;
import java.util.List;
import budgetreader.ReadBudget;
import budgetreader.Eggrafi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestMoreCharts {

    @Test
    void testPieChartEsodaExoda() {
        List<Eggrafi> eggrafes = ReadBudget.readGeneralBudget("proypologismos2025.csv");
        assertDoesNotThrow(() -> MoreCharts.pieChartEsodaExoda(eggrafes));
    }

    @Test
    void testPieChartElleimma() {
        List<Eggrafi> eggrafes = ReadBudget.readGeneralBudget("proypologismos2025.csv");
        assertDoesNotThrow(() -> MoreCharts.pieChartElleimma(eggrafes));
    }
}
