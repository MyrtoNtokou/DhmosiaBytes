package budgetlogic;

import java.math.BigDecimal;
import java.util.Map;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

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
    /** ANSI red for negative difference. */
    public static final String RED = "\u001B[31m";
    /** ANSI green for positive difference. */
    public static final String GREEN = "\u001B[32m";
    /** ANSI underline text modifier. */
    public static final String UNDERLINE = "\u001B[4m";
    /** Line to be removed when printing ministries. */
    private static final int MINISTRY_TOTALS = 4;
    /** Line to be removed when printing ministries. */
    private static final int TOTALS = 33;
    /** Target display width for budget ammounts. */
    private static final int DISPLAY_AMMOUNT_WIDTH = 17;

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
     * Calculate difference between the old and the new value.
     * Format the difference.
     * @param oldVal
     * @param newVal
     * @return difference formatted
     */
    private static String formatDiff(final BigDecimal oldVal,
                                    final BigDecimal newVal) {
        BigDecimal diff = newVal.subtract(oldVal);
        if (diff.signum() == 0) {
            return "";
        }
        if (diff.signum() > 0) {
            return " (" + GREEN + "+" + diff + RESET + ")";
        } else {
            return " (" + RED + diff + RESET + ")";
        }
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
        + "=== ΑΛΛΑΓΕΣ ΣΤΑ ΕΣΟΔΑ ΤΟΥ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===" + RESET);

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
                               + " → " + BLUE + newVal + RESET
                               + formatDiff(oldVal, newVal));
            }
        }

        Eggrafi resultRecord = after.getExpenses().values().stream()
        .filter(e -> e.getPerigrafi().toUpperCase().contains("ΑΠΟΤΕΛΕΣΜΑ"))
                        .findFirst()
                        .orElse(null);

        String resultCode = resultRecord.getKodikos();

        BigDecimal oldResult = before.totalRevenues()
                                .subtract(before.totalExpenses());
        BigDecimal newResult = after.totalRevenues()
                                .subtract(after.totalExpenses());

        if (oldResult.compareTo(newResult) != 0) {
            System.out.println("\n" + BOLD + "  " + resultCode + " | "
            + "ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)" + RESET);

            System.out.println("    " + oldResult
                + " → " + BLUE + newResult + RESET
                + formatDiff(oldResult, newResult));
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
        + "=== ΑΛΛΑΓΕΣ ΣΤΟΝ ΠΡΟΫΠΟΛΟΓΙΣΜΟ ΤΩΝ ΥΠΟΥΡΓΕΙΩΝ ===" + RESET);

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
                                   + " → " + BLUE + newM.getTaktikos() + RESET
                                   + formatDiff(oldM.getTaktikos(),
                                                newM.getTaktikos()));
                }

                if (oldM.getEpendyseis()
                        .compareTo(newM.getEpendyseis()) != 0) {
                    System.out.println("    ΠΔΕ: " + oldM.getEpendyseis()
                                + " → " + BLUE + newM.getEpendyseis() + RESET
                                + formatDiff(oldM.getEpendyseis(),
                                            newM.getEpendyseis()));
                }

                if (oldM.getSynolo().compareTo(newM.getSynolo()) != 0) {
                    System.out.println("    Σύνολο: " + oldM.getSynolo()
                                    + " → " + BLUE + newM.getSynolo() + RESET
                                    + formatDiff(oldM.getSynolo(),
                                                newM.getSynolo()));
                }
            }
        }
    }

    /**
     * Convert the compareMinistries System.out into String.
     * @param before
     * @param after
     * @return String of change
     */
    public static String captureMinistryDiff(final Budget before,
                                            final Budget after) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
        PrintStream oldOut = System.out;

        System.setOut(ps);
        compareMinistries(before, after);
        System.out.flush();
        System.setOut(oldOut);

        return baos.toString(StandardCharsets.UTF_8);
    }

    /**
     * Print all revenues in aligned table format.
     * @param budget the budget to print
     */
    public static void printRevenues(final Budget budget) {
        System.out.println("\n" + BOLD + CYAN
            + "=== ΕΣΟΔΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===\n" + RESET);

        // Table header
        System.out.printf(BOLD + "%-6s | %-60s | %-25s%n", "Α/Α",
        "Έσοδα", "Ποσό" + RESET);

        for (Map.Entry<String, Eggrafi> entry : budget
                                                .getRevenues().entrySet()) {
            String code = entry.getKey();
            Eggrafi e = entry.getValue();

            if (e.getPerigrafi().toUpperCase().contains("ΕΣΟΔΑ")) {
                    continue;
            }

            System.out.printf("%-6s | %-60s | %-25s%n",
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
            + "=== ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ΥΠΟΥΡΓΕΙΩΝ ===\n" + RESET);

        // Table header
        System.out.printf(BOLD + "%-5s | %-65s | %-15s | %-15s | %-15s%n",
            "A/A", "Υπουργείο", "Τακτικός", "Επενδύσεων", "Σύνολο" + RESET);

        for (Map.Entry<Integer, Ypourgeio> entry : budget
                                                .getMinistries().entrySet()) {
            Integer code = entry.getKey();
            Ypourgeio y = entry.getValue();

            if (code == MINISTRY_TOTALS || code == TOTALS) {
                continue;
            }

            System.out.printf("%-5d | %-65s | %-15s | %-15s | %-15s%n",
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
            + "=== ΣΥΓΚΡΙΣΗ ΕΣΟΔΩΝ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===\n" + RESET);

         System.out.printf(BOLD + "%-6s | %-60s | %-30s | %-30s%n", "Α/Α",
        "Έσοδα", "Τρέχον", "Τροποποιημένος", "Διαφορά" + RESET);

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

            String diff = formatDiff(oldE.getPoso(), newE.getPoso());

            System.out.printf("%-6s | %-60s | %-30s | %-30s%n",
                    code,
                    oldE.getPerigrafi(),
                    beforeVal,
                    afterVal,
                    diff);
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

        System.out.printf("%-96s %-48s %-20s %-44s%n",
                    " ",
                    UNDERLINE + BOLD + "Τρέχον" + RESET,
                    " | ",
                    UNDERLINE + BOLD + "Τροποποιημένος" + RESET);

        System.out.printf(BOLD
            + "%-5s | %-65s | %-17s  %-17s  %-20s | %-17s  %-17s  %-17s%n",
            "A/A", "Υπουργείο", "Τακτικός", "Επενδύσεων",
            "Σύνολο", "Τακτικός", "Επενδύσεων", "Σύνολο" + RESET);

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
                "%-5s | %-65s | %-17s  %-17s  %-20s | %-17s  %-17s  %-17s%n",
                code,
                oldM.getOnoma(),
                oldM.getTaktikos(), oldM.getEpendyseis(), oldM.getSynolo(),
                padRight(tAfter, DISPLAY_AMMOUNT_WIDTH),
                padRight(pAfter, DISPLAY_AMMOUNT_WIDTH),
                padRight(sAfter, DISPLAY_AMMOUNT_WIDTH));
        }
    }

    /**
     * Pad a string with spaces to the right.
     * @param s string that might contain ANSI format
     * @param n the target display width
     * @return the string followed by the correct number of visual spaces
     */
    private static String padRight(final String s, final int n) {
        // Remove ANSI codes to calculate the actual visible length
        String plain = s.replaceAll("\u001B\\[[;\\d]*m", "");
        int spacesNeeded = n - plain.length();
        return s + " ".repeat(Math.max(0, spacesNeeded));
    }
}
