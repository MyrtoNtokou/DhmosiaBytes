package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class RequestsController {

     /** ANSI reset code to clear all formatting. */
    private static final String RESET = "\u001B[0m";
    /** ANSI bold text modifier. */
    private static final String BOLD = "\u001B[1m";

    /**
     * Helper: Generate ANSI escape codes for RGB.
     * @param r red component
     * @param g green component
     * @param b blue component
     * @return ANSI escape code fro RGB
     */
    private static String rgb(final int r, final int g, final int b) {
        return "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
    }

    /** Cyan for ID. */
    private static final String CYAN = rgb(0, 200, 255);

    /** Code for option 2: rejected. */
    private static final int REJECT = 2;

    /** Constructor. */
    private RequestsController() { }

    /**
     * Asks user to enter the code of the request to be completed or rejected.
     *
     * @param input the Scanner for user input
     * @return the user's choice
     */
    public static int chooseRequest(final Scanner input) {
        boolean valid;
        int choice = -1;
        do {
            System.out.println("Έχετε την δυνατότητα να σημειώσετε τα "
            + "αιτήματα που έχετε ολοκληρώσει ή απορρίψει.");
            System.out.println("Επιλέξτε 0 για επιστροφή στο "
            + "προηγούμενο μενού.");
            System.out.print("Επιλέξτε το " + BOLD + CYAN
            + "ID Αιτήματος " + RESET
            + "για ολοκλήρωση ή απόρριψη: ");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 0) {
                    break;
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
                valid = false;
            }
        } while (!valid);
        return choice;
    }

    /**
     * Asks user if the selected request is going to be completed or rejected.
     *
     * @param input the Scanner for user input
     * @param code the code of the selected request
     * @return the user's choice
     */
    public static int completeOrReject(final Scanner input, final int code) {
        boolean valid = false;
        int choice = -1;
        do {
            System.out.println();
            System.out.println("Θέλετε να ολοκληρώσετε ή να απορρίψετε "
            + "το αίτημα " + code + ";");
            System.out.println("1. Ολοκλήρωση "
            + "(αφού έχει καταχωρηθεί η αλλαγή)");
            System.out.println("2. Απόρριψη");
            System.out.println("0. Εξοδος");
            System.out.print("Επιλογή: ");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 0) {
                    break;
                }
                if (choice != 1 && choice != REJECT) {
                    System.out.println("Μη έγκυρος κωδικός.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
            }
        } while (!valid);
        return choice;
    }
}
