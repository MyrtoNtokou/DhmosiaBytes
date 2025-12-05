package dhmosiabytes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Singleton final class that stores and manages all registered users.
 * Maintains a map of users and allows adding, searching,
 * saving, and loading users.
 */
@SuppressFBWarnings(
    value = "SING_SINGLETON_IMPLEMENTS_SERIALIZABLE",
    justification = "Singleton is intentionally Serializable"
    + " with readResolve()"
)
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
     * Returns the instance of the UserDatabase.
     *
     * @return the singleton instance
     */
    @SuppressFBWarnings(value = "MS_EXPOSE_REP",
    justification = "Singleton instance intentionally exposed")
    public static UserDatabase getDB() {
        return DB;
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
     * Saves all users to "users.json" in JSON format.
     */
    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (OutputStreamWriter writer = new OutputStreamWriter(new
        FileOutputStream("users.json"), StandardCharsets.UTF_8)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την αποθήκευση.");
        }
    }

    /**
     * Loads all users from "users.json" if the file exists,
     * so they are available in the next program execution.
     */
    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File("users.json");
        if (!file.exists()) {
            users = new HashMap<>();
            return;
        }

        Gson gson = new Gson();
        try (InputStreamReader reader = new
        InputStreamReader(new FileInputStream(file),
        StandardCharsets.UTF_8)) {
            Type type = new TypeToken<Map<String, User>>() { }.getType();
            users = gson.fromJson(reader, type);

            if (users == null) {
                users = new HashMap<>();
            }

        } catch (IOException e) {
            System.out.println("Σφάλμα κατά την φόρτωση.");
            users = new HashMap<>();
        }
    }

    /**
     * Ensures that after deserialization, the Singleton property is preserved.
     *
     * @return the singleton instance of UserDatabase
     */
    private Object readResolve() {
        return DB;
    }

    /**
     * Cleans users.db only for testing.
     */
    public void clearUsersForTest() {
        users.clear();
        save();
    }
}
