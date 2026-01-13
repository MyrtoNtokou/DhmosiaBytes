package budgetlogic;

import budgetreader.BasicRecord;
import budgetreader.Ministry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void testWriteGeneralWithFormatting() throws IOException {
        Path filePath = tempDir.resolve("test_general.csv");
        List<BasicRecord> data = List.of(
            new BasicRecord("1", "Έσοδα", new BigDecimal("1000000.00")),
            new BasicRecord("2", "Έξοδα", new BigDecimal("5500.50"))
        );

        BudgetWriter.writeGeneral(filePath.toString(), data);

        List<String> lines = Files.readAllLines(filePath);
        
        assertTrue(lines.get(0).contains("1.000.000"), "Το μεγάλο ποσό πρέπει να έχει τελείες");
        assertTrue(lines.get(1).contains("5.501") || lines.get(1).contains("5.500"), "Το ποσό πρέπει να είναι ακέραιο");
        
        System.out.println("General CSV Output: " + lines.get(0));
    }

    @Test
    void testWriteMinistriesWithFormatting() throws IOException {
  
        Path filePath = tempDir.resolve("test_ministries.csv");
        List<Ministry> data = List.of(
            new Ministry(1, "Υπουργείο Τεστ", 
                new BigDecimal("2000000"), 
                new BigDecimal("500000"), 
                new BigDecimal("2500000"))
        );

        BudgetWriter.writeMinistries(filePath.toString(), data);

        String content = Files.readString(filePath);

        assertTrue(content.contains("2.000.000"), "Ο τακτικός πρέπει να έχει τελείες");
        assertTrue(content.contains("500.000"), "Οι επενδύσεις πρέπει να έχουν τελείες");
        assertTrue(content.contains("2.500.000"), "Το σύνολο πρέπει να έχει τελείες");

        System.out.println("Ministries CSV Output: " + content.trim());
    }
}