package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestMinistryOptions {

    @Test
    void testFromCodeValid() {
        MinistryOptions ministry = MinistryOptions.PROEDRIADHM;
        MinistryOptions result = MinistryOptions.fromCode(ministry.getMinistryCode());
        assertNotNull(result);
        assertEquals(ministry, result);
    }

    @Test
    void testFromCodeInvalid() {
        int invalidCode = 999;
        assertThrows(IllegalArgumentException.class, () -> {
            MinistryOptions.fromCode(invalidCode);
        });
    }

    @Test
    void testGetters() {
        MinistryOptions ministry = MinistryOptions.OIKONOMIKON;
        assertEquals(12, ministry.getMinistryCode());
        assertEquals("Υπουργείο Εθνικής Οικονομίας και Οικονομικών",
                     ministry.getMinistryDescription());
    }

    @Test
    void testAllMinistriesHaveUniqueCodes() {
        boolean[] seen = new boolean[100]; // assume max 100 ministries
        for (MinistryOptions ministry : MinistryOptions.values()) {
            int code = ministry.getMinistryCode();
            assertFalse(seen[code], "Duplicate code found: " + code);
            seen[code] = true;
        }
    }
}
