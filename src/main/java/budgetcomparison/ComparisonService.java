package budgetcomparison;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;
import static aggregatedata.ConsoleColors.RED;
import static aggregatedata.ConsoleColors.GREEN;
import static aggregatedata.ConsoleColors.RESET;
import static aggregatedata.ConsoleColors.BOLD;

/** Service class
 * for performing budget comparisons. */
public class ComparisonService {

    /**
     * Compares general budget entries between two years for a specific code.
     *
     * @param year1 the base year
     * @param year2 the comparison year
     * @param kodikos the budget code to compare
     */
    public void compareGeneralBudgetByYear(
            final int year1,
            final int year2,
            final String kodikos) {

        // Resolve file names based on years
        String f1 = BudgetFileResolver.generalBudgetFile(year1);
        String f2 = BudgetFileResolver.generalBudgetFile(year2);

        // Read budget entries from files
        List<Eggrafi> l1 = ReadBudget.readGeneralBudget(f1);
        List<Eggrafi> l2 = ReadBudget.readGeneralBudget(f2);

        // Find entries for the specified code
        Optional<Eggrafi> e1 = findEggrafi(l1, kodikos);
        Optional<Eggrafi> e2 = findEggrafi(l2, kodikos);

        // Check if entries exist in both years
        if (e1.isEmpty() || e2.isEmpty()) {
            System.out.println(RED + "Δεν βρέθηκε η εγγραφή και στα δύο έτη."
                                                + RESET);
            return;
        }

        // Calculate percentage change
        ComparisonResult result = calculatePercentageChange(
            e1.get().getPoso(),
            e2.get().getPoso(),
            kodikos,
            e1.get().getPerigrafi(),
            year1,
            year2);

        // Print the comparison result
        printResult(result, year1, year2, e1.get().getPerigrafi());
    }

    /**
     * Compares ministry budget entries between two years for a specific code.
     *
     * @param year1 the base year
     * @param year2 the comparison year
     * @param kodikos the budget code to compare
     */
    public void compareMinistryBudgetByYear(
            final int year1,
            final int year2,
            final String kodikos) {

        // Resolve file names based on years
        String f1 = BudgetFileResolver.ministryBudgetFile(year1);
        String f2 = BudgetFileResolver.ministryBudgetFile(year2);

        // Read ministry budget entries from files
        List<Ypourgeio> l1 = ReadBudget.readByMinistry(f1);
        List<Ypourgeio> l2 = ReadBudget.readByMinistry(f2);

        // Find entries for the specified code
        Optional<Ypourgeio> y1 = findYpourgeio(l1, kodikos);
        Optional<Ypourgeio> y2 = findYpourgeio(l2, kodikos);

        // Check if entries exist in both years
        if (y1.isEmpty() || y2.isEmpty()) {
            System.out.println(RED + "Δεν βρέθηκε υπουργείο και στα δύο έτη."
                                            + RESET);
            return;
        }

        // Calculate percentage change
        ComparisonResult result = calculatePercentageChange(
            y1.get().getSynolo(),
            y2.get().getSynolo(),
            kodikos,
            y1.get().getOnoma(),
            year1,
            year2);

        // Print the comparison result
        printResult(result, year1, year2, y1.get().getOnoma());
    }

    /**
     * Finds a budget entry by code in the provided list.
     *
     * @param list the list of budget entries
     * @param kodikos the budget code to find
     * @return an Optional containing the found entry, or empty if not found
     */
    private Optional<Eggrafi> findEggrafi(
        final List<Eggrafi> list, final String kodikos) {

        return list.stream()
            .filter(e -> e.getKodikos().trim().equals(kodikos.trim()))
            .findFirst();
    }


    /**
     * Finds a ministry budget entry by code in the provided list.
     *
     * @param list the list of ministry budget entries
     * @param kodikos the budget code to find
     * @return an Optional containing the found entry, or empty if not found
     */
    private Optional<Ypourgeio> findYpourgeio(
        final List<Ypourgeio> list, final String kodikos) {

        return list.stream()
                .filter(y -> String.valueOf(y.getKodikos()).equals(kodikos))
                .findFirst();
    }


    /** Constant for percentage calculations. */
    private static final int PERCENT = 100;

    /**
     * Calculates the percentage change between two amounts.
     *
     * @param base the amount in the base year
     * @param current the amount in the comparison year
     * @param kodikos the budget code
     * @param name the name or description of the budget item
     * @param baseYear the base year
     * @param compareYear the comparison year
     * @return a ComparisonResult containing the percentage change
     * @throws IllegalArgumentException if the base amount is zero
     */
    private ComparisonResult calculatePercentageChange(
        final BigDecimal base,
        final BigDecimal current,
        final String kodikos,
        final String name,
        final int baseYear,
        final int compareYear) {

        // Check for division by zero
        if (base.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException(
            "Η βάση σύγκρισης είναι μηδέν για τον κωδικό " + kodikos + ".");
        }

        // Calculate percentage change
        BigDecimal percentage = current
                .subtract(base)
                .multiply(BigDecimal.valueOf(PERCENT))
                .divide(base, 2, RoundingMode.HALF_UP);


        // Create and return ComparisonResult
        return new ComparisonResult(
            kodikos, name, baseYear, compareYear, percentage);
    }

    /**
     * Prints the comparison result to the console.
     *
     * @param r the ComparisonResult to print
     * @param year1 the base year
     * @param year2 the comparison year
     * @param name the name or description of the budget item
     */
    private void printResult(
        final ComparisonResult r,
        final int year1,
        final int year2,
        final String name) {

        // Determine if the amount increased or decreased
        int sign = r.getPercentageChange().signum();
        String verb = (sign >= 0)
                ? GREEN + "αυξήθηκε" + RESET : RED + "μειώθηκε" + RESET;

        // Print formatted result
        System.out.printf(
                "%s (%s) %s κατά %s%% το %d σε σχέση με το %d%n",
                r.getKodikos(),
                name,
                verb,
                BOLD + r.getPercentageChange().abs() + RESET,
                year2,
                year1
        );
    }
}
