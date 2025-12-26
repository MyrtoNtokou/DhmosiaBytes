package dhmosiabytes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
        ReadBudget.readGeneralBudget("proypologismos2025.csv");

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
        ReadBudget.readGeneralBudget("proypologismos2025.csv");

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
        ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

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
     * Asks for specific revenue to be edited.
     *
     * @param scanner the Scanner for user's input
     * @param esoda the List<Eggrafi>
     * @return the code of the revenue to be edited
     */
    public String selectRevenue(final Scanner scanner,
    final List<Eggrafi> esoda) {
        String choice;
        System.out.printf("%-6s | %-60s | %-25s%n", "Α/Α",
            "Έσοδα (1) / Έξοδα (2)", "Ποσό");
            for (Eggrafi e : esoda) {
                System.out.printf("%-6s | %-60s | %-25.2f%n",
                e.getKodikos(), e.getPerigrafi(), e.getPoso());
            }
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
        System.out.printf("%-3s | %-55s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο");
            for (Ypourgeio y : ministries) {
                System.out.printf("%-3d | %-55s | %-25.2f | %-35.2f | %-25.2f%n",
                y.getKodikos(), y.getOnoma(), y.getTaktikos(),
                y.getEpendyseis(), y.getSynolo());
            }
        while (true) {
            System.out.print("Επιλογή: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Δώστε έναν αριθμό από το 1 έως το "
                + MinistryOptions.values().length);
                continue;
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
     * Displays a numbered list of revenue entries
     * and prompts the user to select one by number.
     *
     * @param scanner the Scanner to read user input
     * @param esoda the list of revenue entries to choose from
     * @return the code of the selected revenue entry
     */
    public int selectRevenueByNumber(final Scanner scanner,
    final List<Eggrafi> esoda) {
        scanner.nextLine();

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
        scanner.nextLine();

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
        scanner.nextLine();

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
