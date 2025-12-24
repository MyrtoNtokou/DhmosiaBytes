package aggregatedata;

import budgetreader.Ypourgeio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Keep max and min for ministries.
 * Based on Taktikos, Ependyseis, Synolo.
 */
public final class MinistryStats {

    /** Max Taktikos list initialisation. */
    private final List<Ypourgeio> maxTaktikos;
    /** Max Ependyseis list initialisation. */
    private final List<Ypourgeio> maxEpendyseis;
    /** Max Synolo list initialisation. */
    private final List<Ypourgeio> maxSynolo;
    /** Max Taktikos percentages list initialisation. */
    private final List<BigDecimal> maxTaktikosPercentages;
    /** Max Ependyseis list initialisation. */
    private final List<BigDecimal> maxEpendyseisPercentages;
    /** Max Synolo list initialisation. */
    private final List<BigDecimal> maxSynoloPercentages;


    /** Constructor.
     * @param maxTakt
     * @param maxEpend
     * @param maxSyn
     * @param maxTaktikosPer
     * @param maxEpendyseisPer
     * @param maxSynoloPer
     * */
    public MinistryStats(
            final List<Ypourgeio> maxTakt,
            final List<Ypourgeio> maxEpend,
            final List<Ypourgeio> maxSyn,
            final List<BigDecimal> maxTaktikosPer,
            final List<BigDecimal> maxEpendyseisPer,
            final List<BigDecimal> maxSynoloPer) {

        maxTaktikos = new ArrayList<>(maxTakt);
        maxEpendyseis = new ArrayList<>(maxEpend);
        maxSynolo = new ArrayList<>(maxSyn);
        maxTaktikosPercentages = new ArrayList<>(maxTaktikosPer);
        maxEpendyseisPercentages = new ArrayList<>(maxEpendyseisPer);
        maxSynoloPercentages = new ArrayList<>(maxSynoloPer);
    }

    /**
     * Getter for max Taktikos.
     * @return max taktikos
     * */
    public List<Ypourgeio> getMaxTaktikos() {
        return new ArrayList<>(maxTaktikos);
    }

    /**
     * Getter for max Ependyseis.
     * @return max ependyseis
     * */
    public List<Ypourgeio> getMaxEpendyseis() {
        return new ArrayList<>(maxEpendyseis);
    }

    /**
     * Getter for max Synolo.
     * @return max synolo
     * */
    public List<Ypourgeio> getMaxSynolo() {
        return new ArrayList<>(maxSynolo);
    }

    /**
     * Getter for max taktikos percentages.
     * @return max taktikos percentages
     * */
    public List<BigDecimal> getMaxTaktikosPercentages() {
        return new ArrayList<>(maxTaktikosPercentages);
    }

    /**
     * Getter for max ependyseis percentages.
     * @return max ependyseis percentages
     * */
    public List<BigDecimal> getMaxEpendyseisPercentages() {
        return new ArrayList<>(maxEpendyseisPercentages);
    }

     /**
     * Getter for max synolo percentages.
     * @return max synolo percentages
     * */
    public List<BigDecimal> getMaxSynoloPercentages() {
        return new ArrayList<>(maxSynoloPercentages);
    }
}
