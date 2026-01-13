package dhmosiabytes;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import budgetlogic.Budget;
import budgetreader.BasicRecord;
import budgetreader.Ministry;

class TestShowEditMenuOptions {

    @Test
    void testEditRevenueOrExpenseIncomeExitImmediately() {
        Budget budget = new Budget();
        budget.addRevenue(new BasicRecord("R1", "Dummy Income", BigDecimal.valueOf(1000)));

        String input = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ShowEditMenuOptions menu = new ShowEditMenuOptions();
        menu.editRevenueOrExpense(budget, scanner, RevenueOrExpense.INCOME, null);

        assertEquals(1, budget.getRevenues().size());
    }

    @Test
    void testEditRevenueOrExpenseExpenseExitImmediately() {
        Budget budget = new Budget();
        budget.addMinistry(new Ministry(
                1, "Dummy Ministry",
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(1500)
        ));

        String input = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ShowEditMenuOptions menu = new ShowEditMenuOptions();
        menu.editRevenueOrExpense(budget, scanner, RevenueOrExpense.EXPENSE, null);

        assertEquals(1, budget.getMinistries().size());
    }

    @Test
    void testChooseRevenueOrExpenseInvalidThenValid() {
        String input = "5\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ShowEditMenuOptions menu = new ShowEditMenuOptions();
        RevenueOrExpense result = menu.chooseRevenueOrExpense(scanner);

        assertEquals(RevenueOrExpense.INCOME, result);
    }

    @Test
    void testSelectBudgetTypeExitImmediately() {
        String input = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        ShowEditMenuOptions menu = new ShowEditMenuOptions();
        int code = menu.selectBudgetType(scanner);

        assertEquals(0, code);
    }
}