package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetlogic.Budget;
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
        RevenueOrExpense selected = null;
        do {
            for (RevenueOrExpense opt : RevenueOrExpense.values()) {
                System.out.println(opt.getRevenueOrExpenseCode() + ". "
                + opt.getDescription());
            }

            System.out.print("\nΕπιλογή: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                selected = RevenueOrExpense.fromCode(choice);
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Δώστε έναν αριθμό από το 1 έως "
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
     */
    public void editRevenueOrExpense(final Budget initialBudget,
    final Scanner scanner, final RevenueOrExpense selected) {
        // εδώ θα κληθεί η Μυρτώ αντί για αυτό
        CutLists cut = new CutLists();
        if (selected == RevenueOrExpense.INCOME) {
            List<Eggrafi> esoda = cut.cutEggrafiEsoda();
            System.out.printf("%-6s | %-60s | %-25s%n", "Α/Α",
            "Έσοδα", "Ποσό");
            for (Eggrafi e : esoda) {
                System.out.printf("%-6s | %-60s | %-25.2f%n",
                e.getKodikos(), e.getPerigrafi(), e.getPoso());
            }

            String code;
            do {
                code = cut.selectRevenue(scanner, esoda, initialBudget);
            } while (!code.equals("0"));
        } else if (selected == RevenueOrExpense.EXPENSE) {
            // εδώ θα κληθεί η Μυρτώ αντί για αυτό
            List<Ypourgeio> ministries = cut.cutYpourgeio();
            System.out.printf("%-3s | %-55s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο");
            for (Ypourgeio y : ministries) {
                System.out.printf("%-3d | %-55s | %-25.2f | %-35.2f | "
                + "%-25.2f%n",
                y.getKodikos(), y.getOnoma(), y.getTaktikos(),
                y.getEpendyseis(), y.getSynolo());
            }

            int code;
            do {
                code = cut.selectMinistry(scanner, ministries, initialBudget);
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
        BudgetType selectedOption = null;

        System.out.println("\nΕπιλέξτε τύπο προϋπολογισμού:");
        do {
            for (BudgetType option : BudgetType.values()) {
                System.out.println(option.getTypeCode() + ". "
                + option.getTypeDescription());
            }

            System.out.print("Επιλογή: ");
                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    selectedOption = BudgetType.fromCode(choice);
                } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                scanner.next();
            } catch (IllegalArgumentException e) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                        + BudgetType.values().length);
            }
        } while (selectedOption == null);
        return selectedOption.getTypeCode();
    }
}
