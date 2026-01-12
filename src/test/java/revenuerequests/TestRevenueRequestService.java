package revenuerequests;

import ministryrequests.RequestStatus;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RevenueRequestService using a simple in-memory FakeRepo.
 */
class TestRevenueRequestService {

    /**
     * Α in-memory fake repository.
     */
    static class FakeRepo extends RevenueRequestRepository {
        private List<RevenueRequest> data = new ArrayList<>();
        private int nextId = 1;

        @Override
        public List<RevenueRequest> loadAll() {
            return new ArrayList<>(data);
        }

        @Override
        public void saveAll(List<RevenueRequest> list) {
            this.data = new ArrayList<>(list);
        }

        public int addWithId(RevenueRequest req) {
            int id = nextId++;
            data.add(req); 
            return id;
        }
    }

    @Test
    void testSubmitRevenueRequest() {
        FakeRepo repo = new FakeRepo();
        RevenueRequestService service = new RevenueRequestService(repo);

        int id = service.submitRevenueRequest(
                "1234",
                "Έσοδα ΦΠΑ",
                "ΑΛΛΑΓΕΣ ΣΤΑ ΕΣΟΔΑ\n+100000"
        );

        List<RevenueRequest> all = repo.loadAll();
        assertFalse(all.isEmpty());
        
        RevenueRequest saved = all.get(0);
        assertEquals("1234", saved.getRevenueCode());
        assertEquals("Έσοδα ΦΠΑ", saved.getRevenueName());
        assertEquals(RequestStatus.REVIEWED_BY_FINANCE_MINISTRY, saved.getStatus());
    }

    @Test
    void testApproveByGovernment() {
        FakeRepo repo = new FakeRepo();
        RevenueRequestService service = new RevenueRequestService(repo);

        RevenueRequest req = new RevenueRequest(
                10, "100", "Test",
                RequestStatus.REVIEWED_BY_FINANCE_MINISTRY,
                LocalDateTime.now(), "text"
        );
        repo.saveAll(List.of(req));

        service.approveByGovernment(10);

        assertEquals(RequestStatus.GOVERNMENT_APPROVED, repo.loadAll().get(0).getStatus());
    }

    @Test
    void testApproveByParliament() {
        FakeRepo repo = new FakeRepo();
        RevenueRequestService service = new RevenueRequestService(repo);

        RevenueRequest req = new RevenueRequest(
                20, "200", "Test",
                RequestStatus.GOVERNMENT_APPROVED,
                LocalDateTime.now(), "text"
        );
        repo.saveAll(List.of(req));

        service.approveByParliament(20);

        assertEquals(RequestStatus.PARLIAMENT_APPROVED, repo.loadAll().get(0).getStatus());
    }

    @Test
    void testRejectRequest() {
        FakeRepo repo = new FakeRepo();
        RevenueRequestService service = new RevenueRequestService(repo);

        RevenueRequest req = new RevenueRequest(
                30, "300", "Test",
                RequestStatus.REVIEWED_BY_FINANCE_MINISTRY,
                LocalDateTime.now(), "text"
        );
        repo.saveAll(List.of(req));

        service.rejectRequest(30);

        assertEquals(RequestStatus.REJECTED, repo.loadAll().get(0).getStatus());
    }

    @Test
    void testGetByStatus() {
        FakeRepo repo = new FakeRepo();
        RevenueRequestService service = new RevenueRequestService(repo);

        RevenueRequest r1 = new RevenueRequest(1, "1", "A", RequestStatus.REJECTED, LocalDateTime.now(), "x");
        RevenueRequest r2 = new RevenueRequest(2, "2", "B", RequestStatus.PENDING, LocalDateTime.now(), "y");
        
        repo.saveAll(List.of(r1, r2));

        List<RevenueRequest> rejected = service.getByStatus(RequestStatus.REJECTED);
        
        assertEquals(1, rejected.size());
        assertEquals(1, rejected.get(0).getId());
    }

    @Test
    void testUpdateStatusNotFound() {
        FakeRepo repo = new FakeRepo();
        RevenueRequestService service = new RevenueRequestService(repo);

        service.rejectRequest(999);

        assertTrue(repo.loadAll().isEmpty());
    }
}