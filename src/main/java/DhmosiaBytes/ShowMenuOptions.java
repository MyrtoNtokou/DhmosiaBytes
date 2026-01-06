package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetcomparison.ComparisonController;
import budgetlogic.Budget;
import budgetlogic.BudgetAssembler;
import budgetlogic.BudgetDiffPrinter;
import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;
import ministryrequests.MinistryRequest;
import ministryrequests.MinistryRequestPrinter;
import ministryrequests.MinistryRequestService;
import ministryrequests.RequestType;

/**
 * Class that displays the application's main menu.
 *
 */
public final class ShowMenuOptions {

    /**
     * Private constractor to prevent object creation.
     */
    private ShowMenuOptions() { }

    /** Code for option 3. */
    private static final int CODE_FOR_OPTION_3 = 3;

    /** Code to edit a new file. */
    private static final int CODE_FOR_MENUS = 4;

    /** Code for rejecting a comment. */
    private static final int REJECTED = 2;

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
            System.out.println();
            for (MenuOptions opt : MenuOptions.values()) {
                if (currentRole.canAccess(opt)) {
                    System.out.println(opt.getCode() + ". "
                    + currentRole.getMenuLabel(opt));
                }
            }

            System.out.print("Επιλογή: ");

            try {
                int code = input.nextInt();
                input.nextLine();
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
                case SUMMARY -> summary();
                case ACTION_3 -> handleAction3(currentRole, input);
                case AGGREGATE -> {
                    AggregateMenu agg = new AggregateMenu();
                    int code = agg.typeOfBudget(input);
                    agg.displayMinMax(code, input);
                }
                case COMPARISON -> {
                    try {
                        ComparisonController controller =
                        new ComparisonController();
                        controller.start();
                    } catch (Exception e) {
                        System.err.println("Σφάλμα κατά την σύγκριση στοιχείων."
                        + e.getMessage());
                    }
                }
                case GRAPHS -> {
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
        ReadBudget.readGeneralBudget("proypologismos2026.csv");
        DisplayBudget.showGeneral(g);
    }

    /**
     * Handles the functionality of the third menu option (ACTION_3)
     * depending on the role of the current user.
     *
     * @param currentRole the role of the user executing the action
     * @param input the Scanner object for reading user input
     */
    public static void handleAction3(final Role currentRole,
    final Scanner input) {
        switch (currentRole) {
            case PRIME_MINISTER, PARLIAMENT -> showComparedBudgets();
            case FINANCE_MINISTER -> editBudget(input);
            case OTHER_MINISTRY -> {
                showComparedBudgets();
                System.out.println();
                System.out.println("Θέλετε να υποβάλλετε κάποιο αίτημα "
                + "αλλαγής του προϋπολογισμού;");
                System.out.println("Τα αιτήματα σας θα σταλούν στο Υπουργείο "
                + "Οικονομικών για αξιολόγηση.");
            }
            default -> System.out.println("Σφάλμα κατά την φόρτωση "
            + "της ενέργειας");
        }
    }

    /**
     * Loads the current and modified budgets and prints a side-by-side
     * comparison of both the general budget and individual ministries.
     */
    public static void showComparedBudgets() {
        BudgetAssembler assembler = new BudgetAssembler();
        Budget currentBudget = assembler
        .loadBudget("proypologismos2026.csv",
        "proypologismos2026anaypourgeio.csv");
        Budget modifiedBudget = assembler.loadBudget("newgeneral.csv",
        "newministries.csv");
        // === Εκτύπωση σύγκρισης Γενικού Προϋπολογισμού ===
        BudgetDiffPrinter.compareGeneralSideBySide(currentBudget,
        modifiedBudget);
        // === Εκτύπωση σύγκρισης Υπουργείων ===
        BudgetDiffPrinter.compareMinistriesSideBySide(currentBudget,
        modifiedBudget);
    }

    /**
     * Allows the user to select a budget file to edit and modify either
     * income or expense entries. The user can return to the previous menu
     * by selecting 0.
     *
     * @param input the scanner for user's input
     */
    public static void editBudget(final Scanner input) {
        int choice;
        do {
            System.out.println("\n\n0. Έξοδος");
            System.out.println("1. Επεξεργασία Προϋπολογισμού");
            System.out.println("2. Ιστορικό Τροποποιήσεων Προϋπολογισμού");
            System.out.println("3. Σύγκριση Τρέχοντος και "
            + "Τροποποιημένου Προϋπολογισμού");
            System.out.println("4. Προβολή Αιτημάτων από άλλα Υπουργεία");
            System.out.print("\nΕπιλογή: ");
            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
                continue;
            }

            if (choice == 0) {
                break;
            } else if (choice < 1 || choice > CODE_FOR_MENUS) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Πρέπει να επιλέξετε από το 1 έως το "
                + CODE_FOR_MENUS + ".");
                continue;
            }

            Budget initialBudget;
            switch (choice) {
                case 1 -> {
                    BudgetAssembler loader = new BudgetAssembler();
                    initialBudget = loader.loadBudget("newgeneral.csv",
                    "newministries.csv");
                    ShowEditMenuOptions edit = new ShowEditMenuOptions();
                    RevenueOrExpense usersChoice = edit
                    .chooseRevenueOrExpense(input);
                    edit.editRevenueOrExpense(initialBudget, input,
                    usersChoice);
                }
                case 2 -> {
                    // μέθοδος Μυρτούς :)
                }
                case CODE_FOR_OPTION_3 -> showComparedBudgets();
                case CODE_FOR_MENUS -> {
                    System.out.print("\nΑιτήματα Άλλων Υπουργείων:");
                    MinistryRequestService reqService =
                        new MinistryRequestService();
                    List<MinistryRequest> pendingReqs =
                        reqService.getPendingByType(RequestType.BOTH);
                    MinistryRequestPrinter.printRequests(pendingReqs);
                    int code = RequestsController.chooseRequest(input);
                    if (code == 0) {
                        break;
                    }
                    int complOrRej = RequestsController.completeOrReject(input,
                        code);
                    if (complOrRej == 1) {
                        reqService.markCompleted(code);
                    } else if (complOrRej == REJECTED) {
                        reqService.markRejected(code);
                    }
                }
                default -> System.out.println("Μη έγκυρη επιλογή.");
            }
        } while (true);
    }

    /** Shows summarized data. */
    public static void summary() {
        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2026anaypourgeio.csv");
        DisplayBudget.showMinistry(y);
    }
}
