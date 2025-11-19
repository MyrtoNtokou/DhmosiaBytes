package dhmosiabytes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestBudgetUrlFinder {

    @Test
    public void testFindUrlForKnownYear() {
        String url = BudgetUrlFinder.findUrl(2020);
        assertNotNull(url, "Δεν επέστρεψε κανένα url");
        assertEquals("https://minfin.gov.gr/wp-content/uploads/2019/11/"
            + "21-11-2019-ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2020.pdf", url);
    }

    @Test
    public void testFindUrlForAnotherKnownYear() {
        String url = BudgetUrlFinder.findUrl(2025);
        assertNotNull(url, "Θα έπρεπε να επιστραφεί URL για το 2025");
        assertEquals("https://minfin.gov.gr/wp-content/uploads/2024/11/"
                + "Κρατικός-Προϋπολογισμός-2025_ΟΕ.pdf", url);
    }

    @Test
    public void testFindUrlForUnknownYear() {
        String url = BudgetUrlFinder.findUrl(2030);
        assertNull(url, "Δεν επέστρεψε null");
    }
}
