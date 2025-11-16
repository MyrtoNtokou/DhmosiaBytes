package budgetreader;


public class Ypourgeio {
    /* Κωδικός Υπουργείου */
    private int kodikos;

    /* Όνομα Υπουργείου */
    private String onoma;

    /* Ποσό Τακτικού Προϋπολογισμού */
    private double taktikos;

    /* Ποσό επενδύσεων */
    private double ependyseis;

    /*συνολικό ποσό */
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
               " | Τακτικός Προϋπολογισμός: " + taktikos + 
               " | Προϋπολογισμός Δημοσίων Επενδύσεων: " + ependyseis + 
               " | Σύνολο: " + synolo;
    }
}
