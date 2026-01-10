package ministryrequests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRequestType {

    @Test
    void enumValues_existAndOrderCorrect() {
        RequestType[] values = RequestType.values();

        assertEquals(3, values.length, "There should be 3 enum constants");

        assertEquals(RequestType.TAKTIKOS, values[0]);
        assertEquals(RequestType.EPENDYSEIS, values[1]);
        assertEquals(RequestType.BOTH, values[2]);
    }

    @Test
    void valueOf_returnsCorrectEnum() {
        assertEquals(RequestType.TAKTIKOS, RequestType.valueOf("TAKTIKOS"));
        assertEquals(RequestType.EPENDYSEIS, RequestType.valueOf("EPENDYSEIS"));
        assertEquals(RequestType.BOTH, RequestType.valueOf("BOTH"));
    }

    @Test
    void toString_returnsName() {
        assertEquals("TAKTIKOS", RequestType.TAKTIKOS.toString());
        assertEquals("EPENDYSEIS", RequestType.EPENDYSEIS.toString());
        assertEquals("BOTH", RequestType.BOTH.toString());
    }
}
