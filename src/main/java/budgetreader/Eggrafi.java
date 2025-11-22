
package budgetreader;

import java.text.DecimalFormat;

/** Represents a single budget record (Eggrafi).
 * Each record contains a code, a description and an amount.
 * This class is immutable except for the provided getter methods.
 * It also provides a formatted string representation of the entry.*/
public final class  Eggrafi {
    /** kodikos of
     * Eggrafi instance.*/
    private String kodikos;

    /**perigrafi  of
     * Eggrafi instance. */
    private String perigrafi;

    /**poso  of
     * Eggrafi instance. */
    private double poso;

    /** constructor
     * that creates new Eggrafi instance.
     
    * @param kodikosValue the code value
     * @param perigrafiValue the description value
     * @param posoValue the amount value 
     */
    public Eggrafi(final String kodikosValue, final String perigrafiValue, 
    final double posoValue) {
        this.kodikos = kodikosValue;
        this.perigrafi = perigrafiValue;
        this.poso = posoValue;
    }

    /** method that
    * returns kodikos. */
    public String getKodikos() {
        return kodikos;
    }

    /** method that
    *returns perigrafi. */
    public String getPerigrafi() { 
        return perigrafi;
    }

    /** method that
     *returns poso. */
    public double getPoso() {
        return poso;
    }

    /* toString method
     * for a specific format.
    */
    @Override
    public String toString() {
         DecimalFormat df = new DecimalFormat("#,###");

    return kodikos + " | " + perigrafi + " | " + df.format(poso);
    }
}
