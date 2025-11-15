package gr.ilias;

public class Ypourgeio {
     private int kodikos;
    private String onoma;
    private double taktikos;
    private double ependyseis;
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
