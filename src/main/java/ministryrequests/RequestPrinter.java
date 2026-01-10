package ministryrequests;

import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.CYAN;
import java.util.List;

/**
 * Utility class responsible for printing {@link Request} objects
 * to the console in a formatted and colored way.
 * <p>
 * It supports any implementation of the {@link Request} interface
 * (e.g. MinistryRequest, RevenueRequest).
 */
public final class RequestPrinter {

    /**
     * Private constructor to prevent instantiation.
     */
    private RequestPrinter() { }

     /**
     * Prints a single {@link Request} to the console.
     * <p>
     * The method relies on polymorphism:
     * {@link Request#printDetails()} is invoked and the actual
     * implementation decides what additional information is printed.
     *
     * @param r the request to be printed
     */
    public static void printRequest(final Request r) {
        System.out.println("\n====================================");
        System.out.println(CYAN + BOLD + "ID: " + r.getId() + RESET);

        // Calls the concrete implementation's details printer
        r.printDetails();

        System.out.println("Κατάσταση: " + r.getStatus());
        System.out.println("Ημερομηνία: " + r.getTimestamp());
        System.out.println("------------------------------------");

        // Colorizes textual differences using DiffColorizer
        String colored = DiffColorizer.colorize(r.getText());
        System.out.println(colored);
        System.out.println("====================================\n");
    }

    /**
     * Prints a list of {@link Request} objects.
     * <p>
     * Uses a bounded wildcard so it can accept lists of any
     * subclass or implementation of {@link Request}.
     *
     * @param requests the list of requests to be printed
     */
    public static void printRequests(final List<? extends Request> requests) {
        if (requests.isEmpty()) {
            System.out.println("Δεν βρέθηκαν αιτήματα.");
            return;
        }
        for (Request r : requests) {
            printRequest(r);
        }
    }
}
