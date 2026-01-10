package budgetlogic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class BudgetSaveTest {

    private BudgetSave budgetSave;

    @BeforeEach
    void setUp() {
        budgetSave = new BudgetSave();
    }

    @Test
    void testPrepareRecordsOrdersCorrectly() throws Exception {
        // Create mock Budget
        Map<String, Eggrafi> revenues = new LinkedHashMap<>();
        Map<String, Eggrafi> expenses = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        Eggrafi revenue1 = new Eggrafi("R1", "Revenue 1", BigDecimal.valueOf(100));
        Eggrafi revenue2 = new Eggrafi("R2", "ΑΠΟΤΕΛΕΣΜΑ", BigDecimal.valueOf(50));
        Eggrafi expense1 = new Eggrafi("E1", "Expense 1", BigDecimal.valueOf(30));
        Eggrafi expense2 = new Eggrafi("E2", "Expense 2", BigDecimal.valueOf(20));

        revenues.put(revenue1.getKodikos(), revenue1);
        revenues.put(revenue2.getKodikos(), revenue2);
        expenses.put(expense1.getKodikos(), expense1);
        expenses.put(expense2.getKodikos(), expense2);

        Ypourgeio yp = new Ypourgeio(1, "Ministry A", BigDecimal.valueOf(1000),
                                       BigDecimal.valueOf(200), BigDecimal.valueOf(1200));
        ministries.put(yp.getKodikos(), yp);

        Budget budget = new Budget(revenues, expenses, ministries);

        // Access private method prepareRecords via reflection
        Method method = BudgetSave.class.getDeclaredMethod("prepareRecords", Budget.class);
        method.setAccessible(true);
        Object preparedRecords = method.invoke(budgetSave, budget);

        // Use reflection on the record's accessors
        Method generalAccessor = preparedRecords.getClass().getMethod("general");
        Method ministriesAccessor = preparedRecords.getClass().getMethod("ministries");

        @SuppressWarnings("unchecked")
        List<Eggrafi> generalList = (List<Eggrafi>) generalAccessor.invoke(preparedRecords);
        @SuppressWarnings("unchecked")
        List<Ypourgeio> ministryList = (List<Ypourgeio>) ministriesAccessor.invoke(preparedRecords);

        // Assertions: revenues first (without result), then expenses, then result
        assertEquals(4, generalList.size());
        assertEquals("Revenue 1", generalList.get(0).getPerigrafi());
        assertEquals("Expense 1", generalList.get(1).getPerigrafi());
        assertEquals("Expense 2", generalList.get(2).getPerigrafi());
        assertEquals("ΑΠΟΤΕΛΕΣΜΑ", generalList.get(3).getPerigrafi());

        // Ministry list
        assertEquals(1, ministryList.size());
        assertEquals("Ministry A", ministryList.get(0).getOnoma());
    }

    @Test
    void testSaveGeneralChangesCallsWriter() throws IOException {
        // Mock Budget and BudgetWriter
        Map<String, Eggrafi> revenues = new LinkedHashMap<>();
        Map<String, Eggrafi> expenses = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        revenues.put("R1", new Eggrafi("R1", "Revenue", BigDecimal.valueOf(100)));
        Budget budget = new Budget(revenues, expenses, ministries);

        try (MockedStatic<BudgetWriter> writerMock = Mockito.mockStatic(BudgetWriter.class)) {
            budgetSave.saveGeneralChanges(budget, "general.csv");

            writerMock.verify(() -> BudgetWriter.writeGeneral(eq("general.csv"), anyList()));
        }
    }

    @Test
    void testSaveMinistryChangesCallsWriter() throws IOException {
        Map<String, Eggrafi> revenues = new LinkedHashMap<>();
        Map<String, Eggrafi> expenses = new LinkedHashMap<>();
        Map<Integer, Ypourgeio> ministries = new LinkedHashMap<>();

        Ypourgeio yp = new Ypourgeio(1, "Ministry", BigDecimal.valueOf(1000),
                                       BigDecimal.valueOf(200), BigDecimal.valueOf(1200));
        ministries.put(1, yp);
        Budget budget = new Budget(revenues, expenses, ministries);

        try (MockedStatic<BudgetWriter> writerMock = Mockito.mockStatic(BudgetWriter.class)) {
            budgetSave.saveMinistryChanges(budget, "ministries.csv");

            writerMock.verify(() -> BudgetWriter.writeMinistries(eq("ministries.csv"), anyList()));
        }
    }
}
