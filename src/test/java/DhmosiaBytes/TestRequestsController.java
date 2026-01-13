package dhmosiabytes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ministryrequests.MinistryRequest;
import ministryrequests.MinistryRequestService;
import ministryrequests.RequestStatus;
import ministryrequests.RequestType;

class TestRequestsController {

    private DummyMinistryRequestService dummyService;
    private MinistryRequest testRequest1;
    private MinistryRequest testRequest2;

    @BeforeEach
    void setup() {
        dummyService = new DummyMinistryRequestService();

        testRequest1 = new MinistryRequest(
                1, 100, "Υπουργείο Α", RequestType.REGULARBUDGET,
                RequestStatus.REVIEWED_BY_FINANCE_MINISTRY,
                LocalDateTime.now(), "Αίτημα 1"
        );

        testRequest2 = new MinistryRequest(
                2, 101, "Υπουργείο Β", RequestType.PUBLIC_INVESTMENTS,
                RequestStatus.REVIEWED_BY_FINANCE_MINISTRY,
                LocalDateTime.now(), "Αίτημα 2"
        );

        dummyService.addRequest(testRequest1);
        dummyService.addRequest(testRequest2);
    }

    @Test
    void testApproveRequest() {
        int idToApprove = testRequest1.getId();
        dummyService.approveByGovernment(idToApprove);

        MinistryRequest updated = dummyService.getRequestById(idToApprove);
        assertNotNull(updated);
        assertEquals(RequestStatus.GOVERNMENT_APPROVED, updated.getStatus(),
                "Το αίτημα πρέπει να είναι πλέον GOVERNMENT_APPROVED");
    }

    @Test
    void testRejectRequest() {
        int idToReject = testRequest2.getId();
        dummyService.markRejected(idToReject);

        MinistryRequest updated = dummyService.getRequestById(idToReject);
        assertNotNull(updated);
        assertEquals(RequestStatus.REJECTED, updated.getStatus(),
                "Το αίτημα πρέπει να είναι πλέον REJECTED");
    }

    static class DummyMinistryRequestService extends MinistryRequestService {
        private final List<MinistryRequest> requests = new ArrayList<>();

        public void addRequest(MinistryRequest r) {
            requests.add(r);
        }

        public MinistryRequest getRequestById(int id) {
            return requests.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        }

        public List<MinistryRequest> getByStatusAndType(RequestStatus status, Object type) {
            List<MinistryRequest> result = new ArrayList<>();
            for (MinistryRequest r : requests) {
                if (r.getStatus() == status) {
                    result.add(r);
                }
            }
            return result;
        }

        @Override
        public void approveByGovernment(int id) {
            MinistryRequest r = getRequestById(id);
            if (r != null) r.setStatus(RequestStatus.GOVERNMENT_APPROVED);
        }

        @Override
        public void markRejected(int id) {
            MinistryRequest r = getRequestById(id);
            if (r != null) r.setStatus(RequestStatus.REJECTED);
        }
    }
}
