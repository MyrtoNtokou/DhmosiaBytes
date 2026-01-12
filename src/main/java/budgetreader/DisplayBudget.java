package budgetreader;

import java.util.List;

/**
 * Utility class for displaying budget data.
 */
public final class DisplayBudget {

    /**
     * Private constructor to prevent object creation.
     */
    private DisplayBudget() { }

    /** Prints the General Budget List on the screen.
     *
     * @param basicRecords the list of budget entries to print
     */
    public static void showGeneral(final List<BasicRecord> basicRecords) {
        System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===\n");

        System.out.printf("%-6s | %-60s | %-25s%n", "Α/Α",
        "Έσοδα (1) / Έξοδα (2)", "Ποσό");

        /* Print each record in the list */
        for (BasicRecord e : basicRecords) {
            System.out.printf("%-6s | %-60s | %-25.2f%n",
            e.getCode(), e.getDescription(), e.getAmount());
        }
    }

    /** Prints the Ministry Budget List on the screen.
     *
     * @param ministry the list of budget entries to print
     */
    public static void showMinistry(final List<Ministry> ministry) {
        System.out.println("\n=== ΚΑΤΗΓΟΡΙΟΠΟΙΗΣΗ ΣΤΟΙΧΕΙΩΝ ===\n");

        System.out.printf("%-3s | %-65s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο");

        /* Print each record in the list*/
        for (Ministry y : ministry) {
            System.out.printf("%-3d | %-65s | %-25.2f | %-35.2f | %-25.2f%n",
                y.getcode(), y.getName(), y.getRegularBudget(),
                y.getPublicInvestments(), y.getTotalBudget());
        }
    }
}
