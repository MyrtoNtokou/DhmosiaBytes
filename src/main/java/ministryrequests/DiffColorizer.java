package ministryrequests;

import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.BLUE;
import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.GREEN;

/**
 * Format with ANSI the requested change.
 */
public final class DiffColorizer {

    /** Constructor. */
    private DiffColorizer() { }

    /**
     * Format with ASNI the String that contains the changes of the request.
     * @param cleanDiff
     * @return the formatted String
     */
    public static String colorize(final String cleanDiff) {
        String colored = cleanDiff;
        colored = colored.replaceAll(
            "\\(\\+([0-9.,]+)\\)",
            "(" + GREEN + "+$1" + RESET + ")");

        colored = colored.replaceAll(
            "\\(-([0-9.,]+)\\)",
            "(" + RED + "-$1" + RESET + ")");

        colored = colored.replaceAll(
                "→\\s*([0-9.,]+)",
                "→ " + BLUE + "$1" + RESET);

        colored = colored.replaceAll(
            "^(\\s*\\d+ \\| .*)$",
            BOLD + "$1" + RESET);

        return colored;
    }
}
