package revenuerequests;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;
import ministryrequests.RequestStatus;
import ministryrequests.RequestTextFormatter;

/**
 * Service class responsible for handling business logic
 * related to revenue requests.
 */
public class RevenueRequestService {

    /** The repository used for managing revenue request data. */
    private final RevenueRequestRepository repo;

    /**
     * Constructor for dependency injection, primarily used for testing.
     * Allows the use of a mock or fake repository to isolate business logic
     * from data persistence.
     *
     * @param repository the {@link RevenueRequestRepository}
     * to be used by this service
     */
    public RevenueRequestService(final RevenueRequestRepository repository) {
        repo = repository;
    }

    /**
     * Default constructor for the application.
     * Initializes the service with a default instance of
     * {@link RevenueRequestRepository}.
     */
    public RevenueRequestService() {
        this.repo = new RevenueRequestRepository();
    }

    /**
     * Submits a new revenue request.
     * <p>
     * The method:
     * <ul>
     *   <li>Cleans the raw diff text</li>
     *   <li>Removes the predefined revenue title</li>
     *   <li>Normalizes whitespace and line breaks</li>
     *   <li>Generates a new incremental request ID</li>
     *   <li>Persists the request to the repository</li>
     * </ul>
     *
     * @param code the revenue code
     * @param name the revenue name
     * @param rawDiff the raw diff text
     * @return the ID of the newly created request
     */
    public int submitRevenueRequest(final String code,
            final String name, final String rawDiff) {
        // Clean and normalize text, removing the revenue title
        String titleToRemove = "ΑΛΛΑΓΕΣ ΣΤΑ ΕΣΟΔΑ ΤΟΥ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ";
        String cleanVersion = RequestTextFormatter
                            .formatForSaving(rawDiff, titleToRemove);
        String normalized = RequestTextFormatter.normalize(cleanVersion);

        List<RevenueRequest> all = repo.loadAll();
        int nextId = all.stream()
                    .mapToInt(RevenueRequest::getId).max().orElse(0) + 1;

        RevenueRequest newReq = new RevenueRequest(
            nextId, code, name,
            RequestStatus.REVIEWED_BY_FINANCE_MINISTRY,
            LocalDateTime.now(),
            normalized);

        all.add(newReq);
        repo.saveAll(all);
        return nextId;
    }

    /**
     * Approves a revenue request by the Government.
     *
     * @param id the request ID
     */
    public void approveByGovernment(final int id) {
        updateStatus(id, RequestStatus.GOVERNMENT_APPROVED);
        System.out.println(BOLD + "Η αλλαγή εσόδων με κωδικό " + id
        + " υποβλήθηκε για τελική έγκριση από το Κοινοβούλιο." + RESET);
    }

    /**
     * Approves a revenue request by Parliament.
     *
     * @param id the request ID
     */
    public void approveByParliament(final int id) {
        updateStatus(id, RequestStatus.PARLIAMENT_APPROVED);
        System.out.println(BOLD + "Η αλλαγή εσόδων με κωδικό " + id
                            + " καταχωρήθηκε επιτυχώς." + RESET);
    }

    /**
     * Rejects a revenue request.
     *
     * @param id the request ID
     */
    public void rejectRequest(final int id) {
        updateStatus(id, RequestStatus.REJECTED);
        System.out.println(BOLD + "Το αίτημα με κωδικό " + id
                        + " απορρίφθηκε." + RESET);
    }

    /**
     * Returns all revenue requests with the given status.
     *
     * @param status the {@link RequestStatus} to filter by
     * @return a list of matching revenue requests
     */
    public List<RevenueRequest> getByStatus(final RequestStatus status) {
        return repo.loadAll().stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status of a revenue request in the repository.
     * <p>
     * This method is private to enforce controlled state transitions
     * through higher-level approval methods.
     *
     * @param id the request ID
     * @param status the new {@link RequestStatus}
     */
    private void updateStatus(final int id, final RequestStatus status) {
        List<RevenueRequest> all = repo.loadAll();
        boolean found = false;
        for (RevenueRequest r : all) {
            if (r.getId() == id) {
                r.setStatus(status);
                found = true;
                break;
            }
        }
        if (found) {
            repo.saveAll(all);
        } else {
            System.err.println(RED + "Δεν βρέθηκε αίτημα εσόδων με ID: "
                    + id + RESET);
        }
    }
}
