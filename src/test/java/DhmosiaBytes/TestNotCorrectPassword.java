package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TestNotCorrectPassword {

    @Test
    void testExceptionMessage() {
        String message = "Το password δεν είναι σωστό!";
        NotCorrectPassword ex = new NotCorrectPassword(message);
        assertEquals(message, ex.getMessage());
    }

    @Test
    void testExceptionInheritance() {
        NotCorrectPassword ex = new NotCorrectPassword("test");
        assertTrue(ex instanceof Exception);
    }
}
