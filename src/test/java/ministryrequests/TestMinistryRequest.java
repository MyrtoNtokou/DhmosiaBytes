package ministryrequests;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MinistryRequest}.
 */
class TestMinistryRequest {

    @Test
    void constructor_andGetters_workCorrectly() {
        LocalDateTime now = LocalDateTime.now();
        String text = "Request change for ministry budget";
        MinistryRequest request = new MinistryRequest(
                1,
                100,
                "Υπουργείο Παιδείας",
                RequestType.TAKTIKOS,
                RequestStatus.PENDING,
                now,
                text
        );

        // Check id
        assertEquals(1, request.getId());

        // Check ministry code
        assertEquals(100, request.getMinistryCode());

        // Check ministry name
        assertEquals("Υπουργείο Παιδείας", request.getMinistryName());

        // Check type
        assertEquals(RequestType.TAKTIKOS, request.getType());

        // Check status
        assertEquals(RequestStatus.PENDING, request.getStatus());

        // Check timestamp
        assertEquals(now, request.getTimestamp());

        // Check text
        assertEquals(text, request.getText());

        // Check hash
        assertEquals(text.hashCode(), request.getHash());
    }

    @Test
    void setStatus_updatesStatusCorrectly() {
        MinistryRequest request = new MinistryRequest(
                2,
                200,
                "Υπουργείο Υγείας",
                RequestType.EPENDYSEIS,
                RequestStatus.PENDING,
                LocalDateTime.now(),
                "Budget increase request"
        );

        assertEquals(RequestStatus.PENDING, request.getStatus());

        // Change status to COMPLETED
        request.setStatus(RequestStatus.COMPLETED);
        assertEquals(RequestStatus.COMPLETED, request.getStatus());

        // Change status to REJECTED
        request.setStatus(RequestStatus.REJECTED);
        assertEquals(RequestStatus.REJECTED, request.getStatus());
    }

    @Test
    void hash_isZero_whenTextIsNull() {
        MinistryRequest request = new MinistryRequest(
                3,
                300,
                "Υπουργείο Οικονομικών",
                RequestType.BOTH,
                RequestStatus.PENDING,
                LocalDateTime.now(),
                null
        );

        assertEquals(0, request.getHash());
    }

    @Test
    void differentText_resultsInDifferentHash() {
        MinistryRequest r1 = new MinistryRequest(
                4, 400, "Υπουργείο Εσωτερικών", RequestType.TAKTIKOS,
                RequestStatus.PENDING, LocalDateTime.now(),
                "Text A"
        );

        MinistryRequest r2 = new MinistryRequest(
                5, 400, "Υπουργείο Εσωτερικών", RequestType.TAKTIKOS,
                RequestStatus.PENDING, LocalDateTime.now(),
                "Text B"
        );

        assertNotEquals(r1.getHash(), r2.getHash(),
                "Different text should produce different hashes");
    }

    @Test
    void constructor_acceptsBothRequestTypes() {
        MinistryRequest request = new MinistryRequest(
                6, 500, "Υπουργείο Υποδομών",
                RequestType.BOTH, RequestStatus.PENDING,
                LocalDateTime.now(), "Combined request"
        );

        assertEquals(RequestType.BOTH, request.getType());
    }
}
