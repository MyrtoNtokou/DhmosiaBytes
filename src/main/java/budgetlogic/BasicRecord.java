package budgetlogic;

import java.math.BigDecimal;

/**
 * basic model for general budget componets
 */
public class BasicRecord {

    private final String kodikos;
    private final String perigrafi;
    private BigDecimal poso;

    public BasicRecord(final String kodikos,
                       final String perigrafi,
                       final BigDecimal poso) {
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

    public BigDecimal getPoso() {
        return poso;
    }

    public void setPoso(final BigDecimal poso) {
        this.poso = poso;
    }
}
