package budgetreader;

import java.math.BigDecimal;

/**
 * Represents a Ministry entry containing its code, name, and associated
 * budget values (regular budget, public investment budget, and total budget).
 */
public class Ypourgeio {

    /** Ministry code. */
    private int kodikos;

    /** Ministry name. */
    private String onoma;

    /** Regular budget amount (taktikos). */
    private BigDecimal taktikos;

    /** Public investment budget amount. */
    private BigDecimal ependyseis;

    /** Total ministry budget amount. */
    private BigDecimal synolo;

    /**
     * Constructs a new {@code Ypourgeio} instance with the given attributes.
     *
     * @param kodikosValue is the kodikos of Ministry
     * @param onomaValue is the name of the Ministry
     * @param taktikosValue is the amount of Ministry's General Budget
     * @param ependyseisValue is the amount of Ministry's investments
     * @param synoloValue is the total amount of Ministry's Budget
     */
    public Ypourgeio(final int kodikosValue, final String onomaValue,
                     final BigDecimal taktikosValue, final BigDecimal ependyseisValue,
                     final BigDecimal synoloValue) {

        kodikos = kodikosValue;
        onoma = onomaValue;
        taktikos = taktikosValue;
        ependyseis = ependyseisValue;
        synolo = synoloValue;
    }

    /**
     * Returns the ministry code.
     *
     * @return kodikos
     */
    public int getKodikos() {
        return kodikos;
    }

    /**
     * Returns the ministry name.
     *
     * @return onoma
     */
    public String getOnoma() {
        return onoma;
    }

    /**
     * Returns the regular budget amount.
     *
     * @return taktikos (General Ministry's Budget)
     */
    public BigDecimal getTaktikos() {
        return taktikos;
    }

    /**
     * Returns the public investment budget amount.
     *
     * @return investments budget
     */
    public BigDecimal getEpendyseis() {
        return ependyseis;
    }

    /**
     * Returns the total ministry budget.
     *
     * @return total budget
     */
    public BigDecimal getSynolo() {
        return synolo;
    }

    /** method that sets new value to kodikos variable */
    public void setKodikos(final int kodikosNew) {
        kodikos = kodikosNew;
    }

    /** method that sets new value to onoma variable */
    public void setOnoma(final String onomaNew) {
        onoma = onomaNew;
    }

    /** method that sets new value to taktikos variable */
    public void setTaktikos(final BigDecimal taktikosNew) {
        taktikos = taktikosNew;
    }
    
    /** method that sets new value to kodikos variable */
    public void setEpendysewn(final BigDecimal ependyseisNew) {
        ependyseis = ependyseisNew;
    }

    /** method that sets new value to kodikos variable */
    public void setSynolo(final BigDecimal synoloNew) {
        synolo = synoloNew;
    }

    /**
     * Returns a formatted string representation of this ministry entry.
     *
     * @return the formatted string representation
     */
    @Override
    public String toString() {
        return kodikos + " | " + onoma
               + " | " + taktikos
               + " | " + ependyseis
               + " | " + synolo;
    }
}
