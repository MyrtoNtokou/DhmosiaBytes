package dhmosiabytes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetlogic.BudgetService;
import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
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

        if (selected == RevenueOrExpense.INCOME) {
            List<Eggrafi> g =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");

            List<Eggrafi> esoda = new ArrayList<>();
            for (Eggrafi e : g) {
                if (e.getKodikos().startsWith("1,")) {
                    esoda.add(e);
                }
            }
            DisplayBudget.showGeneral(esoda);
            String code = selectRevenue(scanner, esoda);
            editor.editIncome(code, scanner);
        } else if (selected == RevenueOrExpense.EXPENSE) {
            List<Ypourgeio> y =
            ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

            List<Ypourgeio> ministries = new ArrayList<>();
            for (Ypourgeio e : y) {
                if (!String.valueOf(e.getKodikos()).startsWith("4")
                && !String.valueOf(e.getKodikos()).startsWith("25")
                && !String.valueOf(e.getKodikos()).startsWith("33")) {
                    ministries.add(e);
                }
            }
            DisplayBudget.showMinistry(ministries);

            int code = selectMinistry(scanner, ministries);
            editor.editExpense(code, scanner);
        }
    }

    /**
     * Asks for specific revenue to be edited.
     *
     * @param scanner the Scanner for user's input
     * @param esoda the List<Eggrafi>
     * @return the code of the revenue to be edited
     */
    public String selectRevenue(final Scanner scanner,
    final List<Eggrafi> esoda) {
        String choice;
        while (true) {
            System.out.print("Επιλογή: ");
            choice = scanner.nextLine();

            boolean exists = false;
            for (Eggrafi eg : esoda) {
                if (eg.getKodikos().equals(choice)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println("Δεν υπάρχει επιλογή "
                + "με αυτόν τον κωδικό.");
                continue;
            }
            break;
        }
        return choice;
    }

    /**
     * Asks for specific expense to be edited.
     *
     * @param scanner the Scanner for user's input
     * @param ministries the List<Ypourgeio>
     * @return the code of the expense to be edited
     */
    public int selectMinistry(final Scanner scanner,
    final List<Ypourgeio> ministries) {
        int choice = -1;
        while (true) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + MinistryOptions.values().length);
            } catch (IllegalArgumentException e) {
                System.out.println("Μη έγκυρη επιλογή.");
                continue;
            }

            boolean exists = false;
            for (Ypourgeio y : ministries) {
                if (y.getKodikos() == choice) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println("Δεν υπάρχει επιλογή "
                + "με αυτόν τον κωδικό.");
                continue;
            }
            break;
        }
        return choice;
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
