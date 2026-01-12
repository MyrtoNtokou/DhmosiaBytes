package budgetlogic;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import budgetreader.BasicRecord;
import budgetreader.Ministry;


/**
 * Represents a complete budget containing revenues, expenses and ministries.
 */
public final class Budget {

    /** Map of revenues indexed by record code. */
    private final Map<String, BasicRecord> revenues;

    /** Map of expenses indexed by record code. */
    private final Map<String, BasicRecord> expenses;

    /** Map of ministries indexed by ministry code. */
    private final Map<Integer, Ministry> ministries;

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
    public Budget(final Map<String, BasicRecord> revs,
                  final Map<String, BasicRecord> exps,
                  final Map<Integer, Ministry> mins) {
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
        for (BasicRecord r : other.revenues.values()) {
            this.revenues.put(
                    r.getCode(),
                    new BasicRecord(r.getCode(),
                    r.getDescription(), r.getAmount()));
        }

        // Copy expenses.
        for (BasicRecord r : other.expenses.values()) {
            this.expenses.put(
                    r.getCode(),
                    new BasicRecord(r.getCode(),
                    r.getDescription(), r.getAmount()));
        }

        // Copy ministries.
        for (Ministry m : other.ministries.values()) {
            Ministry mc = new Ministry(
                    m.getcode(),
                    m.getName(),
                    m.getRegularBudget(),
                    m.getPublicInvestments(),
                    m.getTotalBudget());

            for (Map.Entry<String, BigDecimal> e
                : m.getAllocation().entrySet()) {
                mc.setAllocationEntry(e.getKey(),
                e.getValue());
            }

            this.ministries.put(m.getcode(), mc);
        }
    }

    /**
     * Adds a revenue record.
     *
     * @param r the revenue record
     */
    public void addRevenue(final BasicRecord r) {
        revenues.put(r.getCode(), r);
    }

    /**
     * Adds an expense record.
     *
     * @param r the expense record
     */
    public void addExpense(final BasicRecord r) {
        expenses.put(r.getCode(), r);
    }

    /**
     * Adds a ministry.
     *
     * @param m the ministry to add
     */
    public void addMinistry(final Ministry m) {
        ministries.put(m.getcode(), m);
    }

    /**
     * Gets all revenues.
     *
     * @return a copy of the revenue records
     */
    public Map<String, BasicRecord> getRevenues() {
        return new LinkedHashMap<>(revenues);
    }

    /**
     * Gets all expenses.
     *
     * @return a copy of the expense records
     */
    public Map<String, BasicRecord> getExpenses() {
        return new LinkedHashMap<>(expenses);
    }

    /**
     * Gets all ministries.
     *
     * @return a copy of the ministry records
     */
    public Map<Integer, Ministry> getMinistries() {
        return new LinkedHashMap<>(ministries);
    }

    /**
     * Computes the total amount of revenues.
     *
     * @return the total revenues
     */
    public BigDecimal totalRevenues() {
        return revenues.values().stream()
                .map(BasicRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Computes the total amount of expenses.
     *
     * @return the total expenses
     */
    public BigDecimal totalExpenses() {
        return expenses.values().stream()
                .map(BasicRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
