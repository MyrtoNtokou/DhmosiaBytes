package dhmosiabytes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetlogic.BudgetService;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;

/**
 * Cuts and displays list Eggrafi and list Ypourgeio.
 * Asks user to choose one element of the list
 * and accepts it only if it is valid.
 */
public class CutLists {

     /**
     * Displays some elements from list Eggrafi.
     *
     * @return the List<Eggrafi> containing only the revenues
     */
    public List<Eggrafi> cutEggrafiEsoda() {
        List<Eggrafi> g =
        ReadBudget.readGeneralBudget("proypologismos2026.csv");

        List<Eggrafi> esoda = new ArrayList<>();
        for (Eggrafi e : g) {
            if (e.getKodikos().startsWith("1,")) {
                esoda.add(e);
            }
        }
        return esoda;
    }

    /**
     * Displays the expenses from list Eggrafi.
     *
     * @return the List<Eggrafi> containing only the expenses
     */
    public List<Eggrafi> cutEggrafiExoda() {
        List<Eggrafi> g =
        ReadBudget.readGeneralBudget("proypologismos2026.csv");

        List<Eggrafi> exoda = new ArrayList<>();
        for (Eggrafi e : g) {
            if (e.getKodikos().startsWith("2,")) {
                exoda.add(e);
            }
        }
        return exoda;
    }

     /**
     * Displays some elements from list Ypourgeio.
     *
     * @return the List<Ypourgeio> containing only the ministries
     */
    public List<Ypourgeio> cutYpourgeio() {
        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2026anaypourgeio.csv");

        List<Ypourgeio> ministries = new ArrayList<>();
        for (Ypourgeio e : y) {
            if (!String.valueOf(e.getKodikos()).startsWith("4")
            && !String.valueOf(e.getKodikos()).startsWith("25")
            && !String.valueOf(e.getKodikos()).startsWith("33")) {
                ministries.add(e);
            }
        }
        return ministries;
    }

    /**
     * Prompts the user to select a revenue entry to edit, validates the input,
     * updates the selected entry, and returns the selected code.
     * The user can enter "0" to return without making any changes.
     *
     * @param scanner    Scanner for reading user input
     * @param esoda      List of revenue entries to choose from
     * @param initialBudget Budget object to edit
     * @return the code of the selected revenue, or "0" to go back
     */
    public String selectRevenue(final Scanner scanner,
    final List<Eggrafi> esoda, final Budget initialBudget) {
        BudgetService service = new BudgetService(initialBudget, null);
        BudgetEditor editor = new BudgetEditor(service);

        String choice;
        do {
            System.out.println("Επιλέξτε 0 για επιστροφή πίσω");
            System.out.print("Επιλέξτε τον κωδικό του εσόδου που θέλετε "
            + "να τροποποιήσετε: ");
            choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                return "0";
            }

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
            } else {
                editor.editIncome(choice, scanner);
                return choice;
            }
        } while (true);
    }

    /**
     * Prompts the user to select a ministry entry to edit,
     * validates the input, updates the selected entry,
     * and returns the selected code.
     * The user can enter 0 to return without making any changes.
     *
     * @param scanner    Scanner for reading user input
     * @param ministries List of ministries to choose from
     * @param initialBudget Budget object to edit
     * @return the code of the selected ministry, or 0 to go back
     */
    public int selectMinistry(final Scanner scanner,
    final List<Ypourgeio> ministries, final Budget initialBudget) {
        BudgetService service = new BudgetService(initialBudget, null);
        BudgetEditor editor = new BudgetEditor(service);

        int choice;
        do {
            System.out.println("Επιλέξτε 0 για επιστροφή πίσω");
            System.out.print("Επιλέξτε τον κωδικό του στοιχείου που θέλετε "
            + "να τροποποιήσετε: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Δώστε έναν έγκυρο αριθμό.");
                 continue;
            } catch (IllegalArgumentException e) {
                System.out.println("Μη έγκυρη επιλογή.");
                continue;
            }
            if (choice == 0) {
                return 0;
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
            } else {
                editor.editExpense(choice, scanner, initialBudget);
                return choice;
            }
        } while (true);

    }

    /**
     * Displays a numbered list of revenue entries
     * and prompts the user to select one by number.
     *
     * @param scanner the Scanner to read user input
     * @param esoda the list of revenue entries to choose from
     * @return the code of the selected revenue entry
     */
    public int selectRevenueByNumber(final Scanner scanner,
    final List<Eggrafi> esoda) {
        for (int i = 0; i < esoda.size(); i++) {
            System.out.println((i + 1) + ". " + esoda.get(i).getPerigrafi());
        }
        int choice = -1;
        while (choice < 1 || choice > esoda.size()) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > esoda.size()) {
                    System.out.println("Μη έγκυρη επιλογή. "
                    + "Δώστε αριθμό από 1 έως " + esoda.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη επιλογή.");
            }
        }
        return choice;
    }

    /**
     * Displays a numbered list of expense entries
     * and prompts the user to select one by number.
     *
     * @param scanner the Scanner to read user input
     * @param exoda the list of expense entries to choose from
     * @return the code of the selected revenue entry
     */
    public int selectExpenseByNumber(final Scanner scanner,
    final List<Eggrafi> exoda) {
        for (int i = 0; i < exoda.size(); i++) {
            System.out.println((i + 1) + ". " + exoda.get(i).getPerigrafi());
        }
        int choice = -1;
        while (choice < 1 || choice > exoda.size()) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > exoda.size()) {
                    System.out.println("Μη έγκυρη επιλογή. "
                    + "Δώστε αριθμό από 1 έως " + exoda.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη επιλογή.");
            }
        }
        return choice;
    }

    /**
     * Displays a numbered list of ministries
     * and prompts the user to select one by number.
     *
     * @param scanner the Scanner to read user input
     * @param ministry the list of ministries to choose from
     * @return the code of the selected ministry
     */
    public int selectMinistryByNumber(final Scanner scanner,
    final List<Ypourgeio> ministry) {
        for (int i = 0; i < ministry.size(); i++) {
            System.out.println((i + 1) + ". " + ministry.get(i).getOnoma());
        }
        int choice = -1;
        while (choice < 1 || choice > ministry.size()) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > ministry.size()) {
                    System.out.println("Μη έγκυρη επιλογή. "
                    + "Δώστε αριθμό από 1 έως " + ministry.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη επιλογή.");
            }
        }
        return choice;
    }
}
