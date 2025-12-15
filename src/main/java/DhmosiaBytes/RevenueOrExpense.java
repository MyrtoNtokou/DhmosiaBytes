package dhmosiabytes;

public enum  RevenueOrExpense {
    INCOME(1 ,"Έσοδα Προϋπολογισμού"),
    EXPENSE(2,"Έξοδα Προϋπολογισμού");
    private final int revenueOrExpenseCode;
    private final String description;

    RevenueOrExpense(final int code, final String description) {
        this.revenueOrExpenseCode = code;
        this.description = description;
    }

    public int getRevenueOrExpenseCode() {
        return revenueOrExpenseCode;
    }

    public String getDescription() {
        return description;
    }

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
