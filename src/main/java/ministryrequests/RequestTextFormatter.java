package ministryrequests;

/**
 * Utility class responsible for formatting and normalizing
 * request text before persistence or comparison.
 */
public final class RequestTextFormatter {

    /**
     * Private constructor to prevent instantiation.
     */
    private RequestTextFormatter() { }

    /**
     * Cleans and formats a diff text before saving it.
     * <p>
     * The method:
     * <ul>
     *   <li>Removes ANSI color escape sequences</li>
     *   <li>Optionally removes a specific title line</li>
     *   <li>Trims leading and trailing whitespace</li>
     * </ul>
     *
     * @param rawDiff the raw diff text (possibly containing ANSI colors)
     * @param titleToRemove a title or header line to remove from the text;
     *                      may be {@code null} or empty
     * @return the cleaned and formatted text
     */
    public static String formatForSaving(final String rawDiff,
                                        final String titleToRemove) {
        // Remove ANSI color codes
        String clean = rawDiff.replaceAll("\u001B\\[[;\\d]*m", "");

        // Remove the specified title if provided
        if (titleToRemove != null && !titleToRemove.isEmpty()) {
            clean = clean.replaceAll(".*" + titleToRemove + ".*\\n?", "");
        }

        return clean.trim();
    }

    /**
     * Normalizes whitespace and line breaks in a text.
     * <p>
     * The normalization includes:
     * <ul>
     *   <li>Converting all line endings to {@code \\n}</li>
     *   <li>Collapsing multiple spaces or tabs into a single space</li>
     *   <li>Reducing multiple consecutive line breaks to one</li>
     *   <li>Trimming leading and trailing whitespace</li>
     * </ul>
     *
     * @param text the text to normalize
     * @return the normalized text
     */
    public static String normalize(final String text) {
        // Κανονικοποίηση κενών και αλλαγών γραμμής
        return text.replaceAll("\r\n", "\n")
                   .replaceAll("\r", "\n")
                   .replaceAll("[ \t]+", " ")
                   .replaceAll("\n+", "\n")
                   .trim();
    }
}
