package budgetlogic;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * simple budget model
 */
public class Budget {

    private final Map<String, BasicRecord> revenues;
    private final Map<String, BasicRecord> expenses;
    private final Map<Integer, Ministry> ministries;

    public Budget() {
        this.revenues = new LinkedHashMap<>();
        this.expenses = new LinkedHashMap<>();
        this.ministries = new LinkedHashMap<>();
    }
    public Budget(Map<String, BasicRecord> revenues,
              Map<String, BasicRecord> expenses,
              Map<Integer, Ministry> ministries) {
    this.revenues = revenues;
    this.expenses = expenses;
    this.ministries = ministries; // or whatever your field name is!
}
    public Budget(Budget other) {
    this.revenues = new LinkedHashMap<>();
    this.expenses = new LinkedHashMap<>();
    this.ministries = new LinkedHashMap<>();

    // Copy revenues
    for (BasicRecord r : other.revenues.values()) {
        this.revenues.put(r.getKodikos(),
                new BasicRecord(r.getKodikos(), r.getPerigrafi(), r.getPoso()));
    }

    // Copy expenses
    for (BasicRecord r : other.expenses.values()) {
        this.expenses.put(r.getKodikos(),
                new BasicRecord(r.getKodikos(), r.getPerigrafi(), r.getPoso()));
    }

    // Copy ministries
    for (Ministry m : other.ministries.values()) {
        Ministry mc = new Ministry(
                m.getKodikos(),
                m.getOnoma(),
                m.getTaktikos(),
                m.getPde(),
                m.getSynolo()
        );

        for (var e : m.getAllocation().entrySet()) {
            mc.setAllocationEntry(e.getKey(), e.getValue());
        }

        this.ministries.put(m.getKodikos(), mc);
    }
}


    public void addRevenue(final BasicRecord r) {
        revenues.put(r.getKodikos(), r);
    }

    public void addExpense(final BasicRecord r) {
        expenses.put(r.getKodikos(), r);
    }

    public void addMinistry(final Ministry m) {
        ministries.put(m.getKodikos(), m);
    }

    public Map<String, BasicRecord> getRevenues() {
        return new LinkedHashMap<>(revenues);
    }

    public Map<String, BasicRecord> getExpenses() {
        return new LinkedHashMap<>(expenses);
    }

    public Map<Integer, Ministry> getMinistries() {
        return new LinkedHashMap<>(ministries);
    }

    public BigDecimal totalRevenues() {
        return revenues.values().stream()
                .map(BasicRecord::getPoso)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalExpenses() {
        return expenses.values().stream()
                .map(BasicRecord::getPoso)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
