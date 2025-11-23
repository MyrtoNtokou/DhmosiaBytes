package dhmosiabytes;

import java.util.Scanner;
import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;
import java.util.List;

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
        MenuOptions choice;
        do {
            // Display menu options
            for (MenuOptions opt : MenuOptions.values()) {
                if (currentRole.canAccess(opt)) {
                    System.out.println(opt.getCode() + ". "
                    + opt.getDescription());
                }
            }

            System.out.print("Επιλογή: ");
            choice = MenuOptions.fromCode(input.nextInt());

            if (!currentRole.canAccess(choice)) {
                System.out.println("Δεν έχετε πρόσβαση σε αυτήν την επιλογή.");
                continue;
            }

            switch (choice) {
                case SHOW_BUDGET -> showBudget();
                case EDIT_BUDGET -> editBudget();
                case SUMMARY -> summary();
                case GRAPHS -> graphs();
                case EXIT -> {
                    System.out.println("Έξοδος από εφαρμογή");
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
        System.out.println("Βλέπεις τον Κρατικό Προϋπολογισμό");
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
        System.out.println("Εμφάνιση στοιχείων ανά Υπουργείο");
        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");
        DisplayBudget.showMinistry(y);
    }

    /** Displays charts for the user. */
    public static void graphs() {
        System.out.println("Τώρα βλέπεις τα γραφήματα");
    }
}
