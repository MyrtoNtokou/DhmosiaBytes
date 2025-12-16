package aggregatedata;

import budgetreader.Eggrafi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Calculate TOP_COUNT max revenues and expenses.
 * Calculate TOP_COUNT min revenues and expenses.
*/
public final class BudgetAnalyzer {

        /** Amount of max and min in every calculation. */
        private static final int TOP_COUNT = 3;

        private BudgetAnalyzer() {
                //Constructor
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
                        // convert back to list
                        .collect(Collectors.toList());

                List<Eggrafi> expenses = list.stream()
                        .filter(e -> e.getKodikos().startsWith("2"))
                        .collect(Collectors.toList());

                // Compare Eggrafi types based on their ammount (poso)
                Comparator<Eggrafi> byAmount = Comparator
                                                .comparing(Eggrafi::getPoso);

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

                return new BudgetStats(maxRevenues, minRevenues,
                                        maxExpenses, minExpenses);
    }
}
