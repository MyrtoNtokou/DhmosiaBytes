package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import aggregatedata.BudgetAnalyzer;
import aggregatedata.BudgetStats;
import aggregatedata.BudgetStatsPrinter;
import aggregatedata.MinistryAnalyzer;
import aggregatedata.MinistryStats;
import aggregatedata.MinistryStatsPrinter;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;

/**
 * Provides a menu for selecting and displaying budget statistics.
 * Supports both general budget and ministries budget.
 */
public final class AggrigateMenu {

    /** Code to edit a new file. */
    private static final int CODE_FOR_MENUS = 2;

    /**
     * Prompts the user to select a budget type and returns the chosen option.
     *
     * @param input the Scanner to read user input
     * @return 1 for general budget, 2 for ministries budget
     */
    public int typeOfBudget(final Scanner input) {
        int choice = 0;
        do {
            System.out.println("1. Γενικός προϋπολογισμός");
            System.out.println("2. Προϋπολογισμός υπουργείων");
            System.out.print("""
            Επιλέξτε το αρχείο για το οποίο θέλετε να δείτε
             συγκεντρωτικά στοιχεία: """);
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.next();
                continue;
            }

            if (choice != 1 && choice != 2) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Πρέπει να επιλέξετε 1 ή "
                + CODE_FOR_MENUS + ".");
            }
        } while (choice != 1 && choice != CODE_FOR_MENUS);
        return choice;
    }

    /**
     * Displays minimum and maximum values for revenues and expenses
     * based on the selected budget type.
     *
     * @param generalOrMinistries 1 for general budget, 2 for ministries budget
     */
    public void displayMinMax(final int generalOrMinistries) {
        if (generalOrMinistries == 1) {
            List<Eggrafi> g =
            ReadBudget.readGeneralBudget("proypologismos2025.csv");
            BudgetStats stats = BudgetAnalyzer.analyze(g);
           System.out.println("""

           ==================== ΕΣΟΔΑ ====================
           """);
            BudgetStatsPrinter.printRevenues(stats);

            System.out.println("""

            ==================== ΕΞΟΔΑ ====================
            """);
            BudgetStatsPrinter.printExpenses(stats);
        } else {
            List<Ypourgeio> y =
            ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");
            MinistryStats stats = MinistryAnalyzer.analyze(y);
            System.out.println("""

            ==================== ΤΑΚΤΙΚΟΣ ====================
            """);
            MinistryStatsPrinter.printTaktikos(stats);
            System.out.println("""

            ==================== ΕΠΕΝΔΥΣΕΙΣ ====================
            """);
            MinistryStatsPrinter.printEpendyseis(stats);
            System.out.println("""

            ==================== ΣΥΝΟΛΟ ====================
            """);
            MinistryStatsPrinter.printSynolo(stats);
        }
    }
}
