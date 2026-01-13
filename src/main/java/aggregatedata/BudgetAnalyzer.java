package aggregatedata;

import budgetreader.BasicRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Calculate TOP_COUNT max revenues and expenses.
 * Calculate TOP_COUNT min revenues and expenses.
 * Claculate percentages for max revenues and expenses.
*/
public final class BudgetAnalyzer {

        /** Amount of max and min in every calculation. */
        private static final int TOP_COUNT = 3;
        /** Percent. */
        private static final int PERCENT = 100;

        private BudgetAnalyzer() {
                //Constructor
        }

        /**
         * Calculate percentage = value / total * 100.
         * @param value
         * @param total
         * @return the percentage
         */
        private static BigDecimal percent(final BigDecimal value,
                                        final BigDecimal total) {
                if (total.compareTo(BigDecimal.ZERO) == 0) {
                        return BigDecimal.ZERO;
                } else {
                        return value.multiply(BigDecimal.valueOf(PERCENT))
                    .divide(total, 2, RoundingMode.HALF_UP);
                }
        }

        /**
         * Execute max-min calculation for each category.
         * @param list list with revenues and expenses.
         * @return BudgetStats object
         */
        public static BudgetStats analyze(final List<BasicRecord> list) {

                List<BasicRecord> revenues = list.stream()
                        // code that starts with 1
                        .filter(e -> e.getCode().startsWith("1"))
                        // code that starts with 1 contains comma
                        .filter(e -> e.getCode().contains(","))
                        // convert back to list
                        .collect(Collectors.toList());

                List<BasicRecord> expenses = list.stream()
                        .filter(e -> e.getCode().startsWith("2"))
                        .filter(e -> e.getCode().contains(","))
                        .collect(Collectors.toList());

                // Calculate total revenues
                BigDecimal totalRevenues = revenues.stream()
                                                .map(BasicRecord::getAmount)
                                                .reduce(BigDecimal.ZERO,
                                                        BigDecimal::add);

                // Calculate total expenses
                BigDecimal totalExpenses = expenses.stream()
                                                .map(BasicRecord::getAmount)
                                                .reduce(BigDecimal.ZERO,
                                                        BigDecimal::add);

                // Compare BasicRecord types based on their ammount (amount)
                Comparator<BasicRecord> byAmount =
                Comparator.comparing(BasicRecord::getAmount);

                // Find max and min
                List<BasicRecord> maxRevenues = revenues.stream()
                        .sorted(byAmount.reversed())
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                List<BasicRecord> minRevenues = revenues.stream()
                        .sorted(byAmount)
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                List<BasicRecord> maxExpenses = expenses.stream()
                        .sorted(byAmount.reversed())
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                List<BasicRecord> minExpenses = expenses.stream()
                        .sorted(byAmount)
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                // Calculate percentages
                List<BigDecimal> maxRevenuePercentages = maxRevenues.stream()
                                .map(e -> percent(e.getAmount(), totalRevenues))
                                .collect(Collectors.toList());
                List<BigDecimal> maxExpensePercentages = maxExpenses.stream()
                                .map(e -> percent(e.getAmount(), totalExpenses))
                                .collect(Collectors.toList());

                return new BudgetStats(maxRevenues, minRevenues,
                                        maxExpenses, minExpenses,
                                        maxRevenuePercentages,
                                        maxExpensePercentages);
    }
}
