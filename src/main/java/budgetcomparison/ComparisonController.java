package budgetcomparison;

import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;

import dhmosiabytes.ShowMenuOptions;

/** Controller class
 * for handling user interactions for budget comparisons. */
public class ComparisonController {
    /** Scanner for user input. */
    private final Scanner sc;

    /** Service for performing budget comparisons. */
    private final ComparisonService service;

    /** Maximum code allowed. */
    private static final int MAX_CODE = 2;

    /** Minimum year allowed to be choosen for comparison. */
    private static final int MIN_YEAR = 2020;

    /** Maximum year allowed to be choosen for comparison. */
    private static final int MAX_YEAR = 2026;

    /**
     * Constructs a ComparisonController.
     *
     */
    public ComparisonController() {
        this.sc = new Scanner(System.in, StandardCharsets.UTF_8);
        this.service = new ComparisonService();
    }

    /**
     * Starts the budget comparison process by interacting with the user.
     */
    public void start() {

        while (true) {
            System.out.println("\nΕπιλέξτε τι θέλετε να συγκρίνετε:");
            System.out.println("1. Εγγραφές (έσοδα/έξοδα)");
            System.out.println("2. Υπουργεία");
            System.out.println("0. Έξοδος");

            int type;
            while (true) {
                System.out.print("\nΕπιλογή: ");
                try {
                    type = sc.nextInt();
                    sc.nextLine();
                    if (type >= 0 && type <= MAX_CODE) {
                        break;
                    } else {
                        System.out.println("Επιλέξτε 0 έως " + MAX_CODE);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    sc.nextLine();
                }
            }

            if (type == 0) {
                break;
            }

            System.out.println("\nΕπιλέξτε έτη από το " + MIN_YEAR
            + " έως το " + MAX_YEAR + ".");
            int year1;
            while (true) {
                try {
                    System.out.print("\nΕπιλογή πρώτου έτους: ");
                    year1 = sc.nextInt();
                    sc.nextLine();
                    if (year1 >= MIN_YEAR && year1 <= MAX_YEAR) {
                        break;
                    } else {
                         System.out.println(RED + "Λάθος επιλογή έτους."
                                                + RESET);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    sc.nextLine();
                }
            }

            int year2;
            while (true) {
                try {
                    System.out.print("\nΕπιλογή δεύτερου έτους: ");
                    year2 = sc.nextInt();
                    sc.nextLine();
                    if (year2 >= MIN_YEAR && year2 <= MAX_YEAR) {
                        break;
                    } else {
                        System.out.println(RED + "Λάθος επιλογή έτους."
                                                + RESET);
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    sc.nextLine();
                }
            }

            String kodikos;
            while (true) {
                System.out.print("""

                Επιλέξτε τον κωδικό του στοιχείου που θέλετε να συγκρίνετε.
                """);
                if (type == 1) {
                    ShowMenuOptions.showBudget();
                } else if (type == 2) {
                    ShowMenuOptions.summary();
                }
                System.out.print("\nΕπιλογή κωδικού: ");
                kodikos = sc.nextLine();

                if (type == 1) {
                    service.compareGeneralBudgetByYear(year1, year2, kodikos);
                    break;
                } else {
                    service.compareMinistryBudgetByYear(year1, year2, kodikos);
                    break;
                }
            }
        }
    }
}
