package budgetlogic;

import java.math.BigDecimal;
import java.util.Map;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;


/**
 * Create report after all changes in the state budget.
 * Output is formatted with ANSI escape codes (bold + blue).
 */
public final class BudgetDiffPrinter {

    /** Constructor. */
    private BudgetDiffPrinter() { }

    /** ANSI reset code to clear all formatting. */
    private static final String RESET = "\u001B[0m";
    /** ANSI bold text modifier. */
    private static final String BOLD = "\u001B[1m";

    /**
     * Helper: Generate ANSI escape codes for RGB.
     * @param r red component
     * @param g green component
     * @param b blue component
     * @return ANSI escape code fro RGB
     */
    private static String rgb(final int r, final int g, final int b) {
        return "\u001B[38;2;" + r + ";" + g + ";" + b + "m";
    }

    /** Blue color used new values. */
    private static final String BLUE = rgb(30, 144, 255);
    /** Cyan color used titles. */
    private static final String CYAN = rgb(0, 200, 255);

    /**
     * Print formatted difference report for general budget.
     * Between two budget objects.
     * @param before
     * @param after
     */
    public static void printDiffGeneral(final Budget before,
                                        final Budget after) {
        compareGeneral(before, after);
    }

    /**
     * Print formatted difference report for ministries.
     * Between two budget objects.
     * @param before
     * @param after
     */
    public static void printDiffMinistries(final Budget before,
                                        final Budget after) {
        compareMinistries(before, after);
    }


    /**
     * Compares the general section of the budget.
     * Print only the entries whose amounts have changed.
     * Print affects on total.
     * @param before
     * @param after
     */
    private static void compareGeneral(final Budget before,
                                    final Budget after) {
        System.out.println("\n" + BOLD
        + "=== Αλλαγές στα Έσοδα του Κρατικού Προϋπολογισμού ===" + RESET);

        // Find changes
        for (Map.Entry<String, Eggrafi> entry : before.getRevenues()
                                                    .entrySet()) {
            String k = entry.getKey();
            BigDecimal oldVal = entry.getValue().getPoso();
            BigDecimal newVal = after.getRevenues().get(k).getPoso();

            if (oldVal.compareTo(newVal) != 0) {
                System.out.println("\n" + BOLD + CYAN + "  " + k + " | "
                    + entry.getValue().getPerigrafi() + RESET);

                System.out.println("    " + oldVal
                               + " → " + BLUE + newVal + RESET);
            }
        }
    }


    /**
     * Compares the ministries section of the budget.
     * Print only the entries whose Taktikos or ΠΔΕ have changed.
     * Print affects on total.
     * @param before
     * @param after
     */
    private static void compareMinistries(final Budget before,
                                        final Budget after) {
        System.out.println("\n" + BOLD
        + "=== Αλλαγές στον Προϋπολογισμού των Υπουργείων ===" + RESET);

        for (Map.Entry<Integer, Ypourgeio> entry : before.getMinistries()
                                                        .entrySet()) {
            Integer k = entry.getKey();
            Ypourgeio oldM = entry.getValue();
            Ypourgeio newM = after.getMinistries().get(k);

            boolean changed =
                oldM.getTaktikos().compareTo(newM.getTaktikos()) != 0
                || oldM.getEpendyseis().compareTo(newM.getEpendyseis()) != 0
                || oldM.getSynolo().compareTo(newM.getSynolo()) != 0;

            if (changed) {
                System.out.println("\n" + BOLD + CYAN + "  " + k + " | "
                                            + oldM.getOnoma() + RESET);

                if (oldM.getTaktikos().compareTo(newM.getTaktikos()) != 0) {
                    System.out.println("    Τακτικός: " + oldM.getTaktikos()
                                   + " → " + BLUE + newM.getTaktikos() + RESET);
                }

                if (oldM.getEpendyseis()
                        .compareTo(newM.getEpendyseis()) != 0) {
                    System.out.println("    ΠΔΕ: " + oldM.getEpendyseis()
                                + " → " + BLUE + newM.getEpendyseis() + RESET);
                }

                if (oldM.getSynolo().compareTo(newM.getSynolo()) != 0) {
                    System.out.println("    Σύνολο: " + oldM.getSynolo()
                                    + " → " + BLUE + newM.getSynolo() + RESET);
                }
            }
        }
    }
}
