package aggregatedata;

import budgetreader.Ministry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Keep max and min for ministries.
 * Based on RegularBudget, Ependyseis, Synolo.
 */
public final class MinistryStats {

    /** Max RegularBudget list initialisation. */
    private final List<Ministry> maxRegularBudget;
    /** Max Ependyseis list initialisation. */
    private final List<Ministry> maxEpendyseis;
    /** Max Synolo list initialisation. */
    private final List<Ministry> maxSynolo;
    /** Max RegularBudget percentages list initialisation. */
    private final List<BigDecimal> maxRegularBudgetPercentages;
    /** Max Ependyseis list initialisation. */
    private final List<BigDecimal> maxEpendyseisPercentages;
    /** Max Synolo list initialisation. */
    private final List<BigDecimal> maxSynoloPercentages;


    /** Constructor.
     * @param maxTakt
     * @param maxEpend
     * @param maxSyn
     * @param maxRegularBudgetPer
     * @param maxEpendyseisPer
     * @param maxSynoloPer
     * */
    public MinistryStats(
            final List<Ministry> maxTakt,
            final List<Ministry> maxEpend,
            final List<Ministry> maxSyn,
            final List<BigDecimal> maxRegularBudgetPer,
            final List<BigDecimal> maxEpendyseisPer,
            final List<BigDecimal> maxSynoloPer) {

        maxRegularBudget = new ArrayList<>(maxTakt);
        maxEpendyseis = new ArrayList<>(maxEpend);
        maxSynolo = new ArrayList<>(maxSyn);
        maxRegularBudgetPercentages = new ArrayList<>(maxRegularBudgetPer);
        maxEpendyseisPercentages = new ArrayList<>(maxEpendyseisPer);
        maxSynoloPercentages = new ArrayList<>(maxSynoloPer);
    }

    /**
     * Getter for max RegularBudget.
     * @return max RegularBudget
     * */
    public List<Ministry> getMaxRegularBudget() {
        return new ArrayList<>(maxRegularBudget);
    }

    /**
     * Getter for max Ependyseis.
     * @return max ependyseis
     * */
    public List<Ministry> getMaxEpendyseis() {
        return new ArrayList<>(maxEpendyseis);
    }

    /**
     * Getter for max Synolo.
     * @return max synolo
     * */
    public List<Ministry> getMaxSynolo() {
        return new ArrayList<>(maxSynolo);
    }

    /**
     * Getter for max regularBudget percentages.
     * @return max regularBudget percentages
     * */
    public List<BigDecimal> getMaxRegularBudgetPercentages() {
        return new ArrayList<>(maxRegularBudgetPercentages);
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
