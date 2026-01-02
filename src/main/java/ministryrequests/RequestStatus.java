package ministryrequests;

/**
 * Represent the current status of a ministry request.
 */
public enum RequestStatus {
    /** Request has been submitted and is waiting for review. */
    PENDING,
    /** Request has been approved and completed. */
    COMPLETED,
    /** Request has been rejected. */
    REJECTED
}
