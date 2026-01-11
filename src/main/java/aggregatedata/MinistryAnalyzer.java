package aggregatedata;

import budgetreader.Ministry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TOP_COUNT max min on ministries for each budget category.
 * Based on regularBudget, Ependyseis, Synolo.
* */
public final class MinistryAnalyzer {

    /** Amount of max and min in every calculation. */
    private static final int TOP_COUNT = 3;
    /** Code of the first ministry in the list. */
    private static final int FIRST_MINISTRY_CODE = 5;
    /** Code of the last ministry in the list. */
    private static final int LAST_MINISTRY_CODE = 24;
    /** Percent. */
    private static final int PERCENT = 100;
    /**Scale division. */
    private static final int SCALE_DIVISION = 10;

    private MinistryAnalyzer() {
        //Constructor
    }

    private static BigDecimal percent(final BigDecimal value,
                                final BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal ratio = value.divide(
                total, SCALE_DIVISION, RoundingMode.HALF_UP);
        return ratio.multiply(BigDecimal.valueOf(PERCENT)).setScale(
                2, RoundingMode.HALF_UP);
    }

    /**
     * Execute max-min calculation for each category.
     * @param list list with ministries.
     * @return MinistryStats object
     * */
    public static MinistryStats analyze(final List<Ministry> list) {

        List<Ministry> ministries = list.stream()
                // codes between 5 and 24
                .filter(y -> y.getcode() >= FIRST_MINISTRY_CODE
                        && y.getcode() <= LAST_MINISTRY_CODE)
                // convert back to list
                .collect(Collectors.toList());

        // Calculate total RegularBudget
        BigDecimal totalRegularBudget = ministries.stream()
                                .map(Ministry::getRegularBudget)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calculate total ependyseis
        BigDecimal totalEpendyseis = ministries.stream()
                                .map(Ministry::getEpendyseis)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calculate total synolo
        BigDecimal totalSynolo = ministries.stream()
                                .map(Ministry::getSynolo)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Comparators for each category
        // Compare BasicRecord types based on RegularBudget
        Comparator<Ministry> byRegularBudget =
                Comparator.comparing(Ministry::getRegularBudget);
        // Compare BasicRecord types based on Ependyseis
        Comparator<Ministry> byEpendyseis =
                Comparator.comparing(Ministry::getEpendyseis);
        // Compare BasicRecord types based on Synolo
        Comparator<Ministry> bySynolo =
                Comparator.comparing(Ministry::getSynolo);

        // Max / Min for each category/column
        List<Ministry> maxRegularBudget = ministries.stream()
                .sorted(byRegularBudget.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ministry> maxEpendyseis = ministries.stream()
                .sorted(byEpendyseis.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ministry> maxSynolo = ministries.stream()
                .sorted(bySynolo.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        // Calculate percentages
        List<BigDecimal> maxRegularBudgetPercentages = maxRegularBudget.stream()
                        .map(y -> percent(y.getRegularBudget(),
                        totalRegularBudget))
                        .collect(Collectors.toList());

        List<BigDecimal> maxEpendyseisPercentages = maxEpendyseis.stream()
                        .map(y -> percent(y.getEpendyseis(), totalEpendyseis))
                        .collect(Collectors.toList());

        List<BigDecimal> maxSynoloPercentages = maxSynolo.stream()
                        .map(y -> percent(y.getSynolo(), totalSynolo))
                        .collect(Collectors.toList());

        return new MinistryStats(
                maxRegularBudget,
                maxEpendyseis,
                maxSynolo,
                maxRegularBudgetPercentages,
                maxEpendyseisPercentages,
                maxSynoloPercentages);
    }
}
