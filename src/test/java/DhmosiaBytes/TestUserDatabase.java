package dhmosiabytes;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUserDatabase {

    @BeforeEach
    void setup() {
        File file = new File("users.db");
        if (file.exists()) file.delete();
        UserDatabase.getDB().clearUsersForTest();
    }

    static class TestUser extends User {
        public TestUser(String username) {
            super(Role.CITIZEN, username, "password");
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
        assertThrows(UnsupportedOperationException.class,
            () -> users.put("mary", new TestUser("mary")));
    }

    @Test
    void testSaveAndLoadPersistence() {
        // First session
        UserDatabase db1 = UserDatabase.getDB();
        db1.addUser(new TestUser("alice"));

        // Second session
        UserDatabase db2 = UserDatabase.getDB();
        User loaded = db2.findUser("alice");
        assertNotNull(loaded);
        assertEquals("alice", loaded.getUsername());
    }
}
