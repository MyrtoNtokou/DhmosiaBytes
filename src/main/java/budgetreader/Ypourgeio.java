package budgetreader;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

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
        this.allocation = new LinkedHashMap<>();
    }

    /** Allocation categories mapped to their percentage. */
    private final Map<String, BigDecimal> allocation;

    /**
     * Gets a copy of the allocation map.
     *
     * @return a copy of the allocation map
     */
    public Map<String, BigDecimal> getAllocation() {
        return new LinkedHashMap<>(allocation);
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
        recalcSynolo();
    }
    
    /** method that sets new value to kodikos variable */
    public void setEpendyseis(final BigDecimal ependyseisNew) {
        ependyseis = ependyseisNew;
        recalcSynolo();
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

    /**
     * Sets or updates an allocation percentage for a category.
     *
     * @param cat the allocation category
     * @param percent the percentage assigned to the category
     */
    public void setAllocationEntry(final String cat, final BigDecimal percent) {
        allocation.put(cat, percent);
    }

        /**
     * Recalculates the total budget based on taktikos and pde.
     */
    private void recalcSynolo() {
        if (taktikos == null) {
            taktikos = BigDecimal.ZERO;
        }
        if (ependyseis == null) {
            ependyseis = BigDecimal.ZERO;
        }
        this.synolo = taktikos.add(ependyseis);
    }

}
