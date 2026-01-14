package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.Scanner;

import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.UNDERLINE;

/**
 * Helper class for reading and validating user inputs,
 * such as username and password.
 */
public final class InputReader {

    /**
     * Private constractor so no objects will be created.
     */
    private InputReader() { }

    /**
     * Reads and validates a username entered by the user.
     *
     * @param input Scanner object for reading user input
     * @return the valid username
     * @throws InputMismatchException if the username does not meet the rules
     */
    public static String enterValidUsername(final Scanner input) {
        System.out.println(BOLD + UNDERLINE + "\nΔημιουργία username" + RESET);
        System.out.println("Επιλέξτε 0 για επιστροφή στο προηγούμενο μενού.");
        System.out.println("Το username πρέπει να "
            + "ξεκινάει με γράμμα και να περιλαμβάνει από "
            + Entry.MIN_USERNAME_AND_PASSWORD + " μέχρι " + Entry.MAX_USERNAME
            + " χαρακτήρες (γράμματα, ψηφία ή κάτω παύλα)");

        while (true) {
            String currentUsername = input.nextLine();

            if ("0".equals(currentUsername)) {
                return null;
            }

            try {
                currentUsername = Entry.isValidUsername(currentUsername);
                return currentUsername;
            } catch (InputMismatchException e) {
                System.err.println(RED + "Σφάλμα: " + e.getMessage() + RESET);
                System.out.println("Παρακαλώ δοκιμάστε άλλο username: ");
            }
        }
    }

    /**
     * Reads and validates a password entered by the user.
     *
     * @param input Scanner object for reading user input
     * @return the valid password
     */
    public static String enterValidPassword(final Scanner input) {
        System.out.println(BOLD + UNDERLINE + "\nΔημιουργία ισχυρού password"
                + RESET);
        System.out.println("Επιλέξτε 0 για επιστροφή στο προηγούμενο μενού.");
        System.out.println("Το password πρέπει να περιλαμβάνει "
            + "τουλάχιστον 5 χαρακτήρες. Τουλάχιστον ένας "
            + "πρέπει να είναι ΚΕΦΑΛΑΙΟ, ένας μικρό και ένας ψηφίο.");

        while (true) {
            String currentPassword = input.nextLine();

            if ("0".equals(currentPassword)) {
                return null;
            }
            try {
                currentPassword = Entry.isValidPassword(currentPassword);
                return currentPassword;
            } catch (NotCorrectPassword e) {
                System.err.println(RED + "Σφάλμα: " + e.getMessage() + RESET);
                System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
            }
        }
    }
}
