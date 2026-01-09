package ministryrequests;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * Parse budget request text and extracts ministry-level
 * budget changes and expense-level contribution percentages.
 */
public class BudgetRequestParser {

    /** Ιnput text of the budget request. */
    private final String rawText;

    /**
     * Constructor.
     * @param text unprocessed text of the budget request
     */
    public BudgetRequestParser(final String text) {
        rawText = text;
    }

    /**
     * Parses the budget request text.
     * @param requestId request id
     * @return ParsedResult containing ministry data and expense percentages
     */
    public ParsedResult parse(final int requestId) {
        String[] lines = rawText.split("\n");

        Integer ministryCode = null;
        BigDecimal ministryOldAmount = null;
        BigDecimal ministryNewAmount = null;
        Map<String, BigDecimal> expensePercentages = new HashMap<>();

        boolean inExpenseSection = false;
        String currentExpenseCode = null; // Κρατάμε τον κωδικό (π.χ. 2,1)

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            // Detect ministry code line
            if (line.matches("^\\d+ \\| Υπουργείο.*")) {
                ministryCode = Integer.parseInt(line.split("\\|")[0].trim());
                continue;
            }

            // Extract ministry old and new amounts
            if (ministryCode != null && line.startsWith("Τακτικός:")
                        && ministryOldAmount == null) {
                String[] parts = line.split("→");
                ministryOldAmount = extractNumber(parts[0]);
                ministryNewAmount = extractNumber(parts[1].split("\\(")[0]);
                continue;
            }

            if (line.contains("ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ")) {
                inExpenseSection = true;
                continue;
            }

            // Enter expense changes section
            if (inExpenseSection) {
                // Skip total expenses line
                if (line.startsWith("3 |")) {
                    break;
                }

                // Detect expense code
                if (line.matches("^2,\\d+ \\|.*")) {
                    currentExpenseCode = line.split("\\|")[0].trim();
                 // Extract expense percentage
                } else if (currentExpenseCode != null && line.contains("→")) {
                    BigDecimal percentage = extractExpensePercentage(
                            line,
                            ministryOldAmount,
                            ministryNewAmount);
                    expensePercentages.put(currentExpenseCode, percentage);
                    currentExpenseCode = null;
                }
            }
        }
        return new ParsedResult(ministryCode,
                                ministryNewAmount,
                                expensePercentages);
    }


    /**
     * Extract the first numeric value found in a text string.
     * @param text input text
     * @return extracted number or zero if none found
     */
    private BigDecimal extractNumber(final String text) {
        String cleaned = text.replaceAll("[^0-9.]", " ").trim();
        for (String token : cleaned.split(" ")) {
            if (!token.isBlank()) {
                return new BigDecimal(token);
            }
        }
        return BigDecimal.ZERO;
    }

     /**
     * Calculate the percentage contribution of an expense change
     * relative to the total ministry budget change.
     * @param line expense line containing the difference
     * @param ministryOld previous ministry amount
     * @param ministryNew new ministry amount
     * @return percentage contribution rounded to 2 decimals
     */
    private BigDecimal extractExpensePercentage(final String line,
                                            final BigDecimal ministryOld,
                                            final BigDecimal ministryNew) {
        int start = line.indexOf('(');
        int end = line.indexOf(')');

        if (start == -1 || end == -1 || end <= start) {
            return BigDecimal.ZERO;
        }

        String deltaText = line.substring(start + 1, end);
        BigDecimal deltaExpense = extractNumber(deltaText);

        BigDecimal deltaMinistry = ministryNew.subtract(ministryOld);

        if (deltaMinistry.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return deltaExpense.divide(deltaMinistry, 2, RoundingMode.HALF_UP);
    }


    /**
     * Result object holding parsed budget data.
     */
    public static class ParsedResult {
        /** Ministry code. */
        private final Integer ministryCode;
        /** New ministry budget amount. */
        private final BigDecimal ministryNewAmount;
        /** Map of expense code to percentage contribution. */
        private final Map<String, BigDecimal> expensePercentages;

        /**
         * Constructor.
         * @param code
         * @param newAmount
         * @param expensePer
         */
        public ParsedResult(final Integer code,
                            final BigDecimal newAmount,
                            final Map<String, BigDecimal> expensePer) {
            ministryCode = code;
            ministryNewAmount = newAmount;
            expensePercentages = new HashMap<>(expensePer);
        }

        /**
         * Getter for ministry code from request.
         * @return ministry code
         * */
        public Integer getMinistryCode() {
            return ministryCode;
        }

        /**
         * Getter for ministry new amount from request.
         * @return new amount
         * */
        public BigDecimal getMinistryNewAmount() {
            return ministryNewAmount;
        }

        /**
         * Getter for ministry new amount from request.
         * @return new amount
         * */
        public Map<String, BigDecimal> getExpensePercentages() {
            return new HashMap<>(expensePercentages);
        }
    }
}
