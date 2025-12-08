package budgetlogic;

import java.math.BigDecimal;

/**
 * Simple record for general budget entries
 * such as revenues or expense categories.
 */
public final class BasicRecord {

    /** Entry code. */
    private final String kodikos;

    /** Entry description. */
    private final String perigrafi;

    /** Entry amount. */
    private BigDecimal poso;

    /**
     * Creates a basic record instance.
     *
     * @param code the entry code
     * @param description the entry description
     * @param amount the monetary amount
     */
    public BasicRecord(final String code,
                       final String description,
                       final BigDecimal amount) {
        this.kodikos = code;
        this.perigrafi = description;
        this.poso = amount;
    }

    /**
     * Gets the entry code.
     *
     * @return the entry code
     */
    public String getKodikos() {
        return kodikos;
    }

    /**
     * Gets the entry description.
     *
     * @return the entry description
     */
    public String getPerigrafi() {
        return perigrafi;
    }

    /**
     * Gets the entry amount.
     *
     * @return the amount
     */
    public BigDecimal getPoso() {
        return poso;
    }

    /**
     * Sets the entry amount.
     *
     * @param newAmount the new amount
     */
    public void setPoso(final BigDecimal newAmount) {
        this.poso = newAmount;
    }
}

