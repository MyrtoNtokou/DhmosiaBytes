package aggregatedata;

import budgetreader.Eggrafi;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * Keep max and min revenues and expenses.
 */
public class BudgetStats {

    /** Max revenues list initialisation. */
    private List<Eggrafi> maxRevenues;
    /** Min revenues list initialisation. */
    private List<Eggrafi> minRevenues;
    /** Max expenses list initialisation. */
    private List<Eggrafi> maxExpenses;
    /** Min expenses list initialisation. */
    private List<Eggrafi> minExpenses;
    /** Max revenues percentages list initialisation. */
    private final List<BigDecimal> maxRevenuePercentages;
    /** Max expenses percentages list initialisation. */
    private final List<BigDecimal> maxExpensePercentages;

    /** Constructor.
     * @param maxRev
     * @param minRev
     * @param maxExp
     * @param minExp
     * @param maxRevPer
     * @param maxExpPer
     * */
    public BudgetStats(final List<Eggrafi> maxRev,
                       final List<Eggrafi> minRev,
                       final List<Eggrafi> maxExp,
                       final List<Eggrafi> minExp,
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
    public List<Eggrafi> getMaxRevenues() {
        return new ArrayList<>(maxRevenues);
    }

    /**
     * Getter for min revenues.
     * @return min revenues
     * */
    public List<Eggrafi> getMinRevenues() {
        return new ArrayList<>(minRevenues);
    }

    /**
     * Getter for max expenses.
     * @return max expenses
     * */
    public List<Eggrafi> getMaxExpenses() {
        return new ArrayList<>(maxExpenses);
    }

    /**
     * Getter for min expenses.
     * @return min expenses
     * */
    public List<Eggrafi> getMinExpenses() {
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
