package dhmosiabytes;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestUserDatabase {

    static class TestUser extends User {
        public TestUser(String username) {
            super(Role.PRIME_MINISTER, username, "password");
        }
    }

    @AfterEach
    @SuppressWarnings("unused")
    void cleanup() {
        UserDatabase db = UserDatabase.getDB();
        String[] testUsers = {"john", "alice", "mary"};
        for (String username : testUsers) {
            db.removeUser(username);
        }
    }

    @Test
    void testSingletonInstance() {
        UserDatabase db1 = UserDatabase.getDB();
        UserDatabase db2 = UserDatabase.getDB();

        assertSame(db1, db2, "getDB() πρέπει να επιστρέφει"
        + " το ίδιο singleton instance");
    }

    @Test
    void testAddUserSuccess() {
        UserDatabase db = UserDatabase.getDB();
        boolean added = db.addUser(new TestUser("john"));
        assertTrue(added);
    }

    @Test
    void testAddUserDuplicate() {
        UserDatabase db = UserDatabase.getDB();
        db.addUser(new TestUser("john"));
        boolean added = db.addUser(new TestUser("john"));
        assertFalse(added);
    }

    @Test
    void testFindUser() {
        UserDatabase db = UserDatabase.getDB();
        db.addUser(new TestUser("john"));
        User u = db.findUser("john");
        assertNotNull(u);
        assertEquals("john", u.getUsername());
    }

    @Test
    void testFindUserNotFound() {
        UserDatabase db = UserDatabase.getDB();
        User u = db.findUser("ghost");
        assertNull(u);
    }

    @Test
    void testGetUsersUnmodifiable() {
        UserDatabase db = UserDatabase.getDB();
        db.addUser(new TestUser("john"));
        Map<String, User> users = db.getUsers();
        Exception exception = assertThrows(UnsupportedOperationException.class,
        () -> users.put("mary", new TestUser("mary")));
        assertNotNull(exception);
    }

    @Test
    void testSaveAndLoadPersistence() {
        UserDatabase db = UserDatabase.getDB();
        db.addUser(new TestUser("alice"));

        File usersFile = new File("users.json");
        assertTrue(usersFile.exists(), "Το αρχείο users.json πρέπει να υπάρχει μετά την αποθήκευση");

        User loaded = db.findUser("alice");
        assertNotNull(loaded);
        assertEquals("alice", loaded.getUsername());
    }

    @Test
    void testAddNullUser() {
        UserDatabase db = UserDatabase.getDB();
        boolean added = db.addUser(null);
        assertFalse(added, "Δεν πρέπει να προστεθεί null user");
    }

    @Test
    void testFindUserWithNullUsername() {
        UserDatabase db = UserDatabase.getDB();
        User u = db.findUser(null);
        assertNull(u, "findUser(null) πρέπει να επιστρέφει null");
    }
}
