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
     *
     * @param ministry List of all ministries to retrieve details from.
     * @param ratios List of calculated investment ratios to be printed.
     */
    public static void print(final List<Ministry> ministry,
                            final List<InvestmentRatio> ratios) {

        System.out.println(BOLD + CYAN + "\n=== ΔΕΙΚΤΗΣ "
                            + "ΛΟΓΟΥ ΕΠΕΝΔΥΣΕΩΝ ===\n" + RESET);

        System.out.printf(BOLD + "%-5s | %-55s | %-15s | %-15s | %-15s%n",
                "A/A", "Υπουργείο", "Επενδύσεις", "Σύνολο",
                "Λόγος Επενδύσεων" + RESET);

        for (InvestmentRatio ir : ratios) {

            // Find Ministry to get code/investment/total
            Ministry y = findByName(ministry, ir.getName());

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
     *
     * @param list The list of ministries to search through.
     * @param name The name of the ministry to find.
     * @return Ministry object if found, otherwise null.
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
