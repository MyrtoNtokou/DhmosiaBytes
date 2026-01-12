package revenuerequests;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ministryrequests.RequestStatus;

/**
 * Repository responsible for persisting and retrieving
 * {@link RevenueRequest} objects.
 * <p>
 * Data is stored in a plain text file using a simple
 * custom serialization format.
 */
public class RevenueRequestRepository {

    /** The path where the revenue requests data is stored. */
    private final Path filePath;

    /**
     * Constructor for testing and custom configurations.
     * Allows specifying a custom file path to isolate data during tests
     * or to use different storage locations.
     *
     * @param customPath the {@link Path} to the file used for
     * storing revenue requests
     */
    public RevenueRequestRepository(final Path customPath) {
        this.filePath = customPath;
    }

    /**
     * Default constructor for the application.
     * Initializes the repository using the default system path
     * within the "runtime-data" directory.
     */
    public RevenueRequestRepository() {
        this(Paths.get("runtime-data").resolve("revenuerequests.txt"));
    }

    /** Length of the "Id: " prefix in the stored file. */
    private static final int ID_PREFIX_LENGTH = 4;
    /** Length of the "Code: " prefix in the stored file. */
    private static final int CODE_PREFIX_LENGTH = 6;
    /** Length of the "Name: " prefix in the stored file. */
    private static final int NAME_PREFIX_LENGTH = 6;
    /** Length of the "Status: " prefix in the stored file. */
    private static final int STATUS_PREFIX_LENGTH = 8;
    /** Length of the "Time: " prefix in the stored file. */
    private static final int TIME_PREFIX_LENGTH = 6;

    /**
     * Loads all revenue requests from the persistence file.
     * <p>
     * If the file does not exist, an empty list is returned.
     *
     * @return a list of all stored {@link RevenueRequest} objects
     */
    public List<RevenueRequest> loadAll() {
        List<RevenueRequest> result = new ArrayList<>();
        Path f = filePath;

        if (!Files.exists(f)) {
            return result;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    Files.newInputStream(f), StandardCharsets.UTF_8))) {

            String line;
            Integer id = null;
            String code = null;
            String name = null;
            RequestStatus status = null;
            LocalDateTime time = null;
            StringBuilder text = new StringBuilder();
            boolean inText = false;

            while ((line = br.readLine()) != null) {
                if (line.equals("=== REQUEST ===")) {
                    id = null;
                    code = null;
                    name = null;
                    status = null;
                    time = null;
                    text = new StringBuilder();
                    inText = false;
                } else if (line.equals("=== END ===")) {
                    if (id != null && status != null) {
                        result.add(new RevenueRequest(
                            id, code, name, status, time,
                            text.toString().trim()));
                    }
                } else if (line.startsWith("Id: ")) {
                    id = Integer.parseInt(
                            line.substring(ID_PREFIX_LENGTH).trim());
                } else if (line.startsWith("Code: ")) {
                    code = line.substring(CODE_PREFIX_LENGTH).trim();
                } else if (line.startsWith("Name: ")) {
                    name = line.substring(NAME_PREFIX_LENGTH).trim();
                } else if (line.startsWith("Status: ")) {
                    status = RequestStatus.valueOf(
                            line.substring(STATUS_PREFIX_LENGTH).trim());
                } else if (line.startsWith("Time: ")) {
                    time = LocalDateTime.parse(
                            line.substring(TIME_PREFIX_LENGTH).trim());
                } else if (line.equals("Diff:")) {
                    inText = true;
                } else if (inText) {
                    text.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Σφάλμα: " + e.getMessage());
        }
        return result;
    }

    /**
     * Saves all revenue requests to the persistence file.
     * <p>
     * Existing file contents are overwritten.
     *
     * @param requests the list of {@link RevenueRequest} objects to persist
     */
    public void saveAll(final List<RevenueRequest> requests) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(filePath.toFile(), false),
                                StandardCharsets.UTF_8))) {
            for (RevenueRequest r : requests) {
                bw.write("=== REQUEST ===\n");
                bw.write("Id: " + r.getId() + "\n");
                bw.write("Code: " + r.getRevenueCode() + "\n");
                bw.write("Name: " + r.getRevenueName() + "\n");
                bw.write("Status: " + r.getStatus().name() + "\n");
                bw.write("Time: " + r.getTimestamp().toString() + "\n");
                bw.write("Diff:\n" + r.getText() + "\n");
                bw.write("=== END ===\n");
            }
        } catch (IOException e) {
            System.err.println("Σφάλμα: " + e.getMessage());
        }
    }
}
