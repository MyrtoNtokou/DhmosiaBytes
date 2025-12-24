package dhmosiabytes;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetreader.DisplayBudget;
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
     * @return the List<Eggrafi> containing only th e revenues
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
        DisplayBudget.showGeneral(esoda);
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
        DisplayBudget.showGeneral(exoda);
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
        DisplayBudget.showMinistry(ministries);
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
     * Asks for specific revenue to be edited.
     *
     * @param scanner the Scanner for user's input
     * @param exoda the List<Eggrafi>
     * @return the code of the revenue to be edited
     */
    public String selectExpense(final Scanner scanner,
    final List<Eggrafi> exoda) {
        String choice;
        while (true) {
            System.out.print("Επιλογή: ");
            choice = scanner.nextLine();

            boolean exists = false;
            for (Eggrafi eg : exoda) {
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
}
