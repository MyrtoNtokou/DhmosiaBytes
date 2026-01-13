package dhmosiabytes;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestShowMenuOptions {

    @Test
    void testShowMenuExitImmediately() {
        // Παρέχουμε άμεσα επιλογή "EXIT"
        Scanner input = new Scanner(MenuOptions.EXIT.getCode() + "\n");
        boolean result = ShowMenuOptions.showMenu(Role.PRIME_MINISTER, input);
        assertTrue(result, "Το μενού πρέπει να επιστρέψει true για άμεσο exit");
    }

    @Test
    void testShowMenuInvalidInputThenExit() {
        // Αρχικά μη έγκυρη είσοδος, μετά EXIT
        Scanner input = new Scanner("abc\n" + MenuOptions.EXIT.getCode() + "\n");
        boolean result = ShowMenuOptions.showMenu(Role.PARLIAMENT, input);
        assertTrue(result, "Μετά από μη έγκυρη είσοδο, πρέπει να γίνει exit σωστά");
    }

    @Test
    void testShowMenuInvalidMenuCodeThenExit() {
        // Αρχικά αριθμός εκτός μενού, μετά EXIT
        Scanner input = new Scanner("999\n" + MenuOptions.EXIT.getCode() + "\n");
        boolean result = ShowMenuOptions.showMenu(Role.FINANCE_MINISTER, input);
        assertTrue(result, "Μετά από μη έγκυρη επιλογή κωδικού, πρέπει να γίνει exit σωστά");
    }

    @Test
    void testHandleAction3FinanceMinisterExitImmediately() {
        // Επιλέγουμε άμεσα έξοδο στο υπομενού του Finance Minister
        Scanner input = new Scanner("0\n");
        assertDoesNotThrow(() -> ShowMenuOptions.handleAction3(Role.FINANCE_MINISTER, input));
    }

    @Test
    void testEditBudgetExitImmediately() {
        // Άμεση έξοδος από editBudget
        Scanner input = new Scanner("0\n");
        assertDoesNotThrow(() -> ShowMenuOptions.editBudget(input, Role.FINANCE_MINISTER));
    }

    @Test
    void testEditHistoryNullChoiceReturnsSafely() {
        // Άμεση έξοδος από editHistory με μη έγκυρη επιλογή τύπου
        Scanner input = new Scanner("0\n");
        assertDoesNotThrow(() -> ShowMenuOptions.editHistory(input));
    }

    @Test
    void testOtherMinistryMenuExitImmediately() {
        // Άμεση έξοδος από το OTHER_MINISTRY υπομενού
        Scanner input = new Scanner("0\n");
        assertDoesNotThrow(() -> ShowMenuOptions.handleAction3(Role.OTHER_MINISTRY, input));
    }

    @Test
    void testShowComparedBudgetsDoesNotThrow() {
        // Βεβαιωνόμαστε ότι η μέθοδος σύγκρισης δεν πετάει exception
        assertDoesNotThrow(ShowMenuOptions::showComparedBudgets);
    }
}
