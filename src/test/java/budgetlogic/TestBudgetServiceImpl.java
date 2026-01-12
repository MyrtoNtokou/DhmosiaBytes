package budgetlogic;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBudgetServiceImpl {

    private Budget budget;
    private BudgetServiceImpl service;

    @BeforeEach
    void setUp() {

        Map<String, Eggrafi> revenues = new LinkedHashMap<>();
        revenues.put("R1", new Eggrafi("R1", "ΕΣΟΔΑ", BigDecimal.ZERO));
        revenues.put("R2", new Eggrafi("R2", "Φόροι", new BigDecimal("1000")));

        Map<String, Eggrafi> expenses = new LinkedHashMap<>();
        expenses.put("E1", new Eggrafi("E1", "ΕΞΟΔΑ", BigDecimal.ZERO));
        expenses.put("E2", new Eggrafi("E2", "Μισθοί", new BigDecimal("400")));
        expenses.put("E3", new Eggrafi("E3", "Συντήρηση", new BigDecimal("200")));
        expenses.put("E4", new Eggrafi("E4", "ΑΠΟΤΕΛΕΣΜΑ", BigDecimal.ZERO));

        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();
        ministries.put(1, new Ypourgeio(1, "Υπουργείο Α", new BigDecimal("300"),
                new BigDecimal("100"), new BigDecimal("400")));
        ministries.put(2, new Ypourgeio(2, "Υπουργείο Β", new BigDecimal("200"),
                new BigDecimal("50"), new BigDecimal("250")));
        ministries.put(4, new Ypourgeio(4, "Σύνολο Υπουργείων", BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO));
        ministries.put(33, new Ypourgeio(33, "Σύνολο εξόδων", BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO));

        budget = new Budget(revenues, expenses, ministries);

        Map<Integer, Map<String, BigDecimal>> mapping = new LinkedHashMap<>();
        Map<String, BigDecimal> map1 = new LinkedHashMap<>();
        map1.put("E2", new BigDecimal("0.5"));
        map1.put("E3", new BigDecimal("0.5"));
        mapping.put(1, map1);

        service = new BudgetServiceImpl(budget, mapping);
    }

    @Test
    void testChangeGeneralAmount() {
        service.changeGeneralAmount("R2", new BigDecimal("2000"));
        BigDecimal newAmount = service.getBudget().getRevenues().get("R2").getPoso();
        assertEquals(new BigDecimal("2000.00"), newAmount);
    }

    @Test
    void testChangeMinistryAmountTaktikos() {
        service.changeMinistryAmount(1, "τακτικός", new BigDecimal("500"));
        Ypourgeio m = service.getBudget().getMinistries().get(1);
        assertEquals(new BigDecimal("500.00"), m.getTaktikos());
        assertEquals(new BigDecimal("600.00"), m.getSynolo()); // 500 + 100
    }

    @Test
    void testChangeMinistryAmountEpendyseis() {
        service.changeMinistryAmount(2, "ΠΔΕ", new BigDecimal("150"));
        Ypourgeio m = service.getBudget().getMinistries().get(2);
        assertEquals(new BigDecimal("150.00"), m.getEpendyseis());
        assertEquals(new BigDecimal("350.00"), m.getSynolo()); // 200 + 150
    }

    @Test
    void testValidateMinistries() {
        assertTrue(service.validateMinistries());
    }

    @Test
    void testValidateGeneral() {
        assertTrue(service.validateGeneral());
        Eggrafi revHeader = service.getBudget().getRevenues().get("R1");
        Eggrafi expHeader = service.getBudget().getExpenses().get("E1");
        Eggrafi resultRow = service.getBudget().getExpenses().get("E4");
        assertEquals(new BigDecimal("1000.00"), revHeader.getPoso());
        assertEquals(new BigDecimal("600.00"), expHeader.getPoso());
        assertEquals(new BigDecimal("400.00"), resultRow.getPoso());
    }

    @Test
    void testPropagationToExpenses() {
        service.changeMinistryAmount(1, "τακτικός", new BigDecimal("400"));
        Eggrafi e2 = service.getBudget().getExpenses().get("E2");
        Eggrafi e3 = service.getBudget().getExpenses().get("E3");

        BigDecimal total = e2.getPoso().add(e3.getPoso());
        assertEquals(new BigDecimal("700.00"), total);
    }

    @Test
    void testGetBudgetReturnsCopy() {
        Budget copy = service.getBudget();
        assertNotSame(budget, copy);
        assertEquals(budget.totalRevenues(), copy.totalRevenues());
    }
}
