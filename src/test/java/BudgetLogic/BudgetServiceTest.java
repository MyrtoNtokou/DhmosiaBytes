package budgetlogic;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetlogic.Budget;
import budgetlogic.BudgetService;
import budgetlogic.BudgetServiceImpl;
import budgetreader.*;

class BudgetServiceTest {

    private Budget budget;
    private Map<Integer, Map<String, BigDecimal>> mapping;

    @BeforeEach
    void setup() {
        // --- Create fake general table ---
        Map<String, Eggrafi> revenues = new LinkedHashMap<>();
        Map<String, Eggrafi> expenses = new LinkedHashMap<>();

        // HEADER ROWS
        revenues.put("revHeader", new Eggrafi("revHeader", "ΕΣΟΔΑ", bd(0)));
        expenses.put("expHeader", new Eggrafi("expHeader", "ΕΞΟΔΑ", bd(0)));
        expenses.put("result", new Eggrafi("result", "ΑΠΟΤΕΛΕΣΜΑ", bd(0)));

        // Example general entries
        revenues.put("100", new Eggrafi("100", "Φόροι", bd(100)));
        expenses.put("200", new Eggrafi("200", "Μισθοί", bd(50)));
        expenses.put("201", new Eggrafi("201", "Προμήθειες", bd(20)));

        // --- Create fake ministries table ---
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        // Ministry 10 (normal ministry)
        ministries.put(10, new Ypourgeio(10, "Υπουργείο Οικονομικών",
                bd(60), bd(40), bd(100)));

        // Ministry total row
        ministries.put(4, new Ypourgeio(4, "Υπουργεία Συνολικά",
                bd(60), bd(40), bd(100)));

        // Expense entries to be updated through propagation
        expenses.put("10A", new Eggrafi("10A", "Δαπάνη Α", bd(30)));
        expenses.put("10B", new Eggrafi("10B", "Δαπάνη Β", bd(70)));

        budget = new Budget(revenues, expenses, ministries);

        // Mapping: ministry 10 affects expenses 10A (70%), 10B (30%)
        mapping = new LinkedHashMap<>();
        Map<String, BigDecimal> m10 = new LinkedHashMap<>();
        m10.put("10A", new BigDecimal("0.70"));
        m10.put("10B", new BigDecimal("0.30"));
        mapping.put(10, m10);
    }

    private static BigDecimal bd(double x) {
        return BigDecimal.valueOf(x).setScale(2);
    }

   
    @Test
    void testChangeMinistryAmountRecomputesTotals() {
        BudgetService service = new BudgetServiceImpl(budget, mapping);

        service.changeMinistryAmount(10, "τακτικός", bd(80));

        Ypourgeio min = budget.getMinistries().get(10);

        assertEquals(bd(80), min.getTaktikos());
        assertEquals(bd(40), min.getEpendyseis());
        assertEquals(bd(120), min.getSynolo());

        // ministries total row should also update:
        Ypourgeio total = budget.getMinistries().get(4);
        assertEquals(bd(80), total.getTaktikos());
        assertEquals(bd(40), total.getEpendyseis());
        assertEquals(bd(120), total.getSynolo());
    }

    @Test
    void testPropagationWithMapping() {
        BudgetService service = new BudgetServiceImpl(budget, mapping);

        // Old total = 100 -> new total = 130 (increase +30)
        service.changeMinistryAmount(10, "ΠΔΕ", bd(60));

        // diff = +20 (total 120 -> 140)
        // mapping:
        // 10A = +20 * 0.70 = +14
        // 10B = +20 * 0.30 = +6

        assertEquals(bd(44), budget.getExpenses().get("10A").getPoso());
        assertEquals(bd(76), budget.getExpenses().get("10B").getPoso());
    }

    @Test
    void testValidateMinistriesPasses() {
        BudgetService service = new BudgetServiceImpl(budget, mapping);
        assertTrue(service.validateMinistries());
    }

    @Test
    void testValidateMinistriesFailsWhenInconsistent() {
        budget.getMinistries().get(10).setSynolo(bd(999)); // break consistency
        BudgetService service = new BudgetServiceImpl(budget, mapping);
        assertFalse(service.validateMinistries());
    }

    @Test
    void testValidateAll() {
        BudgetService service = new BudgetServiceImpl(budget, mapping);
        assertTrue(service.validateAll());
    }
}
