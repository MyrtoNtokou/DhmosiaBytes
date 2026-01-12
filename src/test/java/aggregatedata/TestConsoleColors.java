package aggregatedata;

import org.fusesource.jansi.AnsiConsole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestConsoleColors {

    @BeforeAll
    static void setup() {
        // Εγκατάσταση της Jansi για το περιβάλλον του test
        AnsiConsole.systemInstall();
    }

    @AfterAll
    static void tearDown() {
        AnsiConsole.systemUninstall();
    }

    @Test
    void testConstantsAreNotEmpty() {
        // Έλεγχος ότι οι σταθερές έχουν τιμή
        assertNotNull(ConsoleColors.RED);
        assertNotNull(ConsoleColors.BLUE);
        assertNotNull(ConsoleColors.RESET);
    }

    @Test
    void testAnsiFormatting() {
        // Έλεγχος αν περιέχουν τον χαρακτήρα ESC ( \u001B )
        // Όλα τα ANSI codes ξεκινούν με αυτόν τον χαρακτήρα
        assertTrue(ConsoleColors.RED.contains("\u001B"));
        assertTrue(ConsoleColors.GREEN.contains("\u001B"));
        assertTrue(ConsoleColors.RESET.contains("\u001B"));
    }

    @Test
    void testManualVisualCheck() {
        // Εκτύπωση στην κονσόλα για οπτική επιβεβαίωση κατά το build
        System.out.println(ConsoleColors.RED + "Testing Red Color" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Testing Blue (RGB) Color" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "Testing Cyan (RGB) Color" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD + ConsoleColors.GREEN + "Testing Bold Green" + ConsoleColors.RESET);
    }
}