package dhmosiabytes;

import java.io.Serializable;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Class representing a user of the application.
 * Each user has a Role, a username, and a password.
 */
public class User implements Serializable {
    /** The role of the user. */
    private final Role role;

    /** The username of the user. */
    private final String username;

    /** Stores the BCrypt hash of the user's password. */
    private final String passwordHash;

    /** Cost factor for BCrypt. */
    private static final int BCRYPT_COST = 12;

    /**
     * Creates a user with a specific role, username, and password.
     *
     * @param currentRole the role of the user
     * @param currentUsername the username of the user
     * @param passwordPlain the password of the user
     */
    public User(final Role currentRole, final String currentUsername,
    final String passwordPlain) {
        this.role = currentRole;
        this.username = currentUsername;
        this.passwordHash = hashPassword(passwordPlain);
    }

    /**
     * Hashes a plain-text password using the BCrypt hashing algorithm.
     *
     * @param passwordPlain the plain-text password provided by the user
     * @return the hashed password string to be safely stored
     */
    private String hashPassword(final String passwordPlain) {
        return BCrypt.hashpw(passwordPlain, BCrypt.gensalt(BCRYPT_COST));
    }

    /**
     * Returns the role of the user.
     *
     * @return the user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Returns the username of the user.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

   /**
     * Returns a string representation of the user, including username
     * and role.
     *
     * @return the username and role of the user
     */
    @Override
    public String toString() {
        return username + " (" + role + ")";
    }

    /**
     * Verifies a plain-text password against the stored BCrypt hash.
     *
     * @param passwordPlain the password entered during login
     * @return true if the password matches the stored hash, false otherwise
     */
    public boolean checkPassword(final String passwordPlain) {
        return BCrypt.checkpw(passwordPlain, passwordHash);
    }
}
