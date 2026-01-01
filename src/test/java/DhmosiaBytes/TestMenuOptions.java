package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class TestMenuOptions {

    @Test
    public void testCode() {
        assertEquals(1, MenuOptions.SHOW_BUDGET.getCode());
        assertEquals(2, MenuOptions.SUMMARY.getCode());
        assertEquals(3, MenuOptions.ACTION_3.getCode());
        assertEquals(4, MenuOptions.AGGREGATE.getCode());
        assertEquals(5, MenuOptions.COMPARISON.getCode());
        assertEquals(6, MenuOptions.GRAPHS.getCode());
        assertEquals(0, MenuOptions.EXIT.getCode());
    }

    @Test
    public void testFromCodeValid() {
        assertEquals(MenuOptions.SHOW_BUDGET, MenuOptions.fromCode(1));
        assertEquals(MenuOptions.SUMMARY, MenuOptions.fromCode(2));
        assertEquals(MenuOptions.ACTION_3, MenuOptions.fromCode(3));
        assertEquals(MenuOptions.AGGREGATE, MenuOptions.fromCode(4));
        assertEquals(MenuOptions.COMPARISON, MenuOptions.fromCode(5));
        assertEquals(MenuOptions.GRAPHS, MenuOptions.fromCode(6));
        assertEquals(MenuOptions.EXIT, MenuOptions.fromCode(0));
    }

    @Test
    public void testFromCodeInvalid() {
        assertNull(MenuOptions.fromCode(-1));
        assertNull(MenuOptions.fromCode(999));
        assertNull(MenuOptions.fromCode(7));
    }
}
