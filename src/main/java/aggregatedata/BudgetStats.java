package aggregatedata;

import budgetreader.Eggrafi;

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

    /** Constructor.
     * @param maxRev
     * @param minRev
     * @param maxExp
     * @param minExp
     * */
    public BudgetStats(final List<Eggrafi> maxRev,
                       final List<Eggrafi> minRev,
                       final List<Eggrafi> maxExp,
                       final List<Eggrafi> minExp) {
        maxRevenues = new ArrayList<>(maxRev);
        minRevenues = new ArrayList<>(minRev);
        maxExpenses = new ArrayList<>(maxExp);
        minExpenses = new ArrayList<>(minExp);
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
}
