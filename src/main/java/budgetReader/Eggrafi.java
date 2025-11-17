
package budgetreader;

import java.text.DecimalFormat;

public final class  Eggrafi {
    /* kodikos of Eggrafi instance */
    private String kodikos;
    /*perigrafi  of Eggrafi instance */
    private String perigrafi;
    /*poso  of Eggrafi instance */
    private double poso;

    public Eggrafi(final String kodikosValue, final String perigrafiValue, final double posoValue) {
        this.kodikos = kodikosValue;
        this.perigrafi = perigrafiValue;
        this.poso = posoValue;
    }

    /* method that returns kodikos */
    public String getKodikos() { 
        return kodikos; 
    }

    /* method that returns perigrafi */
    public String getPerigrafi() { 
        return perigrafi; 
    }

    /* method that returns poso */
    public double getPoso() { 
        return poso; 
    }

    /* toString method */
    @Override
    public String toString() {
         DecimalFormat df = new DecimalFormat("#,###");

    return kodikos + " | " + perigrafi + " | " + df.format(poso);
    }
}

