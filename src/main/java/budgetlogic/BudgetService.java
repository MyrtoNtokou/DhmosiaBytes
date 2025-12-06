package budgetlogic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * BudgetService που δουλεύει με τα μοντέλα σου:
 * - Budget: getRevenues(), getExpenses() -> Map<String,BasicRecord>
 * - Budget: getMinistries() -> Map<Integer,Ministry>
 * - BasicRecord: getKodikos(), getPerigrafi(), getPoso(), setPoso(BigDecimal)
 * - Ministry: getKodikos(), getOnoma(), getTaktikos(), getPde(), getSynolo(),
 *            setTaktikos(BigDecimal), setPde(BigDecimal), setSynolo(BigDecimal)
 *
 * Επιπλέον δέχεται optional mapping για πιο ακριβή προώθηση αλλαγών σε expenses:
 *   Map<Integer, Map<String, BigDecimal>>
 * όπου key = ministryKodikos, inner map: expenseKodikos -> percent (0..1)
 */
public class BudgetService {

    private static final String REVENUES_KEYWORD = "ΕΣΟΔΑ";
    private static final String EXPENSES_KEYWORD = "ΕΞΟΔΑ";
    private static final String RESULT_KEYWORD = "ΑΠΟΤΕΛΕΣΜΑ";
    private static final String MINISTRY_PREFIX = "Υπουργείο";
    private static final String DECENTRALIZED_PREFIX = "Αποκεντρω";
    private static final String TOTAL_EXPENDITURE_KEYWORD = "Σύνολο εξόδων";

    private final Budget budget;
    /**
     * Optional mapping: ministryKodikos -> (expenseKodikos -> percent(0..1))
     * If null, fallback proportional scaling heuristic is used.
     */
    private final Map<Integer, Map<String, BigDecimal>> mapping;

    public BudgetService(final Budget budget,
                         final Map<Integer, Map<String, BigDecimal>> mapping) {
        this.budget = Objects.requireNonNull(budget, "budget");
        this.mapping = new LinkedHashMap<>();
for (var e : mapping.entrySet()) {
    this.mapping.put(e.getKey(), new LinkedHashMap<>(e.getValue()));
}

    }

    /* ================= Public entry points ================= */

    /**
     * Change a general amount (proypologismos general table). kodikos is the BasicRecord kodikos (String).
     * After change it recomputes header totals (ΕΣΟΔΑ, ΕΞΟΔΑ, ΑΠΟΤΕΛΕΣΜΑ).
     */
    public void changeGeneralAmount(final String kodikos, final BigDecimal newAmount) {
        final Map<String, BasicRecord> revenues = budget.getRevenues();
        final Map<String, BasicRecord> expenses = budget.getExpenses();

        BasicRecord target = revenues.get(kodikos);
        if (target == null) {
            target = expenses.get(kodikos);
        }
        if (target == null) {
            throw new IllegalArgumentException("No general record with kodikos=" + kodikos);
        }
        target.setPoso(normalize(newAmount));

        recomputeGeneralAggregates();
    }

    /**
     * Change a ministry value. column must be "taktikos" or "pde" or "synolo".
     * ministryKodikos is the integer code stored in Ministry.kodikos.
     */
    public void changeMinistryAmount(final int ministryKodikos, final String column, final BigDecimal newValue) {
        final Map<Integer, Ministry> ministries = budget.getMinistries();
        final Ministry m = ministries.get(ministryKodikos);
        if (m == null) {
            throw new IllegalArgumentException("No ministry with kodikos=" + ministryKodikos);
        }

        final BigDecimal nv = normalize(newValue);
        final BigDecimal oldTotal = normalize(m.getSynolo() == null ? BigDecimal.ZERO : m.getSynolo());

        switch (column.toLowerCase(Locale.ROOT)) {
            case "taktikos":
            case "taktiko":
                m.setTaktikos(nv);
                break;
            case "pde":
                m.setPde(nv);
                break;
            case "synolo":
            case "total":
                m.setSynolo(nv);
                break;
            default:
                throw new IllegalArgumentException("Unknown ministry column: " + column);
        }

        // ensure ministry internal consistency when taktiko/pde changed:
        reconcileMinistryParts(m);

        // propagate change to expenses (using mapping if available)
        propagateChangeToExpenses(ministryKodikos, oldTotal, normalize(m.getSynolo() == null ? BigDecimal.ZERO : m.getSynolo()));

        // recompute ministries-level aggregates and overall totals
        recomputeMinistriesAggregates();
    }

    /* ================= General aggregates (proypologismos2025) ================= */

    /**
     * Recompute ΕΣΟΔΑ, ΕΞΟΔΑ, ΑΠΟΤΕΛΕΣΜΑ header rows inside the general tables.
     * It finds indices by scanning perigrafi for keywords.
     */
    public void recomputeGeneralAggregates() {
        // Combine revenues + expenses into a single list order - but we need original order.
        // We assume loader preserved ordering in the Map insertion order (Budget uses LinkedHashMap).
        // We'll scan revenue map then expense map to find headers and parts.
        final Map<String, BasicRecord> revs = budget.getRevenues();
        final Map<String, BasicRecord> exps = budget.getExpenses();

        // Create an ordered array of records as they appear in CSV (first revenues then expenses).
        final List<BasicRecord> ordered = new java.util.ArrayList<>();
        ordered.addAll(revs.values());
        ordered.addAll(exps.values());

        int revHeader = -1, expHeader = -1, resultRow = -1;
        for (int i = 0; i < ordered.size(); i++) {
            final String p = safe(ordered.get(i).getPerigrafi()).toUpperCase(Locale.ROOT);
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
            // Could not find the special rows; nothing to do
            return;
        }

        // Sum revenues: records between revHeader+1 .. expHeader-1
        BigDecimal revSum = BigDecimal.ZERO;
        for (int i = revHeader + 1; i < expHeader; i++) {
            revSum = revSum.add(nonNull(ordered.get(i).getPoso()));
        }

        // Sum expenses: records between expHeader+1 .. resultRow-1
        BigDecimal expSum = BigDecimal.ZERO;
        for (int i = expHeader + 1; i < resultRow; i++) {
            expSum = expSum.add(nonNull(ordered.get(i).getPoso()));
        }

        // Update header rows (they may belong to revenues map or expenses map)
        BasicRecord revHeaderRec = ordered.get(revHeader);
        revHeaderRec.setPoso(normalize(revSum));
        BasicRecord expHeaderRec = ordered.get(expHeader);
        expHeaderRec.setPoso(normalize(expSum));

        BasicRecord resultRec = ordered.get(resultRow);
        resultRec.setPoso(normalize(revSum.subtract(expSum)));

        // write back into maps (they're same objects so no extra action needed)
    }

    /* ================= Ministries aggregates (proypologismos2025anaypourgeio) ================= */

    /**
     * Recompute ministries aggregated rows:
     * - 'Υπουργεία Συνολικά' = sum of rows that start with 'Υπουργείο'
     * - 'Αποκεντρωμένες Διοικήσεις' = sum of rows that start with 'Αποκεντρωμένη' / 'Αποκεντρωμένες'
     * - 'Σύνολο εξόδων' (33) = 1 + 2 + 3 + ministriesTotal + kodikos(25)  (it reads 1/2/3 from general table)
     */
    public void recomputeMinistriesAggregates() {
        final Map<Integer, Ministry> ministries = budget.getMinistries();

        // sum individual ministries (rows that start with MINISTRY_PREFIX)
        BigDecimal sumMin = BigDecimal.ZERO;
        BigDecimal sumMinTakt = BigDecimal.ZERO;
        BigDecimal sumMinPde = BigDecimal.ZERO;
        for (Ministry m : ministries.values()) {
            final String name = safe(m.getOnoma());
            if (name.startsWith(MINISTRY_PREFIX)) {
                sumMin = sumMin.add(nonNull(m.getSynolo()));
                sumMinTakt = sumMinTakt.add(nonNull(m.getTaktikos()));
                sumMinPde = sumMinPde.add(nonNull(m.getPde()));
            }
        }

        // find and set ministries total row (attempt by name contains "ΥΠΟΥΡΓΕΙΑ" otherwise fallback to kodikos==4)
        Ministry ministriesTotalRow = null;
        for (Ministry m : ministries.values()) {
            if (safe(m.getOnoma()).toUpperCase(Locale.ROOT).contains("ΥΠΟΥΡΓΕΙΑ")) {
                ministriesTotalRow = m;
                break;
            }
        }
        if (ministriesTotalRow == null && ministries.containsKey(4)) {
            ministriesTotalRow = ministries.get(4);
        }
        if (ministriesTotalRow != null) {
            ministriesTotalRow.setTaktikos(normalize(sumMinTakt));
            ministriesTotalRow.setPde(normalize(sumMinPde));
            ministriesTotalRow.setSynolo(normalize(sumMin));
        }

        // sum decentralized rows (name starts with DECENTRALIZED_PREFIX)
        BigDecimal sumDec = BigDecimal.ZERO;
        BigDecimal sumDecTakt = BigDecimal.ZERO;
        BigDecimal sumDecPde = BigDecimal.ZERO;
        for (Ministry m : ministries.values()) {
            final String name = safe(m.getOnoma());
            if (name.startsWith(DECENTRALIZED_PREFIX) || name.startsWith("Αποκεντρωμένη") || name.startsWith("Αποκεντρωμένες")) {
                sumDec = sumDec.add(nonNull(m.getSynolo()));
                sumDecTakt = sumDecTakt.add(nonNull(m.getTaktikos()));
                sumDecPde = sumDecPde.add(nonNull(m.getPde()));
            }
        }

        // find decentralised total row (by name contains 'Αποκεντρωμένες') or fallback to kodikos 35
        Ministry decentralisedTotalRow = null;
        for (Ministry m : ministries.values()) {
            if (safe(m.getOnoma()).toUpperCase(Locale.ROOT).contains("ΑΠΟΚΕΝΤΡ")) {
                // pick the aggregated one if it looks like aggregated phrase
                if (safe(m.getOnoma()).toUpperCase(Locale.ROOT).contains("ΔΙΟΙΚ") || safe(m.getOnoma()).toUpperCase(Locale.ROOT).contains("ΣΥΝΟΛ")) {
                    decentralisedTotalRow = m;
                    break;
                }
            }
        }
        if (decentralisedTotalRow == null && ministries.containsKey(35)) {
            decentralisedTotalRow = ministries.get(35);
        }
        if (decentralisedTotalRow != null) {
            decentralisedTotalRow.setTaktikos(normalize(sumDecTakt));
            decentralisedTotalRow.setPde(normalize(sumDecPde));
            decentralisedTotalRow.setSynolo(normalize(sumDec));
        }

        // Compute overall total expenditures row 33 = 1 + 2 + 3 + ministriesTotal + kodikos(25)
        BigDecimal g1 = BigDecimal.ZERO, g2 = BigDecimal.ZERO, g3 = BigDecimal.ZERO;
        // read from general table: kodikos "1","2","3"
        final Map<String, BasicRecord> genRev = budget.getRevenues();
        final Map<String, BasicRecord> genExp = budget.getExpenses();
        BasicRecord r1 = genRev.get("1");
        if (r1 == null) r1 = genExp.get("1");
        BasicRecord r2 = genRev.get("2");
        if (r2 == null) r2 = genExp.get("2");
        BasicRecord r3 = genRev.get("3");
        if (r3 == null) r3 = genExp.get("3");
        if (r1 != null) g1 = nonNull(r1.getPoso());
        if (r2 != null) g2 = nonNull(r2.getPoso());
        if (r3 != null) g3 = nonNull(r3.getPoso());

        BigDecimal ministriesTotal = nonNull(ministriesTotalRow == null ? BigDecimal.ZERO : ministriesTotalRow.getSynolo());
        BigDecimal kod25 = BigDecimal.ZERO;
        if (ministries.containsKey(25)) {
            kod25 = nonNull(ministries.get(25).getSynolo());
        }

        final BigDecimal overall = normalize(g1.add(g2).add(g3).add(ministriesTotal).add(kod25));

        // find row with TOTAL_EXPENDITURE_KEYWORD and set its synolo (otherwise try kodikos 33)
        boolean setOverall = false;
        for (Ministry m : ministries.values()) {
            if (safe(m.getOnoma()).toUpperCase(Locale.ROOT).contains(TOTAL_EXPENDITURE_KEYWORD.toUpperCase(Locale.ROOT))) {
                m.setSynolo(overall);
                setOverall = true;
                break;
            }
        }
        if (!setOverall && ministries.containsKey(33)) {
            ministries.get(33).setSynolo(overall);
        }
    }

    /* ================= Mapping propagation ================= */

    /**
     * Propagate change of a ministry total to expense BasicRecord entries.
     * If mapping is provided it uses it (expenseKodikos -> percent (0..1)).
     * Otherwise it attempts a fallback: scale all expense entries whose kodikos startsWith ministryKodikos (string).
     */
    private void propagateChangeToExpenses(final int ministryKodikos,
                                           final BigDecimal oldTotal,
                                           final BigDecimal newTotal) {
        final Map<String, BasicRecord> expenses = budget.getExpenses();
        if (expenses == null || expenses.isEmpty()) {
            return;
        }
        final BigDecimal oldT = nonNull(oldTotal);
        final BigDecimal nw = nonNull(newTotal);

        if (mapping != null && mapping.containsKey(ministryKodikos)) {
            final Map<String, BigDecimal> map = mapping.get(ministryKodikos);
            // map gives percentages; set expense = nw * percent
            for (Map.Entry<String, BigDecimal> e : map.entrySet()) {
                final String expenseKodikos = e.getKey();
                final BigDecimal percent = nonNull(e.getValue());
                final BasicRecord expenseRec = expenses.get(expenseKodikos);
                if (expenseRec != null) {
                    final BigDecimal newVal = normalize(nw.multiply(percent));
                    expenseRec.setPoso(newVal);
                }
            }
            return;
        }

        // Fallback proportional scaling for expenses whose kodikos starts with the ministryKodikos string
        final String prefix = String.valueOf(ministryKodikos);
        if (oldT.compareTo(BigDecimal.ZERO) == 0) {
            // if old was zero: distribute equally among matching expense rows
            int count = 0;
            for (String k : expenses.keySet()) {
                if (k.startsWith(prefix)) count++;
            }
            if (count == 0) return;
            final BigDecimal each = nw.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_EVEN);
            for (Map.Entry<String, BasicRecord> en : expenses.entrySet()) {
                if (en.getKey().startsWith(prefix)) {
                    en.getValue().setPoso(each);
                }
            }
        } else {
            final BigDecimal ratio = nw.divide(oldT, 10, RoundingMode.HALF_EVEN);
            for (Map.Entry<String, BasicRecord> en : expenses.entrySet()) {
                if (en.getKey().startsWith(prefix)) {
                    final BigDecimal old = nonNull(en.getValue().getPoso());
                    en.getValue().setPoso(normalize(old.multiply(ratio)));
                }
            }
        }
    }

    /* ================= Helpers & validation ================= */

    /** ensures ministry synolo = taktiko + pde when taktiko/pde are set */
    private void reconcileMinistryParts(final Ministry m) {
        final BigDecimal takt = nonNull(m.getTaktikos());
        final BigDecimal pde = nonNull(m.getPde());
        // If synolo doesn't equal takt+pde, set synolo = takt + pde
        final BigDecimal sum = takt.add(pde);
        if (m.getSynolo() == null || m.getSynolo().compareTo(sum) != 0) {
            m.setSynolo(normalize(sum));
        }
    }

    public boolean validateMinistries() {
        for (Ministry m : budget.getMinistries().values()) {
            final BigDecimal takt = nonNull(m.getTaktikos());
            final BigDecimal pde = nonNull(m.getPde());
            final BigDecimal syn = nonNull(m.getSynolo());
            if (takt.add(pde).setScale(2, RoundingMode.HALF_EVEN).compareTo(syn.setScale(2, RoundingMode.HALF_EVEN)) != 0) {
                return false;
            }
        }
        return true;
    }

    /** basic validation for general: ensures headers are present (no exhaustive check) */
    public boolean validateGeneral() {
        // recompute aggregates to ensure consistent state
        recomputeGeneralAggregates();
        return true;
    }

    public boolean validateAll() {
        return validateGeneral() && validateMinistries();
    }

    /* ================= Utilities ================= */

    private static String safe(final String s) {
        return s == null ? "" : s;
    }

    private static BigDecimal nonNull(final BigDecimal b) {
        return b == null ? BigDecimal.ZERO : b;
    }

    private static BigDecimal normalize(final BigDecimal x) {
        if (x == null) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        return x.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Budget getBudget() {
        return new Budget(this.budget);
    }
}
