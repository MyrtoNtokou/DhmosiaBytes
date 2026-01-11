package budgetreader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test class for ReadBudget methods.
 * The goal is to check that the CSV files are read
 * correctly and the returned lists are not empty.
 */
public class TestReadBudget {

    /**
     * Tests that the general budget CSV file is loaded
     * and returns a non-null, non-empty list.
     */
    @Test
    public void testReadGeneralBudget() {
        List<BasicRecord> result =
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
            TestReadBudget.class
            .getResource("/proypologismos2025.csv")
            .toURI()
        );

        List<BasicRecord> result =
            ReadBudget.readGeneralBudgetFromPath(path);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        BasicRecord e = result.get(0);
        assertNotNull(e.getCode());
        assertNotNull(e.getDescription());
        assertNotNull(e.getAmount());
    }


    /**
     * Tests that the amount field of the first entry
     * in the general budget CSV file is parsed correctly
     * and is not zero.
     */
    @Test
    public void testGeneralBudgetAmountParsing() {
        List<BasicRecord> result =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

        BasicRecord e = result.get(0);

        assertFalse(e.getAmount().compareTo(BigDecimal.ZERO) == 0,
            "Amount should not be zero");
}

    /**
     * Tests that the first entry of the general budget
     * CSV file is read correctly.
     */
    @Test
    public void testReadGeneralBudgetFirstEntry() {
        List<BasicRecord> result =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

        BasicRecord first = result.get(0);

        assertNotNull(first);
        assertNotNull(first.getCode());
        assertNotNull(first.getDescription());
        assertNotNull(first.getAmount());
    }

    /**
     * Tests that reading the ministry budget
     * from a specified path works correctly.
     */
    @Test
    public void testReadByMinistryFromPath() throws Exception {
        Path path = Paths.get(
            TestReadBudget.class
            .getResource("/proypologismos2025anaypourgeio.csv")
            .toURI()
        );

        List<Ministry> result =
            ReadBudget.readByMinistryFromPath(path);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Ministry y = result.get(0);
        assertNotNull(y.getName());
        assertNotNull(y.getTotalBudget());
    }
    
    /**
     * Tests that the first ministry entry
     * in the ministry CSV file is read correctly.
     */
    @Test
    public void testReadByMinistryFields() {
        List<Ministry> result =
            ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

        Ministry y = result.get(0);

        assertNotNull(y);
        assertNotNull(y.getName());
        assertNotNull(y.getTotalBudget());
    }
    /**
     * Tests that the ministry CSV file is loaded properly
     * and contains at least one ministry entry.
     */
    @Test
    public void testReadByMinistry() {
        List<Ministry> result =
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
        List<BasicRecord> result =
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
        List<Ministry> result =
            ReadBudget.readCroppedByMinistry(
            "proypologismos2025anaypourgeio.csv");

        assertNotNull(result);
        assertFalse(result.isEmpty());

        Ministry y = result.get(0);

        assertNotNull(y.getName());
        assertNotNull(y.getTotalBudget());
    }

    /**
     * Tests the parseNumber method with valid and invalid inputs.
     * @throws ParseException
     */
    @Test
    public void parseNumber_validNumber() throws ParseException {
        BigDecimal result = ReadBudget.parseNumber("123,45");
    assertEquals(new BigDecimal("123.45"), result);
    }

   @Test
    void parseNumber_invalidNumber_returnsZero() throws ParseException {
        BigDecimal result = ReadBudget.parseNumber("abc");
        assertEquals(BigDecimal.ZERO, result);
    }
}
