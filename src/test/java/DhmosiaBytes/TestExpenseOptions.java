package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestExpenseOptions {

    @Test
    void testGetters() {
        ExpenseOptions opt = ExpenseOptions.EMPLOYEE_BENEFITS;

        assertEquals("2,1", opt.getExpenseCode());
        assertEquals("Παροχές σε εργαζομένους", opt.getExpenseDescription());

        ExpenseOptions opt2 = ExpenseOptions.EQUITY_SECURITIES;
        assertEquals("2,9,3", opt2.getExpenseCode());
        assertEquals("Συμμετοχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων",
                     opt2.getExpenseDescription());
    }

    @Test
    void testFromCodeValid() {
        ExpenseOptions opt = ExpenseOptions.fromCode("2,5");
        assertEquals(ExpenseOptions.SUBSIDIES, opt);

        ExpenseOptions opt2 = ExpenseOptions.fromCode("2,9,6");
        assertEquals(ExpenseOptions.XRHMATOOIKONOMIKA, opt2);
    }

    @Test
    void testFromCodeInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ExpenseOptions.fromCode("invalid-code");
        });

        assertTrue(exception.getMessage().contains("Μη έγκυρη επιλογή"));
    }
}
