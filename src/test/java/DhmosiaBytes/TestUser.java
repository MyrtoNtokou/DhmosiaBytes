package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TestUser {

    @Test
    void testUserCreationAndGetters() {
        Role role = Role.PRIME_MINISTER;
        String username = "User123";
        String password = "Abc123";
        User user = new User(role, username, password);
        // Check getters
        assertEquals(role, user.getRole());
        assertEquals(username, user.getUsername());

        assertTrue(user.checkPassword(password), "Ο κωδικός πρέπει να ταιριάζει με το hash");
        assertFalse(user.checkPassword("ΛάθοςΚωδικός"), "Λάθος κωδικός πρέπει να απορρίπτεται");
    }

    @Test
    void testToString() {
        Role role = Role.FINANCE_MINISTER;
        String username = "Test";
        String password = "Test123";
        User user = new User(role, username, password);
        String expected = username + " (" + role + ")";
        assertEquals(expected, user.toString());
    }
}