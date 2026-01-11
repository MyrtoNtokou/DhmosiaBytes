package aggregatedata;

import budgetreader.Ministry;
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
    public static void print(final List<Ministry> ypourg,
                            final List<InvestmentRatio> ratios) {

        System.out.println(BOLD + CYAN + "\n=== ΔΕΙΚΤΗΣ "
                            + "ΛΟΓΟΥ ΕΠΕΝΔΥΣΕΩΝ ===\n" + RESET);

        System.out.printf(BOLD + "%-5s | %-55s | %-15s | %-15s | %-15s%n",
                "A/A", "Υπουργείο", "Επενδύσεις", "Σύνολο",
                "Λόγος Επενδύσεων" + RESET);

        for (InvestmentRatio ir : ratios) {

            // Find Ministry to get code/investment/total
            Ministry y = findByName(ypourg, ir.getName());

            if (y == null) {
                continue;
            }

            System.out.printf("%-5d | %-55s | %-15s | %-15s | %-15s%n",
                    y.getcode(),
                    y.getName(),
                    y.getPublicInvestments().toPlainString(),
                    y.getTotalBudget().toPlainString(),
                    BLUE + ir.getPercentage().toPlainString() + "%" + RESET);
        }
    }

    /**
     * Find ministry by name.
     * @param list
     * @param name
     * @return Ministry
     */
    private static Ministry findByName(final List<Ministry> list,
                                        final String name) {
        for (Ministry y : list) {
            if (y.getName().equals(name)) {
                return y;
            }
        }
        return null;
    }
}
