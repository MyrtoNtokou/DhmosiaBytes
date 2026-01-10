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
    /** TAKTIKOS / EPENDYSEIS / BOTH. */
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
     * Constructor.
     * @param identification
     * @param minCode
     * @param minName
     * @param ty
     * @param s
     * @param stamp
     * @param t
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
     * @param s
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
