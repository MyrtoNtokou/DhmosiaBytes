import java.util.InputMismatchException;

/**
 * Class that validates usernames and passwords according to
 * predefined rules.
 */
public class Entry {
    /** Minimum length for username and password. */
    public static final int MIN_USERNAME_AND_PASSWORD = 5;
    
    /** Maximum length for username. */
    public static final int MAX_USERNAME = 14;

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
            throw new InputMismatchException("Το username δεν μπορεί " 
            + "να είναι αριθμός");
        }

        if (username.length() >= MIN_USERNAME_AND_PASSWORD 
        && username.length() <= MAX_USERNAME 
        && Character.isLetter(username.charAt(0)) 
        && username.matches("[A-Za-z0-9_]+")) {
            return username;
        } else {
            throw new InputMismatchException("Το username δεν ικανοποιεί " 
            + "τις προϋποθεσεις.");
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
    throws NotCorrectPassword{
        if (password.length() >= MIN_USERNAME_AND_PASSWORD 
        && password.matches(".*[A-Z].*") 
        && password.matches(".*[a-z].*") 
        && password.matches(".*[0-9].*")) {
            return password;
        } else {
            throw new NotCorrectPassword("Το password δεν ικανοποιεί" 
            + "τις προϋποθέσεις.");
        }
    }
}
