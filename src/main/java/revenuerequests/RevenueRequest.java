package revenuerequests;

import java.time.LocalDateTime;
import ministryrequests.Request;
import ministryrequests.RequestStatus;

/**
 * Represents a revenue-related request.
 * <p>
 * This class implements the {@link Request} interface in order to be
 * compatible with shared infrastructure components such as
 * {@code RequestPrinter}.
 */
public class RevenueRequest implements Request {

    /** Unique number of request. */
    private final int id;
    /** Revenue code. */
    private final String revenueCode;
    /** Revenue name. */
    private final String revenueName;
    /** PENDING / COMPLETED / REJECTED. */
    private RequestStatus status;
    /** Date and time of request sumbition. */
    private final LocalDateTime timestamp;
    /** Change that has been requested. */
    private final String diffText;

    /**
     * Creates a new {@code RevenueRequest}.
     *
     * @param identification the unique request identifier
     * @param revCode the revenue code
     * @param revName the revenue name
     * @param s the current request status
     * @param time the request creation timestamp
     * @param diff the diff or textual content of the request
     */
    public RevenueRequest(final int identification,
                        final String revCode,
                        final String revName,
                        final RequestStatus s,
                        final LocalDateTime time,
                        final String diff) {
        id = identification;
        revenueCode = revCode;
        revenueName = revName;
        status = s;
        timestamp = time;
        diffText = diff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Updates the status of the request.
     *
     * @param s the new {@link RequestStatus}
     */
    public void setStatus(final RequestStatus s) {
        status = s;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return diffText;
    }

    /**
     * Returns the textual content of the request.
     * <p>
     * In revenue requests, this typically represents a diff text.
     */
    @Override
    public void printDetails() {
        System.out.println("Έσοδο: "
        + revenueName + " (κωδικός "
        + revenueCode + ")");
    }

    /**
     * Prints revenue-specific details.
     * <p>
     * This method is invoked automatically by {@code RequestPrinter}
     * through polymorphism.
     *
     * @return the requested revenue code
     */
    public String getRevenueCode() {
        return revenueCode;
    }

    /**
     * Returns the revenue name.
     *
     * @return the revenue name
     */
    public String getRevenueName() {
        return revenueName;
    }
}
