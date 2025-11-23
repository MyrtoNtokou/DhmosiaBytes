/**
 * Class that provides user login and registration services.
 * Uses UserDatabase for storing and retrieving users.
 */
public class LoginService {
    /** The database of users for the application. */
    private final UserDatabase db;

    /**
     * Creates a LoginService object.
     */
    public LoginService() {
        this.db = UserDatabase.getDB();
    }

    /**
     * Logs a user into the application.
     *
     * @param role the user's role
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return the User object if login is successful, or null if login failed
     */
    public User login(final Role role, final String username,
    final String password) {
        User currentUser = db.findUser(username);
        if (currentUser == null) {
            return null;
        }
        if (!currentUser.getPassword().equals(password)
        || !currentUser.getRole().equals(role)) {
            System.out.println("Λανθασμένος κωδικός πρόσβασης.");
            return null;
        }
        return currentUser;
    }

    /**
     * Registers a new account for a user with the specified details.
     *
     * @param role the role of the user
     * @param username the username of the user
     * @param password the password of the user
     * @return the newly created User object,
     * or null if the username already exists
     */
    public User register(final Role role, final String username,
    final String password) {
        if (db.findUser(username) == null) {
            User newUser = new User(role, username, password);
            db.addUser(newUser);
            return newUser;
        } else {
            return null;
        }
    }
}
