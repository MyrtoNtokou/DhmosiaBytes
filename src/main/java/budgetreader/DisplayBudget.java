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
     * @param eggrafes the list of budget entries to print
     */
    public static void showGeneral(final List<BasicRecord> eggrafes) {
        System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===\n");

        System.out.printf("%-6s | %-60s | %-25s%n", "Α/Α",
        "Έσοδα (1) / Έξοδα (2)", "Ποσό");

        /* Print each record in the list */
        for (BasicRecord e : eggrafes) {
            System.out.printf("%-6s | %-60s | %-25.2f%n",
            e.getCode(), e.getDescription(), e.getAmount());
        }
    }

    /** Prints the Ministry Budget List on the screen.
     *
     * @param ypourg the list of budget entries to print
     */
    public static void showMinistry(final List<Ministry> ypourg) {
        System.out.println("\n=== ΚΑΤΗΓΟΡΙΟΠΟΙΗΣΗ ΣΤΟΙΧΕΙΩΝ ===\n");

        System.out.printf("%-3s | %-65s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο");

        /* Print each record in the list*/
        for (Ministry y : ypourg) {
            System.out.printf("%-3d | %-65s | %-25.2f | %-35.2f | %-25.2f%n",
                y.getcode(), y.getName(), y.getRegularBudget(),
                y.getEpendyseis(), y.getSynolo());
        }
    }
}
