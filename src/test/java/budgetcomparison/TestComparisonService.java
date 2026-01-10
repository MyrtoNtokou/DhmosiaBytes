package budgetcomparison;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import org.junit.jupiter.api.Test;

/** Test class for ComparisonService */
public class TestComparisonService {
 
    /** 
     * Test for calculatePercentageChange method.
     */
    @Test
    public void testCompareGeneralBudgetIncrease() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        ComparisonService service = new ComparisonService();

        service.compareGeneralBudgetByYear(2020, 2021, "1");

        System.setOut(originalOut);

        String output = out.toString();

        assertTrue(output.contains("1"));
        assertTrue(output.contains("αυξήθηκε"));
        assertTrue(output.contains("2021"));
        assertTrue(output.contains("2020"));
    }

    /** 
     * Test for calculatePercentageChange method.
     */
    @Test
    public void testCompareGeneralBudgetDecrease() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new ComparisonService()
            .compareGeneralBudgetByYear(2020, 2022, "1,1");

        System.setOut(originalOut);

        assertTrue(out.toString().contains("μειώθηκε"));
    }

    /** 
     * Test for calculatePercentageChange method when entry not found.
     */
    @Test
    public void testCompareGeneralBudgetNotFound() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new ComparisonService()
            .compareGeneralBudgetByYear(2020, 2021, "999");

        System.setOut(System.out);

        assertTrue(out.toString()
            .contains("Δεν βρέθηκε η εγγραφή"));
    }


    /** 
     * Test for calculatePercentageChange method when base amount is zero.
     */
    @Test
    public void testBaseAmountZeroHandled() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        assertDoesNotThrow(() ->
            new ComparisonService()
            .compareGeneralBudgetByYear(2020, 2021, "ZERO")
        );

        System.setOut(System.out);
    }

    /** 
     * Test for compareMinistryBudgetByYear method.
     */
    @Test
    public void testCompareMinistryBudget() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new ComparisonService()
            .compareMinistryBudgetByYear(2022, 2023, "1");
        System.setOut(System.out);
    assertTrue(out.toString().contains("3"));
    }

}
