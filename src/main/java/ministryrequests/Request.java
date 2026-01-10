package ministryrequests;

import java.time.LocalDateTime;

/**
 * Interface representing a generic request in the system.
 * <p>
 * Implementations may represent different request types
 * (e.g. ministry-related requests or revenue-related requests),
 * but must expose a unified API for printing and processing.
 */
public interface Request {
    /**
     * Returns the unique identifier of the request.
     *
     * @return the request ID
     */
    int getId();

    /**
     * Returns the main textual content of the request.
     * <p>
     * This may represent the full request text or a diff text,
     * depending on the implementation.
     *
     * @return the request text
     */
    String getText();

    /**
     * Returns the timestamp indicating when the request was created.
     *
     * @return the request creation timestamp
     */
    LocalDateTime getTimestamp();

    /**
     * Returns the current status of the request.
     *
     * @return the request status as a {@link RequestStatus} enum
     */
    RequestStatus getStatus(); // Επιστρέφει το Enum της εκάστοτε κλάσης

    /**
     * Prints request-specific details to the console.
     * <p>
     * Each implementation is responsible for defining what
     * additional information is printed (e.g. ministry name,
     * code, revenue-related metadata, etc.).
     */
    void printDetails();
}
