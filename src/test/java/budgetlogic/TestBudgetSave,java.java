package budgetlogic;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BudgetSaveIntegrationTest {

    private BudgetSave budgetSave;
    private Budget testBudget;
    private Eggrafi revenue1;
    private Eggrafi revenueResult;
    private Eggrafi expense1;
    private Ypourgeio ministry1;

    @BeforeEach
    void setUp() {
        budgetSave = new BudgetSave();
        testBudget = new Budget();

        // Create revenues
        revenue1 = new Eggrafi("rev1", "REVENUE 1", BigDecimal.valueOf(100));
        revenueResult = new Eggrafi("rev2", "ΑΠΟΤΕΛΕΣΜΑ", BigDecimal.valueOf(50));

        testBudget.getRevenues().put("rev1", revenue1);
        testBudget.getRevenues().put("rev2", revenueResult);

        // Create expense
        expense1 = new Eggrafi("exp1", "EXPENSE 1", BigDecimal.valueOf(60));
        testBudget.getExpenses().put("exp1", expense1);

        // Create ministry
        ministry1 = new Ypourgeio(1, "MINISTRY 1",
                BigDecimal.valueOf(200), BigDecimal.valueOf(150), BigDecimal.valueOf(350));
        testBudget.getMinistries().put(1, ministry1);
    }

    @Test
    void testSaveGeneralAndMinistryChanges() throws IOException {
        String generalFile = "test_general.csv";
        String ministryFile = "test_ministries.csv";

        // Save changes
        budgetSave.saveGeneralChanges(testBudget, generalFile);
        budgetSave.saveMinistryChanges(testBudget, ministryFile);

        // Check that files exist
        Path generalPath = Paths.get("runtime-data").resolve(generalFile);
        Path ministryPath = Paths.get("runtime-data").resolve(ministryFile);

        assertTrue(Files.exists(generalPath), "General CSV should be created");
        assertTrue(Files.exists(ministryPath), "Ministries CSV should be created");

        // Optional: read the files and verify content
        // List<String> generalLines = Files.readAllLines(generalPath);
        // List<String> ministryLines = Files.readAllLines(ministryPath);

        // Cleanup
        Files.deleteIfExists(generalPath);
        Files.deleteIfExists(ministryPath);
    }
}
