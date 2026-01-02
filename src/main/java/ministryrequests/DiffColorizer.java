package ministryrequests;

/**
 * Format with ANSI the requested change.
 */
public final class DiffColorizer {

    /** Constructor. */
    private DiffColorizer() { }

    /** ANSI reset code to clear all formatting. */
    private static final String RESET = "\u001B[0m";
    /** ANSI bold text modifier. */
    private static final String BOLD = "\u001B[1m";
    /** ANSI red for negative difference. */
    public static final String RED = "\u001B[31m";
    /** ANSI green for positive difference. */
    public static final String GREEN = "\u001B[32m";

    /**
     * Helper: Generate ANSI escape codes for RGB.
     * @param r red component
     * @param g green component
     * @param b blue component
     * @return ANSI escape code fro RGB
     */
    private static String rgb(final int r, final int g, final int b) {
        return "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
    }

    /** Blue color used new values. */
    private static final String BLUE = rgb(30, 144, 255);

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
                "→ " + BLUE + "$1" + RESET
        );

        colored = colored.replaceAll(
            "^(\\s*\\d+ \\| .*)$",
            BOLD + "$1" + RESET
        );

        return colored;
    }
}
