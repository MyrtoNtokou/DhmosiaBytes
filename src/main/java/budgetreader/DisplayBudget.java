package budgetreader;

import java.util.List;
import static aggregatedata.ConsoleColors.CYAN;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.RESET;

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
        System.out.println(CYAN + BOLD
                    + "\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===\n" + RESET);

        System.out.printf(BOLD + "%-6s | %-60s | %-25s%n", "Α/Α",
        "Έσοδα (1) / Έξοδα (2)", "Ποσό" + RESET);

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
        System.out.println(CYAN + BOLD
            + "\n=== ΚΑΤΗΓΟΡΙΟΠΟΙΗΣΗ ΣΤΟΙΧΕΙΩΝ ===\n" + RESET);

        System.out.printf(BOLD + "%-3s | %-65s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο" + RESET);

        /* Print each record in the list*/
        for (Ypourgeio y : ypourg) {
            System.out.printf("%-3d | %-65s | %-25.2f | %-35.2f | %-25.2f%n",
                y.getKodikos(), y.getOnoma(), y.getTaktikos(),
                y.getEpendyseis(), y.getSynolo());
        }
    }
}
