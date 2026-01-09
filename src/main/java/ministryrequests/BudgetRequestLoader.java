package ministryrequests;

/**
 * Utility class responsible for extracting a specific
 * budget request block from a larger text file.
 */
public final class BudgetRequestLoader {

    /** Constructor. */
    private BudgetRequestLoader() { }

    /**
     * Extract the text block corresponding to a given request ID.
     * @param fileContent full content of the input file
     * @param requestId   id of the requested block
     * @return the matching request block, or {@code null} if not found
     */
    public static String extractRequestBlock(final String fileContent,
                                            final int requestId) {
        String[] blocks = fileContent.split("=== REQUEST ===");

        for (String block : blocks) {
            if (block.contains("Id: " + requestId)) {
                return block.trim();
            }
        }
        return null; // δεν βρέθηκε
    }
}
