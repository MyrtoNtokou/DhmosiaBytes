package dhmosiabytes;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class TestInputReader {

    @Test
    void testEnterValidUsername_FirstTryCorrect() {
        String mockInput = "CorrectUser\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        String result = InputReader.enterValidUsername(scanner);

        assertEquals("CorrectUser", result);
    }

    @Test
    void testEnterValidUsername_RetryUntilCorrect() {
        String mockInput = "1WrongUser\nValid_User\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        String result = InputReader.enterValidUsername(scanner);

        assertEquals("Valid_User", result);
    }

    @Test
    void testEnterValidPassword_FirstTryCorrect() {
        String mockInput = "Abc123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        String result = InputReader.enterValidPassword(scanner);

        assertEquals("Abc123", result);
    }

    @Test
    void testEnterValidPassword_RetryUntilCorrect() {
        String mockInput = "abc\nAAAAA\nAbc123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

        String result = InputReader.enterValidPassword(scanner);

        assertEquals("Abc123", result);
    }

    @Test
        void testEnterValidUsername_ZeroForReturn() {
            String mockInput = "0\n";
            Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));

            String result = InputReader.enterValidUsername(scanner);

            assertNull(result);
        }

    @Test
    void testEnterValidUsername_MinLength() {
        String mockInput = "Abcde\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));
        assertEquals("Abcde", InputReader.enterValidUsername(scanner));
    }

    @Test
    void testEnterValidUsername_MaxLength() {
        String mockInput = "Abcdefghijklmn\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));
        assertEquals("Abcdefghijklmn", InputReader.enterValidUsername(scanner));
    }

    @Test
    void testEnterValidPassword_MinLength() {
        String mockInput = "A1bcd\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));
        assertEquals("A1bcd", InputReader.enterValidPassword(scanner));
    }

    @Test
    void testEnterValidPassword_WithSpecialChars() {
        String mockInput = "Ab1!@#\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(mockInput.getBytes()));
        assertEquals("Ab1!@#", InputReader.enterValidPassword(scanner));
    }
}
