package budgetlogic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

class BudgetLoaderTest {

    private Path resourcePath(String name) {
        try {
            return Path.of(
                    getClass().getClassLoader().getResource(name).toURI()
            );
        } catch (URISyntaxException | NullPointerException e) {
            throw new RuntimeException("Resource not found: " + name, e);
        }
    }

    @Test
    void testLoadGeneral_RealCSV() throws IOException {
        Path csv = resourcePath("proypologismos2025.csv");

        List<BasicRecord> list = BudgetLoader.loadGeneral(csv.toString());

        assertNotNull(list);
        assertFalse(list.isEmpty(), "general CSV should not be empty!");

        // Example checks — adjust based on your real data:
        BasicRecord first = list.get(0);
        assertNotNull(first.getKodikos());
        assertNotNull(first.getPerigrafi());
        assertTrue(first.getPoso().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    void testLoadMinistries_RealCSV() throws IOException {
        Path csv = resourcePath("proypologismos2025anaypourgeio.csv");

        List<Ministry> list = BudgetLoader.loadMinistries(csv.toString());

        assertNotNull(list);
        assertFalse(list.isEmpty(), "ministries CSV should not be empty!");

        Ministry m = list.get(0);
        assertTrue(m.getKodikos() > 0);
        assertNotNull(m.getOnoma());
        assertTrue(m.getTaktikos().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(m.getPde().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(m.getSynolo().compareTo(BigDecimal.ZERO) >= 0);
    }
}
