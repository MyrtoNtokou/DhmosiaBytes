package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.InputMismatchException;
import org.junit.jupiter.api.Test;

class TestEntry {

    // Username validation tests

    @Test
    void testValidUsername() {
        String username = "User_123";
        assertEquals(username, Entry.isValidUsername(username));
    }

    @Test
    void testUsernameStartsWithNumber() {
        assertThrows(InputMismatchException.class, () -> {
            Entry.isValidUsername("1abcde");
        });
    }

    @Test
    void testUsernameTooShort() {
        assertThrows(InputMismatchException.class, () -> {
            Entry.isValidUsername("abc");
        });
    }

    @Test
    void testUsernameTooLong() {
        assertThrows(InputMismatchException.class, () -> {
            Entry.isValidUsername("abcdefghijklmnop");
        });
    }

    @Test
    void testUsernameContainsInvalidCharacters() {
        assertThrows(InputMismatchException.class, () -> {
            Entry.isValidUsername("abc@123");
        });
    }

    @Test
    void testUsernameIsOnlyNumbers() {
        assertThrows(InputMismatchException.class, () -> {
            Entry.isValidUsername("123456");
        });
    }

    // Password validation tests

    @Test
    void testValidPassword() throws NotCorrectPassword {
        String password = "Abc123";
        assertEquals(password, Entry.isValidPassword(password));
    }

    @Test
    void testPasswordMissingUppercase() {
        assertThrows(NotCorrectPassword.class, () -> {
            Entry.isValidPassword("abc123");
        });
    }

    @Test
    void testPasswordMissingLowercase() {
        assertThrows(NotCorrectPassword.class, () -> {
            Entry.isValidPassword("ABC123");
        });
    }

    @Test
    void testPasswordMissingNumber() {
        assertThrows(NotCorrectPassword.class, () -> {
            Entry.isValidPassword("Abcdef");
        });
    }

    @Test
    void testPasswordTooShort() {
        assertThrows(NotCorrectPassword.class, () -> {
            Entry.isValidPassword("A1b");
        });
    }
}
