package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import aggregatedata.BudgetAnalyzer;
import aggregatedata.BudgetStats;
import aggregatedata.BudgetStatsPrinter;
import aggregatedata.InvestmentAnalyzer;
import aggregatedata.InvestmentPrinter;
import aggregatedata.InvestmentRatio;
import aggregatedata.MinistryAnalyzer;
import aggregatedata.MinistryStats;
import aggregatedata.MinistryStatsPrinter;
import budgetreader.BasicRecord;
import budgetreader.ReadBudget;
import budgetreader.Ministry;

/**
 * Provides a menu for selecting and displaying budget statistics.
 * Supports both general budget and ministries budget.
 */
public final class AggregateMenu {

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
            System.out.println();
            System.out.println("1. Γενικός προϋπολογισμός");
            System.out.println("2. Προϋπολογισμός υπουργείων");
            System.out.println("0. Έξοδος");
            System.out.print("Επιλέξτε το αρχείο για το οποίο θέλετε να δείτε "
            + "συγκεντρωτικά στοιχεία: ");

            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
                continue;
            }

            if (choice == 0) {
                return 0;
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
     * Displays a menu that allows the user to choose between
     * viewing budget statistics or investment ratio indicators.
     *
     * @param input the Scanner used to read user input
     */
    public void budgetOrIndices(final Scanner input) {
        int choice = 0;
        do {
            System.out.println("1. Προϋπολογισμός");
            System.out.println("2. Δείκτες Λόγου Επενδύσεων");
            System.out.print("Επιλογή: ");
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

        if (choice == 1) {
            List<Ministry> y =
            ReadBudget.readByMinistry("proypologismos2026anaypourgeio.csv");
            MinistryStats stats = MinistryAnalyzer.analyze(y);

            MinistryStatsPrinter.printRegularBudget(stats);

            MinistryStatsPrinter.printEpendyseis(stats);

            MinistryStatsPrinter.printSynolo(stats);
        } else if (choice == 2) {
            List<Ministry> ypourg =
            ReadBudget.readByMinistry("proypologismos2026anaypourgeio.csv");
            List<InvestmentRatio> ratios =
            InvestmentAnalyzer.calculate(ypourg);
            InvestmentPrinter.print(ypourg, ratios);
        }
    }

    /**
     * Displays minimum and maximum values for revenues and expenses
     * based on the selected budget type.
     *
     * @param generalOrMinistries 1 for general budget, 2 for ministries budget
     * @param input the Scanner used to read user input
     */
    public void displayMinMax(final int generalOrMinistries,
    final Scanner input) {
        if (generalOrMinistries == 1) {
            List<BasicRecord> g =
            ReadBudget.readGeneralBudget("proypologismos2026.csv");
            BudgetStats stats = BudgetAnalyzer.analyze(g);

            BudgetStatsPrinter.printRevenues(stats);

            BudgetStatsPrinter.printExpenses(stats);
        } else {
            budgetOrIndices(input);
        }
    }
}
