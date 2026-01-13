package dhmosiabytes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetreader.BasicRecord;
import budgetreader.ReadBudget;
import budgetreader.Ministry;
import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.RED;

/**
 * Cuts and displays list BasicRecord and list Ministry.
 * Asks user to choose one element of the list
 * and accepts it only if it is valid.
 */
public class CutLists {

     /**
     * Displays some elements from list BasicRecord.
     *
     * @return the List<BasicRecord> containing only the revenues
     */
    public List<BasicRecord> cutBasicRecordEsoda() {
        List<BasicRecord> g =
        ReadBudget.readGeneralBudget("proypologismos2026.csv");

        List<BasicRecord> esoda = new ArrayList<>();
        for (BasicRecord e : g) {
            if (e.getCode().startsWith("1,")) {
                esoda.add(e);
            }
        }
        return esoda;
    }

    /**
     * Displays the expenses from list BasicRecord.
     *
     * @return the List<BasicRecord> containing only the expenses
     */
    public List<BasicRecord> cutBasicRecordExoda() {
        List<BasicRecord> g =
        ReadBudget.readGeneralBudget("proypologismos2026.csv");

        List<BasicRecord> exoda = new ArrayList<>();
        for (BasicRecord e : g) {
            if (e.getCode().startsWith("2,")) {
                exoda.add(e);
            }
        }
        return exoda;
    }

     /**
     * Displays some elements from list Ministry.
     *
     * @return the List<Ministry> containing only the ministries
     */
    public List<Ministry> cutMinistry() {
        List<Ministry> y =
        ReadBudget.readByMinistry("proypologismos2026anaypourgeio.csv");

        List<Ministry> ministries = new ArrayList<>();
        for (Ministry e : y) {
            if (!String.valueOf(e.getcode()).startsWith("4")
            && !String.valueOf(e.getcode()).startsWith("25")
            && !String.valueOf(e.getcode()).startsWith("33")) {
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
    final List<BasicRecord> esoda, final Budget initialBudget) {
        BudgetEditor editor = new BudgetEditor();

        String choice;
        do {
            System.out.println("\nΕπιλέξτε 0 για επιστροφή πίσω");
            System.out.print("Επιλέξτε τον κωδικό του εσόδου που θέλετε "
            + "να τροποποιήσετε: ");
            choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                return "0";
            }

            boolean exists = false;
            for (BasicRecord eg : esoda) {
                if (eg.getCode().equals(choice)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println(RED + "Δεν υπάρχει επιλογή "
                + "με αυτόν τον κωδικό." + RESET);
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
     * @param currentRole Role of login
     * @return the code of the selected ministry, or 0 to go back
     */
    public int selectMinistry(final Scanner scanner,
    final List<Ministry> ministries, final Budget initialBudget,
    final Role currentRole) {
        BudgetEditor editor = new BudgetEditor();

        int choice;
        do {
            System.out.println("\nΕπιλέξτε 0 για επιστροφή πίσω");
            System.out.print("Επιλέξτε τον κωδικό του στοιχείου που θέλετε "
            + "να τροποποιήσετε: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Δώστε έναν έγκυρο αριθμό.");
                 continue;
            } catch (IllegalArgumentException e) {
                System.out.println(RED + "Μη έγκυρη επιλογή." + RESET);
                continue;
            }
            if (choice == 0) {
                return 0;
            }

            boolean exists = false;
            for (Ministry y : ministries) {
                if (y.getcode() == choice) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                System.out.println(RED + "Δεν υπάρχει επιλογή "
                + "με αυτόν τον κωδικό." + RESET);
            } else {
                editor.editExpense(choice, scanner, initialBudget, currentRole);
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
    final List<BasicRecord> esoda) {
        System.out.println();
        for (int i = 0; i < esoda.size(); i++) {
            System.out.println((i + 1) + ". " + esoda.get(i).getDescription());
        }
        int choice = -1;
        while (choice < 1 || choice > esoda.size()) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > esoda.size()) {
                    System.out.println(RED + "Μη έγκυρη επιλογή. " + RESET
                    + "Δώστε αριθμό από 1 έως " + esoda.size());
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη επιλογή." + RESET);
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
    final List<BasicRecord> exoda) {
        System.out.println();
        for (int i = 0; i < exoda.size(); i++) {
            System.out.println((i + 1) + ". " + exoda.get(i).getDescription());
        }
        int choice = -1;
        while (choice < 1 || choice > exoda.size()) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > exoda.size()) {
                    System.out.println(RED + "Μη έγκυρη επιλογή. " + RESET
                    + "Δώστε αριθμό από 1 έως " + exoda.size());
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη επιλογή." + RESET);
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
    final List<Ministry> ministry) {
        System.out.println();
        for (int i = 0; i < ministry.size(); i++) {
            System.out.println((i + 1) + ". " + ministry.get(i).getName());
        }
        int choice = -1;
        while (choice < 1 || choice > ministry.size()) {
            System.out.print("Επιλογή: ");
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > ministry.size()) {
                    System.out.println(RED + "Μη έγκυρη επιλογή. " + RESET
                    + "Δώστε αριθμό από 1 έως " + ministry.size());
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Μη έγκυρη επιλογή." + RESET);
            }
        }
        return choice;
    }
}
