package revenuerequests;

import java.math.BigDecimal;

/**
 * Parser for extracting revenue information from a budget request text block.
 * <p>
 * Relies on the textual structure recognized by {@code BudgetRequestLoader}.
 */
public class RevenueRequestParser {

    /** Ιnput text of the revenue request. */
    private final String rawText;

    /**
     * Constructs a new parser for the given budget request text block.
     *
     * @param text the text block from {@code BudgetRequestLoader}
     */
    public RevenueRequestParser(final String text) {
        this.rawText = text;
    }

    /**
     * Extracts revenue data from the text.
     * <p>
     * The method searches for a line matching the revenue pattern
     * (e.g., "1,1 | Taxes") and retrieves the updated amount
     * from the immediately following line containing "→".
     *
     * @return a {@link RevenueResult}
     * containing the revenue code and the new amount
     */
    public RevenueResult parse() {
        String[] lines = rawText.split("\n");
        String revenueCode = null;
        BigDecimal newAmount = BigDecimal.ZERO;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            // Identify the revenue line
            if (line.matches("^\\d+(,\\d+)*\\s*\\|.*")) {
                revenueCode = line.split("\\|")[0].trim();

                // The amount is in the immediately next line
                if (i + 1 < lines.length) {
                    String amountLine = lines[i + 1].trim();
                    if (amountLine.contains("→")) {
                        // Take the part after the arrow "→"
                        String rightPart = amountLine.split("→", 2)[1].trim();
                        // Isolate the number before any parentheses
                        newAmount =
                                extractNumber(rightPart.split("\\(", 2)[0]);
                    }
                }
                break;
            }
        }
        return new RevenueResult(revenueCode, newAmount);
    }

    /**
     * Cleans the input string and returns its numeric value.
     * <p>
     * Non-digit characters (except dot) are removed, and the
     * first valid number is converted to {@link BigDecimal}.
     *
     * @param text the string containing a numeric value
     * @return the extracted number as {@link BigDecimal},
     * or ZERO if parsing fails
     */
    private BigDecimal extractNumber(final String text) {
        String cleaned = text.replaceAll("[^0-9.]", " ").trim();
        for (String token : cleaned.split(" ")) {
            if (!token.isBlank()) {
                try {
                    return new BigDecimal(token);
                } catch (NumberFormatException e) {
                    return BigDecimal.ZERO;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * Encapsulates the result of parsing a revenue line.
     */
    public static class RevenueResult {

        /** Revenue code. */
        private final String code;
        /** Revenue amount. */
        private final BigDecimal amount;

        /**
         * Constructs a revenue result with the given code and amount.
         *
         * @param revCode the revenue code
         * @param revAmount the new amount
         */
        public RevenueResult(final String revCode,
                        final BigDecimal revAmount) {
            code = revCode;
            amount = revAmount;
        }

        /**
         * Returns the revenue code.
         *
         * @return the code
         */
        public String getCode() {
            return code;
        }

        /**
         * Returns the revenue amount.
         *
         * @return the amount
         */
        public BigDecimal getAmount() {
            return amount;
        }
    }
}
