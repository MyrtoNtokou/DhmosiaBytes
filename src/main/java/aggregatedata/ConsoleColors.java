package aggregatedata;

import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * Utility class containing ANSI escape codes for terminal color formatting.
 * <p>
 * This class leverages the {@code Jansi} library to provide cross-platform
 * support for console colors, ensuring compatibility between Unix-based
 * terminals and Windows environments.
 * </p>
 */
public final class ConsoleColors {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ConsoleColors() { }

    /** ANSI reset code to clear all text formatting and colors. */
    public static final String RESET = animate(ansi().reset());

    /** ANSI modifier to apply bold to the text. */
    public static final String BOLD = animate(ansi().bold());

    /** ANSI escape code for standard red. */
    public static final String RED = animate(ansi().fgRed());

    /** ANSI escape code for standard green. */
    public static final String GREEN = animate(ansi().fgGreen());

    /** ANSI modifier to apply an underline to the text. */
    public static final String UNDERLINE = animate(ansi()
                                    .a(Ansi.Attribute.UNDERLINE));

    /** * Blue foreground color defined via RGB components. */
    public static final String BLUE = animate(ansi().fgRgb(30, 144, 255));

    /** * Cyan foreground color defined via RGB components. */
    public static final String CYAN = animate(ansi().fgRgb(0, 200, 255));

    /**
     * Helper method to convert an {@link Ansi} object
     * to its String representation.
     *
     * @param ansi The Jansi object to be converted.
     * @return The string containing the ANSI escape sequences.
     */
    private static String animate(final Ansi ansi) {
        return ansi.toString();
    }
}
