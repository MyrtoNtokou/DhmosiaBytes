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
    void testMainFullAppFlow() {
        String inputData = "1\n" +
                        "9\n" +
                        "1\n" +
                        "testUser\n" + 
                        "Password123!\n" + 
                        "0\n";

        assertDoesNotThrow(() -> {
            System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
            BudgetPlusPlus.main(new String[]{});
        });
    }

    @Test
    void testMainLoginFlow() {
        String inputData = "2\n" + 
                        "2\n" + 
                        "nonExistentUser\n" + 
                        "0\n"; 

        assertDoesNotThrow(() -> {
            System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
            BudgetPlusPlus.main(new String[]{});
        });
    }

    @Test
    void testLoginMaxAttemptsExceeded() {
        String inputData = "1\n2\ntestUser\nwrong1\n\nwrong2\n\nwrong3\n\n0\n"; 

        assertDoesNotThrow(() -> {
            System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
            BudgetPlusPlus.main(new String[]{});
        });
    }

    @Test
    void testInvalidMenuChoices() {
        String inputData = "1\n" +
                        "abc\n" +
                        "99\n" +
                        "0\n";

        assertDoesNotThrow(() -> {
            System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
            BudgetPlusPlus.main(new String[]{});
        });
    }

    @Test
    void testRegisterDuplicateRoleRestriction() {

        String inputData = "1\n1\nuserA\nPass123!\n" +
                        "1\n1\nuserB\n" +
                        "0\n";

        assertDoesNotThrow(() -> {
            System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
            BudgetPlusPlus.main(new String[]{});
        });
    }
}