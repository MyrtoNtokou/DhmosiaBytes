package budgetreader;

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
    private double taktikos;

    /** Public investment budget amount. */
    private double ependyseis;

    /** Total ministry budget amount. */
    private double synolo;

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
                     final double taktikosValue, final double ependyseisValue,
                     final double synoloValue) {

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
    public double getTaktikos() {
        return taktikos;
    }

    /**
     * Returns the public investment budget amount.
     *
     * @return investments budget
     */
    public double getEpendysewn() {
        return ependyseis;
    }

    /**
     * Returns the total ministry budget.
     *
     * @return total budget
     */
    public double getSynolo() {
        return synolo;
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
