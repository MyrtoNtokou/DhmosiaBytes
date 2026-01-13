package budgetlogic;

import budgetreader.Ministry;
import budgetreader.BasicRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestBudgetAssembler {

    private BudgetAssembler assembler;
    private Budget budget;

    @BeforeEach
    void setUp() {
        assembler = new BudgetAssembler();
        // Φόρτωσε τα αρχεία CSV από το runtime-data
        budget = assembler.loadBudget("newgeneral.csv", "newministries.csv");
    }

    @Test
    void testMinistriesMapIsNotEmpty() {
        Map<Integer, Ministry> ministries = budget.getMinistries();
        assertNotNull(ministries, "Το map των υπουργείων δεν πρέπει να είναι null");
        assertFalse(ministries.isEmpty(), "Το map των υπουργείων δεν πρέπει να είναι κενό");

        // Τύπωσε όλα τα keys για να δεις ποιους κωδικούς έχει φορτώσει
        Set<Integer> keys = ministries.keySet();
        System.out.println("Υπάρχοντες κωδικοί υπουργείων: " + keys);

        // Έλεγχος για έναν υπαρκτό κωδικό (π.χ. 5 = Υπουργείο Εσωτερικών)
        assertTrue(keys.contains(5), "Πρέπει να υπάρχει το Υπουργείο Εσωτερικών");
    }

    @Test
    void testRevenuesAndExpensesMaps() {
        Map<String, BasicRecord> revenues = budget.getRevenues();
        Map<String, BasicRecord> expenses = budget.getExpenses();

        assertNotNull(revenues, "Το map των εσόδων δεν πρέπει να είναι null");
        assertNotNull(expenses, "Το map των εξόδων δεν πρέπει να είναι null");

        assertFalse(revenues.isEmpty(), "Πρέπει να υπάρχουν έσοδα");
        assertFalse(expenses.isEmpty(), "Πρέπει να υπάρχουν έξοδα");
    }

    @Test
    void testMinistryValuesAreNotNull() {
        // Έλεγχος ότι όλα τα αντικείμενα Ministry είναι σωστά φορτωμένα
        for (Map.Entry<Integer, Ministry> entry : budget.getMinistries().entrySet()) {
            Ministry ministry = entry.getValue();
            assertNotNull(ministry, "Το αντικείμενο υπουργείου δεν πρέπει να είναι null για key " + entry.getKey());
            assertNotNull(ministry.getRegularBudget(), "Η τιμή τακτικού δεν πρέπει να είναι null για key " + entry.getKey());
            assertNotNull(ministry.getPublicInvestments(), "Η τιμή επενδύσεων δεν πρέπει να είναι null για key " + entry.getKey());
        }
    }
}
