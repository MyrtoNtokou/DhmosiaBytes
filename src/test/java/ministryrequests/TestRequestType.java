package ministryrequests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestRequestType {

    @Test
    void enumValues_existAndOrderCorrect() {
        RequestType[] values = RequestType.values();

        assertEquals(3, values.length, "There should be 3 enum constants");

        assertEquals(RequestType.REGULARBUDGET, values[0]);
        assertEquals(RequestType.PUBLIC_INVESTMENTS, values[1]);
        assertEquals(RequestType.BOTH, values[2]);
    }

    @Test
    void valueOf_returnsCorrectEnum() {
        assertEquals(RequestType.REGULARBUDGET, RequestType.valueOf("REGULARBUDGET"));
        assertEquals(RequestType.PUBLIC_INVESTMENTS, RequestType.valueOf("PUBLIC_INVESTMENTS"));
        assertEquals(RequestType.BOTH, RequestType.valueOf("BOTH"));
    }

    @Test
    void toString_returnsName() {
        assertEquals("REGULARBUDGET", RequestType.REGULARBUDGET.toString());
        assertEquals("PUBLIC_INVESTMENTS", RequestType.PUBLIC_INVESTMENTS.toString());
        assertEquals("BOTH", RequestType.BOTH.toString());
    }
}
