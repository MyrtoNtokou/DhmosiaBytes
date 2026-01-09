package budgetlogic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

/**
 * Service class for managing and updating budget information.
 *
 * <p>This class coordinates interactions with:
 * <ul>
 *   <li>{@code Budget}: revenues, expenses, ministries</li>
 *   <li>{@code Eggrafi}: general table records</li>
 *   <li>{@code Ypourgeio}: ministry-level budget entries</li>
 * </ul>
 *
 * <p>Optional mapping provides allocation percentages:
 * {@code Map<ministryKodikos, Map<expenseKodikos, percent>>}.
 */
public final class BudgetServiceImpl implements BudgetService {

    /** Keyword used to detect the revenues header row. */
private static final String REVENUES_KEYWORD = "ΕΣΟΔΑ";

/** Keyword used to detect the expenses header row. */
private static final String EXPENSES_KEYWORD = "ΕΞΟΔΑ";

/** Keyword used to detect the results row. */
private static final String RESULT_KEYWORD = "ΑΠΟΤΕΛΕΣΜΑ";

/** Prefix for ministry rows. */
private static final String MINISTRY_PREFIX = "Υπουργείο";

/** Prefix for decentralized administration rows. */
private static final String DECENTRALIZED_PREFIX = "Αποκεντρωμένη";

/** Keyword for total expenditure row. */
private static final String TOTAL_EXPENDITURE_KEYWORD = "Σύνολο εξόδων";

    /** Underlying budget model. */
    private final Budget budget;

    /**
     * Optional mapping:
     * ministryKodikos -> (expenseKodikos -> percent(0..1)).
     */
    private final Map<Integer, Map<String, BigDecimal>> mapping;

    /**
     * Constructor.
     *
     * @param initBudget Budget instance
     * @param initMapping mapping definition (may be null)
     */
    public BudgetServiceImpl(final Budget initBudget,
                         final Map<Integer, Map<String,
                         BigDecimal>> initMapping) {

        this.budget = Objects.requireNonNull(initBudget, "budget");
        this.mapping = new LinkedHashMap<>();

        if (initMapping != null) {
            for (var e : initMapping.entrySet()) {
                this.mapping.put(e.getKey(),
                new LinkedHashMap<>(e.getValue()));
            }
        }
    }

    /**
     * Changes a general table amount. Kodikos identifies the
     * {@code Eggrafi}.
     * Automatically updates the header totals: ΕΣΟΔΑ,
     * ΕΞΟΔΑ, ΑΠΟΤΕΛΕΣΜΑ.
     *
     * @param kodikos identifier of the record
     * @param newAmount new value to set
     */
    public void changeGeneralAmount(final String kodikos,
        final BigDecimal newAmount) {
        final Map<String, Eggrafi> revenues = budget.getRevenues();
        final Map<String, Eggrafi> expenses = budget.getExpenses();

        Eggrafi target = revenues.get(kodikos);
        if (target == null) {
            target = expenses.get(kodikos);
        }
        if (target == null) {
            throw new IllegalArgumentException("Δεν υπάρχει εγγραφή με κωδικό "
            + kodikos);
        }

        target.setPoso(normalize(newAmount));
        recomputeGeneralAggregates();
    }

    /**
     * Changes a ministry amount in either "τακτικός" or "ΠΔΕ".
     *
     * @param ministryKodikos ministry ID
     * @param column either "τακτικός" or "ΠΔΕ"
     * @param newValue new value
     */
    public void changeMinistryAmount(final int ministryKodikos,
                                     final String column,
                                     final BigDecimal newValue) {

        final Ypourgeio m = budget.getMinistries().get(ministryKodikos);
        if (m == null) {
            throw new IllegalArgumentException("Δεν υπάρχει υπουργείο με κωδικό"
            + ministryKodikos);
        }

        final BigDecimal nv = normalize(newValue);
        final BigDecimal oldTotal =
            normalize(m.getSynolo() == null ? BigDecimal.ZERO
            : m.getSynolo());

        switch (column.toLowerCase(Locale.ROOT)) {
            case "τακτικός" -> m.setTaktikos(nv);
            case "πδε", "προϋπολογισμός δημοσίων επενδύσεων" ->
            m.setEpendyseis(nv);
            default -> throw new IllegalArgumentException(
                "Άγνωστη κατηγορία Υπουργείου: " + column);
        }

        reconcileMinistryParts(m);
        propagateChangeToExpenses(ministryKodikos, oldTotal,
                normalize(m.getSynolo() == null ? BigDecimal.ZERO
                : m.getSynolo()));
        recomputeMinistriesAggregates();
        recomputeGeneralAggregates();
    }

    /**
     * Recomputes the aggregate general table rows (ΕΣΟΔΑ/ΕΞΟΔΑ/ΑΠΟΤΕΛΕΣΜΑ).
     * Scans the table to locate these rows dynamically.
     */
    public void recomputeGeneralAggregates() {
        final Map<String, Eggrafi> revs = budget.getRevenues();
        final Map<String, Eggrafi> exps = budget.getExpenses();

        final List<Eggrafi> ordered = new java.util.ArrayList<>();
        ordered.addAll(revs.values());
        ordered.addAll(exps.values());

        int revHeader = -1;
        int expHeader = -1;
        int resultRow = -1;

        for (int i = 0; i < ordered.size(); i++) {
            final String p = safe(ordered.get(i).getPerigrafi())
                    .toUpperCase(Locale.ROOT);

            if (revHeader < 0 && p.contains(REVENUES_KEYWORD)) {
                revHeader = i;
            }
            if (expHeader < 0 && p.contains(EXPENSES_KEYWORD)) {
                expHeader = i;
            }
            if (resultRow < 0 && p.contains(RESULT_KEYWORD)) {
                resultRow = i;
            }
        }

        if (revHeader < 0 || expHeader < 0 || resultRow < 0) {
            return;
        }

        BigDecimal revSum = BigDecimal.ZERO;
        for (int i = revHeader + 1; i < expHeader; i++) {
            revSum = revSum.add(nonNull(ordered.get(i).getPoso()));
        }

        BigDecimal expSum = BigDecimal.ZERO;
        for (int i = expHeader + 1; i < resultRow; i++) {
            expSum = expSum.add(nonNull(ordered.get(i).getPoso()));
        }

        ordered.get(revHeader).setPoso(normalize(revSum));
        ordered.get(expHeader).setPoso(normalize(expSum));
        ordered.get(resultRow).setPoso(normalize(revSum.subtract(expSum)));
    }

    /** ministries total row code. */
    private static final int MINISTRY_TOTAL_CODE = 4;

    /** decentralized total row code. */
    private static final int DEC_TOTAL_CODE = 25;

    /** total expenses row code. */
    private static final int SYN_TOTAL_CODE = 33;
    /**
     * Recomputes all ministry-level aggregate totals including:
     * <ul>
     *   <li>Total ministries</li>
     *   <li>Decentralized administrations</li>
     *   <li>Row 33 (Σύνολο εξόδων)</li>
     * </ul>.
     */
    public void recomputeMinistriesAggregates() {
        final Map<Integer, Ypourgeio> ministries = budget.getMinistries();

        BigDecimal sumMin = BigDecimal.ZERO;
        BigDecimal sumMinTakt = BigDecimal.ZERO;
        BigDecimal sumMinPde = BigDecimal.ZERO;

        for (Ypourgeio m : ministries.values()) {
            final String name = safe(m.getOnoma());
            if (name.startsWith(MINISTRY_PREFIX)) {
                sumMin = sumMin.add(nonNull(m.getSynolo()));
                sumMinTakt = sumMinTakt.add(nonNull(m.getTaktikos()));
                sumMinPde = sumMinPde.add(nonNull(m.getEpendyseis()));
            }
        }

        Ypourgeio ministriesTotalRow = null;
        for (Ypourgeio m : ministries.values()) {
            if (safe(m.getOnoma()).toUpperCase(Locale.ROOT)
                .contains("ΥΠΟΥΡΓΕΙΑ")) {
                ministriesTotalRow = m;
                break;
            }
        }
        if (ministriesTotalRow == null && ministries.containsKey(
            MINISTRY_TOTAL_CODE)) {
            ministriesTotalRow = ministries.get(MINISTRY_TOTAL_CODE);
        }

        if (ministriesTotalRow != null) {
            ministriesTotalRow.setTaktikos(normalize(sumMinTakt));
            ministriesTotalRow.setEpendyseis(normalize(sumMinPde));
            ministriesTotalRow.setSynolo(normalize(sumMin));
        }

        BigDecimal sumDec = BigDecimal.ZERO;
        BigDecimal sumDecTakt = BigDecimal.ZERO;
        BigDecimal sumDecPde = BigDecimal.ZERO;

        for (Ypourgeio m : ministries.values()) {
            final String name = safe(m.getOnoma());
            if (name.startsWith(DECENTRALIZED_PREFIX)) {
                sumDec = sumDec.add(nonNull(m.getSynolo()));
                sumDecTakt = sumDecTakt.add(nonNull(m.getTaktikos()));
                sumDecPde = sumDecPde.add(nonNull(m.getEpendyseis()));
            }
        }

        Ypourgeio decentralizedTotalRow = null;
        for (Ypourgeio m : ministries.values()) {
            if (safe(m.getOnoma()).toUpperCase(Locale.ROOT)
                    .contains("ΑΠΟΚΕΝΤΡΩΜΕΝΕΣ")) {
                decentralizedTotalRow = m;
            }
        }

        if (decentralizedTotalRow == null && ministries
            .containsKey(DEC_TOTAL_CODE)) {
            decentralizedTotalRow = ministries.get(DEC_TOTAL_CODE);
        }

        if (decentralizedTotalRow != null) {
            decentralizedTotalRow.setTaktikos(normalize(sumDecTakt));
            decentralizedTotalRow.setEpendyseis(normalize(sumDecPde));
            decentralizedTotalRow.setSynolo(normalize(sumDec));
        }

        final Ypourgeio m1 = ministries.get(1);
        final Ypourgeio m2 = ministries.get(2);
        final Ypourgeio m3 = ministries.get(3);

        BigDecimal g1 = m1 == null ? BigDecimal.ZERO
        : nonNull(m1.getSynolo());
        BigDecimal gt1 = m1 == null ? BigDecimal.ZERO
        : nonNull(m1.getTaktikos());
        BigDecimal gp1 = m1 == null ? BigDecimal.ZERO
        : nonNull(m1.getEpendyseis());

        BigDecimal g2 = m2 == null ? BigDecimal.ZERO
        : nonNull(m2.getSynolo());
        BigDecimal gt2 = m2 == null ? BigDecimal.ZERO
        : nonNull(m2.getTaktikos());
        BigDecimal gp2 = m2 == null ? BigDecimal.ZERO
        : nonNull(m2.getEpendyseis());

        BigDecimal g3 = m3 == null ? BigDecimal.ZERO
        : nonNull(m3.getSynolo());
        BigDecimal gt3 = m3 == null ? BigDecimal.ZERO
        : nonNull(m3.getTaktikos());
        BigDecimal gp3 = m3 == null ? BigDecimal.ZERO
        : nonNull(m3.getEpendyseis());

        BigDecimal ministriesTotal =
            nonNull(ministriesTotalRow == null ? BigDecimal.ZERO
                : ministriesTotalRow.getSynolo());
        BigDecimal ministriesTakt =
            nonNull(ministriesTotalRow == null ? BigDecimal.ZERO
                : ministriesTotalRow.getTaktikos());
        BigDecimal ministriesPde =
            nonNull(ministriesTotalRow == null ? BigDecimal.ZERO
                : ministriesTotalRow.getEpendyseis());

        BigDecimal kod25 = BigDecimal.ZERO;
        BigDecimal kod25t = BigDecimal.ZERO;
        BigDecimal kod25p = BigDecimal.ZERO;

        if (ministries.containsKey(DEC_TOTAL_CODE)) {
            kod25 = nonNull(ministries.get(DEC_TOTAL_CODE).getSynolo());
            kod25t = nonNull(ministries.get(DEC_TOTAL_CODE).getTaktikos());
            kod25p = nonNull(ministries.get(DEC_TOTAL_CODE).getEpendyseis());
        }

        final BigDecimal overall = normalize(
                g1.add(g2).add(g3).add(ministriesTotal).add(kod25));
        final BigDecimal overallT = normalize(
                gt1.add(gt2).add(gt3).add(ministriesTakt).add(kod25t));
        final BigDecimal overallP = normalize(
                gp1.add(gp2).add(gp3).add(ministriesPde).add(kod25p));

        boolean updated = false;

        for (Ypourgeio m : ministries.values()) {
            if (safe(m.getOnoma()).toUpperCase(Locale.ROOT)
                    .contains(TOTAL_EXPENDITURE_KEYWORD
                .toUpperCase(Locale.ROOT))) {
                m.setSynolo(overall);
                m.setTaktikos(overallT);
                m.setEpendyseis(overallP);
                updated = true;
                break;
            }
        }

        if (!updated && ministries.containsKey(SYN_TOTAL_CODE)) {
            final Ypourgeio r = ministries.get(SYN_TOTAL_CODE);
            r.setSynolo(overall);
            r.setTaktikos(overallT);
            r.setEpendyseis(overallP);
        }
    }

    /**
     * Propagates ministry total changes to expense entries.
     *
     * @param ministryKodikos ministry ID
     * @param oldTotal previous total
     * @param newTotal updated total
     */
    private void propagateChangeToExpenses(final int ministryKodikos,
                                           final BigDecimal oldTotal,
                                           final BigDecimal newTotal) {

        final Map<String, Eggrafi> expenses = budget.getExpenses();
        if (expenses == null || expenses.isEmpty()) {
            return;
        }

        final BigDecimal oldT = nonNull(oldTotal);
        final BigDecimal nw = nonNull(newTotal);
        final BigDecimal diff = nw.subtract(oldT);

        if (mapping != null && mapping.containsKey(ministryKodikos)) {
            final Map<String, BigDecimal> map = mapping
            .get(ministryKodikos);
            for (Map.Entry<String, BigDecimal> e : map.entrySet()) {
                final Eggrafi expenseRec = expenses.get(e.getKey());
                if (expenseRec != null) {
                    final BigDecimal percent = nonNull(e.getValue());
                    final BigDecimal newVal =
                        normalize(expenseRec.getPoso()
                        .add(diff.multiply(percent)));
                    expenseRec.setPoso(newVal);
                }
            }
            return;
        }

        final String prefix = String.valueOf(ministryKodikos);

        if (oldT.compareTo(BigDecimal.ZERO) == 0) {
            int count = 0;
            for (String k : expenses.keySet()) {
                if (k.startsWith(prefix)) {
                    count++;
                }
            }
            if (count == 0) {
                return;
            }
            final BigDecimal each =
                nw.divide(BigDecimal.valueOf(count),
                2, RoundingMode.HALF_EVEN);

            for (Map.Entry<String, Eggrafi> en
                : expenses.entrySet()) {
                if (en.getKey().startsWith(prefix)) {
                    en.getValue().setPoso(each);
                }
            }
        } else {
            final BigDecimal ratio =
                nw.divide(oldT, 10, RoundingMode.HALF_EVEN);

            for (Map.Entry<String, Eggrafi> en : expenses.entrySet()) {
                if (en.getKey().startsWith(prefix)) {
                    final BigDecimal oldVal = nonNull(en.getValue()
                    .getPoso());
                    en.getValue().setPoso(normalize(oldVal
                        .multiply(ratio)));
                }
            }
        }
    }

    /**
     * Ensures {@code synolo = taktikos + pde}.
     *
     * @param m ministry to reconcile
     */
    private void reconcileMinistryParts(final Ypourgeio m) {
        final BigDecimal takt = nonNull(m.getTaktikos());
        final BigDecimal pde = nonNull(m.getEpendyseis());
        final BigDecimal sum = takt.add(pde);

        if (m.getSynolo() == null || m.getSynolo().compareTo(sum) != 0) {
            m.setSynolo(normalize(sum));
        }
    }

    /**
     * Validates that for every ministry:
     * {@code synolo == taktikos + pde}.
     *
     * @return true if consistent
     */
    public boolean validateMinistries() {
        for (Ypourgeio m : budget.getMinistries().values()) {
            final BigDecimal takt = nonNull(m.getTaktikos());
            final BigDecimal pde = nonNull(m.getEpendyseis());
            final BigDecimal syn = nonNull(m.getSynolo());

            if (takt.add(pde)
                    .setScale(2, RoundingMode.HALF_EVEN)
                    .compareTo(syn.setScale(2,
                        RoundingMode.HALF_EVEN)) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Performs a basic validation for the general table.
     *
     * @return always true (state is recomputed)
     */
    public boolean validateGeneral() {
        recomputeGeneralAggregates();
        return true;
    }

    /**
     * Validates both general and ministry tables.
     *
     * @return true if all validations pass
     */
    public boolean validateAll() {
        return validateGeneral() && validateMinistries();
    }

    private static String safe(final String s) {
        return s == null ? "" : s;
    }

    private static BigDecimal nonNull(final BigDecimal b) {
        return b == null ? BigDecimal.ZERO : b;
    }

    private static BigDecimal normalize(final BigDecimal x) {
        if (x == null) {
            return BigDecimal.ZERO.setScale(2,
                RoundingMode.HALF_EVEN);
        }
        return x.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Returns a defensive copy of the budget.
     *
     * @return new Budget instance
     */
    public Budget getBudget() {
        return new Budget(this.budget);
    }
}
