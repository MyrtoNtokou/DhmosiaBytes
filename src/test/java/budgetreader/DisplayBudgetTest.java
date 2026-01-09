package budgetreader;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** These tests capture console output using {@code System.setOut} in order to verify
 * that the formatting and displayed information are correct.*/
public class DisplayBudgetTest {

    /**
     * Tests whether the {@code showGeneral} method prints the expected elements
     * when provided with a list of {@link Eggrafi} objects.
     */
    @Test
    public void testShowGeneralOutput() {
        /* Store original System.out to restore later.*/
        PrintStream originalOut = System.out;

        /*Redirect System.out to a fake stream to capture output. */
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        /*Mock list with Eggrafi. */
        List<Eggrafi> eggrafes = new ArrayList<>();
        eggrafes.add(new Eggrafi("1", "Τεστ Έσοδο", BigDecimal.valueOf(100)));

        /*Call showGeneral method of DispayBudget. */
        DisplayBudget.showGeneral(eggrafes);

        /* Execute the method under test. */
        String output = outContent.toString();

        /*Restore original System.out. */
        System.setOut(originalOut);

        /* Assertions of outputs */
        assertTrue(output.contains("ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ"));
        assertTrue(output.contains("Τεστ Έσοδο"));
        assertTrue(output.contains("100"));
    }

    /**
     * Tests whether the {@code showMinistry} method prints the expected
     * ministry budget information when provided with a list of {@link Ypourgeio}.
     */
    @Test
    public void testShowMinistryOutput() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        /* Mock data. */
        List<Ypourgeio> lista = new ArrayList<>();
        lista.add(new Ypourgeio(1, "Υπ. Παιδείας",
                new BigDecimal(200),
                new BigDecimal(300),
            new BigDecimal(400)));

        /* Call showMinistry method of DispayBudget.*/
        DisplayBudget.showMinistry(lista);

        String output = outContent.toString();
        System.setOut(originalOut);

        /*assertions. */
        assertTrue(output.contains("ΚΑΤΗΓΟΡΙΟΠΟΙΗΣΗ ΣΤΟΙΧΕΙΩΝ"));
        assertTrue(output.contains("Υπ. Παιδείας"));
        assertTrue(output.contains("200"));
        assertTrue(output.contains("300"));
        assertTrue(output.contains("400"));
    }


    /**
     * Tests that showGeneral handles an empty list gracefully.
     */
    @Test
    public void testShowGeneralWithEmptyList() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DisplayBudget.showGeneral(new ArrayList<>());
        String output = outContent.toString();
        System.setOut(originalOut);
        assertTrue(output.contains("ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ"));
        assertFalse(output.contains("Τεστ Έσοδο"));
    }
}