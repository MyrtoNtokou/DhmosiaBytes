package budgetcomparison;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class ComparisonControllerTest {

    /** Tests that the program exits immediately when user selects option 0. */
    @Test
    public void testImmediateExit() {
        // Arrange
        String input = "0\n"; // επιλογή εξόδου
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ComparisonController controller = new ComparisonController();

        // Act & Assert
        assertDoesNotThrow(() -> controller.start());
    }

    /** Tests that the program handles invalid menu input gracefully. */
    @Test
    public void testInvalidMenuInput() {
        // Arrange
        String input = "invalid\n3\n0\n"; // μη έγκυρη είσοδος, έγκυρη έξοδος
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ComparisonController controller = new ComparisonController();

        // Act & Assert
        assertDoesNotThrow(() -> controller.start());
    }

    /** Tests a full comparison flow with valid inputs. */
    @Test
    public void testOneComparisonFlow() {
        String input = """
            1
            2022
            2023
            001
            0
            """;
        

        System.setIn(new ByteArrayInputStream(
            input.getBytes(StandardCharsets.UTF_8)));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        ComparisonController controller = new ComparisonController();

        assertDoesNotThrow(() -> controller.start());
    }
}
