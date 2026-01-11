package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetlogic.BudgetDiffPrinter;
import budgetreader.BasicRecord;
import budgetreader.Ministry;

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
        RevenueOrExpense selected = null;
        do {
            System.out.println();
            for (RevenueOrExpense opt : RevenueOrExpense.values()) {
                System.out.println(opt.getRevenueOrExpenseCode() + ". "
                + opt.getDescription());
            }
            System.out.println("0. Έξοδος");

            System.out.print("\nΕπιλογή: ");
            int choice;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 0) {
                    return null;
                }
                selected = RevenueOrExpense.fromCode(choice);
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + RevenueOrExpense.values().length);
            }

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
     * @param currentRole Login role
     */
    public void editRevenueOrExpense(final Budget initialBudget,
            final Scanner scanner, final RevenueOrExpense selected,
            final Role currentRole) {
        CutLists cut = new CutLists();
        if (selected == RevenueOrExpense.INCOME) {
            List<BasicRecord> esoda = cut.cutBasicRecordEsoda();
            BudgetDiffPrinter.printRevenues(initialBudget);
            String code;
            do {
                code = cut.selectRevenue(scanner, esoda, initialBudget);
            } while (!code.equals("0"));
        } else if (selected == RevenueOrExpense.EXPENSE) {
            List<Ministry> ministries = cut.cutMinistry();
            BudgetDiffPrinter.printMinistries(initialBudget);
            int code;
            do {
                code = cut.selectMinistry(scanner, ministries, initialBudget,
                currentRole);
            } while (code != 0);
        }
    }

    /**
     * Displays the BudgetType menu and asks the user for their choice.
     *
     * @param scanner the Scanner for user's input
     * @return the code of user's choice
     */
    public int selectBudgetType(final Scanner scanner) {

        System.out.println("\nΕπιλέξτε τύπο προϋπολογισμού:");
        while (true) {
            for (BudgetType option : BudgetType.values()) {
                System.out.println(option.getTypeCode() + ". "
                + option.getTypeDescription());
            }
            System.out.println("0. Έξοδος");
            System.out.print("Επιλογή: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 0) {
                    return 0;
                }

                BudgetType selectedOption = BudgetType.fromCode(choice);
                return selectedOption.getTypeCode();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
