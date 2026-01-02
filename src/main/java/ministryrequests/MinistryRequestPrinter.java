package ministryrequests;

import java.util.List;

/**
 * Print all the requests with pending status.
 */
public final class MinistryRequestPrinter {

    /** Constructor. */
    private MinistryRequestPrinter() { }

    /**
     * Print all the info of one request.
     * @param r MinistryRequest object
     */
    public static void printRequest(final MinistryRequest r) {
        System.out.println("====================================");
        System.out.println("Αίτημα ID: " + r.getId());
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
            System.out.println("Δεν υπάρχουν αιτήματα για τα κριτήρια αυτά.");
            return;
        }
        for (MinistryRequest r : requests) {
            printRequest(r);
        }
    }
}
