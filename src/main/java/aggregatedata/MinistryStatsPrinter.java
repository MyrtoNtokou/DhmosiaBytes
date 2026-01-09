package aggregatedata;

import budgetreader.Ypourgeio;

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
     * Print max Taktikos.
     * @param stats object
     */
    public static void printTaktikos(final MinistryStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ ΔΑΠΑΝΕΣ "
            + "ΤΑΚΤΙΚΟΥ ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ ===" + RESET);

        List<Ypourgeio> max = stats.getMaxTaktikos();
        List<BigDecimal> per = stats.getMaxTaktikosPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ypourgeio y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.printf("%-5s | %-45s | %-15s | %-10s%n",
                y.getKodikos(),
                y.getOnoma(),
                y.getTaktikos(),
                BLUE + p + "%" + RESET);
        }
    }

    /**
     * Print max Ependyseis.
     * @param stats object
     */
    public static void printEpendyseis(final MinistryStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ ΔΑΠΑΝΕΣ "
            + "ΔΗΜΟΣΙΩΝ ΕΠΕΝΔΥΣΕΩΝ ===" + RESET);

        List<Ypourgeio> max = stats.getMaxEpendyseis();
        List<BigDecimal> per = stats.getMaxEpendyseisPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ypourgeio y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.printf("%-5s | %-45s | %-15s | %-10s%n",
                y.getKodikos(),
                y.getOnoma(),
                y.getEpendyseis(),
                BLUE + p + "%" + RESET);
        }
    }

    /**
     * Print max Synolo.
     * @param stats object
     */
    public static void printSynolo(final MinistryStats stats) {

        System.out.println(BOLD + CYAN + "\n=== ΜΕΓΙΣΤΕΣ "
            + "ΣΥΝΟΛΙΚΕΣ ΔΑΠΑΝΕΣ ===" + RESET);

        List<Ypourgeio> max = stats.getMaxSynolo();
        List<BigDecimal> per = stats.getMaxSynoloPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ypourgeio y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.printf("%-5s | %-45s | %-15s | %-10s%n",
                y.getKodikos(),
                y.getOnoma(),
                y.getSynolo(),
                BLUE + p + "%" + RESET);
        }
    }
}
