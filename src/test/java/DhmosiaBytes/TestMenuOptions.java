package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class TestMenuOptions {

    @Test
    public void testCodeAndDescription() {
        assertEquals(1, MenuOptions.SHOW_BUDGET.getCode());
        assertEquals("Εμφάνιση Κρατικού Προϋπολογισμού", MenuOptions.SHOW_BUDGET.getDescription());
        assertEquals(MenuOptions.SHOW_BUDGET, MenuOptions.fromCode(1));

        assertEquals(2, MenuOptions.EDIT_BUDGET.getCode());
        assertEquals("Αλλαγή στοιχείων", MenuOptions.EDIT_BUDGET.getDescription());
        assertEquals(MenuOptions.EDIT_BUDGET, MenuOptions.fromCode(2));

        assertEquals(3, MenuOptions.SUMMARY.getCode());
        assertEquals("Κατηγοριοποίηση στοιχείων", MenuOptions.SUMMARY.getDescription());
        assertEquals(MenuOptions.SUMMARY, MenuOptions.fromCode(3));

        assertEquals(4, MenuOptions.AGGRIGATE.getCode());
        assertEquals("Συγκεντρωτικά στοιχεία", MenuOptions.AGGRIGATE.getDescription());
        assertEquals(MenuOptions.AGGRIGATE, MenuOptions.fromCode(4));

        assertEquals(5, MenuOptions.COMPARISON.getCode());
        assertEquals("Σύγκριση Κρατικού Προϋπολογισμού διαφορετικών ετών", MenuOptions.COMPARISON.getDescription());
        assertEquals(MenuOptions.COMPARISON, MenuOptions.fromCode(5));

        assertEquals(6, MenuOptions.GRAPHS.getCode());
        assertEquals("Γραφήματα", MenuOptions.GRAPHS.getDescription());
        assertEquals(MenuOptions.GRAPHS, MenuOptions.fromCode(6));

        assertEquals(0, MenuOptions.EXIT.getCode());
        assertEquals("Έξοδος", MenuOptions.EXIT.getDescription());
        assertEquals(MenuOptions.EXIT, MenuOptions.fromCode(0));
    }

    @Test
    public void testFromCodeInvalid() {
        assertNull(MenuOptions.fromCode(-1));
        assertNull(MenuOptions.fromCode(999));
    }
}
