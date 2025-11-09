import static org.junit.jupiter.api.Assertions.*;
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

        assertEquals(4, MenuOptions.GRAPHS.getCode());
        assertEquals("Γραφήματα", MenuOptions.GRAPHS.getDescription());
        assertEquals(MenuOptions.GRAPHS, MenuOptions.fromCode(4));

        assertEquals(5, MenuOptions.EXIT.getCode());
        assertEquals("Έξοδος", MenuOptions.EXIT.getDescription());
        assertEquals(MenuOptions.EXIT, MenuOptions.fromCode(5));
    }
}