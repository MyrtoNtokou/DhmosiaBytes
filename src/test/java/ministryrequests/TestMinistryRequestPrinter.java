package ministryrequests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static aggregatedata.ConsoleColors.*;

/**
 * Unit tests for {@link MinistryRequestPrinter}.
 * Captures System.out to verify printed output.
 */
class TestMinistryRequestPrinter {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void printRequest_printsCorrectly() {
        MinistryRequest request = new MinistryRequest(
                1, 100, "Υπουργείο Παιδείας",
                RequestType.TAKTIKOS, RequestStatus.PENDING,
                LocalDateTime.of(2026, 1, 10, 12, 0),
                "Τακτικός: 1000 → 1200 (+200)"
        );

        MinistryRequestPrinter.printRequest(request);

        String output = outContent.toString();

        // Check header with ID in cyan + bold
        assertTrue(output.contains(CYAN + BOLD + "ID: 1" + RESET));

        // Check ministry name and code
        assertTrue(output.contains("Υπουργείο: Υπουργείο Παιδείας (κωδικός 100)"));

        // Check type
        assertTrue(output.contains("Τύπος: TAKTIKOS"));

        // Check status
        assertTrue(output.contains("Κατάσταση: PENDING"));

        // Check timestamp
        assertTrue(output.contains("Ημερομηνία: 2026-01-10T12:00"));

        // Check colored text (green for +200)
        assertTrue(output.contains("(" + GREEN + "+200" + RESET + ")"));
    }

    @Test
    void printRequests_printsAllRequests() {
        MinistryRequest r1 = new MinistryRequest(
                1, 100, "Υπουργείο Παιδείας",
                RequestType.TAKTIKOS, RequestStatus.PENDING,
                LocalDateTime.of(2026, 1, 10, 12, 0),
                "Τακτικός: 1000 → 1200 (+200)"
        );

        MinistryRequest r2 = new MinistryRequest(
                2, 200, "Υπουργείο Υγείας",
                RequestType.EPENDYSEIS, RequestStatus.PENDING,
                LocalDateTime.of(2026, 1, 11, 15, 30),
                "Επενδύσεις: 500 → 700 (+200)"
        );

        MinistryRequestPrinter.printRequests(List.of(r1, r2));
        String output = outContent.toString();

        // Check that both IDs appear
        assertTrue(output.contains(CYAN + BOLD + "ID: 1" + RESET));
        assertTrue(output.contains(CYAN + BOLD + "ID: 2" + RESET));

        // Check that both ministry names appear
        assertTrue(output.contains("Υπουργείο: Υπουργείο Παιδείας (κωδικός 100)"));
        assertTrue(output.contains("Υπουργείο: Υπουργείο Υγείας (κωδικός 200)"));

        // Check that colored text is applied for both
        assertTrue(output.contains("(" + GREEN + "+200" + RESET + ")"));
    }

    @Test
    void printRequests_emptyList_printsMessage() {
        MinistryRequestPrinter.printRequests(List.of());
        String output = outContent.toString();

        assertTrue(output.contains("Δεν βρέθηκαν αιτήματα."));
    }
}
