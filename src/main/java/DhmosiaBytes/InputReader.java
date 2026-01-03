package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.Scanner;

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
        System.out.println("Δημιουργία username");
        System.out.println("Το username πρέπει να "
            + "ξεκινάει με γράμμα και να περιλαμβάνει από "
            + Entry.MIN_USERNAME_AND_PASSWORD + " μέχρι " + Entry.MAX_USERNAME
            + " χαρακτήρες (γράμματα, ψηφία ή κάτω παύλα)");
        String currentUsername = input.nextLine();

        boolean usernameOk = false;
        while (!usernameOk) {
            try {
                currentUsername = Entry.isValidUsername(currentUsername);
                usernameOk = true;
            } catch (InputMismatchException e) {
                System.err.println("Error " + e.getMessage());
                System.out.println("Παρακαλώ δοκιμάστε άλλο username: ");
                currentUsername = input.nextLine();
            }
        }
        return currentUsername;
    }

    /**
     * Reads and validates a password entered by the user.
     *
     * @param input Scanner object for reading user input
     * @return the valid password
     * @throws NotCorrectPassword if the password does not meet the rules
     */
    public static String enterValidPassword(final Scanner input) {
        System.out.println("Δημιουργία ισχυρού password");
        System.out.println("Το password πρέπει να περιλαμβάνει "
            + "τουλάχιστον 5 χαρακτήρες. Τουλάχιστον ένας "
            + "πρέπει να είναι ΚΕΦΑΛΑΙΟ, ένας μικρό και ένας ψηφίο.");
        String currentPassword = input.nextLine();

        boolean passwordOk = false;
        while (!passwordOk) {
            try {
                currentPassword = Entry.isValidPassword(currentPassword);
                passwordOk = true;
            } catch (NotCorrectPassword e) {
                System.err.println("Error: " + e.getMessage());
                System.out.println("Παρακαλώ προσπαθήστε ξανά: ");
                currentPassword = input.nextLine();
            }
        }
        return currentPassword;
    }
}
