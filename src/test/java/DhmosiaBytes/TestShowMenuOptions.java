package dhmosiabytes;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestShowMenuOptions {

    @Test
    void testShowMenuExitImmediately() {
        Scanner input = new Scanner(
                MenuOptions.EXIT.getCode() + "\n"
        );

        boolean result = ShowMenuOptions.showMenu(
                Role.PRIME_MINISTER,
                input
        );

        assertTrue(result);
    }

    @Test
    void testShowMenuInvalidInputThenExit() {
        Scanner input = new Scanner(
                "abc\n" +
                MenuOptions.EXIT.getCode() + "\n"
        );

        boolean result = ShowMenuOptions.showMenu(
                Role.PARLIAMENT,
                input
        );

        assertTrue(result);
    }

    @Test
    void testShowMenuInvalidMenuCodeThenExit() {
        Scanner input = new Scanner(
                "999\n" +
                MenuOptions.EXIT.getCode() + "\n"
        );

        boolean result = ShowMenuOptions.showMenu(
                Role.FINANCE_MINISTER,
                input
        );

        assertTrue(result);
    }

    @Test
    void testHandleAction3FinanceMinisterExitImmediately() {
        Scanner input = new Scanner("0\n");

        assertDoesNotThrow(() ->
                ShowMenuOptions.handleAction3(
                        Role.FINANCE_MINISTER,
                        input
                )
        );
    }

    @Test
    void testEditBudgetExitImmediately() {
        Scanner input = new Scanner("0\n");

        assertDoesNotThrow(() ->
                ShowMenuOptions.editBudget(
                        input,
                        Role.FINANCE_MINISTER
                )
        );
    }

    @Test
    void testEditHistoryNullChoiceReturnsSafely() {
        Scanner input = new Scanner("0\n");

        assertDoesNotThrow(() ->
                ShowMenuOptions.editHistory(input)
        );
    }
}