package budgetcomparison;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Test class for BudgetFileResolver. */
public class BudgetFileResolverTest {

    /** Tests that valid years return the correct file names. */
    @Test
    public void testGeneralBudgetFileValidYears() {
        assertEquals("proypologismos2020.csv",
                BudgetFileResolver.generalBudgetFile(2020));

        assertEquals("proypologismos2023.csv",
                BudgetFileResolver.generalBudgetFile(2023));

        assertEquals("proypologismos2026.csv",
                BudgetFileResolver.generalBudgetFile(2026));
    }

    /** Tests that an invalid year throws IllegalArgumentException. */
    @Test
    public void testGeneralBudgetFileInvalidYear() {
        IllegalArgumentException ex =
            assertThrows(IllegalArgumentException.class, () ->
                BudgetFileResolver.generalBudgetFile(2019)
            );

        assertTrue(ex.getMessage().contains("Μη υποστηριζόμενο έτος"));
    }

    /** Tests that valid years return the correct ministry file names. */
    @Test
    public void testMinistryBudgetFileValidYears() {
        assertEquals("proypologismos2020anaypourgeio.csv",
                BudgetFileResolver.ministryBudgetFile(2020));

        assertEquals("proypologismos2023anaypourgeio.csv",
                BudgetFileResolver.ministryBudgetFile(2023));

        assertEquals("proypologismos2024anaypourgeio.csv",
                BudgetFileResolver.ministryBudgetFile(2024));
    }

    /** Tests that an invalid year throws IllegalArgumentException. */
    @Test
    public void testMinistryBudgetFileInvalidYear() {
        IllegalArgumentException ex =
            assertThrows(IllegalArgumentException.class, () ->
                BudgetFileResolver.ministryBudgetFile(2027)
            );

        assertTrue(ex.getMessage().contains("Μη υποστηριζόμενο έτος"));
    }
}
