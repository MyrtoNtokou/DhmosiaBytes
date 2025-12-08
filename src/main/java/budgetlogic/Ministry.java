
package budgetlogic;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a ministry with its budget values and allocation breakdown.
 */
public final class Ministry {

    /** Ministry code. */
    private final int kodikos;

    /** Ministry name. */
    private final String onoma;

    /** Regular budget (taktikos). */
    private BigDecimal taktikos;

    /** Public investment budget (pde). */
    private BigDecimal pde;

    /** Total budget (taktikos + pde). */
    private BigDecimal synolo;

    /** Allocation categories mapped to their percentage. */
    private final Map<String, BigDecimal> allocation;

    /**
     * Creates a new ministry instance.
     *
     * @param kodikos1 the ministry code
     * @param onoma1 the ministry name
     * @param taktikos1 the regular budget
     * @param pde1 the public investment budget
     * @param synolo1 the initial total budget
     */
    public Ministry(final int kodikos1,
                    final String onoma1,
                    final BigDecimal taktikos1,
                    final BigDecimal pde1,
                    final BigDecimal synolo1) {
        this.kodikos = kodikos1;
        this.onoma = onoma1;
        this.taktikos = taktikos1;
        this.pde = pde1;
        this.synolo = synolo1;
        this.allocation = new LinkedHashMap<>();
    }

    /**
     * Gets the ministry code.
     *
     * @return the ministry code
     */
    public int getKodikos() {
        return kodikos;
    }

    /**
     * Gets the ministry name.
     *
     * @return the name of the ministry
     */
    public String getOnoma() {
        return onoma;
    }

    /**
     * Gets the regular budget (taktikos).
     *
     * @return the regular budget
     */
    public BigDecimal getTaktikos() {
        return taktikos;
    }

    /**
     * Gets the public investment budget (pde).
     *
     * @return the public investment budget
     */
    public BigDecimal getPde() {
        return pde;
    }

    /**
     * Gets the total budget.
     *
     * @return the total budget
     */
    public BigDecimal getSynolo() {
        return synolo;
    }

    /**
     * Sets the regular budget and updates the total.
     *
     * @param val the new regular budget
     */
    public void setTaktikos(final BigDecimal val) {
        this.taktikos = val;
        recalcSynolo();
    }

    /**
     * Sets the public investment budget and updates the total.
     *
     * @param val the new public investment budget
     */
    public void setPde(final BigDecimal val) {
        this.pde = val;
        recalcSynolo();
    }

    /**
     * Sets the total budget explicitly.
     *
     * @param val the new total budget
     */
    public void setSynolo(final BigDecimal val) {
        this.synolo = val;
    }

    /**
     * Gets a copy of the allocation map.
     *
     * @return a copy of the allocation map
     */
    public Map<String, BigDecimal> getAllocation() {
        return new LinkedHashMap<>(allocation);
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
        if (pde == null) {
            pde = BigDecimal.ZERO;
        }
        this.synolo = taktikos.add(pde);
    }
}
