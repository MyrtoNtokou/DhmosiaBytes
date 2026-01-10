package ministryrequests;

import org.junit.jupiter.api.Test;

import static aggregatedata.ConsoleColors.*;
import static org.junit.jupiter.api.Assertions.*;

class TestDiffColorizer {

    @Test
    void colorize_positiveChange_appliesGreen() {
        String input = "(+200)";
        String expected = "(" + GREEN + "+200" + RESET + ")";
        assertEquals(expected, DiffColorizer.colorize(input));
    }

    @Test
    void colorize_negativeChange_appliesRed() {
        String input = "(-50)";
        String expected = "(" + RED + "-50" + RESET + ")";
        assertEquals(expected, DiffColorizer.colorize(input));
    }

    @Test
    void colorize_arrowValue_appliesBlue() {
        String input = "Τακτικός: 1000 → 1200";
        String expected = "Τακτικός: 1000 → " + BLUE + "1200" + RESET;
        assertEquals(expected, DiffColorizer.colorize(input));
    }

    @Test
    void colorize_lineWithCode_appliesBold() {
        String input = "2 | Λειτουργικά";
        String expected = BOLD + "2 | Λειτουργικά" + RESET;
        assertEquals(expected, DiffColorizer.colorize(input));
    }

    @Test
    void colorize_combinedChanges_appliesAllColors() {
        String input = "2 | Λειτουργικά\nΤακτικός: 1000 → 1200 (+200)\nΕπενδύσεις: 500 → 400 (-100)";
        String colored = DiffColorizer.colorize(input);

        // Check bold for first line
        assertTrue(colored.contains(BOLD + "2 | Λειτουργικά" + RESET));

        // Check blue for arrows
        assertTrue(colored.contains("→ " + BLUE + "1200" + RESET));
        assertTrue(colored.contains("→ " + BLUE + "400" + RESET));

        // Check green for positive change
        assertTrue(colored.contains("(" + GREEN + "+200" + RESET + ")"));

        // Check red for negative change
        assertTrue(colored.contains("(" + RED + "-100" + RESET + ")"));
    }

    @Test
    void colorize_noChanges_onlyBoldLines() {
        String input = "2 | Λειτουργικά\n3 | Μισθοί";
        String colored = DiffColorizer.colorize(input);

        assertTrue(colored.contains(BOLD + "2 | Λειτουργικά" + RESET));
        assertTrue(colored.contains(BOLD + "3 | Μισθοί" + RESET));
    }
}
