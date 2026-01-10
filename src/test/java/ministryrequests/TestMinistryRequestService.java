package ministryrequests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import budgetreader.Ypourgeio;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMinistryRequestService {

    private MinistryRequestService service;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeAll
    void setupAll() {
        originalOut = System.out;
    }

    @AfterAll
    void restoreSystemOut() {
        System.setOut(originalOut);
    }

    @BeforeEach
    void setup() {
        service = new MinistryRequestService();

        // Capture System.out
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Clean repository file for a fresh start
        new java.io.File("ministryrequests.txt").delete();
    }

    @Test
    void submitRequest_createsPendingRequest() {
        // Use the new constructor of Ypourgeio
        Ypourgeio ministry = new Ypourgeio(
                100,
                "Υπουργείο Παιδείας",
                new BigDecimal("1000"),
                new BigDecimal("500"),
                new BigDecimal("1500")
        );

        String rawText = "Τακτικός: 1000 → 1200 (+200)";
        int id = service.submitRequest(ministry, rawText, RequestType.TAKTIKOS);

        assertEquals(1, id, "First request should have ID 1");

        List<MinistryRequest> loaded = service.getByStatusAndType(RequestStatus.PENDING, RequestType.TAKTIKOS);
        assertEquals(1, loaded.size());
        MinistryRequest r = loaded.get(0);

        assertEquals("Υπουργείο Παιδείας", r.getMinistryName());
        assertEquals(RequestStatus.PENDING, r.getStatus());
        assertEquals("Τακτικός: 1000 → 1200 (+200)", r.getText());
    }

    @Test
    void formatForSaving_removesAnsiAndHeader() {
        String raw = "\u001B[32mΤακτικός: 1000 → 1200 (+200)\u001B[0m\nΑΛΛΑΓΕΣ ΣΤΟΝ ΠΡΟΫΠΟΛΟΓΙΣΜΟ ΤΩΝ ΥΠΟΥΡΓΕΙΩΝ";
        String cleaned = service.formatForSaving(raw);

        assertFalse(cleaned.contains("\u001B[32m"));
        assertFalse(cleaned.contains("ΑΛΛΑΓΕΣ ΣΤΟΝ ΠΡΟΫΠΟΛΟΓΙΣΜΟ"));
        assertTrue(cleaned.contains("Τακτικός: 1000 → 1200 (+200)"));
    }

    @Test
    void markCompleted_updatesStatusAndPrints() {
        Ypourgeio ministry = new Ypourgeio(
                101,
                "Υπουργείο Υγείας",
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
        );

        int id = service.submitRequest(ministry, "Επενδύσεις: 500 → 700 (+200)", RequestType.EPENDYSEIS);

        service.markCompleted(id);

        List<MinistryRequest> loaded = service.getByStatusAndType(RequestStatus.COMPLETED, null);
        assertEquals(1, loaded.size());
        assertEquals(id, loaded.get(0).getId());

        String out = outContent.toString();
        assertTrue(out.contains("Η αλλαγή με κωδικό " + id + " αξιολογήθηκε θετικά."));
    }

    @Test
    void workflowFinanceAndGovernment() {
        Ypourgeio ministry = new Ypourgeio(
                103,
                "Υπουργείο Πολιτισμού",
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
        );

        int id = service.submitRequest(ministry, "Τακτικός: 500 → 600 (+100)", RequestType.BOTH);

        service.reveiwByFinanceMinistry(id);
        List<MinistryRequest> r1 = service.getByStatusAndType(RequestStatus.REVIEWED_BY_FINANCE_MINISTRY, null);
        assertEquals(1, r1.size());

        service.approveByGovernment(id);
        List<MinistryRequest> r2 = service.getByStatusAndType(RequestStatus.GOVERNMENT_APPROVED, null);
        assertEquals(1, r2.size());

        service.approveByParliament(id);
        List<MinistryRequest> r3 = service.getByStatusAndType(RequestStatus.PARLIAMENT_APPROVED, null);
        assertEquals(1, r3.size());

        String out = outContent.toString();
        assertTrue(out.contains("υποβλήθηκε για έγκριση από την κυβέρνηση"));
        assertTrue(out.contains("υποβλήθηκε για τελική έγκριση από το Κοινοβούλιο"));
        assertTrue(out.contains("καταχωρήθηκε επιτυχώς"));
    }

    @Test
    void getPendingByType_returnsCorrectRequests() {
        Ypourgeio y1 = new Ypourgeio(1, "Υπουργείο Α", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        Ypourgeio y2 = new Ypourgeio(2, "Υπουργείο Β", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        service.submitRequest(y1, "Τακτικός: 100 → 150 (+50)", RequestType.TAKTIKOS);
        service.submitRequest(y2, "Επενδύσεις: 200 → 300 (+100)", RequestType.EPENDYSEIS);

        List<MinistryRequest> taktikosPending = service.getPendingByType(RequestType.TAKTIKOS);
        assertEquals(1, taktikosPending.size());
        assertEquals("Υπουργείο Α", taktikosPending.get(0).getMinistryName());
    }
}
