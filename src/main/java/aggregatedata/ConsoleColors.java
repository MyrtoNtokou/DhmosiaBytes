package aggregatedata;

/**
 * Utility class containing ANSI escape codes for terminal color formatting.
 * This class provides colors and text styles for consistent console output
 * across the application.
 */
public final class ConsoleColors {

    /** Constructor. */
    private ConsoleColors() { }

    /** ANSI reset code to clear all formatting. */
    public static final String RESET = "\u001B[0m";
    /** ANSI bold text modifier. */
    public static final String BOLD = "\u001B[1m";
    /** ANSI red colou. */
    public static final String RED = "\u001B[31m";
    /** ANSI green color. */
    public static final String GREEN = "\u001B[32m";
    /** ANSI underline text modifier. */
    public static final String UNDERLINE = "\u001B[4m";

    /** Blue color. */
    public static final String BLUE = rgb(30, 144, 255);
    /** Cyan color. */
    public static final String CYAN = rgb(0, 200, 255);

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
}
