package budgetreader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
