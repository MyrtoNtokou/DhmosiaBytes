package aggregatedata;

import budgetreader.Ypourgeio;
import java.util.List;

public final class InvestmentPrinter {

    /** Constructor. */
    private InvestmentPrinter() { }

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
     * Print ministry, investment, total and percentage for each ministry.
     * @param ypourg
     * @param ratios
     */
    public static void print(final List<Ypourgeio> ypourg,
                            final List<InvestmentRatio> ratios) {

        System.out.println(BOLD + CYAN + "\n=== ΔΕΙΚΤΗΣ "
                            + "ΛΟΓΟΥ ΕΠΕΝΔΥΣΕΩΝ ===\n" + RESET);

        System.out.printf(BOLD + "%-5s | %-55s | %-15s | %-15s | %-15s%n",
                "A/A", "Υπουργείο", "Επενδύσεις", "Σύνολο",
                "Λόγος Επενδύσεων" + RESET);

        for (InvestmentRatio ir : ratios) {

            // Find Ypourgeio to get code/investment/total
            Ypourgeio y = findByName(ypourg, ir.getOnoma());

            if (y == null) {
                continue;
            }

            System.out.printf("%-5d | %-55s | %-15s | %-15s | %-15s%n",
                    y.getKodikos(),
                    y.getOnoma(),
                    y.getEpendyseis().toPlainString(),
                    y.getSynolo().toPlainString(),
                    BLUE + ir.getPercentage().toPlainString() + "%" + RESET);
        }
    }

    /**
     * Find ministry by name.
     * @param list
     * @param name
     * @return Ypourgeio
     */
    private static Ypourgeio findByName(final List<Ypourgeio> list,
                                        final String name) {
        for (Ypourgeio y : list) {
            if (y.getOnoma().equals(name)) {
                return y;
            }
        }
        return null;
    }
}
