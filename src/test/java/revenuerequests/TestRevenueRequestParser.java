package revenuerequests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class TestRevenueRequestParser {

    @Test
    void testParseValidRevenue() {
        String input = """
                1,1 | Άμεσοι Φόροι
                Προηγούμενο: 1000.00 → 1250.50 (250.50)
                """;

        RevenueRequestParser parser = new RevenueRequestParser(input);
        RevenueRequestParser.RevenueResult result = parser.parse();

        assertNotNull(result);
        assertEquals("1,1", result.getCode(), "Ο κωδικός εσόδου πρέπει να είναι 1,1");
        assertEquals(new BigDecimal("1250.50"), result.getAmount(), "Το ποσό πρέπει να είναι 1250.50");
    }

    @Test
    void testParseWithMultipleLines() {
        String input = """
                Τίτλος Αναφοράς
                ----------------
                1,2 | Έμμεσοι Φόροι
                100.00 → 500.00 (400.00)
                Άσχετη πληροφορία
                """;

        RevenueRequestParser parser = new RevenueRequestParser(input);
        RevenueRequestParser.RevenueResult result = parser.parse();

        assertEquals("1,2", result.getCode());
        assertEquals(new BigDecimal("500.00"), result.getAmount());
    }

    @Test
    void testExtractNumberCleansText() {
        String input = """
                2,1 | Τέλη
                Αλλαγή: → +1.500,75€ (παράδειγμα)
                """;
        
        RevenueRequestParser parser = new RevenueRequestParser(input);
        RevenueRequestParser.RevenueResult result = parser.parse();

        assertEquals(new BigDecimal("1.500"), result.getAmount());
    }

    @Test
    void testEmptyInput() {
        RevenueRequestParser parser = new RevenueRequestParser("");
        RevenueRequestParser.RevenueResult result = parser.parse();

        assertNull(result.getCode());
        assertEquals(BigDecimal.ZERO, result.getAmount());
    }
}