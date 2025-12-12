package budgetlogic;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import budgetreader.*;

/**
 * Represents a complete budget containing revenues, expenses and ministries.
 */
public final class Budget {

    /** Map of revenues indexed by record code. */
    private final Map<String, Eggrafi> revenues;

    /** Map of expenses indexed by record code. */
    private final Map<String, Eggrafi> expenses;

    /** Map of ministries indexed by ministry code. */
    private final Map<Integer, Ypourgeio> ministries;

    /**
     * Creates an empty budget instance.
     */
    public Budget() {
        this.revenues = new LinkedHashMap<>();
        this.expenses = new LinkedHashMap<>();
        this.ministries = new LinkedHashMap<>();
    }

    /**
     * Creates a budget instance using the provided maps.
     *
     * @param revs the revenue records
     * @param exps the expense records
     * @param mins the ministry records
     */
    public Budget(final Map<String, Eggrafi> revs,
                  final Map<String, Eggrafi> exps,
                  final Map<Integer, Ypourgeio> mins) {
        this.revenues = new LinkedHashMap<>(revs);
        this.expenses = new LinkedHashMap<>(exps);
        this.ministries = new LinkedHashMap<>(mins);
    }

    /**
     * Copy constructor that performs a deep copy.
     *
     * @param other the budget instance to copy
     */
    public Budget(final Budget other) {
        this.revenues = new LinkedHashMap<>();
        this.expenses = new LinkedHashMap<>();
        this.ministries = new LinkedHashMap<>();

        // Copy revenues.
        for (Eggrafi r : other.revenues.values()) {
            this.revenues.put(
                    r.getKodikos(),
                    new Eggrafi(r.getKodikos(),
                    r.getPerigrafi(), r.getPoso()));
        }

        // Copy expenses.
        for (Eggrafi r : other.expenses.values()) {
            this.expenses.put(
                    r.getKodikos(),
                    new Eggrafi(r.getKodikos(),
                    r.getPerigrafi(), r.getPoso()));
        }

        // Copy ministries.
        for (Ypourgeio m : other.ministries.values()) {
            Ypourgeio mc = new Ypourgeio(
                    m.getKodikos(),
                    m.getOnoma(),
                    m.getTaktikos(),
                    m.getEpendyseis(),
                    m.getSynolo());

            for (Map.Entry<String, BigDecimal> e
                : m.getAllocation().entrySet()) {
                mc.setAllocationEntry(e.getKey(),
                e.getValue());
            }

            this.ministries.put(m.getKodikos(), mc);
        }
    }

    /**
     * Adds a revenue record.
     *
     * @param r the revenue record
     */
    public void addRevenue(final Eggrafi r) {
        revenues.put(r.getKodikos(), r);
    }

    /**
     * Adds an expense record.
     *
     * @param r the expense record
     */
    public void addExpense(final Eggrafi r) {
        expenses.put(r.getKodikos(), r);
    }

    /**
     * Adds a ministry.
     *
     * @param m the ministry to add
     */
    public void addMinistry(final Ypourgeio m) {
        ministries.put(m.getKodikos(), m);
    }

    /**
     * Gets all revenues.
     *
     * @return a copy of the revenue records
     */
    public Map<String, Eggrafi> getRevenues() {
        return new LinkedHashMap<>(revenues);
    }

    /**
     * Gets all expenses.
     *
     * @return a copy of the expense records
     */
    public Map<String, Eggrafi> getExpenses() {
        return new LinkedHashMap<>(expenses);
    }

    /**
     * Gets all ministries.
     *
     * @return a copy of the ministry records
     */
    public Map<Integer, Ypourgeio> getMinistries() {
        return new LinkedHashMap<>(ministries);
    }

    /**
     * Computes the total amount of revenues.
     *
     * @return the total revenues
     */
    public BigDecimal totalRevenues() {
        return revenues.values().stream()
                .map(Eggrafi::getPoso)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Computes the total amount of expenses.
     *
     * @return the total expenses
     */
    public BigDecimal totalExpenses() {
        return expenses.values().stream()
                .map(Eggrafi::getPoso)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

