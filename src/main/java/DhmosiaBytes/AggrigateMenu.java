package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AggrigateMenu {

    /** Code to edit a new file */
    private static final int CODE_FOR_MENUS = 2;

    public static int typeOfBudget(final Scanner input) {
        int choice = 0;
        do {
            System.out.println("1. Γενικός προϋπολογισμός"); // -> εμφανίζει έσοδα/έξοδα
            System.out.println("2. Προϋπολογισμός υπουργείων"); // -> 
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
        return choice;
    }
}
