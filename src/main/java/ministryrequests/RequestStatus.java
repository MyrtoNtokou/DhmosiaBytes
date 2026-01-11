package ministryrequests;

/**
 * Represent the lifecycle status of a budget request.
 */
public enum RequestStatus {
    /** Initial submission by the ministry. */
    PENDING,
    /** Reviewed by the Finance Ministry. */
    REVIEWED_BY_FINANCE_MINISTRY,
    /** Approved by the Government. */
    GOVERNMENT_APPROVED,
    /** Approved by Parliament. */
    PARLIAMENT_APPROVED,
    /** Finalized and applied to the csv budgets. */
    MODIFIED,
    /** Rejected at any stage of the process. */
    REJECTED
}
