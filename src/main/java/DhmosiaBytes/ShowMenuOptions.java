package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import budgetreader.DisplayBudget;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;
import budgetlogic.Budget;
import budgetlogic.BudgetAssembler;


/**
 * Class that displays the application's main menu.
 *
 */
public final class ShowMenuOptions {

    /**
     * Private constractor to prevent object creation.
     */
    private ShowMenuOptions() { }

    /** Code to edit a new file */
    private static final int CODE_FOR_MENUS = 2;

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
                case EDIT_BUDGET -> editBudget(input);
                case SUMMARY -> summary();
                case AGGRIGATE -> {
                    AggrigateMenu agg = new AggrigateMenu();
                    int code = agg.typeOfBudget(input);
                    ShowEditMenuOptions revOrExp = new ShowEditMenuOptions();
                    RevenueOrExpense preference =
                    revOrExp.chooseRevenueOrExpense(input);
                    agg.minOrMax(input);
                }
                case GRAPHS -> {
                    graph.chooseGraph(input);
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

    /** 
     * Asks for the file to be edited.
     *
     * @param imput the scanner for user's input
     */
    public static void editBudget(final Scanner input) {
        int choice = 0;
        do {
            System.out.println("1. Τρέχων προϋπολογισμός");
            System.out.println("2. Τροποποιημένο αρχείο");
            System.out.print("\nΕπιλέξτε το αρχείο που θα επεξεργαστείτε: ");
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.next();
                continue;
            }

            if (choice != 1 && choice !=2) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Πρέπει να επιλέξετε 1 ή "
                + CODE_FOR_MENUS + ".");
            }
        } while (choice !=1 && choice != CODE_FOR_MENUS);

        if (choice == 1) {
            BudgetAssembler loader = new BudgetAssembler();
            Budget initialBudget = loader.loadBudget("general.csv",
            "ministries.csv");
        } else {
            BudgetAssembler loader = new BudgetAssembler();
            Budget initialBudget = loader.loadBudget("newgeneral.csv",
            "newministries.csv");
        }
        ShowEditMenuOptions edit = new ShowEditMenuOptions();
        RevenueOrExpense usersChoice = edit
        .chooseRevenueOrExpense(input);
        edit.editRevenueOrExpence(initialBudget, input);
    }

    /** Shows summarized data. */
    public static void summary() {
        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");
        DisplayBudget.showMinistry(y);
    }
}
