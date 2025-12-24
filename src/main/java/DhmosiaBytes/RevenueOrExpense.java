package dhmosiabytes;

/**
 * Enum representing whether a budget entry is an income or an expense,
 * each with a numeric code and description.
 */
public enum  RevenueOrExpense {
    /** Budget income. */
    INCOME(1, "Έσοδα Προϋπολογισμού"),

    /** Budget expense. */
    EXPENSE(2, "Έξοδα Προϋπολογισμού");

    /** Numeric code identifying the type. */
    private final int revenueOrExpenseCode;

    /** Description of the type. */
    private final String description;

     /**
     * Constructs a RevenueOrExpense constant.
     *
     * @param code numeric code for the type
     * @param descr human-readable description
     */
    RevenueOrExpense(final int code, final String descr) {
        this.revenueOrExpenseCode = code;
        this.description = descr;
    }

     /**
     * Returns the numeric code of the type.
     *
     * @return revenue or expense code
     */
    public int getRevenueOrExpenseCode() {
        return revenueOrExpenseCode;
    }

     /**
     * Returns the description of the type.
     *
     * @return description of the type
     */
    public String getDescription() {
        return description;
    }

     /**
     * Returns the RevenueOrExpense constant matching the given code.
     *
     * @param revenueOrExpenseCode code to look up
     * @return corresponding RevenueOrExpense constant
     * @throws IllegalArgumentException if no matching constant is found
     */
    public static RevenueOrExpense fromCode(final int revenueOrExpenseCode) {
        for (RevenueOrExpense opt : RevenueOrExpense.values()) {
            if (opt.getRevenueOrExpenseCode() == revenueOrExpenseCode) {
                return opt;
            }
        }
        throw new IllegalArgumentException("Μη έγκυρη επιλογή: "
        + revenueOrExpenseCode);
    }
}
