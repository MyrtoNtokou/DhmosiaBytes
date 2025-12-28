package aggregatedata;

import java.math.BigDecimal;

/**
 * Keep ministry, ratio and percentage.
 */
public class InvestmentRatio {

    /** Ministry. */
    private final String onoma;
    /** Raw ratio=invenstment/total. */
    private final BigDecimal ratio;
    /** Ratio in percentage. */
    private final BigDecimal percentage;

    /**
     * Constructor.
     * @param on
     * @param rat
     * @param per
     */
    public InvestmentRatio(final String on,
                        final BigDecimal rat,
                        final BigDecimal per) {
        onoma = on;
        ratio = rat;
        percentage = per;
    }

    /**
     * Getter for ministry name.
     * @return ministry
     */
    public String getOnoma() {
        return onoma;
    }

    /**
     * Getter for ratio.
     * @return ratio
     */
    public BigDecimal getRatio() {
        return ratio;
    }

    /**
     * Getter for percentage.
     * @return percentage
     */
    public BigDecimal getPercentage() {
        return percentage;
    }
}
