package ministryrequests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRequestStatus {

    @Test
    void enumValues_existAndOrderCorrect() {
        RequestStatus[] values = RequestStatus.values();

        assertEquals(6, values.length, "There should be 6 enum constants");

        assertEquals(RequestStatus.PENDING, values[0]);
        assertEquals(RequestStatus.REVIEWED_BY_FINANCE_MINISTRY, values[1]);
        assertEquals(RequestStatus.GOVERNMENT_APPROVED, values[2]);
        assertEquals(RequestStatus.PARLIAMENT_APPROVED, values[3]);
        assertEquals(RequestStatus.COMPLETED, values[4]);
        assertEquals(RequestStatus.REJECTED, values[5]);
    }

    @Test
    void valueOf_returnsCorrectEnum() {
        assertEquals(RequestStatus.PENDING, RequestStatus.valueOf("PENDING"));
        assertEquals(RequestStatus.REJECTED, RequestStatus.valueOf("REJECTED"));
    }

    @Test
    void toString_returnsName() {
        assertEquals("PENDING", RequestStatus.PENDING.toString());
        assertEquals("COMPLETED", RequestStatus.COMPLETED.toString());
    }
}
