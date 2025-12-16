package aggregatedata;

import budgetreader.Ypourgeio;

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

    private MinistryAnalyzer() {
        //Constructor
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

        List<Ypourgeio> minTaktikos = ministries.stream()
                .sorted(byTaktikos)
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ypourgeio> maxEpendyseis = ministries.stream()
                .sorted(byEpendyseis.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ypourgeio> minEpendyseis = ministries.stream()
                .sorted(byEpendyseis)
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ypourgeio> maxSynolo = ministries.stream()
                .sorted(bySynolo.reversed())
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        List<Ypourgeio> minSynolo = ministries.stream()
                .sorted(bySynolo)
                .limit(TOP_COUNT)
                .collect(Collectors.toList());

        return new MinistryStats(
                maxTaktikos, minTaktikos,
                maxEpendyseis, minEpendyseis,
                maxSynolo, minSynolo
        );
    }
}
