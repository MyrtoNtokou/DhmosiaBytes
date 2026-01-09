package aggregatedata;

import budgetreader.Eggrafi;

import java.math.BigDecimal;
import java.util.List;

import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.BLUE;
import static aggregatedata.ConsoleColors.CYAN;

/**
 * Print Budget stats formatted.
 */
public final class BudgetStatsPrinter {

    /** Constructor. */
    private BudgetStatsPrinter() { }

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
