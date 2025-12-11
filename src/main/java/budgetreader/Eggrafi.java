package budgetreader;

import java.math.BigDecimal;
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
    private BigDecimal poso;

    /** constructor
     * that creates new Eggrafi instance.

    * @param kodikosValue the code value
     * @param perigrafiValue the description value
     * @param posoValue the amount value
     */
    public Eggrafi(final String kodikosValue, final String perigrafiValue,
    final BigDecimal posoValue) {
        this.kodikos = kodikosValue;
        this.perigrafi = perigrafiValue;
        this.poso = posoValue;
    }

    /** method that return kodikos.
    * @return kodikos.
    */
    public String getKodikos() {
        return kodikos;
    }

    /** method that return perigrafi.
    *@return perigrafi
    */
    public String getPerigrafi() {
        return perigrafi;
    }

    /** method that return poso.
     * @return poso
     */
    public BigDecimal getPoso() {
        return poso;
    }

    /** method that sets new value to Kodikos variable */
    public void setKodikos(final String kodikosNew) {
        kodikos = kodikosNew;
    }

    /** method that sets new value to perigrafi variable*/
    public void setPerigrafi(final String perigrafiNew) {
        perigrafi = perigrafiNew;
    }

    /**method that set new value to poso variable */
    public void setPoso(final BigDecimal posoNew) {
        poso = posoNew;
    }

    /** toString method.
    *
    * @return the formatted string representation
    */
    @Override
    public String toString() {
         DecimalFormat df = new DecimalFormat("#,###");

    return kodikos + " | " + perigrafi + " | " + df.format(poso);
    }
}
