
package budgetreader;

import java.text.DecimalFormat;

public final class  Eggrafi {
    /*Κωδικός εγγραφής */
    private String kodikos;
    /*Περιγραφή εγγραφής */
    private String perigrafi;
    /*Ποσό εγγραφής */
    private double poso;

    public Eggrafi(final String kodikosValue, final String perigrafiValue, final double posoValue) {
        this.kodikos = kodikosValue;
        this.perigrafi = perigrafiValue;
        this.poso = posoValue;
    }

    /* Μέθοδος επιστροφής Κωδικού */
    public String getKodikos() { 
        return kodikos; 
    }

    /* Μέθοδος επσιτροφής περιγραφής */
    public String getPerigrafi() { 
        return perigrafi; 
    }

    /* Μέθοδος επιστορφής ποσού */
    public double getPoso() { 
        return poso; 
    }

    /* Μέθοδος που επιστρέφει κωδικό | περιγραφή | ποσό */
    @Override
    public String toString() {
         DecimalFormat df = new DecimalFormat("#,###");

    return kodikos + " | " + perigrafi + " | " + df.format(poso);
    }
}

