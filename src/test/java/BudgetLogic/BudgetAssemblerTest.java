package budgetlogic;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.Test;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

class BudgetAssemblerTest {

    @Test
    void testSeparateAndMapGeneralRecords() throws Exception {
        BudgetAssembler assembler = new BudgetAssembler();

        // Mock data for general budget
        List<Eggrafi> generalList = List.of(
            new Eggrafi("1", "ΕΣΟΔΑ ΤΑΜΕΙΟΥ", BigDecimal.valueOf(1000)),
            new Eggrafi("2", "ΦΟΡΟΙ", BigDecimal.valueOf(500)),
            new Eggrafi("3", "ΕΞΟΔΑ ΔΗΜΟΣΙΑΣ ΔΙΟΙΚΗΣΗΣ", BigDecimal.valueOf(400)),
            new Eggrafi("4", "ΜΙΣΘΟΙ", BigDecimal.valueOf(200)),
            new Eggrafi("5", "ΑΠΟΤΕΛΕΣΜΑ", BigDecimal.valueOf(900))
        );

        // Maps to store separated revenues and expenses
        Map<String, Eggrafi> revenuesMap = new LinkedHashMap<>();
        Map<String, Eggrafi> expensesMap = new LinkedHashMap<>();

        // Call the private method using reflection
        var method = BudgetAssembler.class
                .getDeclaredMethod("separateAndMapGeneralRecords",
                        List.class, Map.class, Map.class);
        method.setAccessible(true);
        method.invoke(assembler, generalList, revenuesMap, expensesMap);

        // Assertions for revenues
        assertEquals(2, revenuesMap.size());
        assertTrue(revenuesMap.containsKey("1"));
        assertTrue(revenuesMap.containsKey("2"));

        // Assertions for expenses
        assertEquals(3, expensesMap.size());
        assertTrue(expensesMap.containsKey("3"));
        assertTrue(expensesMap.containsKey("4"));
        assertTrue(expensesMap.containsKey("5")); // RESULT is included in expenses
    }

    @Test
    void testMapMinistryRecords() throws Exception {
        BudgetAssembler assembler = new BudgetAssembler();

        // Mock data for ministries
        List<Ypourgeio> ministries = List.of(
            new Ypourgeio(1, "Υπουργείο Α", BigDecimal.valueOf(1000),
                           BigDecimal.valueOf(200), BigDecimal.valueOf(1200)),
            new Ypourgeio(2, "Υπουργείο Β", BigDecimal.valueOf(1500),
                           BigDecimal.valueOf(300), BigDecimal.valueOf(1800))
        );

        // Call the private method using reflection
        var method = BudgetAssembler.class
                .getDeclaredMethod("mapMinistryRecords", List.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<Integer, Ypourgeio> map = (Map<Integer, Ypourgeio>) method.invoke(assembler, ministries);

        // Assertions for the ministry map
        assertNotNull(map);
        assertEquals(2, map.size());
        assertTrue(map.containsKey(1));
        assertTrue(map.containsKey(2));

        // Check the ministry objects
        Ypourgeio yp1 = map.get(1);
        assertEquals("Υπουργείο Α", yp1.getOnoma());
        assertEquals(BigDecimal.valueOf(1200), yp1.getSynolo());

        Ypourgeio yp2 = map.get(2);
        assertEquals("Υπουργείο Β", yp2.getOnoma());
        assertEquals(BigDecimal.valueOf(1800), yp2.getSynolo());
    }
}
