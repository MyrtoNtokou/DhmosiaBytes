package ministryrequests;

import java.time.LocalDateTime;

/**
 * Keep the information of all requsts.
 */
public class MinistryRequest implements Request {

    /** Unique number of request. */
    private final int id;
    /** Ministry code. */
    private final int ministryCode;
    /** Ministry name. */
    private final String ministryName;
    /** REGULARBUDGET / PUBLIC_INVESTMENTS / BOTH. */
    private final RequestType type;
    /** PENDING / COMPLETED / REJECTED. */
    private RequestStatus status;
    /** Date and time of request sumbition. */
    private final LocalDateTime timestamp;
    /** Change that has been requested. */
    private final String text;
    /** Duplicate finder. */
    private final int hash;

    /**
     * Constructor for MinistryRequest.
     *
     * @param identification The unique ID of the request.
     * @param minCode The unique code of the ministry.
     * @param minName The official name of the ministry.
     * @param ty The type of request (REGULARBUDGET,
     * PUBLIC_INVESTMENTS, or BOTH).
     * @param s The current status of the request (PENDING, COMPLETED,
     * or REJECTED).
     * @param stamp The date and time when the request was submitted.
     * @param t The raw text containing the requested budget changes.
     */
    public MinistryRequest(final int identification,
                           final int minCode,
                           final String minName,
                           final RequestType ty,
                           final RequestStatus s,
                           final LocalDateTime stamp,
                           final String t) {
        id = identification;
        ministryCode = minCode;
        ministryName = minName;
        type = ty;
        status = s;
        timestamp = stamp;
        text = t;
        hash = t != null ? t.hashCode() : 0;
    }

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for ministry code.
     * @return ministry code
     */
    public int getMinistryCode() {
        return ministryCode;
    }

    /**
     * Getter for ministry name.
     * @return ministry name
     */
    public String getMinistryName() {
        return ministryName;
    }

    /**
     * Getter for type.
     * @return ministry budget type
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Getter for status.
     * @return request status
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Setter for status.
     * @param s status
     */
    public void setStatus(final RequestStatus s) {
        status = s;
    }

    /**
     * Getter for date and time.
     * @return date and time of request submition
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for text of the requested change.
     * @return text that contains the requested change
     */
    public String getText() {
        return text;
    }

    /**
     * Getter for duplicates.
     * @return int
     */
    public int getHash() {
        return hash;
    }

    /**
     * Print ministry details.
     */
    @Override
    public void printDetails() {
        System.out.println("Υπουργείο: " + ministryName
                        + " (κωδικός " + ministryCode + ")");
        System.out.println("Τύπος: " + type);
    }
}
