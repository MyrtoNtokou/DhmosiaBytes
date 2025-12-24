package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetlogic.BudgetService;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

/**
 * Provides menus and input handling for editing budget entries.
 */
public class ShowEditMenuOptions {
    /** Constracts a ShowEditMenuOptions object. */
    public ShowEditMenuOptions() { }

    /**
     * Displays the RevenueOrExpense menu and gets the user's choice.
     *
     * @param scanner the Scanner for user input
     * @return the selected RevenueOrExpense constant
     */
    public RevenueOrExpense chooseRevenueOrExpense(final Scanner scanner) {
        for (RevenueOrExpense opt : RevenueOrExpense.values()) {
            System.out.println(opt.getRevenueOrExpenseCode() + ". "
            + opt.getDescription());
        }
        RevenueOrExpense selected = null;
        do {
            System.out.print("\nΕπιλογή: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                scanner.next();
                continue;
            }
            if (choice < 1 || choice > RevenueOrExpense.values().length) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + RevenueOrExpense.values().length);
                continue;
            }
            selected = RevenueOrExpense.fromCode(choice);
        } while (selected == null);
        return selected;
    }

    /**
     * Edits either income or expense entries in the given budget.
     * Displays the relevant entries and invokes the editor to update values.
     *
     * @param initialBudget the Budget object to edit
     * @param scanner Scanner to read user input
     * @param selected RevenueOrExpense constant
     * indicating whether to edit income or expense
     */
    public void editRevenueOrExpence(final Budget initialBudget,
    final Scanner scanner, final RevenueOrExpense selected) {
        BudgetService service = new BudgetService(initialBudget, null);
        BudgetEditor editor = new BudgetEditor(service);
        CutLists cut = new CutLists();

        if (selected == RevenueOrExpense.INCOME) {
            List<Eggrafi> esoda = cut.cutEggrafiEsoda();
            String code = cut.selectRevenue(scanner, esoda);
            editor.editIncome(code, scanner);
        } else if (selected == RevenueOrExpense.EXPENSE) {
            List<Ypourgeio> ministries = cut.cutYpourgeio();
            int code = cut.selectMinistry(scanner, ministries);
            editor.editExpense(code, scanner);
        }
    }

    /**
     * Displays the BudgetType menu and asks the user for their choice.
     *
     * @param scanner the Scanner for user's input
     * @return the code of user's choice
     */
    public int selectBudgetType(final Scanner scanner) {
        BudgetType selectedOption1 = null;
        for (BudgetType option : BudgetType.values()) {
            System.out.println(option.getTypeCode() + ". "
            + option.getTypeDescription());
        }

        while (selectedOption1 == null) {
            System.out.print("Επιλογή: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                selectedOption1 = BudgetType.fromCode(choice);
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + BudgetType.values().length);
            }
        }
        return selectedOption1.getTypeCode();
    }
}
