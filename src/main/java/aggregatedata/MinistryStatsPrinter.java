package aggregatedata;

import budgetreader.Ministry;

import java.math.BigDecimal;
import java.util.List;

import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.BLUE;
import static aggregatedata.ConsoleColors.CYAN;

/**
 * Print ministry stats formatted.
 */
public final class MinistryStatsPrinter {

    /** Constructor. */
    private MinistryStatsPrinter() { }

    /**
     * Print max RegularBudget.
     * @param stats object
     */
    public static void printRegularBudget(final MinistryStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ ΔΑΠΑΝΕΣ "
            + "ΤΑΚΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===" + RESET);

        List<Ministry> max = stats.getMaxRegularBudget();
        List<BigDecimal> per = stats.getMaxRegularBudgetPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ministry y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.printf("%-5s | %-45s | %-15s | %-10s%n",
                y.getcode(),
                y.getName(),
                y.getRegularBudget(),
                BLUE + p + "%" + RESET);
        }
    }

    /**
     * Print max PublicInvestments.
     * @param stats object
     */
    public static void printPublicInvestments(final MinistryStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ ΔΑΠΑΝΕΣ "
            + "ΔΗΜΟΣΙΩΝ ΕΠΕΝΔΥΣΕΩΝ ===" + RESET);

        List<Ministry> max = stats.getMaxPublicInvestments();
        List<BigDecimal> per = stats.getMaxPublicInvestmentsPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ministry y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.printf("%-5s | %-45s | %-15s | %-10s%n",
                y.getcode(),
                y.getName(),
                y.getPublicInvestments(),
                BLUE + p + "%" + RESET);
        }
    }

    /**
     * Print max TotalBudget.
     * @param stats object
     */
    public static void printTotalBudget(final MinistryStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ "
            + "ΣΥΝΟΛΙΚΕΣ ΔΑΠΑΝΕΣ ===" + RESET);

        List<Ministry> max = stats.getMaxTotalBudget();
        List<BigDecimal> per = stats.getMaxTotalBudgetPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ministry y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.printf("%-5s | %-45s | %-15s | %-10s%n",
                y.getcode(),
                y.getName(),
                y.getTotalBudget(),
                BLUE + p + "%" + RESET);
        }
    }
}
