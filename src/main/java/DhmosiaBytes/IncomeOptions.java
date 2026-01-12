package dhmosiabytes;

import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.RESET;

 /**
 * Enum representing categories of income/revenues, each with a unique code
 * and a descriptive label.
 */
public enum IncomeOptions {
    /** Taxes. */
    FOROI("1,1", "Φόροι"),

    /** Social contributions. */
    KOINONIKES_EISFORES("1,2", "Κοινωνικές εισφορές"),

    /** Transfers. */
    METAVIVASEIS("1,3", "Μεταβιβάσεις"),

    /** Sales of goods and services. */
    POLHSEIS("1,4", "Πωλήσεις αγαθών και υπηρεσιών"),

    /** Other current revenues. */
    LOIPA("1,5", "Λοιπά τρέχοντα έσοδα"),

    /** Fixed assets. */
    PAGIA("1,6", "Πάγια Περιουσιακά Στοιχεία"),

    /** Debt securities. */
    XREOSTIKOI("1,7", "Χρεωστικοί τίτλοι"),

    /** Loans. */
    DANEIO("1,8", "Δάνεια"),

    /** Participatory securities and investment fund shares. */
    SYMMETOXIKOI("1,9", "Συμμεχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων"),

    /** Currency and deposit liabilities. */
    YPOXREOSEIS("1,9,1", "Υποχρεώσεις από νόμισμα και καταθέσεις"),

    /** Debt securities (liabilities). */
    XREOSTIKOI_YPOXREOSEIS("1,9,2", "Χρεωστικοί τίτλοι (υποχρεώσεις)"),

    /** Loans (liabilities). */
    DANEIA("1,9,3", "Δάνεια"),

    /** Financial derivatives. */
    XRHMATOOIKONOMIKA("1,9,4", "Χρηματοοικονομικά παράγωγα");

    /** Unique code identifying the income category. */
    private final String incomeCode;

    /** Human-readable description of the income category. */
    private final String incomeDescription;

     /**
     * Constructs an IncomeOptions enum constant.
     *
     * @param code unique code for the income category
     * @param description descriptive label for the income category
     */
    IncomeOptions(final String code, final String description) {
        this.incomeCode = code;
        this.incomeDescription = description;
    }

     /**
     * Returns the code of the income category.
     *
     * @return the income code
     */
    public String getIncomeCode() {
        return incomeCode;
    }

     /**
     * Returns the description of the income category.
     *
     * @return the income description
     */


    public String getIncomeDescription() {
        return incomeDescription;
    }

     /**
     * Returns the IncomeOptions constant corresponding to the given code.
     *
     * @param incomeCode the code to look up
     * @return matching IncomeOptions constant
     * @throws IllegalArgumentException if no matching constant is found
     */
    public static IncomeOptions fromCode(final String incomeCode) {
        for (IncomeOptions incomeOpt : IncomeOptions.values()) {
            if (incomeOpt.getIncomeCode().equals(incomeCode)) {
                return incomeOpt;
            }
        }
        throw new IllegalArgumentException(RED + "Μη έγκυρη επιλογή: "
                                    + incomeCode + RESET);
    }
}
