package budgetcomparison;

import java.math.BigDecimal;

public class ComparisonResult {
    
    private final String kodikos;
    private final String perigrafi;
    private final int baseYear;
    private final int compareYear;
    private final BigDecimal percentageChange;

    public ComparisonResult(
            String kodikos,
            String perigrafi,
            int baseYear,
            int compareYear,
            BigDecimal percentageChange) {

        this.kodikos = kodikos;
        this.perigrafi = perigrafi;
        this.baseYear = baseYear;
        this.compareYear = compareYear;
        this.percentageChange = percentageChange;
    }

    public String getKodikos() { 
        return kodikos; 
    }
    public String getPerigrafi() {
        return perigrafi; 
    }

    public int getBaseYear() {
        return baseYear; 
    }

    public int getCompareYear() {
        return compareYear; 
    }

    public BigDecimal getPercentageChange() {
        return percentageChange;
    }
}
