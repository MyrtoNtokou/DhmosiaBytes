package aggregatedata;

import budgetreader.Ministry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Keep max and min for ministries.
 * Based on RegularBudget, PublicInvestments, TotalBudget.
 */
public final class MinistryStats {

    /** Max RegularBudget list initialisation. */
    private final List<Ministry> maxRegularBudget;
    /** Max PublicInvestments list initialisation. */
    private final List<Ministry> maxPublicInvestments;
    /** Max TotalBudget list initialisation. */
    private final List<Ministry> maxTotalBudget;
    /** Max RegularBudget percentages list initialisation. */
    private final List<BigDecimal> maxRegularBudgetPercentages;
    /** Max PublicInvestments list initialisation. */
    private final List<BigDecimal> maxPublicInvestmentsPercentages;
    /** Max TotalBudget list initialisation. */
    private final List<BigDecimal> maxTotalBudgetPercentages;


    /** Constructor.
     * @param maxTakt
     * @param maxEpend
     * @param maxSyn
     * @param maxRegularBudgetPer
     * @param maxPublicInvestmentsPer
     * @param maxTotalBudgetPer
     * */
    public MinistryStats(
            final List<Ministry> maxTakt,
            final List<Ministry> maxEpend,
            final List<Ministry> maxSyn,
            final List<BigDecimal> maxRegularBudgetPer,
            final List<BigDecimal> maxPublicInvestmentsPer,
            final List<BigDecimal> maxTotalBudgetPer) {

        maxRegularBudget = new ArrayList<>(maxTakt);
        maxPublicInvestments = new ArrayList<>(maxEpend);
        maxTotalBudget = new ArrayList<>(maxSyn);
        maxRegularBudgetPercentages = new ArrayList<>(maxRegularBudgetPer);
        maxPublicInvestmentsPercentages = new ArrayList<>(
            maxPublicInvestmentsPer);
        maxTotalBudgetPercentages = new ArrayList<>(maxTotalBudgetPer);
    }

    /**
     * Getter for max RegularBudget.
     * @return max RegularBudget
     * */
    public List<Ministry> getMaxRegularBudget() {
        return new ArrayList<>(maxRegularBudget);
    }

    /**
     * Getter for max PublicInvestments.
     * @return max publicInvestments
     * */
    public List<Ministry> getMaxPublicInvestments() {
        return new ArrayList<>(maxPublicInvestments);
    }

    /**
     * Getter for max TotalBudget.
     * @return max totalBudget
     * */
    public List<Ministry> getMaxTotalBudget() {
        return new ArrayList<>(maxTotalBudget);
    }

    /**
     * Getter for max regularBudget percentages.
     * @return max regularBudget percentages
     * */
    public List<BigDecimal> getMaxRegularBudgetPercentages() {
        return new ArrayList<>(maxRegularBudgetPercentages);
    }

    /**
     * Getter for max publicInvestments percentages.
     * @return max publicInvestments percentages
     * */
    public List<BigDecimal> getMaxPublicInvestmentsPercentages() {
        return new ArrayList<>(maxPublicInvestmentsPercentages);
    }

     /**
     * Getter for max totalBudget percentages.
     * @return max totalBudget percentages
     * */
    public List<BigDecimal> getMaxTotalBudgetPercentages() {
        return new ArrayList<>(maxTotalBudgetPercentages);
    }
}
