package budgetlogic;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

import java.io.IOException;
import java.util.List;

/**
 * Save the Budget object.
 */
public final class BudgetSave {

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
        PreparedRecords records = prepareRecords(finalBudget);
        BudgetWriter.writeGeneral(updatedGeneral, records.general());
    }

    /**
     * Save the changes in the modified ministries csv.
     * @param finalBudget object budget after changes
     * @param updatedMinistries modified ministries csv name
     */
    public void saveMinistryChanges(final Budget finalBudget,
                                    final String updatedMinistries)
                                    throws IOException {
        PreparedRecords records = prepareRecords(finalBudget);
        BudgetWriter.writeMinistries(updatedMinistries, records.ministries());
    }
}
