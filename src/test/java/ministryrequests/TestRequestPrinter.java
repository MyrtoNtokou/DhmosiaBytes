package ministryrequests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestRequestPrinter {

    // Store original System.out to restore after tests
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture printed output
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    void testPrintSingleRequest() {
        // Create a mock Request
        Request mockRequest = mock(Request.class);
        when(mockRequest.getId()).thenReturn(42);
        when(mockRequest.getStatus()).thenReturn(RequestStatus.PENDING);
        when(mockRequest.getTimestamp()).thenReturn(LocalDateTime.of(2026, 1, 12, 10, 0));
        when(mockRequest.getText()).thenReturn("Amount change");

        // Define behavior for printDetails() to simulate actual printing
        doAnswer(invocation -> {
            System.out.println("Details called");
            return null;
        }).when(mockRequest).printDetails();

        // Call the method under test
        RequestPrinter.printRequest(mockRequest);

        // Capture printed output
        String output = outContent.toString();

        // Assert that key information is printed
        assertTrue(output.contains("ID: 42"));
        assertTrue(output.contains("Details called"));
        assertTrue(output.contains("Κατάσταση: PENDING"));
        assertTrue(output.contains("Ημερομηνία: 2026-01-12T10:00"));
        assertTrue(output.contains("Amount change"));
    }

    @Test
    void testPrintEmptyRequestList() {
        // Call printRequests with an empty list
        RequestPrinter.printRequests(List.of());

        String output = outContent.toString();
        // Verify that the "no requests found" message is printed
        assertTrue(output.contains("Δεν βρέθηκαν αιτήματα."));
    }

    @Test
    void testPrintMultipleRequests() {
        // Create first mock request
        Request mock1 = mock(Request.class);
        when(mock1.getId()).thenReturn(1);
        when(mock1.getStatus()).thenReturn(RequestStatus.PENDING);
        when(mock1.getTimestamp()).thenReturn(LocalDateTime.of(2026, 1, 12, 9, 0));
        when(mock1.getText()).thenReturn("Text1");
        doAnswer(invocation -> { System.out.println("Details1"); return null; }).when(mock1).printDetails();

        // Create second mock request
        Request mock2 = mock(Request.class);
        when(mock2.getId()).thenReturn(2);
        when(mock2.getStatus()).thenReturn(RequestStatus.REJECTED);
        when(mock2.getTimestamp()).thenReturn(LocalDateTime.of(2026, 1, 12, 10, 0));
        when(mock2.getText()).thenReturn("Text2");
        doAnswer(invocation -> { System.out.println("Details2"); return null; }).when(mock2).printDetails();

        // Call printRequests with a list of requests
        RequestPrinter.printRequests(List.of(mock1, mock2));

        String output = outContent.toString();
        // Verify that all requests are printed with correct details
        assertTrue(output.contains("ID: 1"));
        assertTrue(output.contains("Details1"));
        assertTrue(output.contains("Text1"));
        assertTrue(output.contains("ID: 2"));
        assertTrue(output.contains("Details2"));
        assertTrue(output.contains("Text2"));
        assertTrue(output.contains("Κατάσταση: REJECTED"));
    }
}
