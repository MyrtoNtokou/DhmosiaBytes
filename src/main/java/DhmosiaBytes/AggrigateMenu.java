import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import aggregatedata.BudgetStats;
import aggregatedata.BudgetAnalyzer;

public class AggrigateMenu {

    /** Code to edit a new file */
    private static final int CODE_FOR_MENUS = 2;

    public static int typeOfBudget(final Scanner input) {
        int choice = 0;
        do {
            System.out.println("1. Γενικός προϋπολογισμός");
            System.out.println("2. Προϋπολογισμός υπουργείων");
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
    }
    
    public void minOrMax(final RevenueOrExpense preference,
    final Scanner scanner) {
        int choice = 0;
        do {
            System.out.println("1. Μέγιστες πηγές");
            System.out.println("2. Ελάχιστες πηγές");
            System.out.print("\nΕπιλέξτε το αρχείο που θα επεξεργαστείτε: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                scanner.next();
                continue;
            }

            if (choice != 1 && choice !=2) {
                System.out.println("Μη έγκυρη επιλογή.");
                System.out.println("Πρέπει να επιλέξετε 1 ή "
                + CODE_FOR_MENUS + ".");
            }
        } while (choice !=1 && choice != CODE_FOR_MENUS);

        if (preference == INCOME) {
            List<Eggrafi> lista = ReadBudget
            .readGeneralBudget("proypologismos2025.csv");
            BudgetStats stats = BudgetAnalyzer.analyze(lista);
            if (choice == 1) {
                System.out.println("=== Μέγιστες Πηγές Εσόδων ===");
                stats.getMaxRevenues().forEach(e ->
                    System.out.println(e.getKodikos()
                    +" | "
                    + e.getPerigrafi()
                    + " | "
                    + e.getPoso()));
            } else {
                System.out.println("=== Ελάχιστες Πηγές Εσόδων ===");
                stats.getMinRevenues().forEach(e ->
                System.out.println(e.getKodikos()
                + " | "
                + e.getPerigrafi()
                + " | "
                + e.getPoso()));
            }
        } else {
            List<Ypourgeio> list = ReadBudget
            .readByMinistry("proypologismos2025anaypourgeio.csv");
            MinistryStats stats = MinistryAnalyzer.analyze(list);
            if (choice == 1) {
                System.out.println("=== Μέγιστες Πηγές Εξόδων ===");
                stats.getMaxExpenses().forEach(e ->
                System.out.println(e.getKodikos()
                + " | "
                + e.getPerigrafi()
                + " | "
                + e.getPoso()));
            } else {
                System.out.println("=== Ελάχιστες Πηγές Εσόδων ===");
                stats.getMinExpenses().forEach(e ->
                System.out.println(e.getKodikos()
                + " | "
                + e.getPerigrafi()
                + " | "
                + e.getPoso()));
            }
        }
    }
}