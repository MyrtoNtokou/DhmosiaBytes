package ministryrequests;

/**
 * Represents the type of budget affected by a ministry request.
 */
public enum RequestType {
    /** RegularBudget ministry budget. */
    REGULARBUDGET,
    /** Investment ministry budget. */
    PUBLIC_INVESTMENTS,
    /** Applies to both ministry budget types. */
    BOTH
}
