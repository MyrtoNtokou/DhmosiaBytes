package budgetcomparison;

import java.math.BigDecimal;

/**
 * Represents the result of a budget comparison between two years
 * for a specific budget code.
 */
public class ComparisonResult {

    /** Budget code identifier. */
    private final String kodikos;

    /** Description of the budget item. */
    private final String perigrafi;

    /** The base year used for comparison. */
    private final int baseYear;


    /** The comparison year used for comparison. */
    private final int compareYear;

    /** The percentage change between the two years. */
    private final BigDecimal percentageChange;


    /**
     * Constructs a ComparisonResult instance.
     *
     * @param kodikosNew the budget code
     * @param perigrafiNew the description of the budget item
     * @param baseYearNew the base year
     * @param compareYearNew the comparison year
     * @param percentageChangeNew the percentage change between the two years
     */
    public ComparisonResult(
            final String kodikosNew,
            final String perigrafiNew,
            final int baseYearNew,
            final int compareYearNew,
            final BigDecimal percentageChangeNew) {

        this.kodikos = kodikosNew;
        this.perigrafi = perigrafiNew;
        this.baseYear = baseYearNew;
        this.compareYear = compareYearNew;
        this.percentageChange = percentageChangeNew;
    }

    /**
    * Returns the budget code.
    *
    * @return the budget code
    */
    public String getKodikos() {
        return kodikos;
    }

    /**
    * Returns the description of the budget item.
    *
    * @return the description
    */
    public String getPerigrafi() {
        return perigrafi;
    }


    /**
     * Returns the base year.
     *
     * @return the base year
     */
    public int getBaseYear() {
        return baseYear;
    }

    /**
     * Returns the comparison year.
     *
     * @return the comparison year
     */
    public int getCompareYear() {
        return compareYear;
    }


    /**
     * Returns the percentage change between the two years.
     *
     * @return the percentage change
     */
    public BigDecimal getPercentageChange() {
        return percentageChange;
    }
}
