package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestRevenueOrExpense {

    @Test
    void testGetRevenueOrExpenseCode() {
        assertEquals(1, RevenueOrExpense.INCOME.getRevenueOrExpenseCode());
        assertEquals(2, RevenueOrExpense.EXPENSE.getRevenueOrExpenseCode());
    }

    @Test
    void testGetDescription() {
        assertEquals("Έσοδα Προϋπολογισμού", RevenueOrExpense.INCOME.getDescription());
        assertEquals("Έξοδα Προϋπολογισμού (ανά Υπουργείο)", RevenueOrExpense.EXPENSE.getDescription());
    }

    @Test
    void testFromCodeValid() {
        assertEquals(RevenueOrExpense.INCOME, RevenueOrExpense.fromCode(1));
        assertEquals(RevenueOrExpense.EXPENSE, RevenueOrExpense.fromCode(2));
    }

    @Test
    void testFromCodeInvalid() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> RevenueOrExpense.fromCode(99)
        );
        assertTrue(exception.getMessage().contains("Μη έγκυρη επιλογή"));
    }
}