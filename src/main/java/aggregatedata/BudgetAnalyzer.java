package aggregatedata;

import budgetreader.Eggrafi;

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
                        return value.divide(total, 2, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(PERCENT));
                }
        }

        /**
         * Execute max-min calculation for each category.
         * @param list list with revenues and expenses.
         * @return BudgetStats object
         */
        public static BudgetStats analyze(final List<Eggrafi> list) {

                List<Eggrafi> revenues = list.stream()
                        // code that starts with 1
                        .filter(e -> e.getKodikos().startsWith("1"))
                        // code that starts with 1 contains comma
                        .filter(e -> e.getKodikos().contains(","))
                        // convert back to list
                        .collect(Collectors.toList());

                List<Eggrafi> expenses = list.stream()
                        .filter(e -> e.getKodikos().startsWith("2"))
                        .filter(e -> e.getKodikos().contains(","))
                        .collect(Collectors.toList());

                // Calculate total revenues
                BigDecimal totalRevenues = revenues.stream()
                                                .map(Eggrafi::getPoso)
                                                .reduce(BigDecimal.ZERO,
                                                        BigDecimal::add);

                // Calculate total expenses
                BigDecimal totalExpenses = expenses.stream()
                                                .map(Eggrafi::getPoso)
                                                .reduce(BigDecimal.ZERO,
                                                        BigDecimal::add);

                // Compare Eggrafi types based on their ammount (poso)
                Comparator<Eggrafi> byAmount = Comparator
                                                .comparing(Eggrafi::getPoso);

                // Find max and min
                List<Eggrafi> maxRevenues = revenues.stream()
                        .sorted(byAmount.reversed())
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                List<Eggrafi> minRevenues = revenues.stream()
                        .sorted(byAmount)
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                List<Eggrafi> maxExpenses = expenses.stream()
                        .sorted(byAmount.reversed())
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                List<Eggrafi> minExpenses = expenses.stream()
                        .sorted(byAmount)
                        .limit(TOP_COUNT)
                        .collect(Collectors.toList());

                // Calculate percentages
                List<BigDecimal> maxRevenuePercentages = maxRevenues.stream()
                                .map(e -> percent(e.getPoso(), totalRevenues))
                                .collect(Collectors.toList());
                List<BigDecimal> maxExpensePercentages = maxExpenses.stream()
                                .map(e -> percent(e.getPoso(), totalExpenses))
                                .collect(Collectors.toList());

                return new BudgetStats(maxRevenues, minRevenues,
                                        maxExpenses, minExpenses,
                                        maxRevenuePercentages,
                                        maxExpensePercentages);
    }
}
