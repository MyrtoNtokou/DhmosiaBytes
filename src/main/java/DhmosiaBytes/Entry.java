package dhmosiabytes;

import java.util.InputMismatchException;

import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;

/**
 * Class that validates usernames and passwords according to
 * predefined rules.
 */
public final class Entry {
    /** Minimum length for username and password. */
    public static final int MIN_USERNAME_AND_PASSWORD = 5;

    /** Maximum length for username. */
    public static final int MAX_USERNAME = 14;

    /**
     * Private constractor so no objects will be created.
     */
    private Entry() { }

    /**
     * Validates that the username follows the rules.
     *
     * @param username the username entered by the user
     * @return the username if it is valid
     * @throws InputMismatchException if the username does not meet the rules
     */
    public static String isValidUsername(final String username)
    throws InputMismatchException {
        if (username.matches("\\d+")) {
            throw new InputMismatchException(RED + "Το username δεν μπορεί "
            + "να είναι αριθμός" + RESET);
        }

        if (username.length() >= MIN_USERNAME_AND_PASSWORD
        && username.length() <= MAX_USERNAME
        && Character.isLetter(username.charAt(0))
        && username.matches("[A-Za-z0-9_]+")) {
            return username;
        } else {
            throw new InputMismatchException(RED
                    + "Το username δεν ικανοποιεί τις προϋποθεσεις." + RESET);
        }
    }

    /**
     * Validates that the password follows the rules.
     *
     * @param password the password entered by the user
     * @return the password if it is valid
     * @throws NotCorrectPassword if the password does not meet the rules
     */
    public static String isValidPassword(final String password)
    throws NotCorrectPassword {
        if (password.length() >= MIN_USERNAME_AND_PASSWORD
        && password.matches(".*[A-Z].*")
        && password.matches(".*[a-z].*")
        && password.matches(".*[0-9].*")) {
            return password;
        } else {
            throw new NotCorrectPassword(RED + "Το password δεν ικανοποιεί"
            + "τις προϋποθέσεις." + RESET);
        }
    }
}
