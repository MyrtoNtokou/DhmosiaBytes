package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;

/**
 * Class that displays the application's main menu.
 *
 */
public final class ShowMenuOptions {

    /**
     * Private constractor to prevent object creation.
     */
    private ShowMenuOptions() { }

    /**
     * Displays the menu options depending on the role's access rights.
     *
     * @param currentRole the user's role
     * @param input Scanner for user's input
     * @return true if user wants to exit, false if not
     */
    public static boolean showMenu(final Role currentRole,
    final Scanner input) {
        Graphs graph = new Graphs();
        MenuOptions choice = null;
        do {
            // Display menu options
            for (MenuOptions opt : MenuOptions.values()) {
                if (currentRole.canAccess(opt)) {
                    System.out.println(opt.getCode() + ". "
                    + opt.getDescription());
                }
            }

            System.out.print("Επιλογή: ");

            try {
                int code = input.nextInt();
                choice = MenuOptions.fromCode(code);
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.next();
                continue;
            }

            if (choice == null) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println(" Δώστε έναν αριθμό από το 1 έως το "
                + MenuOptions.values().length);
                continue;
            }

            if (!currentRole.canAccess(choice)) {
                System.out.println("Δεν έχετε πρόσβαση σε αυτήν την επιλογή.");
                continue;
            }

            switch (choice) {
                case SHOW_BUDGET -> showBudget();
                case EDIT_BUDGET -> editBudget();
                case SUMMARY -> summary();
                case GRAPHS -> {
                    int code = graph.chooseGraph(input);
                    graph.runGraphs(input);
                }
                case EXIT -> {
                    return true;
                }
                default -> System.out.println("Μη έγκυρη επιλογή. "
                + "Παρακαλώ δοκιμάστε ξανά.");
            }
        } while (choice != MenuOptions.EXIT);
        return false;
    }

    /** Displays the national budget. */
    public static void showBudget() {
        List<Eggrafi> g =
        ReadBudget.readGeneralBudget("proypologismos2025.csv");
        DisplayBudget.showGeneral(g);
    }

    /** Edits the national budget. */
    public static void editBudget() {
        System.out.println("Τώρα επεξεργάζεσαι τον Κρατικό Προϋπολογισμό");
    }

    /** Shows summarized data. */
    public static void summary() {
        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");
        DisplayBudget.showMinistry(y);
    }
}
