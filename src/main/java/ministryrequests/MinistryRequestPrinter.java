package ministryrequests;

import java.util.List;

/**
 * Print all the requests with pending status.
 */
public final class MinistryRequestPrinter {

    /** Constructor. */
    private MinistryRequestPrinter() { }

    /** ANSI reset code to clear all formatting. */
    private static final String RESET = "\u001B[0m";
    /** ANSI bold text modifier. */
    private static final String BOLD = "\u001B[1m";

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

    /** Cyan for ID. */
    private static final String CYAN = rgb(0, 200, 255);

    /**
     * Print all the info of one request.
     * @param r MinistryRequest object
     */
    public static void printRequest(final MinistryRequest r) {
        System.out.println("\n====================================");
        System.out.println(CYAN + BOLD + "Αίτημα ID: " + r.getId() + RESET);
        System.out.println("Υπουργείο: " + r.getMinistryName()
                + " (κωδικός " + r.getMinistryCode() + ")");
        System.out.println("Τύπος: " + r.getType());
        System.out.println("Κατάσταση: " + r.getStatus());
        System.out.println("Ημερομηνία: " + r.getTimestamp());
        System.out.println("------------------------------------");
        String colored = DiffColorizer.colorize(r.getText());
        System.out.println(colored);
        System.out.println("====================================\n");
    }

    /**
     * Print all the requests.
     * @param requests list with MinistryRequest objects
     */
    public static void printRequests(final List<MinistryRequest> requests) {
        if (requests.isEmpty()) {
            System.out.println("Δεν υπάρχουν PENDING αιτήματα.");
            return;
        }
        for (MinistryRequest r : requests) {
            printRequest(r);
        }
    }
}
