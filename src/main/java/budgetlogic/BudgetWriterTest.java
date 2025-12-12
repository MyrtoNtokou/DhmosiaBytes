package budgetlogic;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.math.BigDecimal;
import java.util.List;

public class BudgetWriterTest {

    @Test
    void testWriteGeneral() throws IOException {
        // Δημιουργία προσωρινού αρχείου
        Path tempFile = Files.createTempFile("general-test", ".csv");

        // Sample data
        List<Eggrafi> list = List.of(
                new Eggrafi("001", "Test Description 1", new BigDecimal("123.45")),
                new Eggrafi("002", "Test Description 2", new BigDecimal("678.90"))
        );

        // Κλήση του writer
        BudgetWriter.writeGeneral(tempFile.toString(), list);

        // Read output
        List<String> lines = Files.readAllLines(tempFile);

        // Assertions
        assertEquals(2, lines.size());
        assertEquals("001;Test Description 1;123.45", lines.get(0));
        assertEquals("002;Test Description 2;678.90", lines.get(1));
    }

    @Test
    void testWriteMinistries() throws IOException {
        // TEMP file
        Path tempFile = Files.createTempFile("ministries-test", ".csv");

        List<Ypourgeio> list = List.of(
                new Ypourgeio("10", "Υπ. Παιδείας",
                        new BigDecimal("1000"),
                        new BigDecimal("200"),
                        new BigDecimal("1200")),
                new Ypourgeio("20", "Υπ. Υγείας",
                        new BigDecimal("1500"),
                        new BigDecimal("500"),
                        new BigDecimal("2000"))
        );

        BudgetWriter.writeMinistries(tempFile.toString(), list);

        List<String> lines = Files.readAllLines(tempFile);

        assertEquals(2, lines.size());
        assertEquals("10;Υπ. Παιδείας;1000;200;1200", lines.get(0));
        assertEquals("20;Υπ. Υγείας;1500;500;2000", lines.get(1));
    }
}
