package budgetlogic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

/**
 * Save the Budget object.
 */
public final class BudgetSave {

    /** Directory with modified budget csv. */
    private static final Path DATA_DIR = Paths.get("runtime-data");

    /** Keyword for result (revenues-expenses). */
    private static final String RESULT = "ΑΠΟΤΕΛΕΣΜΑ";

    /** Constructor. */
    public BudgetSave() { }

    /**
     * Record that returns Eggrafi list and Ypourgeio list.
     * @param general
     * @param ministries
     */
    private record PreparedRecords(List<Eggrafi> general,
                                List<Ypourgeio> ministries) { }

    /**
     * Create PreparedRecords from Budget object.
     * @param finalBudget final object
     * @return PreparedRecords
     */
    private PreparedRecords prepareRecords(final Budget finalBudget) {

        List<Eggrafi> allGeneralRecords = new java.util.ArrayList<>();
        Eggrafi resultRecord = null;

        // Save result
        for (Eggrafi r : finalBudget.getRevenues().values()) {
            if (r.getPerigrafi().toUpperCase().contains(RESULT)) {
                resultRecord = r;
            }
        }

        // Add all revenues to list
        for (Eggrafi r : finalBudget.getRevenues().values()) {
            if (r != resultRecord) {
                allGeneralRecords.add(r);
            }
        }

        // Add all expenses to list
        for (Eggrafi r : finalBudget.getExpenses().values()) {
            if (r != resultRecord) {
                allGeneralRecords.add(r);
            }
        }

        // Add result at the end of the list
        if (resultRecord != null) {
            allGeneralRecords.add(resultRecord);
        }

        List<Ypourgeio> allMinistries =
            new java.util.ArrayList<>(finalBudget.getMinistries().values());

        return new PreparedRecords(allGeneralRecords, allMinistries);
    }

    /**
     * Save the changes in the modified general csv.
     * @param finalBudget object budget after changes
     * @param updatedGeneral modified general budget csv name
     */
    public void saveGeneralChanges(final Budget finalBudget,
                            final String updatedGeneral) throws IOException {
        Path generalPath = DATA_DIR.resolve(updatedGeneral);
        Files.createDirectories(DATA_DIR);
        PreparedRecords records = prepareRecords(finalBudget);
        BudgetWriter.writeGeneral(generalPath.toString(), records.general());
    }

    /**
     * Save the changes in the modified ministries csv.
     * @param finalBudget object budget after changes
     * @param updatedMinistries modified ministries csv name
     */
    public void saveMinistryChanges(final Budget finalBudget,
                                    final String updatedMinistries)
                                    throws IOException {
        Path ministriesPath = DATA_DIR.resolve(updatedMinistries);
        Files.createDirectories(DATA_DIR);
        PreparedRecords records = prepareRecords(finalBudget);
        BudgetWriter.writeMinistries(ministriesPath.toString(),
                                    records.ministries());
    }
}
