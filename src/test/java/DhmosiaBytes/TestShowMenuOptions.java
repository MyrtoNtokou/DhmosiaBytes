package dhmosiabytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestShowMenuOptions {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testShowMenu_exitImmediately() {
        String inputData = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        boolean exit = ShowMenuOptions.showMenu(Role.PRIME_MINISTER, scanner);

        assertTrue(exit);
    }

    @Test
    void testShowMenu_invalidInput_thenExit() {
        String inputData = "abc\n0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        boolean exit = ShowMenuOptions.showMenu(Role.PRIME_MINISTER, scanner);

        assertTrue(outContent.toString().contains("Παρακαλώ εισάγετε αριθμό"));
        assertTrue(exit);
    }

    @Test
    void testShowMenu_invalidOptionNumber() {
        String inputData = "99\n0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        ShowMenuOptions.showMenu(Role.PRIME_MINISTER, scanner);

        assertTrue(outContent.toString().contains("Μη έγκυρη επιλογή"));
    }

    @Test
    void testShowMenu_otherMinistry_action3_doesNotThrow() {
        String inputData = "3\n0\n0\n0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        assertDoesNotThrow(() ->
            ShowMenuOptions.showMenu(Role.OTHER_MINISTRY, scanner)
        );
    }

    @Test
    void testShowBudget_doesNotThrow() {
        assertDoesNotThrow(ShowMenuOptions::showBudget);
    }

    @Test
    void testSummary_doesNotThrow() {
        assertDoesNotThrow(ShowMenuOptions::summary);
    }

    @Test
    void testHandleAction3_primeMinister_exitImmediately() {
        String inputData = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        assertDoesNotThrow(() ->
                ShowMenuOptions.handleAction3(Role.PRIME_MINISTER, scanner));
    }

    @Test
    void testHandleAction3_otherMinistry_invalidThenExit() {
        String inputData = "9\n0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        ShowMenuOptions.handleAction3(Role.OTHER_MINISTRY, scanner);

        assertTrue(outContent.toString().contains("Μη έγκυρος κωδικός"));
    }

    @Test
    void testEditHistory_cancelImmediately() {
        String inputData = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        assertDoesNotThrow(() ->
                ShowMenuOptions.editHistory(scanner));
    }

    @Test
    void testShowComparedBudgets_doesNotThrow() {
        assertDoesNotThrow(ShowMenuOptions::showComparedBudgets);
    }

    @Test
    void testEditBudget_exitImmediately() {
        String inputData = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        assertDoesNotThrow(() ->
                ShowMenuOptions.editBudget(scanner, Role.FINANCE_MINISTER));
    }

    @Test
    void testEditBudget_invalidInput() {
        String inputData = "abc\n0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        ShowMenuOptions.editBudget(scanner, Role.FINANCE_MINISTER);

        assertTrue(outContent.toString().contains("Παρακαλώ εισάγετε αριθμό"));
    }

    @Test
    void testSubmitRequest_exitImmediately() {
        String inputData = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        assertDoesNotThrow(() ->
                ShowMenuOptions.submitRequest(scanner, Role.OTHER_MINISTRY));
    }
}
