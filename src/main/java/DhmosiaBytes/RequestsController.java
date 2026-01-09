package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ministryrequests.MinistryRequest;

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

    /** Code for option 3 for Prime Minister */
    private static final int MAX_CHOICE_PRIME_MINISTER = 3;

    /** Constructor. */
    private RequestsController() { }

    /**
     * Asks user to enter the code of the request to be completed or rejected.
     *
     * @param input the Scanner for user input
     * @return the user's choice
     */
    public static int chooseRequest(final Scanner input,
            final List<MinistryRequest> pendingReqs) {

        if (pendingReqs.isEmpty()) {
            return 0;
        }

        int choice;
        while (true) {
            if (!pendingReqs.isEmpty()) {
                System.out.println("Έχετε την δυνατότητα να σημειώσετε τα "
                + "αιτήματα που έχετε ολοκληρώσει ή απορρίψει.");
                System.out.println("Επιλέξτε 0 για επιστροφή στο "
                + "προηγούμενο μενού.");
                System.out.print("Επιλέξτε το " + BOLD + CYAN
                + "ID Αιτήματος " + RESET
                + "για θετική ή αρνητική αξιολόγηση: ");

                try {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 0) {
                        return choice;
                    }
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    input.nextLine();
                }
            }
        }
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
            System.out.println("1. Το αίτημα αξιολογήθηκε θετικά");
            System.out.println("2. Το αίτημα απορρίθφηκε");
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

    public static int primeMinisterAndParlMenu(final Scanner input) {
        int choice;
        while (true) {
            System.out.println("\n1. Ιστορικό Αλλαγών");
            System.out.println("2. Σύγκριση Δημοσιευμένου και "
                    + "Τροποποιημένου");
            System.out.println("3. Προβολή Τροποποιήσεων");
            System.out.println("0. Εξοδος");
            System.out.print("Επιλογή: ");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 0) {
                    break;
                }
                if (choice < 1 && choice > MAX_CHOICE_PRIME_MINISTER) {
                    System.out.println("Μη έγκυρος κωδικός.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
            }
        }
        return choice;
    }

    public static int chooseEdit(final Scanner input,
            final List<MinistryRequest> financeMinistry) {

        if (financeMinistry.isEmpty()) {
            return 0;
        }

        int choice;
        while (true) {
            if (!financeMinistry.isEmpty()) {
                System.out.println("Αξιολόγηση Τροποποιήσεων "
                        + "από Υπουργείο Οικονομικών");
                System.out.println("Επιλέξτε 0 για επιστροφή στο "
                + "προηγούμενο μενού.");
                System.out.print("Επιλέξτε το " + BOLD + CYAN
                + "ID " + RESET + "για έγκριση ή απόρριψη ");

                try {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 0) {
                        return choice;
                    }
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    input.nextLine();
                }
            }
        }
    }

    public static int completeOrRejectPrimMinist(final Scanner input,
            final int code) {
        boolean valid = false;
        int choice = -1;
        do {
            System.out.println();
            System.out.println("1. Έγκριση");
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

    public static int chooseEditParl(final Scanner input,
            final List<MinistryRequest> govApproved) {

        if (govApproved.isEmpty()) {
            return 0;
        }

        int choice;
        while (true) {
            if (!govApproved.isEmpty()) {
                System.out.println(" Τελική Αξιολόγηση Τροποποιήσεων");
                System.out.println("Επιλέξτε 0 για επιστροφή στο "
                + "προηγούμενο μενού.");
                System.out.print("Επιλέξτε το " + BOLD + CYAN
                + "ID " + RESET + "για έγκριση ή απόρριψη ");

                try {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 0) {
                        return choice;
                    }
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    input.nextLine();
                }
            }
        }
    }
}
