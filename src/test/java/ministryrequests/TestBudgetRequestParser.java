package ministryrequests;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link BudgetRequestParser}.
 * These tests verify correct parsing of ministry data
 * and calculation of expense contribution percentages.
 */
class TestBudgetRequestParser {

    /**
     * Verifies correct parsing of ministry code, budget type,
     * new ministry amount and expense percentages.
     */
    @Test
    void parse_validRequest_extractsAllFields() {
        String text =
                "123 | Υπουργείο Παιδείας\n" +
                "Type: TAKTIKOS\n" +
                "Τακτικός: 1000 → 1200 (αύξηση)\n" +
                "\n" +
                "ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ\n" +
                "2,01 | Μισθοί\n" +
                "Μισθοί → Μισθοί (100)\n" +
                "2,02 | Λειτουργικά\n" +
                "Λειτουργικά → Λειτουργικά (100)\n" +
                "3 | Σύνολο\n";

        BudgetRequestParser parser = new BudgetRequestParser(text);
        BudgetRequestParser.ParsedResult result = parser.parse(1);

        // Verify ministry-level data
        assertEquals(123, result.getMinistryCode());
        assertEquals("TAKTIKOS", result.getBudgetType());
        assertEquals(new BigDecimal("1200"), result.getMinistryNewAmount());

        // Verify expense percentages
        Map<String, BigDecimal> expenses = result.getExpensePercentages();
        assertEquals(2, expenses.size());

        // Ministry delta = 1200 - 1000 = 200
        // Each expense delta = 100 → 100 / 200 = 0.50
        assertEquals(new BigDecimal("0.50"), expenses.get("2,01"));
        assertEquals(new BigDecimal("0.50"), expenses.get("2,02"));
    }

    /**
     * Verifies that zero percentages are returned
     * when ministry budget does not change.
     */
    @Test
    void parse_zeroMinistryDelta_returnsZeroPercentages() {
        String text =
                "50 | Υπουργείο Υγείας\n" +
                "Type: TAKTIKOS\n" +
                "Τακτικός: 1000 → 1000 (καμία αλλαγή)\n" +
                "\n" +
                "ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ\n" +
                "2,10 | Προμήθειες\n" +
                "Προμήθειες → Προμήθειες (50)\n";

        BudgetRequestParser parser = new BudgetRequestParser(text);
        BudgetRequestParser.ParsedResult result = parser.parse(2);

        Map<String, BigDecimal> expenses = result.getExpensePercentages();

        assertEquals(1, expenses.size());
        assertEquals(BigDecimal.ZERO, expenses.get("2,10"));
    }

    /**
     * Verifies that parsing works for investment budgets
     * (Επενδύσεις instead of Τακτικός).
     */
    @Test
    void parse_investmentBudget_detectsCorrectPrefix() {
        String text =
                "77 | Υπουργείο Υποδομών\n" +
                "Type: EPENDYSEIS\n" +
                "Επενδύσεις: 500 → 700 (αύξηση)\n" +
                "\n" +
                "ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ\n" +
                "2,20 | Έργα\n" +
                "Έργα → Έργα (200)\n";

        BudgetRequestParser parser = new BudgetRequestParser(text);
        BudgetRequestParser.ParsedResult result = parser.parse(3);

        assertEquals(77, result.getMinistryCode());
        assertEquals("EPENDYSEIS", result.getBudgetType());
        assertEquals(new BigDecimal("700"), result.getMinistryNewAmount());

        Map<String, BigDecimal> expenses = result.getExpensePercentages();
        assertEquals(new BigDecimal("1.00"), expenses.get("2,20"));
    }

    /**
     * Verifies that missing expense section
     * results in an empty expense map.
     */
    @Test
    void parse_noExpenseSection_returnsEmptyMap() {
        String text =
                "10 | Υπουργείο Πολιτισμού\n" +
                "Type: TAKTIKOS\n" +
                "Τακτικός: 300 → 400 (αύξηση)\n";

        BudgetRequestParser parser = new BudgetRequestParser(text);
        BudgetRequestParser.ParsedResult result = parser.parse(4);

        assertNotNull(result);
        assertTrue(result.getExpensePercentages().isEmpty());
    }

    /**
     * Verifies that parser ignores empty lines and whitespace
     * and still correctly extracts ministry data.
     */
    @Test
    void parse_ignoresEmptyLines_extractsMinistry() {
        String text =
                "\n\n" +
                "88 | Υπουργείο Εσωτερικών\n" +
                "\n" +
                "Type: TAKTIKOS\n" +
                "Τακτικός: 1500 → 1800 (αύξηση)\n" +
                "\n";

        BudgetRequestParser parser = new BudgetRequestParser(text);
        BudgetRequestParser.ParsedResult result = parser.parse(5);

        assertEquals(88, result.getMinistryCode());
        assertEquals("TAKTIKOS", result.getBudgetType());
        assertEquals(new BigDecimal("1800"), result.getMinistryNewAmount());
        assertTrue(result.getExpensePercentages().isEmpty());
    }
}
