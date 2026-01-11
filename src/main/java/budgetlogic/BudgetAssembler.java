package budgetlogic;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import budgetreader.BasicRecord;
import budgetreader.ReadBudget;
import budgetreader.Ministry;

/**
 * Separate revenues and expenses.
 * Prepare object Budget.
 * Create the map with the expense percentages.
 */
public final class BudgetAssembler {

    /** Directory with modified budget csv. */
    private static final Path DATA_DIR = Paths.get("runtime-data");

    /** Keyword for revenues. */
    private static final String REVENUES = "ΕΣΟΔΑ";
    /** Keyword for expenses. */
    private static final String EXPENSES = "ΕΞΟΔΑ";
    /** Keyword for result (revenues-expenses). */
    private static final String RESULT = "ΑΠΟΤΕΛΕΣΜΑ";

    /** Constructor. */
    public BudgetAssembler() { }

    /**
     * Load general budget and ministries csv.
     * Assemble the final Budget object.
     * @param generalFile general budget file name
     * @param ministriesFile ministries budget file name
     * @return object Budget
     */
    public Budget loadBudget(final String generalFile,
                            final String ministriesFile) {

        boolean generalIsModified = generalFile.contains("new");
        boolean ministriesIsModified = ministriesFile.contains("new");

        List<BasicRecord> generalList;
        if (generalIsModified) {
            Path generalPath = DATA_DIR.resolve(generalFile);
            generalList = ReadBudget.readGeneralBudgetFromPath(generalPath);
        } else {
            generalList = ReadBudget.readGeneralBudget(generalFile);
        }

        List<Ministry> ministriesList;
        if (ministriesIsModified) {
            Path ministriesPath = DATA_DIR.resolve(ministriesFile);
            ministriesList = ReadBudget.readByMinistryFromPath(ministriesPath);
        } else {
            ministriesList = ReadBudget.readByMinistry(ministriesFile);
        }

        // Initialise revenues map and expenses map
        Map<String, BasicRecord> revenuesMap = new LinkedHashMap<>();
        Map<String, BasicRecord> expensesMap = new LinkedHashMap<>();

        separateAndMapGeneralRecords(generalList, revenuesMap, expensesMap);

        Map<Integer, Ministry> ministriesMap =
                        mapMinistryRecords(ministriesList);

        // Final Budget object
        return new Budget(revenuesMap, expensesMap, ministriesMap);
    }

    /**
     * Separete list with revenues and expenses into two.
     * Create one map with revenues and one map with expenses.
     * @param generalList list with everything
     * @param revenuesMap map for revenues
     * @param expensesMap map for expenses
     */
    private void separateAndMapGeneralRecords(
            final List<BasicRecord> generalList,
            final Map<String, BasicRecord> revenuesMap,
            final Map<String, BasicRecord> expensesMap) {

        // Define either revenue section or expenses setcion
        boolean inRevenuesSection = false;
        boolean inExpensesSection = false;

        for (BasicRecord basicRecord : generalList) {
            final String descriptionUpper = basicRecord.getDescription()
                                                 .toUpperCase(Locale.ROOT);

            // Put result in the expenses map
            if (descriptionUpper.contains(RESULT)) {
                expensesMap.put(basicRecord.getCode(), basicRecord);
                continue;
            }
            // Check revenues section
            if (descriptionUpper.contains(REVENUES)) {
                inRevenuesSection = true;
                inExpensesSection = false;
            // Check expenses section
            } else if (descriptionUpper.contains(EXPENSES)) {
                inExpensesSection = true;
                inRevenuesSection = false;
            }
            // Based on the section add BasicRecord contents to the correct map
            if (inRevenuesSection) {
                revenuesMap.put(basicRecord.getCode(), basicRecord);
            } else if (inExpensesSection) {
                 expensesMap.put(basicRecord.getCode(), basicRecord);
            }
        }
    }

    /**
     * Convert ministry list in map and set the code as key.
     * @param list with all the ministries
     * @return the map version
     */
    private Map<Integer, Ministry> mapMinistryRecords(
                                        final List<Ministry> list) {
        Map<Integer, Ministry> map = new LinkedHashMap<>();
        for (Ministry ypourg : list) {
            map.put(ypourg.getcode(), ypourg);
        }
        return map;
    }

    /**
     * Create the map that will be used in budget service,
     * when executing a change.
     * @param ministryCode
     * @param distribution
     * @return map with ministry code, and value a map with expense codes,
     * and their percentages
     */
    public static Map<Integer, Map<String, BigDecimal>>
                createMappingForMinistryChange(final int ministryCode,
                    final Map<String, BigDecimal> distribution) {
        validateDistribution(distribution);

        Map<Integer, Map<String, BigDecimal>> mapping = new LinkedHashMap<>();
        mapping.put(ministryCode, new LinkedHashMap<>(distribution));

        return mapping;
    }

    /**
     * Check if the given percentages sum equals 1.
     * @param distribution
     */
    private static void validateDistribution(
                final Map<String, BigDecimal> distribution) {
        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal v : distribution.values()) {
            sum = sum.add(v);
        }

        if (sum.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException(
                "Τα ποσοστά κατανομής στα έξοδα πρέπει να αθροίζουν σε 100%"
            );
        }
    }
}
