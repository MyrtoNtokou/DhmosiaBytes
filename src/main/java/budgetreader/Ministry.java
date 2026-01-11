package budgetreader;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a Ministry entry containing its code, name, and associated
 * budget values (regular budget, public investment budget, and total budget).
 */
public class Ministry {

    /** Ministry code. */
    private int code;

    /** Ministry name. */
    private String name;

    /** Regular budget amount (regularBudget). */
    private BigDecimal regularBudget;

    /** Public investment budget amount. */
    private BigDecimal ependyseis;

    /** Total ministry budget amount. */
    private BigDecimal synolo;

    /**
     * Constructs a new {@code Ministry} instance with the given attributes.
     *
     * @param codeValue is the code of Ministry
     * @param nameValue is the name of the Ministry
     * @param regularBudgetValue is the amount of Ministry's General Budget
     * @param ependyseisValue is the amount of Ministry's investments
     * @param synoloValue is the total amount of Ministry's Budget
     */
    public Ministry(final int codeValue,
                    final String nameValue,
                    final BigDecimal regularBudgetValue,
                    final BigDecimal ependyseisValue,
                    final BigDecimal synoloValue) {

        code = codeValue;
        name = nameValue;
        regularBudget = regularBudgetValue;
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
     * @return code
     */
    public int getcode() {
        return code;
    }

    /**
     * Returns the ministry name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the regular budget amount.
     *
     * @return regularBudget (General Ministry's Budget)
     */
    public BigDecimal getRegularBudget() {
        return regularBudget;
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

    /** method that sets new value to code variable.
     * @param codeNew to set.
     */
    public void setcode(final int codeNew) {
        code = codeNew;
    }

    /** method that sets new value to name variable.
     * @param nameNew to set.
    */
    public void setName(final String nameNew) {
        name = nameNew;
    }

    /** method that sets new value to regularBudget variable.
     * @param regularBudgetNew to set.
    */
    public void setRegularBudget(final BigDecimal regularBudgetNew) {
        regularBudget = regularBudgetNew;
        recalcSynolo();
    }

    /** method that sets new value to ependyseis variable.
     * @param ependyseisNew to set.
     */
    public void setEpendyseis(final BigDecimal ependyseisNew) {
        ependyseis = ependyseisNew;
        recalcSynolo();
    }

    /** method that sets new value to synolo variable.
     * @param synoloNew to set.
     */
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
        return code + " | " + name
               + " | " + regularBudget
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
     * Recalculates the total budget based on regularBudget and pde.
     */
    private void recalcSynolo() {
        if (regularBudget == null) {
            regularBudget = BigDecimal.ZERO;
        }
        if (ependyseis == null) {
            ependyseis = BigDecimal.ZERO;
        }
        this.synolo = regularBudget.add(ependyseis);
    }

}
