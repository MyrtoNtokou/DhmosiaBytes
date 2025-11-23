package dhmosiabytes;

/** Exception for invalid password.
 *
 */
public class NotCorrectPassword extends Exception {

    /**
     * Creates a NotCorrectPassword object.
     *
     * @param message the explanation message that will be displayed
     */
    public NotCorrectPassword(final String message) {
        super(message);
    }
}
