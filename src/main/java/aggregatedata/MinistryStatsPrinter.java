package aggregatedata;

import budgetreader.Ypourgeio;

import java.math.BigDecimal;
import java.util.List;

public final class MinistryStatsPrinter {

    /** Constructor. */
    private MinistryStatsPrinter() { }

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

    /**
     * Print max Taktikos.
     * @param stats object
     */
    public static void printTaktikos(final MinistryStats stats) {

        System.out.println(BOLD + "\n=== Μέγιστες Δαπάνες "
            + "Τακτικού Προϋπολογισμού ===" + RESET);

        List<Ypourgeio> max = stats.getMaxTaktikos();
        List<BigDecimal> per = stats.getMaxTaktikosPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ypourgeio y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.println(
                y.getKodikos() + " | "
                + y.getOnoma() + " | "
                + y.getTaktikos() + " | "
                + BLUE + p + "%" + RESET
            );
        }
    }

    /**
     * Print max Ependyseis.
     * @param stats object
     */
    public static void printEpendyseis(final MinistryStats stats) {

        System.out.println(BOLD + "\n=== Μέγιστες Δαπάνες "
            + "Δημοσίων Επενδύσεων ===" + RESET);

        List<Ypourgeio> max = stats.getMaxEpendyseis();
        List<BigDecimal> per = stats.getMaxEpendyseisPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ypourgeio y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.println(
                y.getKodikos() + " | "
                + y.getOnoma() + " | "
                + y.getEpendyseis() + " | "
                + BLUE + p + "%" + RESET
            );
        }
    }

    /**
     * Print max Synolo.
     * @param stats object
     */
    public static void printSynolo(final MinistryStats stats) {

        System.out.println(BOLD + "\n=== Μέγιστες "
            + "Συνολικές Δαπάνες ===" + RESET);

        List<Ypourgeio> max = stats.getMaxSynolo();
        List<BigDecimal> per = stats.getMaxSynoloPercentages();

        for (int i = 0; i < max.size(); i++) {
            Ypourgeio y = max.get(i);
            BigDecimal p = per.get(i);

            System.out.println(
                y.getKodikos() + " | "
                + y.getOnoma() + " | "
                + y.getSynolo() + " | "
                + BLUE + p + "%" + RESET
            );
        }
    }
}
