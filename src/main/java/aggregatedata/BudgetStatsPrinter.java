package aggregatedata;

import budgetreader.Eggrafi;

import java.math.BigDecimal;
import java.util.List;

public final class BudgetStatsPrinter {

    /** Constructor. */
    private BudgetStatsPrinter() { }

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

    /** Blue color used percentages. */
    private static final String BLUE = rgb(30, 144, 255);
    /** Cyan color used titles. */
    private static final String CYAN = rgb(0, 200, 255);

    /**
     * Print revenues (max + min).
     * @param stats BudgetStats object
     */
    public static void printRevenues(final BudgetStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ "
                    + "ΠΗΓΕΣ ΕΣΟΔΩΝ ===" + RESET);

        List<Eggrafi> maxRev = stats.getMaxRevenues();
        List<BigDecimal> maxRevPer = stats.getMaxRevenuePercentages();

        for (int i = 0; i < maxRev.size(); i++) {
            Eggrafi e = maxRev.get(i);
            BigDecimal p = maxRevPer.get(i);

            System.out.printf("%-8s | %-35s | %-15s | %-10s%n",
                e.getKodikos(),
                e.getPerigrafi(),
                e.getPoso(),
                BLUE + p + "%" + RESET);
        }
        System.out.println(BOLD + CYAN + "\n=== ΕΛΑΧΙΣΤΕΣ "
                    + "ΠΗΓΕΣ ΕΣΟΔΩΝ ===" + RESET);

        for (Eggrafi e : stats.getMinRevenues()) {
            System.out.printf("%-8s | %-35s | %-15s%n",
                e.getKodikos(),
                e.getPerigrafi(),
                e.getPoso());
        }
    }

    /**
     * Print expenses (max + min).
     * @param stats BudgetStats object
     */
    public static void printExpenses(final BudgetStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΟΙ "
                    + "ΤΟΜΕΙΣ ΕΞΟΔΩΝ ===" + RESET);

        List<Eggrafi> maxExp = stats.getMaxExpenses();
        List<BigDecimal> maxExpPer = stats.getMaxExpensePercentages();

        for (int i = 0; i < maxExp.size(); i++) {
            Eggrafi e = maxExp.get(i);
            BigDecimal p = maxExpPer.get(i);

            System.out.printf("%-8s | %-35s | %-15s | %-10s%n",
                e.getKodikos(),
                e.getPerigrafi(),
                e.getPoso(),
                BLUE + p + "%" + RESET);
        }
        System.out.println(BOLD + CYAN + "\n=== ΕΛΑΧΙΣΤΟΙ "
                    + "ΤΟΜΕΙΣ ΕΞΟΔΩΝ ===" + RESET);

        for (Eggrafi e : stats.getMinExpenses()) {
            System.out.printf("%-8s | %-35s | %-15s%n",
                e.getKodikos(),
                e.getPerigrafi(),
                e.getPoso());
        }
    }
}
