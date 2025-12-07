package budgetlogic;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * simple ministry model
 */
public class Ministry {

    private final int kodikos;
    private final String onoma;
    private BigDecimal taktikos;
    private BigDecimal pde;
    private BigDecimal synolo;
    private final Map<String, BigDecimal> allocation;

    public Ministry(final int kodikos,
                    final String onoma,
                    final BigDecimal taktikos,
                    final BigDecimal pde,
                    final BigDecimal synolo) {
        this.kodikos = kodikos;
        this.onoma = onoma;
        this.taktikos = taktikos;
        this.pde = pde;
        this.synolo = synolo;
        this.allocation = new LinkedHashMap<>();
    }

    public int getKodikos() {
        return kodikos;
    }

    public String getOnoma() {
        return onoma;
    }

    public BigDecimal getTaktikos() {
        return taktikos;
    }

    public BigDecimal getPde() {
        return pde;
    }

    public BigDecimal getSynolo() {
        return synolo;
    }

    public void setTaktikos(final BigDecimal val) {
        this.taktikos = val;
        recalcSynolo();
    }

    public void setPde(final BigDecimal val) {
        this.pde = val;
        recalcSynolo();
    }

    public void setSynolo(final BigDecimal val) {
        this.synolo = val;
    }

    public Map<String, BigDecimal> getAllocation() {
        return new LinkedHashMap<>(allocation);
    }

    public void setAllocationEntry(final String cat, final BigDecimal percent) {
        allocation.put(cat, percent);
    }

    private void recalcSynolo() {
        if (taktikos == null) {
            taktikos = BigDecimal.ZERO;
        }
        if (pde == null) {
            pde = BigDecimal.ZERO;
        }
        this.synolo = taktikos.add(pde);
    }
}
