package dhmosiabytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Singleton final class that stores and manages all registered users.
 * Maintains a map of users and allows adding, searching,
 * saving, and loading users.
 */
public final class UserDatabase implements Serializable {

    /** The single shared instance of the database (Singleton pattern). */
    private static final UserDatabase DB = new UserDatabase();

    /** Map storing users with username as the key. */
    private Map<String, User> users;

    /**
     * Private constructor to prevent external instantiation.
     */
    private UserDatabase() {
        users = new HashMap<>();
        try {
            load();
        } catch (Exception e) {
            System.out.println("Η βάση δεδομένων είναι κενή.");
        }
    }

    /**
     * Returns a copy of the instance of the UserDatabase.
     *
     * @return the singleton instance
     */
    public static UserDatabase getDB() {
        return new UserDatabase();
    }

    /**
     * Adds a new user to the database.
     *
     * @param newUser the user to be added
     * @return true if the user was successfully added,
     *         false if the user already exists
     */
    public boolean addUser(final User newUser) {
        if (!users.containsKey(newUser.getUsername())) {
            users.put(newUser.getUsername(), newUser);
            save();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the user with the given username, if it exists.
     *
     * @param username the username to search for
     * @return the User object or null if not found
     */
    public User findUser(final String username) {
        return users.get(username);
    }

    /**
     * Displays the list of users.
     * You can't edit this list.
     *
     * @return all the users
     */
    public Map<String, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    /**
     * Saves all users to the file "users.db".
     */
    private void save() {
        try (ObjectOutputStream out =
        new ObjectOutputStream(new FileOutputStream("users.db"))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την αποθήκευση.");
        }
    }

    /**
     * Loads all users from "users.db" if the file exists,
     * so they are available in the next program execution.
     */
    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File("users.db");
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in =
        new ObjectInputStream(new FileInputStream(file))) {
            users = (Map<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Σφάλμα κατά την φόρτωση.");
        }
    }

    /**
     * Ensures that after deserialization, the Singleton property is preserved.
     *
     * @return the singleton instance of UserDatabase
     */
    private Object readResolve() {
        return getDB();
    }
}
