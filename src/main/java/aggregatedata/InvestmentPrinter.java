package aggregatedata;

import budgetreader.Ypourgeio;
import java.util.List;

import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.BLUE;
import static aggregatedata.ConsoleColors.CYAN;

/**
 * Print investment stats formatted.
 */
public final class InvestmentPrinter {

    /** Constructor. */
    private InvestmentPrinter() { }

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
