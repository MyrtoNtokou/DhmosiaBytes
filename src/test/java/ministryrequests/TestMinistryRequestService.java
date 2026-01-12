package ministryrequests;

import budgetreader.Ypourgeio;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TestMinistryRequestService {

    /**
     * Fake repository που αποθηκεύει τα δεδομένα σε μια λίστα στη μνήμη.
     */
    static class FakeRepo extends MinistryRequestRepository {
        private List<MinistryRequest> data = new ArrayList<>();
        private int nextId = 1;

        @Override
        public MinistryRequest saveNew(MinistryRequest request) {
            MinistryRequest saved = new MinistryRequest(
                    nextId++,
                    request.getMinistryCode(),
                    request.getMinistryName(),
                    request.getType(),
                    request.getStatus(),
                    request.getTimestamp(),
                    request.getText());
            data.add(saved);
            return saved;
        }

        @Override
        public void updateStatus(int id, RequestStatus status) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == id) {
                    MinistryRequest old = data.get(i);
                    old.setStatus(status); 
                    return;
                }
            }
        }

        @Override
        public List<MinistryRequest> findByStatusAndType(RequestStatus status, RequestType type) {
            return data.stream()
                    .filter(r -> r.getStatus() == status && r.getType() == type)
                    .collect(Collectors.toList());
        }

        public List<MinistryRequest> getAll() {
            return data;
        }
    }

    @Test
    void testSubmitRequest() {
        FakeRepo repo = new FakeRepo();
        MinistryRequestService service = new MinistryRequestService(repo);

        Ypourgeio min = new Ypourgeio(10, "Υπουργείο Παιδείας", 
                                     BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        int id = service.submitRequest(min, "Αύξηση κονδυλίων +1000", RequestType.TAKTIKOS);

        assertEquals(1, id);
        assertEquals(1, repo.getAll().size());
        
        MinistryRequest saved = repo.getAll().get(0);
        assertEquals(10, saved.getMinistryCode());
        assertEquals(RequestStatus.PENDING, saved.getStatus());
        assertEquals(RequestType.TAKTIKOS, saved.getType());
    }

    @Test
    void testApproveFlow() {
        FakeRepo repo = new FakeRepo();
        MinistryRequestService service = new MinistryRequestService(repo);

        MinistryRequest req = new MinistryRequest(5, 20, "Υπ. Υγείας", 
                RequestType.TAKTIKOS, RequestStatus.PENDING, LocalDateTime.now(), "text");
        repo.data.add(req);

        service.reveiwByFinanceMinistry(5);
        assertEquals(RequestStatus.REVIEWED_BY_FINANCE_MINISTRY, repo.getAll().get(0).getStatus());

        service.approveByGovernment(5);
        assertEquals(RequestStatus.GOVERNMENT_APPROVED, repo.getAll().get(0).getStatus());

        service.approveByParliament(5);
        assertEquals(RequestStatus.PARLIAMENT_APPROVED, repo.getAll().get(0).getStatus());
    }

    @Test
    void testMarkRejected() {
        FakeRepo repo = new FakeRepo();
        MinistryRequestService service = new MinistryRequestService(repo);

        MinistryRequest req = new MinistryRequest(1, 10, "Test", 
                RequestType.TAKTIKOS, RequestStatus.PENDING, LocalDateTime.now(), "text");
        repo.data.add(req);

        service.markRejected(1);
        assertEquals(RequestStatus.REJECTED, repo.getAll().get(0).getStatus());
    }
}