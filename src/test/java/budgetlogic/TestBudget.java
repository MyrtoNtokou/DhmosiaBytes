
package budgetlogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import budgetreader.*;

class TestBudget {

    @Test
    void testAddRevenueAndTotalRevenues() {
        Budget b = new Budget();

        b.addRevenue(new BasicRecord("R1", "Test Rev 1", new BigDecimal("10.00")));
        b.addRevenue(new BasicRecord("R2", "Test Rev 2", new BigDecimal("25.50")));

        assertEquals(new BigDecimal("35.50"), b.totalRevenues());
        assertEquals(2, b.getRevenues().size());
    }

    @Test
    void testAddExpenseAndTotalExpenses() {
        Budget b = new Budget();

        b.addExpense(new BasicRecord("E1", "Test Exp 1", new BigDecimal("5.00")));
        b.addExpense(new BasicRecord("E2", "Test Exp 2", new BigDecimal("20.00")));

        assertEquals(new BigDecimal("25.00"), b.totalExpenses());
        assertEquals(2, b.getExpenses().size());
    }

    @Test
    void testAddMinistry() {
        Budget b = new Budget();

        Ministry m = new Ministry(100, "Υπουργείο Test",
                new BigDecimal("100"), new BigDecimal("200"), new BigDecimal("300"));

        b.addMinistry(m);

        assertEquals(1, b.getMinistries().size());
        assertTrue(b.getMinistries().containsKey(100));
    }

    @Test
    void testGettersReturnCopies() {
        Budget b = new Budget();
        b.addRevenue(new BasicRecord("R1", "Rev", new BigDecimal("10")));

        var revenuesCopy = b.getRevenues();
        revenuesCopy.clear();   // modify returned map

        // original must NOT change
        assertEquals(1, b.getRevenues().size());
    }

    @Test
    void testCopyConstructor() {
        Budget b1 = new Budget();
        b1.addRevenue(new BasicRecord("R1", "Rev1", new BigDecimal("50")));
        b1.addExpense(new BasicRecord("E1", "Exp1", new BigDecimal("20")));

        Ministry m1 = new Ministry(1, "Min1",
                new BigDecimal("10"), new BigDecimal("20"), new BigDecimal("30"));
        m1.setAllocationEntry("A1", new BigDecimal("100"));
        b1.addMinistry(m1);

        Budget b2 = new Budget(b1);

        // Everything should be equal…
        assertEquals(b1.totalRevenues(), b2.totalRevenues());
        assertEquals(b1.totalExpenses(), b2.totalExpenses());
        assertEquals(b1.getMinistries().size(), b2.getMinistries().size());

        // …BUT not the same instances (defensive copy!)
        assertNotSame(b1.getRevenues().get("R1"), b2.getRevenues().get("R1"));
        assertNotSame(b1.getExpenses().get("E1"), b2.getExpenses().get("E1"));

        // Ministry deep copy check
        assertNotSame(b1.getMinistries().get(1), b2.getMinistries().get(1));
    }
}