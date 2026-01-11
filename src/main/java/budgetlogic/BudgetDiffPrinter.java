package budgetlogic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import budgetreader.BasicRecord;
import budgetreader.Ministry;

import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.BLUE;
import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.GREEN;
import static aggregatedata.ConsoleColors.CYAN;
import static aggregatedata.ConsoleColors.UNDERLINE;

/**
 * Print general and ministries budget after changes.
 * Output is formatted with ANSI escape codes (bold + blue).
 */
public final class BudgetDiffPrinter {

    /** Constructor. */
    private BudgetDiffPrinter() { }

    /** Line to be removed when printing ministries. */
    private static final int MINISTRY_TOTALS = 4;
    /** Line to be removed when printing ministries. */
    private static final int TOTALS = 33;
    /** Target display width for budget ammounts. */
    private static final int DISPLAY_AMMOUNT_WIDTH = 17;

    /**
     * Print formatted difference report for revenues.
     * Between two budget objects.
     * @param before
     * @param after
     */
    public static void printDiffRevenues(final Budget before,
                                        final Budget after) {
        compareRevenues(before, after);
        compareResult(before, after);
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
        compareExpenses(before, after);
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
     * Compare the revenues section of the general budget.
     * Print only the entries whose amounts have changed.
     * @param before
     * @param after
     */
    private static void compareRevenues(final Budget before,
                                    final Budget after) {
        System.out.println("\n" + BOLD + CYAN
        + "=== ΑΛΛΑΓΕΣ ΣΤΑ ΕΣΟΔΑ ΤΟΥ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===" + RESET);

        // Find changes
        for (Map.Entry<String, BasicRecord> entry : before.getRevenues()
                                                    .entrySet()) {
            String k = entry.getKey();
            BigDecimal oldVal = entry.getValue().getAmount();
            BigDecimal newVal = after.getRevenues().get(k).getAmount();

            if (oldVal.compareTo(newVal) != 0) {
                System.out.println("\n" + BOLD + k + " | "
                    + entry.getValue().getDescription() + RESET);

                System.out.println(oldVal
                               + " → " + BLUE + newVal + RESET
                               + formatDiff(oldVal, newVal));
            }
        }
    }

    /**
     * Compare the expenses section of the general budget.
     * Print only the entries whose amounts have changed.
     * @param before
     * @param after
     */
    public static void compareExpenses(final Budget before,
                                    final Budget after) {
        System.out.println("\n" + BOLD + CYAN
            + "=== ΑΛΛΑΓΕΣ ΣΤΑ ΕΞΟΔΑ ΤΟΥ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===" + RESET);

        // Find changes
        for (Map.Entry<String, BasicRecord> entry : before.getExpenses()
                                                        .entrySet()) {
            String k = entry.getKey();
            BigDecimal oldVal = entry.getValue().getAmount();
            BigDecimal newVal = after.getExpenses().get(k).getAmount();

            if (oldVal.compareTo(newVal) != 0) {
                System.out.println("\n" + BOLD + k + " | "
                    + entry.getValue().getDescription() + RESET);

                System.out.println(oldVal
                            + " → " + BLUE + newVal + RESET
                            + formatDiff(oldVal, newVal));
            }
        }
    }

    /**
     * Compare and print the change od the result of the general budget.
     * @param before
     * @param after
     */
    private static void compareResult(final Budget before,
                                    final Budget after) {
        BasicRecord resultRecord = after.getExpenses().values().stream()
        .filter(e -> e.getDescription().toUpperCase().contains("ΑΠΟΤΕΛΕΣΜΑ"))
                            .findFirst()
                            .orElse(null);

        String resultCode = resultRecord.getCode();

        BigDecimal oldResult = before.totalRevenues()
                                    .subtract(before.totalExpenses());
        BigDecimal newResult = after.totalRevenues()
                                    .subtract(after.totalExpenses());

        if (oldResult.compareTo(newResult) != 0) {
            System.out.println("\n" + BOLD + resultCode + " | "
            + "ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)" + RESET);

            System.out.println(oldResult
                + " → " + BLUE + newResult + RESET
                + formatDiff(oldResult, newResult));
        }
    }

    /**
     * Compares the ministries section of the budget.
     * Print only the entries whose RegularBudget or ΠΔΕ have changed.
     * Print affects on total.
     * @param before
     * @param after
     */
    private static void compareMinistries(final Budget before,
                                        final Budget after) {
        System.out.println("\n" + BOLD + CYAN
        + "=== ΑΛΛΑΓΕΣ ΣΤΟΝ ΠΡΟΫΠΟΛΟΓΙΣΜΟ ΤΩΝ ΥΠΟΥΡΓΕΙΩΝ ===" + RESET);

        for (Map.Entry<Integer, Ministry> entry : before.getMinistries()
                                                        .entrySet()) {
            Integer k = entry.getKey();
            Ministry oldM = entry.getValue();
            Ministry newM = after.getMinistries().get(k);

            boolean changed =
                oldM.getRegularBudget().compareTo(newM.getRegularBudget()) != 0
                || oldM.getPublicInvestments().compareTo(
                    newM.getPublicInvestments()) != 0
                || oldM.getTotalBudget().compareTo(newM.getTotalBudget()) != 0;

            if (changed) {
                System.out.println("\n" + BOLD + k + " | "
                                            + oldM.getName() + RESET);

                if (oldM.getRegularBudget().compareTo(
                    newM.getRegularBudget()) != 0) {
                        System.out.println("Τακτικός: "
                        + oldM.getRegularBudget() + " → "
                        + BLUE + newM.getRegularBudget()
                        + RESET + formatDiff(oldM.getRegularBudget(),
                                newM.getRegularBudget()));
                }

                if (oldM.getPublicInvestments()
                        .compareTo(newM.getPublicInvestments()) != 0) {
                    System.out.println("ΠΔΕ: " + oldM.getPublicInvestments()
                                + " → " + BLUE + newM.getPublicInvestments()
                                + RESET
                                + formatDiff(oldM.getPublicInvestments(),
                                            newM.getPublicInvestments()));
                }

                if (oldM.getTotalBudget().compareTo(
                    newM.getTotalBudget()) != 0) {
                    System.out.println("Σύνολο: " + oldM.getTotalBudget()
                                    + " → " + BLUE + newM.getTotalBudget()
                                    + RESET
                                    + formatDiff(oldM.getTotalBudget(),
                                                newM.getTotalBudget()));
                }
            }
        }
    }

    /**
     * Convert the compareMinistries and compareExpenses
     * System.out into String.
     *
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
        compareExpenses(before, after);
        System.out.flush();
        System.setOut(oldOut);

        return baos.toString(StandardCharsets.UTF_8);
    }

    /**
     * Convert the compareRevenues and compareResult
     * System.out into String.
     *
     * @param before
     * @param after
     * @return String of change
     */
    public static String captureRevenuesDiff(final Budget before,
                                            final Budget after) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, StandardCharsets.UTF_8);
        PrintStream oldOut = System.out;

        System.setOut(ps);
        compareRevenues(before, after);
        compareResult(before, after);
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

        for (Map.Entry<String, BasicRecord> entry : budget
                                                .getRevenues().entrySet()) {
            String code = entry.getKey();
            BasicRecord e = entry.getValue();

            if (e.getDescription().toUpperCase().contains("ΕΣΟΔΑ")) {
                    continue;
            }

            System.out.printf("%-6s | %-60s | %-25s%n",
                    code,
                    e.getDescription(),
                    e.getAmount().toString());
        }
    }

    /**
     * Print all expenses in aligned table format.
     * @param budget the budget to print
     */
    public static void printExpenses(final Budget budget) {
        System.out.println("\n" + BOLD + CYAN
            + "=== ΕΞΟΔΑ ΚΡΑΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===\n" + RESET);

        // Table header
        System.out.printf(BOLD + "%-6s | %-60s%n", "Α/Α",
        "Έξοδα" + RESET);

        for (Map.Entry<String, BasicRecord> entry : budget
                                                .getExpenses().entrySet()) {
            String code = entry.getKey();
            BasicRecord e = entry.getValue();

            if (e.getDescription().toUpperCase().contains("ΕΞΟΔΑ")) {
                    continue;
            }

            if (code.equals("2,8")) {
                    continue;
            }

            System.out.printf("%-6s | %-60s%n",
                    code,
                    e.getDescription());
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

        for (Map.Entry<Integer, Ministry> entry : budget
                                                .getMinistries().entrySet()) {
            Integer code = entry.getKey();
            Ministry y = entry.getValue();

            if (code == MINISTRY_TOTALS || code == TOTALS) {
                continue;
            }

            System.out.printf("%-5d | %-65s | %-15s | %-15s | %-15s%n",
                    code,
                    y.getName(),
                    y.getRegularBudget().toString(),
                    y.getPublicInvestments().toString(),
                    y.getTotalBudget().toString());
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

         System.out.printf(BOLD + "%-6s | %-60s | %-30s | %-30s%n", "Α/Α",
        "Έσοδα (1) / Έξοδα (2)", "Τρέχον", "Τροποποιημένος" + RESET);

        for (Map.Entry<String, BasicRecord> entry : before.getRevenues()
                                                    .entrySet()) {
            String code = entry.getKey();
            BasicRecord oldE = entry.getValue();
            BasicRecord newE = after.getRevenues().get(code);

            String beforeVal = oldE.getAmount().toString();
            String afterVal = newE.getAmount().toString();

            boolean changed = oldE.getAmount().compareTo(newE.getAmount()) != 0;

            if (changed) {
                afterVal = BOLD + BLUE + afterVal + RESET;
            }

            String diff = formatDiff(oldE.getAmount(), newE.getAmount());

            System.out.printf("%-6s | %-60s | %-30s | %-30s%n",
                    code,
                    oldE.getDescription(),
                    beforeVal,
                    afterVal);
        }
        for (Map.Entry<String, BasicRecord> entry : before.getExpenses()
                                                    .entrySet()) {
            String code = entry.getKey();
            BasicRecord oldE = entry.getValue();
            BasicRecord newE = after.getExpenses().get(code);

            String beforeVal = oldE.getAmount().toString();
            String afterVal = newE.getAmount().toString();

            boolean changed = oldE.getAmount().compareTo(newE.getAmount()) != 0;

            if (changed) {
                afterVal = BOLD + BLUE + afterVal + RESET;
            }

            String diff = formatDiff(oldE.getAmount(), newE.getAmount());

            System.out.printf("%-6s | %-60s | %-30s | %-30s%n",
                    code,
                    oldE.getDescription(),
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

        System.out.printf("%-96s %-48s %-20s %-44s%n",
                    " ",
                    UNDERLINE + BOLD + "Τρέχον" + RESET,
                    " | ",
                    UNDERLINE + BOLD + "Τροποποιημένος" + RESET);

        System.out.printf(BOLD
            + "%-5s | %-65s | %-17s  %-17s  %-20s | %-17s  %-17s  %-17s%n",
            "A/A", "Υπουργείο", "Τακτικός", "Επενδύσεων",
            "Σύνολο", "Τακτικός", "Επενδύσεων", "Σύνολο" + RESET);

        for (Map.Entry<Integer, Ministry> entry : before.getMinistries()
                                                        .entrySet()) {
            Integer code = entry.getKey();
            Ministry oldM = entry.getValue();
            Ministry newM = after.getMinistries().get(code);

            String tBefore = oldM.getRegularBudget().toString();
            String tAfter = newM.getRegularBudget().toString();

            String pBefore = oldM.getPublicInvestments().toString();
            String pAfter = newM.getPublicInvestments().toString();

            String sBefore = oldM.getTotalBudget().toString();
            String sAfter = newM.getTotalBudget().toString();

            if (oldM.getRegularBudget().compareTo(
                newM.getRegularBudget()) != 0) {
                tAfter = BOLD + BLUE + tAfter + RESET;
            }
            if (oldM.getPublicInvestments().compareTo(
                newM.getPublicInvestments()) != 0) {
                pAfter = BOLD + BLUE + pAfter + RESET;
            }
            if (oldM.getTotalBudget().compareTo(newM.getTotalBudget()) != 0) {
                sAfter = BOLD + BLUE + sAfter + RESET;
            }

            System.out.printf(
                "%-5s | %-65s | %-17s  %-17s  %-20s | %-17s  %-17s  %-17s%n",
                code,
                oldM.getName(),
                oldM.getRegularBudget(), oldM.getPublicInvestments(),
                oldM.getTotalBudget(),
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
