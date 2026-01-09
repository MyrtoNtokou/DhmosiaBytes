package budgetreader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test class for ReadBudget methods.
 * The goal is to check that the CSV files are read
 * correctly and the returned lists are not empty.
 */
public class ReadBudgetTest {

    /**
     * Tests that the general budget CSV file is loaded
     * and returns a non-null, non-empty list.
     */
    @Test
    public void testReadGeneralBudget() {
        List<Eggrafi> result =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

        /* The list should exist */
        assertNotNull(result, "List should not be null.");

        /* The list should have at least one record */
        assertFalse(result.isEmpty(), "List should not be empty.");
    }

    /**
     * Tests that reading the general budget
     * from a specified path works correctly.
     */
    @Test
    public void testReadGeneralBudgetFromPath() throws Exception {
        Path path = Paths.get(
            ReadBudgetTest.class
            .getResource("/proypologismos2025.csv")
            .toURI()
        );

        List<Eggrafi> result =
            ReadBudget.readGeneralBudgetFromPath(path);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Eggrafi e = result.get(0);
        assertNotNull(e.getKodikos());
        assertNotNull(e.getPerigrafi());
        assertNotNull(e.getPoso());
    }


    /**
     * Tests that the amount field of the first entry
     * in the general budget CSV file is parsed correctly
     * and is not zero.
     */
    @Test
    public void testGeneralBudgetAmountParsing() {
        List<Eggrafi> result =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

        Eggrafi e = result.get(0);

        assertFalse(e.getPoso().compareTo(BigDecimal.ZERO) == 0,
            "Amount should not be zero");
}

    /**
     * Tests that the first entry of the general budget
     * CSV file is read correctly.
     */
    @Test
    public void testReadGeneralBudgetFirstEntry() {
        List<Eggrafi> result =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

        Eggrafi first = result.get(0);

        assertNotNull(first);
        assertNotNull(first.getKodikos());
        assertNotNull(first.getPerigrafi());
        assertNotNull(first.getPoso());
    }

    /**
     * Tests that reading the ministry budget
     * from a specified path works correctly.
     */
    @Test
    public void testReadByMinistryFromPath() throws Exception {
        Path path = Paths.get(
            ReadBudgetTest.class
            .getResource("/proypologismos2025anaypourgeio.csv")
            .toURI()
        );

        List<Ypourgeio> result =
            ReadBudget.readByMinistryFromPath(path);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Ypourgeio y = result.get(0);
        assertNotNull(y.getOnoma());
        assertNotNull(y.getSynolo());
    }
    
    /**
     * Tests that the first ministry entry
     * in the ministry CSV file is read correctly.
     */
    @Test
    public void testReadByMinistryFields() {
        List<Ypourgeio> result =
            ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

        Ypourgeio y = result.get(0);

        assertNotNull(y);
        assertNotNull(y.getOnoma());
        assertNotNull(y.getSynolo());
    }
    /**
     * Tests that the ministry CSV file is loaded properly
     * and contains at least one ministry entry.
     */
    @Test
    public void testReadByMinistry() {
        List<Ypourgeio> result =
                ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

        /* The list should exist */
        assertNotNull(result, "List should not be null.");

        /* The list should have data */
        assertFalse(result.isEmpty(), "List should not be empty.");
    }
    
    /**
     * Tests that reading a non-existent general budget file
     * returns an empty list.
     */
    @Test
    public void testReadGeneralBudgetFileNotFound() {
        List<Eggrafi> result =
            ReadBudget.readGeneralBudget("does_not_exist.csv");

        assertNotNull(result);
        assertTrue(result.isEmpty(), "List should be empty if file not found");
    }

    /**
     * Tests that reading a non-existent ministry budget file
     * returns an empty list.
     */
    @Test
    public void testReadCroppedByMinistryBasic() {
        List<Ypourgeio> result =
            ReadBudget.readCroppedByMinistry(
            "proypologismos2025anaypourgeio.csv");

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Ypourgeio y = result.get(0);

        assertNotNull(y.getOnoma());
        assertNotNull(y.getSynolo());
    }
}
