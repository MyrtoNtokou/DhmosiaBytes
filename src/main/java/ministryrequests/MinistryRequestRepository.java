package ministryrequests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for storing and loading,
 * MinistryRequest objects from a text file.
 */
public class MinistryRequestRepository {

    /** Requeste file name. */
    private static final String FILE = "ministryrequests.txt";

    /** Length of the "Id:" prefix in the stored file. */
    private static final int ID_PREFIX_LENGTH = 3;
    /** Length of the "Code:" prefix in the stored file. */
    private static final int CODE_PREFIX_LENGTH = 5;
    /** Length of the "Name:" prefix in the stored file. */
    private static final int NAME_PREFIX_LENGTH = 5;
    /** Length of the "Type:" prefix in the stored file. */
    private static final int TYPE_PREFIX_LENGTH = 5;
    /** Length of the "Status:" prefix in the stored file. */
    private static final int STATUS_PREFIX_LENGTH = 7;
    /** Length of the "Time:" prefix in the stored file. */
    private static final int TIME_PREFIX_LENGTH = 5;

    /**
     * Load all ministry requests from the storage file.
     * @return a list of all stored requests, or an empty list if none exist
     */
    public List<MinistryRequest> loadAll() {
        List<MinistryRequest> result = new ArrayList<>();

        File f = new File(FILE);
        if (!f.exists()) {
            return result;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f),
                                    StandardCharsets.UTF_8))) {

            String line;
            Integer id = null;
            Integer code = null;
            String name = null;
            RequestType type = null;
            RequestStatus status = null;
            LocalDateTime time = null;
            StringBuilder text = null;
            boolean inText = false;

            while ((line = br.readLine()) != null) {
                if (line.equals("=== REQUEST ===")) {
                    id = null;
                    code = null;
                    name = null;
                    type = null;
                    status = null;
                    time = null;
                    text = new StringBuilder();
                    inText = false;
                } else if (line.equals("=== END ===")) {
                    if (id != null && code != null
                        && name != null && type != null
                        && status != null && time != null && text != null) {
                        result.add(new MinistryRequest(
                                id,
                                code,
                                name,
                                type,
                                status,
                                time,
                                text.toString().trim()
                        ));
                    }
                } else if (line.startsWith("Id:")) {
                    id = Integer.parseInt(line.substring(ID_PREFIX_LENGTH)
                                                .trim());
                } else if (line.startsWith("Code:")) {
                    code = Integer.parseInt(line.substring(CODE_PREFIX_LENGTH)
                                                .trim());
                } else if (line.startsWith("Name:")) {
                    name = line.substring(NAME_PREFIX_LENGTH)
                                .trim();
                } else if (line.startsWith("Type:")) {
                    type = RequestType.valueOf(line
                                            .substring(TYPE_PREFIX_LENGTH)
                                            .trim());
                } else if (line.startsWith("Status:")) {
                    status = RequestStatus.valueOf(line
                                            .substring(STATUS_PREFIX_LENGTH)
                                            .trim());
                } else if (line.startsWith("Time:")) {
                    time = LocalDateTime.parse(line
                                            .substring(TIME_PREFIX_LENGTH)
                                            .trim());
                } else if (line.equals("Text:")) {
                    inText = true;
                } else if (inText && text != null) {
                    text.append(line).append("\n");
                }
            }

        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την ανάγνωση των αιτημάτων: "
                                + e.getMessage());
        }

        return result;
    }

    /**
     * Save all given requests to the storage file,
     * overwriting any existing data.
     * @param requests the list of requests to store
     */
    private void saveAll(final List<MinistryRequest> requests) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE, false),
                                        StandardCharsets.UTF_8))) {

            for (MinistryRequest r : requests) {
                bw.write("=== REQUEST ===\n");
                bw.write("Id: " + r.getId() + "\n");
                bw.write("Code: " + r.getMinistryCode() + "\n");
                bw.write("Name: " + r.getMinistryName() + "\n");
                bw.write("Type: " + r.getType().name() + "\n");
                bw.write("Status: " + r.getStatus().name() + "\n");
                bw.write("Time: " + r.getTimestamp().toString() + "\n");
                bw.write("Text:\n");
                bw.write(r.getText() == null ? "" : r.getText());
                bw.write("\n=== END ===\n");
            }

        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση των αιτημάτων: "
                                + e.getMessage());
        }
    }

    /**
     * Saves a new ministry request.
     * If an identical PENDING request already exists for the same ministry,
     * the request is not saved again.
     * @param request the request to be saved
     */
    public void saveNew(final MinistryRequest request) {
        List<MinistryRequest> all = loadAll();

        int hash = request.getHash();
        int code = request.getMinistryCode();

        for (MinistryRequest r : all) {
            if (r.getMinistryCode() == code
                    && r.getHash() == hash
                    && r.getStatus() == RequestStatus.PENDING) {
                System.out.println(
                    "Υπάρχει ήδη ίδιο PENDING αίτημα. Δεν αποθηκεύτηκε ξανά.");
                return;
            }
        }

        int nextId = all.stream()
                .mapToInt(MinistryRequest::getId)
                .max()
                .orElse(0) + 1;

        MinistryRequest withId = new MinistryRequest(
                nextId,
                request.getMinistryCode(),
                request.getMinistryName(),
                request.getType(),
                request.getStatus(),
                request.getTimestamp(),
                request.getText()
        );

        all.add(withId);
        saveAll(all);
    }

    /**
     * Update the status of a request with the given id.
     * @param id the request id
     * @param newStatus the new status to set
     */
    public void updateStatus(final int id, final RequestStatus newStatus) {
        List<MinistryRequest> all = loadAll();
        boolean found = false;

        for (MinistryRequest r : all) {
            if (r.getId() == id) {
                r.setStatus(newStatus);
                found = true;
                break;
            }
        }

        if (found) {
            saveAll(all);
            System.out.println("Η κατάσταση ενημερώθηκε επιτυχώς.");
        } else {
            System.out.println("Δεν βρέθηκε αίτημα με Id = " + id);
        }
    }

    /**
     * Find requests that match the given status and type.
     * If status or type is null, it is ignored during filtering.
     * @param status the requested status (or null)
     * @param type the requested type (or null)
     * @return a list of matching requests
     */
    public List<MinistryRequest> findByStatusAndType(
                    final RequestStatus status,
                    final RequestType type) {
        List<MinistryRequest> all = loadAll();
        List<MinistryRequest> result = new ArrayList<>();

        for (MinistryRequest r : all) {
            boolean statusMatch = (status == null) || r.getStatus() == status;
            boolean typeMatch = (type == null)
                                || r.getType() == type
                                || type == RequestType.BOTH;

            if (statusMatch && typeMatch) {
                result.add(r);
            }
        }

        return result;
    }

    /**
     * Check if a completed request already exists.
     * @param ministryCode
     * @param hash
     * @return true/false
     */
    public boolean existsCompletedDuplicate(final int ministryCode,
                                            final int hash) {
        return loadAll().stream()
                .anyMatch(r ->
                        r.getMinistryCode() == ministryCode
                        && r.getHash() == hash
                        && r.getStatus() == RequestStatus.COMPLETED);
    }

}
