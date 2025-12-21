package aggregatedata;

import budgetreader.Ypourgeio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TOP_COUNT max min on ministries for each budget category.
 * Based on Taktikos, Ependyseis, Synolo.
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

    private MinistryAnalyzer() {
        //Constructor
    }

    private static BigDecimal percent(final BigDecimal value,
                                final BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
        }
        return value
                .divide(total, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(PERCENT));
    }

    /**
     * Execute max-min calculation for each category.
     * @param list list with ministries.
     * @return MinistryStats object
     * */
    public static MinistryStats analyze(final List<Ypourgeio> list) {

        List<Ypourgeio> ministries = list.stream()
                // codes between 5 and 24
                .filter(y -> y.getKodikos() >= FIRST_MINISTRY_CODE
                        && y.getKodikos() <= LAST_MINISTRY_CODE)
                // convert back to list
                .collect(Collectors.toList());

        // Calculate total taktikos
        BigDecimal totalTaktikos = ministries.stream()
                                .map(Ypourgeio::getTaktikos)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calculate total ependyseis
        BigDecimal totalEpendyseis = ministries.stream()
                                .map(Ypourgeio::getEpendyseis)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calculate total synolo
        BigDecimal totalSynolo = ministries.stream()
                                .map(Ypourgeio::getSynolo)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Comparators for each category
        // Compare Eggrafi types based on Taktikos
        Comparator<Ypourgeio> byTaktikos =
                Comparator.comparing(Ypourgeio::getTaktikos);
        // Compare Eggrafi types based on Ependyseis
        Comparator<Ypourgeio> byEpendyseis =
                Comparator.comparing(Ypourgeio::getEpendyseis);
        // Compare Eggrafi types based on Synolo
        Comparator<Ypourgeio> bySynolo =
                Comparator.comparing(Ypourgeio::getSynolo);

        // Max / Min for each category/column
        List<Ypourgeio> maxTaktikos = ministries.stream()
                .sorted(byTaktikos.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ypourgeio> maxEpendyseis = ministries.stream()
                .sorted(byEpendyseis.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ypourgeio> maxSynolo = ministries.stream()
                .sorted(bySynolo.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        // Calculate percentages
        List<BigDecimal> maxTaktikosPercentages = maxTaktikos.stream()
                        .map(y -> percent(y.getTaktikos(), totalTaktikos))
                        .collect(Collectors.toList());

        List<BigDecimal> maxEpendyseisPercentages = maxEpendyseis.stream()
                        .map(y -> percent(y.getEpendyseis(), totalEpendyseis))
                        .collect(Collectors.toList());

        List<BigDecimal> maxSynoloPercentages = maxSynolo.stream()
                        .map(y -> percent(y.getSynolo(), totalSynolo))
                        .collect(Collectors.toList());

        return new MinistryStats(
                maxTaktikos,
                maxEpendyseis,
                maxSynolo,
                maxTaktikosPercentages,
                maxEpendyseisPercentages,
                maxSynoloPercentages);
    }
}
