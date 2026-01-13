package ministryrequests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

class TestMinistryRequestRepository {

    private MinistryRequestRepository repository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        Path tempFile = tempDir.resolve("test_ministry_requests.txt");
        repository = new MinistryRequestRepository(tempFile);
    }

    @Test
    void testSaveNewAndLoadAll() {
        MinistryRequest request = new MinistryRequest(
                0, 23, "Υπουργείο Οικονομικών", 
                RequestType.TAKTIKOS, RequestStatus.PENDING, 
                LocalDateTime.now(), "Δοκιμαστικό κείμενο αλλαγής"
        );

        MinistryRequest saved = repository.saveNew(request);
        assertNotNull(saved);
        assertEquals(1, saved.getId(), "Το πρώτο ID πρέπει να είναι 1");

        List<MinistryRequest> all = repository.loadAll();
        assertEquals(1, all.size());
        assertEquals("Υπουργείο Οικονομικών", all.get(0).getMinistryName());
    }

    @Test
    void testUpdateStatus() {
        MinistryRequest request = new MinistryRequest(0, 10, "Υπουργείο", RequestType.TAKTIKOS, RequestStatus.PENDING, LocalDateTime.now(), "Text");
        MinistryRequest saved = repository.saveNew(request);

        repository.updateStatus(saved.getId(), RequestStatus.GOVERNMENT_APPROVED);

        List<MinistryRequest> updated = repository.loadAll();
        assertEquals(RequestStatus.GOVERNMENT_APPROVED, updated.get(0).getStatus());
    }

    @Test
    void testFindByStatusAndType() {
        repository.saveNew(new MinistryRequest(0, 1, "M1", RequestType.TAKTIKOS, RequestStatus.PENDING, LocalDateTime.now(), "T1"));
        repository.saveNew(new MinistryRequest(0, 2, "M2", RequestType.EPENDYSEIS, RequestStatus.REJECTED, LocalDateTime.now(), "T2"));

        List<MinistryRequest> pendingOnly = repository.findByStatusAndType(RequestStatus.PENDING, null);
        assertEquals(1, pendingOnly.size());
        assertEquals("M1", pendingOnly.get(0).getMinistryName());

        List<MinistryRequest> bothTypes = repository.findByStatusAndType(null, RequestType.BOTH);
        assertEquals(2, bothTypes.size());
    }
}