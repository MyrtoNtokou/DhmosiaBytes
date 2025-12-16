package aggregatedata;

import budgetreader.Ypourgeio;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep max and min for ministries.
 * Based on Taktikos, Ependyseis, Synolo.
 */
public final class MinistryStats {

    /** Max Taktikos list initialisation. */
    private final List<Ypourgeio> maxTaktikos;
    /** Min Taktikos list initialisation. */
    private final List<Ypourgeio> minTaktikos;
    /** Max Ependyseis list initialisation. */
    private final List<Ypourgeio> maxEpendyseis;
    /** Min Ependyseis list initialisation. */
    private final List<Ypourgeio> minEpendyseis;
    /** Max Synolo list initialisation. */
    private final List<Ypourgeio> maxSynolo;
    /** Min Synolo list initialisation. */
    private final List<Ypourgeio> minSynolo;

    /** Constructor.
     * @param maxTakt
     * @param minTakt
     * @param maxEpend
     * @param minEpend
     * @param maxSyn
     * @param minSyn
     * */
    public MinistryStats(
            final List<Ypourgeio> maxTakt,
            final List<Ypourgeio> minTakt,
            final List<Ypourgeio> maxEpend,
            final List<Ypourgeio> minEpend,
            final List<Ypourgeio> maxSyn,
            final List<Ypourgeio> minSyn) {

        this.maxTaktikos = new ArrayList<>(maxTakt);
        this.minTaktikos = new ArrayList<>(minTakt);
        this.maxEpendyseis = new ArrayList<>(maxEpend);
        this.minEpendyseis = new ArrayList<>(minEpend);
        this.maxSynolo = new ArrayList<>(maxSyn);
        this.minSynolo = new ArrayList<>(minSyn);
    }

    /**
     * Getter for max Taktikos.
     * @return max taktikos
     * */
    public List<Ypourgeio> getMaxTaktikos() {
        return new ArrayList<>(maxTaktikos);
    }

    /**
     * Getter for min Taktikos.
     * @return min taktikos
     * */
    public List<Ypourgeio> getMinTaktikos() {
        return new ArrayList<>(minTaktikos);
    }

    /**
     * Getter for max Ependyseis.
     * @return max ependyseis
     * */
    public List<Ypourgeio> getMaxEpendyseis() {
        return new ArrayList<>(maxEpendyseis);
    }

    /**
     * Getter for min Ependyseis.
     * @return max ependyseis
     * */
    public List<Ypourgeio> getMinEpendyseis() {
        return new ArrayList<>(minEpendyseis);
    }

    /**
     * Getter for max Synolo.
     * @return max synolo
     * */
    public List<Ypourgeio> getMaxSynolo() {
        return new ArrayList<>(maxSynolo);
    }

    /**
     * Getter for min Synolo.
     * @return max synolo
     * */
    public List<Ypourgeio> getMinSynolo() {
        return new ArrayList<>(minSynolo);
    }
}
