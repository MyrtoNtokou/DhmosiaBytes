package budgetlogic;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class BasicRecordTest {

    @Test
    void testConstructorAndGetters() {
        BasicRecord record = new BasicRecord("001", "Test", new BigDecimal("12.50"));

        assertEquals("001", record.getKodikos());
        assertEquals("Test", record.getPerigrafi());
        assertEquals(new BigDecimal("12.50"), record.getPoso());
    }
}

