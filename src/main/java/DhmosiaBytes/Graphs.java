package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Displays a menu of graph options and
 * receives a validated user selection from the console.
 */
public class Graphs {
    /** The maximum valid option number. */
    private static final int MAX_CODE = 5;

    /** The minimum valid option number. */
    private static final int MIN_CODE = 1;

    /**
     * Creates a Graph object.
     */
    public Graphs() { }

    /**
     * Displays a menu of graph options until a valid
     * numeric choice within the allowed range is provided.
     *
     * @param input the Scanner for user input
     * @return the validated selected option
     */
    public int chooseGraph(final Scanner input) {
        int code = -1;
        boolean valid = false;

        while (!valid) {
            System.out.println("1. Πίτα Συνολικών Εσόδων - Εξόδων");
            System.out.println("2. Πίτα Χρηματοδότησης Εξόδων");
            System.out.println("3. Ιστόγραμμα Εσόδων");
            System.out.println("4. Ιστόγραμμα Εξόδων");
            System.out.println("5. Ιστόγραμμα Προϋπολογισμού ανά Υπουργείο");
            System.out.print("Επιλογή: ");

            try {
                code = input.nextInt();
                if (code > MAX_CODE || code < MIN_CODE) {
                    System.out.println("Παρακαλώ επιλέξτε αριθμό από το "
                    + MIN_CODE + " έως το " + MAX_CODE);
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.next();
            }
        }
        return code;
    }
}
