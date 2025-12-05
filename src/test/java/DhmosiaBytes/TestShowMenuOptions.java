package dhmosiabytes;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestShowMenuOptions {

    @Test
    void testExitImmediately() {
        Scanner scanner = new Scanner("0");
        boolean result = ShowMenuOptions.showMenu(Role.FINANCE_MINISTER,
        scanner);
        assertTrue(result);
    }

    @Test
    void testChooseValidOptionThenExit() {
        Scanner scanner = new Scanner("1\n0");
        boolean result = ShowMenuOptions.showMenu(Role.FINANCE_MINISTER,
        scanner);
        assertTrue(result);
    }

    @Test
    void testRoleWithoutEditCannotAccessEdit() {
        Scanner scanner = new Scanner("3\n0");
        boolean result = ShowMenuOptions.showMenu(Role.PARLIAMENT, scanner);
        assertTrue(result);
    }

    @Test
    void testInvalidInputHandledGracefully() {
        Scanner scanner = new Scanner("99\n0");
        boolean result = ShowMenuOptions.showMenu(Role.FINANCE_MINISTER,
        scanner);
        assertTrue(result);
    }
}