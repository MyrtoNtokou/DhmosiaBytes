package aggregatedata;

import budgetreader.BasicRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * Keep max and min revenues and expenses.
 */
public class BudgetStats {

    /** Max revenues list initialisation. */
    private List<BasicRecord> maxRevenues;
    /** Min revenues list initialisation. */
    private List<BasicRecord> minRevenues;
    /** Max expenses list initialisation. */
    private List<BasicRecord> maxExpenses;
    /** Min expenses list initialisation. */
    private List<BasicRecord> minExpenses;
    /** Max revenues percentages list initialisation. */
    private final List<BigDecimal> maxRevenuePercentages;
    /** Max expenses percentages list initialisation. */
    private final List<BigDecimal> maxExpensePercentages;

    /** Constructor.
     * @param maxRev List of records with the maximum revenue values.
     * @param minRev List of records with the minimum revenue values.
     * @param maxExp List of records with the maximum expense values.
     * @param minExp List of records with the minimum expense values.
     * @param maxRevPer List of percentages corresponding
     * to the maximum revenues.
     * @param maxExpPer List of percentages corresponding
     * to the maximum expenses.
     */
    public BudgetStats(final List<BasicRecord> maxRev,
                       final List<BasicRecord> minRev,
                       final List<BasicRecord> maxExp,
                       final List<BasicRecord> minExp,
                       final List<BigDecimal> maxRevPer,
                       final List<BigDecimal> maxExpPer) {
        maxRevenues = new ArrayList<>(maxRev);
        minRevenues = new ArrayList<>(minRev);
        maxExpenses = new ArrayList<>(maxExp);
        minExpenses = new ArrayList<>(minExp);
        maxRevenuePercentages = new ArrayList<>(maxRevPer);
        maxExpensePercentages = new ArrayList<>(maxExpPer);
    }

    /**
     * Getter for max revenues.
     * @return max revenues
     * */
    public List<BasicRecord> getMaxRevenues() {
        return new ArrayList<>(maxRevenues);
    }

    /**
     * Getter for min revenues.
     * @return min revenues
     * */
    public List<BasicRecord> getMinRevenues() {
        return new ArrayList<>(minRevenues);
    }

    /**
     * Getter for max expenses.
     * @return max expenses
     * */
    public List<BasicRecord> getMaxExpenses() {
        return new ArrayList<>(maxExpenses);
    }

    /**
     * Getter for min expenses.
     * @return min expenses
     * */
    public List<BasicRecord> getMinExpenses() {
        return new ArrayList<>(minExpenses);
    }

    /**
     * Getter for max revenues percentages.
     * @return max revenues percentages
     * */
    public List<BigDecimal> getMaxRevenuePercentages() {
        return new ArrayList<>(maxRevenuePercentages);
    }

    /**
     * Getter for max expenses percentages.
     * @return max expenses percentages
     * */
    public List<BigDecimal> getMaxExpensePercentages() {
        return new ArrayList<>(maxExpensePercentages);
    }
}
