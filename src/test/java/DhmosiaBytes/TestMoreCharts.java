package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import java.awt.GraphicsEnvironment;

import budgetreader.Eggrafi;

public class TestMoreCharts {

    private List<Eggrafi> mockEggrafes;

    // Setup method to initialize the mock data before each test
    @BeforeEach
    void setUp() {
        mockEggrafes = new ArrayList<>();
        mockEggrafes.add(new Eggrafi("01", "ΕΣΟΔΑ", 10000.0));
        mockEggrafes.add(new Eggrafi("02", "ΕΞΟΔΑ", 8000.0));
        mockEggrafes.add(new Eggrafi("03", "ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)", 2000.0));
        mockEggrafes.add(new Eggrafi("04", "ΆΛΛΟ", 500.0));
    }

    @Test
    void testPieChartEsodaExoda_DisplaySuccess() {
        // Skip the test if the environment is headless (CI)
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), 
                                "Το test παραλείπεται γαιτί το περιβάλλον δεν υποστηρίζει GUI");
        try {
            MoreCharts.pieChartEsodaExoda(mockEggrafes);
            assertNotNull(mockEggrafes, "Mock data δεν πρέπει να είναι null");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("pieChartEsodaExoda threw an exception: " + e.getMessage());
        }
    }

    @Test
    void testPieChartElleimma_DisplaySuccess() {
        // Skip the test if the environment is headless (CI)
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless(), 
                                "Το test παραλείπεται γαιτί το περιβάλλον δεν υποστηρίζει GUI");
        try {
            MoreCharts.pieChartElleimma(mockEggrafes);
            assertNotNull(mockEggrafes, "Mock data δεν πρέπει να είναι null");
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("pieChartElleimma threw an exception: " + e.getMessage());
        }
    }
}