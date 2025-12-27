package dhmosiabytes;

import java.io.Serializable;

/**
 * Class representing a user of the application.
 * Each user has a Role, a username, and a password.
 */
public class User implements Serializable {
    /** The role of the user. */
    private final Role role;

    /** The username of the user. */
    private final String username;

    /** The password of the user. */
    private final String password;

    /**
     * Creates a user with a specific role, username, and password.
     *
     * @param currentRole the role of the user
     * @param currentUsername the username of the user
     * @param currentPassword the password of the user
     */
    public User(final Role currentRole, final String currentUsername,
    final String currentPassword) {
        this.role = currentRole;
        this.username = currentUsername;
        this.password = currentPassword;
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
     * Returns the password of the user.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
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
}
