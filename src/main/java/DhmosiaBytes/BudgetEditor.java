package dhmosiabytes;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetlogic.BudgetDiffPrinter;
import budgetlogic.BudgetSave;
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
     * Edits the amount of a specific income entry in the general budget.
     * The user is prompted to enter a new non-negative amount.
     * The updated value is saved to the modified budget files and
     * the differences between the initial and the updated budget
     * are displayed.
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
        }

        Budget before = new Budget(service.getBudget());
        this.service.changeGeneralAmount(code, newAmount);

        try {
            BudgetSave saver = new BudgetSave();
            Budget finalBudget = service.getBudget();
            saver.saveChanges(finalBudget, "newgeneral.csv",
            "newministries.csv");
            System.out.println("Η αλλαγή αποθηκεύτηκε επιτυχώς.");
            BudgetDiffPrinter.printDiffGeneral(before, finalBudget);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση.");
        }
    }

    /**
     * Edits the budget amount of a specific ministry.
     * The user selects the budget type (regular or public investments),
     * enters a new amount, and the change is saved to the modified budget
     * files.
     * After saving, the differences between the initial and the updated budget
     * are printed.
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
            }
        }

        Budget before = new Budget(service.getBudget());
        this.service.changeMinistryAmount(code, column, newAmount);

        try {
            BudgetSave saver = new BudgetSave();
            Budget finalBudget = service.getBudget();
            saver.saveChanges(finalBudget, "newgeneral.csv",
            "newministries.csv");
            System.out.println("Η αλλαγή αποθηκεύτηκε επιτυχώς.");
            BudgetDiffPrinter.printDiffMinistries(before, finalBudget);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση.");
        }
    }
}
