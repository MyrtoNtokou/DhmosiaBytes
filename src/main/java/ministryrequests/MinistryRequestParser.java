package ministryrequests;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;

/**
 * Parse budget request text and extracts ministry-level
 * budget changes and expense-level contribution percentages.
 */
public class MinistryRequestParser {

    /** Ιnput text of the budget request. */
    private final String rawText;

    /** Code for number 5. */
    private static final int CODE_FOR_FIVE = 5;

    /**
     * Constructor.
     * @param text unprocessed text of the budget request
     */
    public MinistryRequestParser(final String text) {
        this.rawText = (text != null) ? text : "";
    }

    /**
     * Parses the budget request text.
     * @param requestId request id
     * @return ParsedResult containing ministry data and expense percentages
     */
    public ParsedResult parse(final int requestId) {
        if (rawText == null || rawText.isEmpty()) {
            System.err.println("Το κείμενο του αιτήματος "
            + requestId + " είναι κενό.");
            return new ParsedResult(null, null, null, new HashMap<>());
        }

        String[] lines = rawText.split("\n");

        Integer ministryCode = null;
        String budgetType = null;
        BigDecimal ministryOldAmount = null;
        BigDecimal ministryNewAmount = null;
        Map<String, BigDecimal> expensePercentages = new HashMap<>();

        boolean inExpenseSection = false;
        String currentExpenseCode = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.contains("Σύνολο εξόδων Κρατικού Προϋπολογισμού")) {
                continue;
            }

            // Detect ministry code line
            if (ministryCode == null && line.matches("^\\d+ \\|.*")) {
                ministryCode = Integer.parseInt(line.split("\\|")[0].trim());
                continue;
            }

            if (line.toUpperCase().startsWith("TYPE:")) {
                budgetType = line.substring(CODE_FOR_FIVE).trim();
                if (budgetType.isEmpty()) {
                    System.err.println(RED + "Σφάλμα: δεν βρέθηκε ο τύπος "
                            + "του προϋπολογισμού για το αίτημα "
                            + requestId + RESET);
                    budgetType = null;
                    continue;
                }
            }

            if (budgetType == null) {
                continue;
            }

            String searchPrefix;
            switch (budgetType.toUpperCase()) {
                case "TAKTIKOS" -> {
                    searchPrefix = "Τακτικός:";
                }
                case "EPENDYSEIS" -> {
                    searchPrefix = "ΠΔΕ:";
                }
                default -> {
                    searchPrefix = budgetType + ":";
                }
            }

            // Extract ministry old and new amounts
            if (ministryCode != null && line.startsWith(searchPrefix)
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
                    if (ministryOldAmount == null
                            || ministryNewAmount == null) {
                        System.err.println(RED
                                + "Σφάλμα: δεν βρέθηκαν τα ποσά για "
                                + "το αίτημα " + requestId + RESET);
                        currentExpenseCode = null;
                        continue;
                    }
                    BigDecimal percentage = extractExpensePercentage(
                            line,
                            ministryOldAmount,
                            ministryNewAmount,
                            requestId);
                    expensePercentages.put(currentExpenseCode, percentage);
                    currentExpenseCode = null;
                }
            }
        }
        return new ParsedResult(ministryCode,
                                budgetType,
                                ministryNewAmount,
                                expensePercentages);
    }


    /**
     * Extract the first numeric value found in a text string.
     * @param text input text
     * @return extracted number or zero if none found
     */
    private BigDecimal extractNumber(final String text) {
        String cleaned = text.replaceAll("[^0-9.-]", " ").trim();
        for (String token : cleaned.split("\\s+")) {
            if (!token.isBlank() && !token.equals("-")) {
                try {
                    return new BigDecimal(token);
                } catch (NumberFormatException e) {
                    System.err.println(RED + "Σφάλμα" + RESET);
                }
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
     * @param requestId selected id
     * @return percentage contribution rounded to 2 decimals
     */
    private BigDecimal extractExpensePercentage(final String line,
                                            final BigDecimal ministryOld,
                                            final BigDecimal ministryNew,
                                            final int requestId) {
        if (ministryOld == null || ministryNew == null) {
            System.err.println(RED + "Σφάλμα: δεν βρέθηκαν τα ποσά για "
                                + "το αίτημα " + requestId + RESET);
            return BigDecimal.ZERO;
        }
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

        return deltaExpense.divide(deltaMinistry, 2,
                RoundingMode.HALF_UP).abs();
    }


    /**
     * Result object holding parsed budget data.
     */
    public static class ParsedResult {
        /** Ministry code. */
        private final Integer ministryCode;
        /** Budget type. */
        private final String budgetType;
        /** New ministry budget amount. */
        private final BigDecimal ministryNewAmount;
        /** Map of expense code to percentage contribution. */
        private final Map<String, BigDecimal> expensePercentages;

        /**
         * Constructor.
         * @param code
         * @param type
         * @param newAmount
         * @param expensePer
         */
        public ParsedResult(final Integer code,
                            final String type,
                            final BigDecimal newAmount,
                            final Map<String, BigDecimal> expensePer) {
            ministryCode = code;
            budgetType = type;
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
         * Getter for budget type from request.
         * @return taktikos or ependyseis
         * */
        public String getBudgetType() {
            return budgetType;
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
