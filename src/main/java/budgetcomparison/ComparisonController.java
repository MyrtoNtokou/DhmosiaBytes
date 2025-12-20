package budgetcomparison;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Controller class
 * for handling user interactions for budget comparisons. */
public class ComparisonController {
    /** Scanner for user input. */
    private final Scanner sc;

    /** Service for performing budget comparisons. */
    private final ComparisonService service;

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

        // Display options to the user
        System.out.println("Τι θέλεις να συγκρίνεις;");
        System.out.println("1. Εγγραφές");
        System.out.println("2. Υπουργεία");

        // Read user choice
        int type = sc.nextInt();
        sc.nextLine();

        System.out.println("Δώσε 1ο έτος:");
        int year1 = sc.nextInt();

        System.out.println("Δώσε 2ο έτος:");
        int year2 = sc.nextInt();
        sc.nextLine();

        System.out.println("Δώσε κωδικό σύγκρισης:");
        String kodikos = sc.nextLine();

        // Perform comparison based on user choice
        if (type == 1) {
            service.compareGeneralBudgetByYear(year1, year2, kodikos);
        } else if (type == 2) {
            service.compareMinistryBudgetByYear(year1, year2, kodikos);
        }
    }

}
