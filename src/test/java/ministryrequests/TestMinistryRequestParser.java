package ministryrequests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Map;

class TestMinistryRequestParser {

    @Test
    void testParseValidRequest() {
        String input = """
                23 | Υπουργείο Οικονομικών
                TYPE: TAKTIKOS
                Τακτικός: 1000.00 → 1500.00 (500.00)
                ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ
                2,101 | Μισθοδοσία
                → (250.00)
                2,102 | Προμήθειες
                → (250.00)
                3 | Σύνολο
                """;

        MinistryRequestParser parser = new MinistryRequestParser(input);
        MinistryRequestParser.ParsedResult result = parser.parse(101);

        assertNotNull(result);
        assertEquals(23, result.getMinistryCode());
        assertEquals("TAKTIKOS", result.getBudgetType());
        assertEquals(new BigDecimal("1500.00"), result.getMinistryNewAmount());

        Map<String, BigDecimal> percentages = result.getExpensePercentages();
        assertNotNull(percentages, "Το map δεν πρέπει να είναι null");

        assertEquals(new BigDecimal("0.50"), percentages.get("2,101"), "Αποτυχία στο έξοδο 2,101");
        assertEquals(new BigDecimal("0.50"), percentages.get("2,102"), "Αποτυχία στο έξοδο 2,102");
    }

    @Test
    void testDivisionByZeroHandled() {
        String input = """
                10 | Υπουργείο Υγείας
                TYPE: TAKTIKOS
                Τακτικός: 1000.00 → 1000.00 (0.00)
                ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ
                2,101 | Έξοδο
                → (50.00)
                """;

        MinistryRequestParser parser = new MinistryRequestParser(input);
        MinistryRequestParser.ParsedResult result = parser.parse(103);

        assertNotNull(result.getExpensePercentages().get("2,101"));
        assertEquals(BigDecimal.ZERO, result.getExpensePercentages().get("2,101"));
    }
}