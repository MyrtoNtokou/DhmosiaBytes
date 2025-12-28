package aggregatedata;

import budgetreader.Ypourgeio;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Calculate invenstment/total for each ministry and percentage.
 */
public final class InvestmentAnalyzer {

    /** Code of the first ministry in the list. */
    private static final int FIRST_MINISTRY_CODE = 5;
    /** Code of the last ministry in the list. */
    private static final int LAST_MINISTRY_CODE = 24;
    /** Percent. */
    private static final int PERCENT = 100;

    private InvestmentAnalyzer() {
        //Constructor
    }

    /**
     * Calculate raw ratio and percentage for ministries.
     * @param ypourg list with ministries
     * @return list with ministry, ratio and percentage
     */
    public static List<InvestmentRatio>
            calculate(final List<Ypourgeio> ypourg) {

        List<InvestmentRatio> results = new ArrayList<>();

        for (Ypourgeio y : ypourg) {
            // Keep ministries only
            if (y.getKodikos() < FIRST_MINISTRY_CODE
            || y.getKodikos() > LAST_MINISTRY_CODE) {
                continue;
            }

            BigDecimal synolo = y.getSynolo();
            BigDecimal ependyseis = y.getEpendyseis();

            // Calculate ratio=investment/total for each ministry
            BigDecimal ratio = BigDecimal.ZERO;
            if (synolo.compareTo(BigDecimal.ZERO) > 0) {
                ratio = ependyseis.divide(synolo, 2, RoundingMode.HALF_UP);
            }

            // Calculate percentage
            BigDecimal percentage = ratio.multiply(BigDecimal.valueOf(PERCENT));

            results.add(new InvestmentRatio(
                    y.getOnoma(),
                    ratio,
                    percentage
            ));
        }

        // Sort from largest to smallest
        results.sort(Comparator.comparing(InvestmentRatio::getRatio)
                                .reversed());

        return results;
    }
}
