package ministryrequests;

import org.junit.jupiter.api.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMinistryRequestRepository {

    private MinistryRequestRepository repo;
    private static final String FILE = "ministryrequests.txt";

    @BeforeAll
    void backupFile() {
        // Backup existing file if exists
        File f = new File(FILE);
        if (f.exists()) {
            f.renameTo(new File(FILE + ".bak"));
        }
    }

    @AfterAll
    void restoreFile() {
        // Restore backup
        File f = new File(FILE);
        File backup = new File(FILE + ".bak");
        if (f.exists()) f.delete();
        if (backup.exists()) backup.renameTo(new File(FILE));
    }

    @BeforeEach
    void setUp() {
        repo = new MinistryRequestRepository();
        // Clean test file
        File f = new File(FILE);
        if (f.exists()) f.delete();
    }

    @Test
    void saveNew_and_loadAll_workCorrectly() {
        MinistryRequest r = new MinistryRequest(
                0, 100, "Υπουργείο Παιδείας",
                RequestType.TAKTIKOS, RequestStatus.PENDING,
                LocalDateTime.of(2026,1,10,12,0),
                "Τακτικός: 1000 → 1200 (+200)"
        );

        MinistryRequest saved = repo.saveNew(r);

        assertEquals(1, saved.getId(), "First saved request should have ID 1");

        List<MinistryRequest> loaded = repo.loadAll();
        assertEquals(1, loaded.size());

        MinistryRequest l = loaded.get(0);
        assertEquals(saved.getId(), l.getId());
        assertEquals(saved.getMinistryCode(), l.getMinistryCode());
        assertEquals(saved.getMinistryName(), l.getMinistryName());
        assertEquals(saved.getType(), l.getType());
        assertEquals(saved.getStatus(), l.getStatus());
        assertEquals(saved.getText(), l.getText());
    }

    @Test
    void updateStatus_changesStatusCorrectly() {
        MinistryRequest r = new MinistryRequest(
                0, 200, "Υπουργείο Υγείας",
                RequestType.EPENDYSEIS, RequestStatus.PENDING,
                LocalDateTime.now(),
                "Επενδύσεις: 500 → 700 (+200)"
        );

        MinistryRequest saved = repo.saveNew(r);
        repo.updateStatus(saved.getId(), RequestStatus.COMPLETED);

        List<MinistryRequest> loaded = repo.loadAll();
        assertEquals(RequestStatus.COMPLETED, loaded.get(0).getStatus());
    }

    @Test
    void findByStatusAndType_filtersCorrectly() {
        MinistryRequest r1 = new MinistryRequest(
                0, 100, "Υπουργείο Παιδείας",
                RequestType.TAKTIKOS, RequestStatus.PENDING,
                LocalDateTime.now(), "Δημιουργία νέου προϋπολογισμού"
        );

        MinistryRequest r2 = new MinistryRequest(
                0, 200, "Υπουργείο Υγείας",
                RequestType.EPENDYSEIS, RequestStatus.COMPLETED,
                LocalDateTime.now(), "Επενδυτικά έργα"
        );

        MinistryRequest r3 = new MinistryRequest(
                0, 300, "Υπουργείο Πολιτισμού",
                RequestType.BOTH, RequestStatus.PENDING,
                LocalDateTime.now(), "Συνδυαστικό αίτημα"
        );

        repo.saveNew(r1);
        repo.saveNew(r2);
        repo.saveNew(r3);

        // Filter by status PENDING
        List<MinistryRequest> pending = repo.findByStatusAndType(RequestStatus.PENDING, null);
        assertEquals(2, pending.size());

        // Filter by type TAKTIKOS
        List<MinistryRequest> taktikos = repo.findByStatusAndType(null, RequestType.TAKTIKOS);
        assertEquals(2, taktikos.size(), "Type TAKTIKOS should include BOTH requests too");

        // Filter by status COMPLETED and type EPENDYSEIS
        List<MinistryRequest> completedEpend = repo.findByStatusAndType(RequestStatus.COMPLETED, RequestType.EPENDYSEIS);
        assertEquals(1, completedEpend.size());
        assertEquals("Υπουργείο Υγείας", completedEpend.get(0).getMinistryName());
    }

    @Test
    void loadAll_returnsEmptyList_whenFileMissing() {
        File f = new File(FILE);
        if (f.exists()) f.delete();

        List<MinistryRequest> loaded = repo.loadAll();
        assertTrue(loaded.isEmpty(), "Should return empty list if file does not exist");
    }
}
