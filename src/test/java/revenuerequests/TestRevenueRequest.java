package revenuerequests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ministryrequests.RequestStatus;

class TestRevenueRequest {

    private RevenueRequest request;
    private final int id = 1;
    private final String revenueCode = "REV123";
    private final String revenueName = "Φόρος Εισοδήματος";
    private final RequestStatus status = RequestStatus.PENDING;
    private final LocalDateTime timestamp = LocalDateTime.of(2026, 1, 12, 10, 30);
    private final String diffText = "Αλλαγή ποσού";

    @BeforeEach
    void setUp() {
        request = new RevenueRequest(id, revenueCode, revenueName, status, timestamp, diffText);
    }

    @Test
    void testGetters() {
        assertEquals(id, request.getId());
        assertEquals(revenueCode, request.getRevenueCode());
        assertEquals(revenueName, request.getRevenueName());
        assertEquals(status, request.getStatus());
        assertEquals(timestamp, request.getTimestamp());
        assertEquals(diffText, request.getText());
    }

    @Test
    void testSetStatus() {
        request.setStatus(RequestStatus.MODIFIED);
        assertEquals(RequestStatus.MODIFIED, request.getStatus());
    }

    @Test
    void testPrintDetails() {
        // Redirect System.out to capture print output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        request.printDetails();

        String expectedOutput = "Έσοδο: Φόρος Εισοδήματος (κωδικός REV123)" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());

        // Restore original System.out
        System.setOut(originalOut);
    }
}
