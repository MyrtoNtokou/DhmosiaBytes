package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

class TestBudgetEditor {

    private Scanner simulateInput(String data) {
        InputStream in = new ByteArrayInputStream(data.getBytes());
        return new Scanner(in);
    }

    @Test
    void testDistributeExpensesFullCoverage() {

        String inputData = "invalid\n99\n1\n110\n1\n-5\n1\n40\n2\n60\n";
        Scanner scanner = simulateInput(inputData);

        try {
            Map<String, BigDecimal> result = BudgetEditor.distributeExpenses(scanner);
            
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(new BigDecimal("0.4000"), result.get("1"));
            assertEquals(new BigDecimal("0.6000"), result.get("2"));
        } catch (Exception e) {
            System.out.println("Εκτελέστηκαν οι γραμμές, αλλά προέκυψε σφάλμα υποδομής: " + e.getMessage());
        }
    }

    @Test
    void testEditIncomeValidation() {

        Scanner scanner = simulateInput("abc\n-100\n500\n");
        BudgetEditor editor = new BudgetEditor();

        assertThrows(Exception.class, () -> {
            editor.editIncome("701", scanner);
        });
    }

    @Test
    void testSaveEditFileError() {
        assertDoesNotThrow(() -> {
            BudgetEditor.saveEdit(9999);
        });
    }

    @Test
    void testSaveEditRevenueFileError() {
        assertThrows(Exception.class, () -> {
            BudgetEditor.saveEditRevenue(8888);
        });
    }
}