package budgetlogic;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import budgetreader.*;

class BudgetWriterTest {

    @Test
    void testWriteGeneralAndReload() throws IOException {

        // TEMP FILE IN TARGET/
        Path temp = Files.createTempFile("general-test-", ".csv");

        List<Eggrafi> original = List.of(
                new Eggrafi("001", "Test Revenue A", new BigDecimal("1000")),
                new Eggrafi("002", "Test Revenue B", new BigDecimal("2500"))
        );

        // WRITE CSV
        BudgetWriter.writeGeneral(temp.toString(), original);

        // READ BACK USING THE LOADER
        List<Eggrafi> loaded = BudgetLoader.loadGeneral(temp.toString());

        assertEquals(original.size(), loaded.size());

        assertEquals("001", loaded.get(0).getKodikos());
        assertEquals("Test Revenue A", loaded.get(0).getPerigrafi());
        assertEquals(new BigDecimal("1000"), loaded.get(0).getPoso());

        assertEquals("002", loaded.get(1).getKodikos());
        assertEquals("Test Revenue B", loaded.get(1).getPerigrafi());
        assertEquals(new BigDecimal("2500"), loaded.get(1).getPoso());
    }

    @Test
    void testWriteMinistriesAndReload() throws IOException {

        // TEMP FILE IN TARGET/
        Path temp = Files.createTempFile("ministries-test-", ".csv");

        List<Ypourgeio> original = List.of(
                new Ypourgeio(10, "Min A",
                        new BigDecimal("500"),
                        new BigDecimal("300"),
                        new BigDecimal("800")),
                new Ministry(20, "Min B",
                        new BigDecimal("1000"),
                        new BigDecimal("2000"),
                        new BigDecimal("3000"))
        );

        // WRITE CSV
        BudgetWriter.writeMinistries(temp.toString(), original);

        // READ BACK USING THE LOADER
        List<Ypourgeio> loaded = BudgetLoader.loadMinistries(temp.toString());

        assertEquals(original.size(), loaded.size());

        assertEquals(10, loaded.get(0).getKodikos());
        assertEquals("Min A", loaded.get(0).getOnoma());
        assertEquals(new BigDecimal("500"), loaded.get(0).getTaktikos());
        assertEquals(new BigDecimal("300"), loaded.get(0).getEpendyseis());
        assertEquals(new BigDecimal("800"), loaded.get(0).getSynolo());

        assertEquals(20, loaded.get(1).getKodikos());
        assertEquals("Min B", loaded.get(1).getOnoma());
        assertEquals(new BigDecimal("1000"), loaded.get(1).getTaktikos());
        assertEquals(new BigDecimal("2000"), loaded.get(1).getEpendyseis());
        assertEquals(new BigDecimal("3000"), loaded.get(1).getSynolo());
    }
}
