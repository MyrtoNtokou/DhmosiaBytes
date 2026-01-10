package dhmosiabytes;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class TestInputReader {

    @Test
    void testEnterValidUsername_FirstTryCorrect() {
        // Mock input as if the user typed "CorrectUser" and pressed Enter
        String mockInput = "CorrectUser\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        // Call the method we want to test
        String result = InputReader.enterValidUsername(scanner);

        // Check if the method returned the expected username
        assertEquals("CorrectUser", result);
    }

    @Test
    void testEnterValidUsername_RetryUntilCorrect() {
        // First input is invalid, second input is correct
        String mockInput = "1WrongUser\nValid_User\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        String result = InputReader.enterValidUsername(scanner);

        // The method should keep asking until it gets a valid username
        assertEquals("Valid_User", result);
    }

    @Test
    void testEnterValidPassword_FirstTryCorrect() {
        // Mock input as if the user typed "Abc123" and pressed Enter
        String mockInput = "Abc123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        // Call the method to test password input
        String result = InputReader.enterValidPassword(scanner);

        // Check if the method returned the expected password
        assertEquals("Abc123", result);
    }

    @Test
    void testEnterValidPassword_RetryUntilCorrect() {
        // First two inputs are invalid, third input is correct
        String mockInput = "abc\nAAAAA\nAbc123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        String result = InputReader.enterValidPassword(scanner);

        // The method should keep asking until it gets a valid password
        assertEquals("Abc123", result);
    }

    @Test
        void testEnterValidUsername_ZeroForReturn() {
            String mockInput = "0\n"; // Simulate user pressing 0 to go back
            Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

            String result = InputReader.enterValidUsername(scanner);

            // Expect null because 0 means return to menu
            assertNull(result);
        }
}
