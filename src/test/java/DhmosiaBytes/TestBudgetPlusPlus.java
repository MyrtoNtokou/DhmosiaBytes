package dhmosiabytes;

import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class TestBudgetPlusPlus {

    @Test
    void testSelectRoleValidRole() throws Exception {
        Method method = BudgetPlusPlus.class
                .getDeclaredMethod("selectRole", Scanner.class);
        method.setAccessible(true);

        Scanner input = new Scanner(Role.PRIME_MINISTER.getCode() + "\n");

        Role result = (Role) method.invoke(null, input);

        assertEquals(Role.PRIME_MINISTER, result);
    }

    @Test
    void testSelectRoleInvalidInputThenExit() throws Exception {
        Method method = BudgetPlusPlus.class
                .getDeclaredMethod("selectRole", Scanner.class);
        method.setAccessible(true);

        Scanner input = new Scanner(
                "abc\n" +
                "0\n"
        );

        Role result = (Role) method.invoke(null, input);

        assertNull(result);
    }

    @Test
    void testSelectRoleOutOfRangeThenExit() throws Exception {
        Method method = BudgetPlusPlus.class
                .getDeclaredMethod("selectRole", Scanner.class);
        method.setAccessible(true);

        Scanner input = new Scanner(
                "999\n" +
                "0\n"
        );

        Role result = (Role) method.invoke(null, input);

        assertNull(result);
    }

    @Test
    void testMainExitImmediately() {
        assertDoesNotThrow(() -> {
            System.setIn(new java.io.ByteArrayInputStream("0\n".getBytes()));
            BudgetPlusPlus.main(new String[]{});
        });
    }
}