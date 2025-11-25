package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(password, user.getPassword());
    }

    @Test
    void testToString() {
        Role role = Role.FINANCE_MINISTER;
        String username = "Test";
        String password = "Test123";
        User user = new User(role, username, password);
        String expected = "Test (Υπουργός Οικονομικών)";
        assertEquals(expected, user.toString());
    }
}