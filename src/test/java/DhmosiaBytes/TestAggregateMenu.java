package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class TestAggregateMenu {

    @Test
    void testTypeOfBudgetGeneralBudget() {
        // User selects "1"
        Scanner scanner = new Scanner("1\n");
        AggregateMenu menu = new AggregateMenu();

        int result = menu.typeOfBudget(scanner);

        assertEquals(1, result);
    }

    @Test
    void testTypeOfBudgetMinistriesBudget() {
        // User selects "2"
        Scanner scanner = new Scanner("2\n");
        AggregateMenu menu = new AggregateMenu();

        int result = menu.typeOfBudget(scanner);

        assertEquals(2, result);
    }

    @Test
    void testTypeOfBudgetExit() {
        // User selects "0"
        Scanner scanner = new Scanner("0\n");
        AggregateMenu menu = new AggregateMenu();

        int result = menu.typeOfBudget(scanner);

        assertEquals(0, result);
    }

    @Test
    void testTypeOfBudgetInvalidThenValid() {
        // User enters invalid input first, then valid
        Scanner scanner = new Scanner("5\n1\n");
        AggregateMenu menu = new AggregateMenu();

        int result = menu.typeOfBudget(scanner);

        assertEquals(1, result);
    }

    @Test
    void testTypeOfBudgetNonNumericThenValid() {
        // User enters text first, then valid number
        Scanner scanner = new Scanner("abc\n2\n");
        AggregateMenu menu = new AggregateMenu();

        int result = menu.typeOfBudget(scanner);

        assertEquals(2, result);
    }
}
