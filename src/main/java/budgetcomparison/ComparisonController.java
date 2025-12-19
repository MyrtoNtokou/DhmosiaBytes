package budgetcomparison;

import java.util.Scanner;

public class ComparisonController {
    
    private final Scanner sc;
    private final ComparisonService service;

    public ComparisonController(Scanner sc) {
        this.sc = sc;
        this.service = new ComparisonService();
    }

    public void start() {

        System.out.println("Τι θέλεις να συγκρίνεις;");
        System.out.println("1. Εγγραφές");
        System.out.println("2. Υπουργεία");

        int type = sc.nextInt();
        sc.nextLine();

        System.out.println("Δώσε 1ο έτος:");
        int year1 = sc.nextInt();

        System.out.println("Δώσε 2ο έτος:");
        int year2 = sc.nextInt();
        sc.nextLine();

        System.out.println("Δώσε κωδικό σύγκρισης:");
        String kodikos = sc.nextLine();

        if (type == 1) {
            service.compareGeneralBudgetByYear(year1, year2, kodikos);
        } else if (type == 2) {
            service.compareMinistryBudgetByYear(year1, year2, kodikos);
        }
    }

}
