package dhmosiabytes;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestShowMenuOptions {

    @Test
    void testExitReturnsTrue() {
        Scanner scanner = new Scanner("5");
        boolean result = ShowMenuOptions.showMenu(Role.CITIZEN, scanner);
        assertTrue(result);
    }

    @Test
    void testNonExitThenExitStillReturnsTrue() {
        Scanner scanner = new Scanner("1\n5");
        boolean result = ShowMenuOptions.showMenu(Role.FINANCE_MINISTER,
        scanner);
        assertTrue(result);
    }

    @Test
    void testRoleWithoutEditCannotExitImmediately() {
        Scanner scanner = new Scanner("2\n5");
        boolean result = ShowMenuOptions.showMenu(Role.CITIZEN, scanner);
        assertTrue(result);
    }

    @Test
    void testLoopDoesNotReturnFalse() {
        Scanner scanner = new Scanner("5");
        boolean result = ShowMenuOptions.showMenu(Role.PRIME_MINISTER,
        scanner);
        assertTrue(result);
    }
}
