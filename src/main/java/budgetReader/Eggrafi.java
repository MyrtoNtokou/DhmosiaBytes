package budgetreader;

import java.text.DecimalFormat;

public class Eggrafi {
    private String kodikos;
    private String perigrafi;
    private double poso;

    public Eggrafi(String kodikos, String perigrafi, double poso) {
        this.kodikos = kodikos;
        this.perigrafi = perigrafi;
        this.poso = poso;
    }

    public String getKodikos() { 
        return kodikos; 
    }

    public String getPerigrafi() { 
        return perigrafi; 
    }
    public double getPoso() { 
        return poso; 
    }

    @Override
    public String toString() {
         DecimalFormat df = new DecimalFormat("#,###");

    return kodikos + " | " + perigrafi + " | " + df.format(poso);
    }
}
