package dhmosiabytes;

import java.math.BigDecimal;
import java.util.Scanner;

import budgetlogic.BudgetService;

 /**
 * Allows editing of income and expense entries in a Budget.
 */
public class BudgetEditor {
    /**  BudgetService used to perform operations on the budget. */
    private final BudgetService service;

    /**
     * Constructs a BudgetEditor with the specified BudgetService.
     *
     * @param serv the BudgetService used to modify and validate budget data
     */
    public BudgetEditor(final BudgetService serv) {
        this.service = serv;
    }

    /**
     * Asks user for the new income amount and replaces the old one.
     *
     * @param code the code of the income to be replaced
     * @param scanner Scanner to read user input
     */
    public void editIncome(final String code, final Scanner scanner) {
        BigDecimal newAmount = null;

        while (newAmount == null) {
            System.out.println("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner.nextLine();
            try {
                newAmount = new BigDecimal(input);
                if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                newAmount = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
            }

            this.service.changeGeneralAmount(code, newAmount);
        }
    }

    /**
     * Asks user for the new expense amount and replaces the old one.
     *
     * @param code the code of the expense to be replaced
     * @param scanner the Scanner to read user input
     */
    public void editExpense(final int code, final Scanner scanner) {
        ShowEditMenuOptions editMenu = new ShowEditMenuOptions();
        int type = editMenu.selectBudgetType(scanner);
        String column;
        if (type == 1) {
            column = "τακτικός";
        } else {
            column = "πδε";
        }
        BigDecimal newAmount = null;

        while (newAmount == null) {
            System.out.print("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner.nextLine();
            try {
                newAmount = new BigDecimal(input);
                if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                newAmount = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
                continue;
            }

            this.service.changeMinistryAmount(code, column, newAmount);
        }
    }
}
