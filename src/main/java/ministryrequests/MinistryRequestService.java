package ministryrequests;

import java.time.LocalDateTime;
import java.util.List;

import budgetreader.Ministry;

/**
 * Service layer for handling ministry requests and business logic.
 */
public class MinistryRequestService {

    /** MinistryRequestRepository object. */
    private final MinistryRequestRepository repo =
                    new MinistryRequestRepository();

    /**
     * Submit a new ministry request and store it as PENDING.
     * @param ministry the ministry submitting the request
     * @param rawDiff the raw budget differences text
     * @param type the request type
     * @return the submited request id
     */
    public int submitRequest(final Ministry ministry, final String rawDiff,
                                    final RequestType type) {

        String title = "ΑΛΛΑΓΕΣ ΣΤΟΝ ΠΡΟΫΠΟΛΟΓΙΣΜΟ ΤΩΝ ΥΠΟΥΡΓΕΙΩΝ";
        String clean = RequestTextFormatter.formatForSaving(rawDiff, title);
        String normalized = RequestTextFormatter.normalize(clean);

        MinistryRequest request = new MinistryRequest(
                0,
                ministry.getcode(),
                ministry.getName(),
                type,
                RequestStatus.PENDING,
                LocalDateTime.now(),
                normalized);

        MinistryRequest saved = repo.saveNew(request);
        return saved.getId();
    }

    /**
     * Mark a request as completed.
     * @param id the request id
     */
    public void markCompleted(final int id) {
        repo.updateStatus(id, RequestStatus.COMPLETED);
        System.out.println("Η αλλαγή με κωδικό " + id
                            + " αξιολογήθηκε θετικά.");
    }

    /**
     * Marks a request as rejected.
     * @param id the request id
     */
    public void markRejected(final int id) {
        repo.updateStatus(id, RequestStatus.REJECTED);
        System.out.println("Το αίτημα με κωδικό " + id + " απορρίφθηκε.");
    }

    /**
     * Return all pending requests of the given type.
     * @param type the request type
     * @return list of pending requests
     */
    public List<MinistryRequest> getPendingByType(final RequestType type) {
        return repo.findByStatusAndType(RequestStatus.PENDING, type);
    }

    /**
     * Return requests filtered by status and type.
     * @param status the request status
     * @param type the request type
     * @return list of matching requests
     */
    public List<MinistryRequest> getByStatusAndType(final RequestStatus status,
                                            final RequestType type) {
        return repo.findByStatusAndType(status, type);
    }

    /**
     * Mark request as reviewed by the finance ministry.
     * @param id request id
     */
    public void reveiwByFinanceMinistry(final int id) {
        repo.updateStatus(id, RequestStatus.REVIEWED_BY_FINANCE_MINISTRY);
        System.out.println("Η αλλαγή με κωδικό " + id
        + " υποβλήθηκε για έγκριση από την κυβέρνηση.");
    }

    /**
     * Mark request as approved by the government.
     * @param id request id
     */
    public void approveByGovernment(final int id) {
        repo.updateStatus(id, RequestStatus.GOVERNMENT_APPROVED);
        System.out.println("Η αλλαγή με κωδικό " + id
        + " υποβλήθηκε για τελική έγκριση από το Κοινοβούλιο.");
    }

    /**
     * Mark request as approved by the Parliament.
     * @param id request id
     */
    public void approveByParliament(final int id) {
        repo.updateStatus(id, RequestStatus.PARLIAMENT_APPROVED);
        System.out.println("Η αλλαγή με κωδικό " + id
                            + " καταχωρήθηκε επιτυχώς.");
    }
}
