package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestIncomeOptions {

    @Test
    void testGetters() {
        IncomeOptions option = IncomeOptions.FOROI;

        assertEquals("1,1", option.getIncomeCode());
        assertEquals("Φόροι", option.getIncomeDescription());
    }

    @Test
    void testFromCodeValid() {
        assertEquals(IncomeOptions.FOROI, IncomeOptions.fromCode("1,1"));
        assertEquals(IncomeOptions.KOINONIKES_EISFORES, IncomeOptions.fromCode("1,2"));
        assertEquals(IncomeOptions.DANEIA, IncomeOptions.fromCode("1,9,3"));
    }

    @Test
    void testFromCodeInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            IncomeOptions.fromCode("9,9");
        });

        assertTrue(exception.getMessage().contains("Μη έγκυρη επιλογή"));
    }
}
