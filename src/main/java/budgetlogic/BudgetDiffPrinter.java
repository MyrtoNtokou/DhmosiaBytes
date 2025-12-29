package budgetlogic;

import java.math.BigDecimal;
import java.util.Map;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;


/**
 * Print general and ministries budget after changes.
 * Output is formatted with ANSI escape codes (bold + blue).
 */
public final class BudgetDiffPrinter {

    /** Constructor. */
    private BudgetDiffPrinter() { }

    /** ANSI reset code to clear all formatting. */
    private static final String RESET = "\u001B[0m";
    /** ANSI bold text modifier. */
    private static final String BOLD = "\u001B[1m";
    /** ANSI underline text modifier. */
    public static final String UNDERLINE = "\u001B[4m";
    /** Line to be removed when printing ministries. */
    private static final int MINISTRY_TOTALS = 4;
    /** Line to be removed when printing ministries. */
    private static final int TOTALS = 33;

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
        System.out.println("\n" + BOLD + CYAN
        + "=== Αλλαγές στα Έσοδα του Κρατικού Προϋπολογισμού ===" + RESET);

        // Find changes
        for (Map.Entry<String, Eggrafi> entry : before.getRevenues()
                                                    .entrySet()) {
            String k = entry.getKey();
            BigDecimal oldVal = entry.getValue().getPoso();
            BigDecimal newVal = after.getRevenues().get(k).getPoso();

            if (oldVal.compareTo(newVal) != 0) {
                System.out.println("\n" + BOLD + "  " + k + " | "
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
        System.out.println("\n" + BOLD + CYAN
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
                System.out.println("\n" + BOLD + "  " + k + " | "
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

    /**
     * Print all revenues in aligned table format.
     * @param budget the budget to print
     */
    public static void printRevenues(final Budget budget) {
        System.out.println("\n" + BOLD + CYAN
            + "=== Έσοδα Κρατικού Προϋπολογισμού ===" + RESET);

        // Table header
        System.out.printf("%-6s | %-60s | %-25s%n", "Α/Α",
        "Έσοδα", "Ποσό");

        for (Map.Entry<String, Eggrafi> entry : budget
                                                .getRevenues().entrySet()) {
            String code = entry.getKey();
            Eggrafi e = entry.getValue();

            if (e.getPerigrafi().toUpperCase().contains("ΕΣΟΔΑ")) {
                    continue;
            }

            System.out.printf("%-8s | %-60s | %-15s%n",
                    code,
                    e.getPerigrafi(),
                    e.getPoso().toString());
        }
    }

    /**
     * Print all ministries in aligned table format.
     * @param budget the budget to print
     */
    public static void printMinistries(final Budget budget) {
        System.out.println("\n" + BOLD + CYAN
            + "=== Προϋπολογισμός Υπουργείων ===" + RESET);

        // Table header
        System.out.printf("%-3s | %-55s | %-25s | %-35s | %-25s%n",
            "Α/Α", "Υπουργείο", "Τακτικός Προϋπολογισμός",
            "Προϋπολογισμός Δημοσίων Επενδύσεων", "Σύνολο");

        for (Map.Entry<Integer, Ypourgeio> entry : budget
                                                .getMinistries().entrySet()) {
            Integer code = entry.getKey();
            Ypourgeio y = entry.getValue();

            if (code == MINISTRY_TOTALS || code == TOTALS) {
                continue;
            }

            System.out.printf("%-3s | %-55s | %-25s | %-35s | %-25s%n",
                    code,
                    y.getOnoma(),
                    y.getTaktikos().toString(),
                    y.getEpendyseis().toString(),
                    y.getSynolo().toString());
        }
    }

    /**
     * Print current and modified general budget side by side.
     * @param before
     * @param after
     */
    public static void compareGeneralSideBySide(final Budget before,
                                                final Budget after) {
        System.out.println("\n" + BOLD + CYAN
            + "=== ΣΥΓΚΡΙΣΗ ΓΕΝΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===\n" + RESET);

        System.out.printf("%-8s | %-70s | %-33s | %-25s%n",
            BOLD + "Α/Α" + RESET,
            BOLD + "Περιγραφή" + RESET,
            BOLD + "Τρέχον" + RESET,
            BOLD + "Τροποποιημένος" + RESET);

        for (Map.Entry<String, Eggrafi> entry : before.getRevenues()
                                                    .entrySet()) {
            String code = entry.getKey();
            Eggrafi oldE = entry.getValue();
            Eggrafi newE = after.getRevenues().get(code);

            String beforeVal = oldE.getPoso().toString();
            String afterVal = newE.getPoso().toString();

            boolean changed = oldE.getPoso().compareTo(newE.getPoso()) != 0;

            if (changed) {
                afterVal = BOLD + BLUE + afterVal + RESET;
            }

            System.out.printf("%-6s | %-60s | %-25s | %-25s%n",
                    code,
                    oldE.getPerigrafi(),
                    beforeVal,
                    afterVal);
        }
    }

    /**
     * Print current and modified ministries budget side by side.
     * @param before
     * @param after
     */
    public static void compareMinistriesSideBySide(final Budget before,
                                                final Budget after) {
        System.out.println("\n" + BOLD + CYAN
            + "=== ΣΥΓΚΡΙΣΗ ΥΠΟΥΡΓΕΙΩΝ ===\n" + RESET);

        System.out.printf("%-92s %-46s %-18s %-44s%n",
                    " ",
                    UNDERLINE + BOLD + "Τρέχον" + RESET,
                    " | ",
                    UNDERLINE + BOLD + "Τροποποιημένος" + RESET);

        System.out.printf(
            "%-4s | %-73s | %-25s %-25s %-25s | %-25s %-25s %-14s%n",
            BOLD + "Κωδ." + RESET,
            BOLD + "Υπουργείο" + RESET,
            BOLD + "Τακτικός" + RESET,
            BOLD + "ΠΔΕ" + RESET,
            BOLD + "Σύνολο" + RESET,
            BOLD + "Τακτικός" + RESET,
            BOLD + "ΠΔΕ" + RESET,
            BOLD + "Σύνολο" + RESET);

        for (Map.Entry<Integer, Ypourgeio> entry : before.getMinistries()
                                                        .entrySet()) {
            Integer code = entry.getKey();
            Ypourgeio oldM = entry.getValue();
            Ypourgeio newM = after.getMinistries().get(code);

            String tBefore = oldM.getTaktikos().toString();
            String tAfter = newM.getTaktikos().toString();

            String pBefore = oldM.getEpendyseis().toString();
            String pAfter = newM.getEpendyseis().toString();

            String sBefore = oldM.getSynolo().toString();
            String sAfter = newM.getSynolo().toString();

            if (oldM.getTaktikos().compareTo(newM.getTaktikos()) != 0) {
                tAfter = BOLD + BLUE + tAfter + RESET;
            }
            if (oldM.getEpendyseis().compareTo(newM.getEpendyseis()) != 0) {
                pAfter = BOLD + BLUE + pAfter + RESET;
            }
            if (oldM.getSynolo().compareTo(newM.getSynolo()) != 0) {
                sAfter = BOLD + BLUE + sAfter + RESET;
            }

            System.out.printf(
                "%-4s | %-65s | %-17s %-17s %-17s | %-17s %-17s %-17s%n",
                code,
                oldM.getOnoma(),
                tBefore, pBefore, sBefore,
                tAfter, pAfter, sAfter);
        }
    }
}
