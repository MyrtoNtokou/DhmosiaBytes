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
     * @param kodikos      the ministry code
     * @param onoma        the ministry name
     * @param taktikos     the regular budget amount
     * @param ependyseis   the public investment budget amount
     * @param synolo       the total budget amount
     */
    public Ypourgeio(final int kodikos, final String onoma,
                     final double taktikos, final double ependyseis,
                     final double synolo) {

        this.kodikos = kodikos;
        this.onoma = onoma;
        this.taktikos = taktikos;
        this.ependyseis = ependyseis;
        this.synolo = synolo;
    }

    /**
     * Returns the ministry code.
     */
    public int getKodikos() {
        return kodikos;
    }

    /**
     * Returns the ministry name.
     */
    public String getOnoma() { 
        return onoma; 
    }

    /**
     * Returns the regular budget amount.
     */
    public double getTaktikos() { 
        return taktikos; 
    }

    /**
     * Returns the public investment budget amount.
     */
    public double getEpendysewn() { 
        return ependyseis;
    }

    /**
     * Returns the total ministry budget.
     */
    public double getSynolo() { 
        return synolo; 
    }

    /**
     * Returns a formatted string representation of this ministry entry.
     */
    @Override
    public String toString() {
        return kodikos + " | " + onoma
               + " | Regular Budget: " + taktikos
               + " | Public Investment Budget: " + ependyseis
               + " | Total: " + synolo;
    }
}