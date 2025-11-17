package budgetreader;

public class Ypourgeio {
    /* Ministry code */
    private int kodikos;

    /* Ministry name */
    private String onoma;

    /* Regular budget amount */
    private double taktikos;

    /* Public investment amount */
    private double ependyseis;

    /* Total budget amount */
    private double synolo;

    public Ypourgeio(int kodikos, String onoma, double taktikos, double ependyseis, double synolo) {
        this.kodikos = kodikos;
        this.onoma = onoma;
        this.taktikos = taktikos;
        this.ependyseis = ependyseis;
        this.synolo = synolo;
    }

    public int getKodikos() {
        return kodikos;
    }

    public String getOnoma() { 
        return onoma; 
    }

    public double getTaktikos() { 
        return taktikos; 
    }

    public double getEpendysewn() { 
        return ependyseis;
    }

    public double getSynolo() { 
        return synolo; 
    }

    @Override
    public String toString() {
        return kodikos + " | " + onoma +
               " | Regular Budget: " + taktikos +
               " | Public Investment Budget: " + ependyseis +
               " | Total: " + synolo;
    }
}
