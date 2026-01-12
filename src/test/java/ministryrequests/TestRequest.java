package ministryrequests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TestRequest {

    @Test
    void testRequestInterfaceMethods() {
        // Create a mock Request
        Request mockRequest = mock(Request.class);

        int id = 123;
        String text = "Sample request text";
        LocalDateTime timestamp = LocalDateTime.of(2026, 1, 12, 10, 0);
        RequestStatus status = RequestStatus.PENDING;

        // Define behavior of mock methods
        when(mockRequest.getId()).thenReturn(id);
        when(mockRequest.getText()).thenReturn(text);
        when(mockRequest.getTimestamp()).thenReturn(timestamp);
        when(mockRequest.getStatus()).thenReturn(status);

        // Call methods and assert
        assertEquals(id, mockRequest.getId());
        assertEquals(text, mockRequest.getText());
        assertEquals(timestamp, mockRequest.getTimestamp());
        assertEquals(status, mockRequest.getStatus());

        // Call printDetails() and verify it was invoked
        mockRequest.printDetails();
        verify(mockRequest, times(1)).printDetails();
    }

    @Test
    void testMultipleCalls() {
        Request mockRequest = mock(Request.class);

        when(mockRequest.getStatus()).thenReturn(RequestStatus.REJECTED);

        // Call multiple times
        assertEquals(RequestStatus.REJECTED, mockRequest.getStatus());
        assertEquals(RequestStatus.REJECTED, mockRequest.getStatus());
        assertEquals(RequestStatus.REJECTED, mockRequest.getStatus());

        // Verify the method was called 3 times
        verify(mockRequest, times(3)).getStatus();
    }
}
