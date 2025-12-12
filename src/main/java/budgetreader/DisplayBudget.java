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
    public static void showGeneral(final List<Eggrafi> eggrafes) {
        System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===\n");

        System.out.printf("%-6s | %-60s | %-25s%n", "Α/Α",
        "Έσοδα (1) / Έξοδα (2)", "Ποσό");

        /* Print each record in the list */
        for (Eggrafi e : eggrafes) {
            System.out.printf("%-6s | %-60s | %-25.2f%n",
            e.getKodikos(), e.getPerigrafi(), e.getPoso());
        }
    }

    /** Prints the Ministry Budget List on the screen.
     *
     * @param ypourg the list of budget entries to print
     */
    public static void showMinistry(final List<Ypourgeio> ypourg) {
        System.out.println("\n=== ΚΑΤΗΓΟΡΙΟΠΟΙΗΣΗ ΣΤΟΙΧΕΙΩΝ ===\n");

        System.out.printf("%-3s | %-55s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο");

        /* Print each record in the list*/
        for (Ypourgeio y : ypourg) {
            System.out.printf("%-3d | %-55s | %-25.2f | %-35.2f | %-25.2f%n",
                y.getKodikos(), y.getOnoma(), y.getTaktikos(),
                y.getEpendyseis(), y.getSynolo());
        }
    }
}
