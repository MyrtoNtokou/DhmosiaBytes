package budgetcomparison;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import budgetreader.BasicRecord;
import budgetreader.ReadBudget;
import budgetreader.Ministry;

/** Service class
 * for performing budget comparisons. */
public class ComparisonService {

    /**
     * Compares general budget entries between two years for a specific code.
     *
     * @param year1 the base year
     * @param year2 the comparison year
     * @param code the budget code to compare
     */
    public void compareGeneralBudgetByYear(
            final int year1,
            final int year2,
            final String code) {

        // Resolve file names based on years
        String f1 = BudgetFileResolver.generalBudgetFile(year1);
        String f2 = BudgetFileResolver.generalBudgetFile(year2);

        // Read budget entries from files
        List<BasicRecord> l1 = ReadBudget.readGeneralBudget(f1);
        List<BasicRecord> l2 = ReadBudget.readGeneralBudget(f2);

        // Find entries for the specified code
        Optional<BasicRecord> e1 = findBasicRecord(l1, code);
        Optional<BasicRecord> e2 = findBasicRecord(l2, code);

        // Check if entries exist in both years
        if (e1.isEmpty() || e2.isEmpty()) {
            System.out.println("Δεν βρέθηκε η εγγραφή και στα δύο έτη.");
            return;
        }

        // Calculate percentage change
        ComparisonResult result = calculatePercentageChange(
            e1.get().getAmount(),
            e2.get().getAmount(),
            code,
            e1.get().getDescription(),
            year1,
            year2);

        // Print the comparison result
        printResult(result, year1, year2, e1.get().getDescription());
    }

    /**
     * Compares ministry budget entries between two years for a specific code.
     *
     * @param year1 the base year
     * @param year2 the comparison year
     * @param code the budget code to compare
     */
    public void compareMinistryBudgetByYear(
            final int year1,
            final int year2,
            final String code) {

        // Resolve file names based on years
        String f1 = BudgetFileResolver.ministryBudgetFile(year1);
        String f2 = BudgetFileResolver.ministryBudgetFile(year2);

        // Read ministry budget entries from files
        List<Ministry> l1 = ReadBudget.readByMinistry(f1);
        List<Ministry> l2 = ReadBudget.readByMinistry(f2);

        // Find entries for the specified code
        Optional<Ministry> y1 = findMinistry(l1, code);
        Optional<Ministry> y2 = findMinistry(l2, code);

        // Check if entries exist in both years
        if (y1.isEmpty() || y2.isEmpty()) {
            System.out.println("Δεν βρέθηκε υπουργείο και στα δύο έτη.");
            return;
        }

        // Calculate percentage change
        ComparisonResult result = calculatePercentageChange(
            y1.get().getSynolo(),
            y2.get().getSynolo(),
            code,
            y1.get().getName(),
            year1,
            year2);

        // Print the comparison result
        printResult(result, year1, year2, y1.get().getName());
    }

    /**
     * Finds a budget entry by code in the provided list.
     *
     * @param list the list of budget entries
     * @param code the budget code to find
     * @return an Optional containing the found entry, or empty if not found
     */
    private Optional<BasicRecord> findBasicRecord(
        final List<BasicRecord> list, final String code) {

        return list.stream()
            .filter(e -> e.getCode().trim().equals(code.trim()))
            .findFirst();
    }


    /**
     * Finds a ministry budget entry by code in the provided list.
     *
     * @param list the list of ministry budget entries
     * @param code the budget code to find
     * @return an Optional containing the found entry, or empty if not found
     */
    private Optional<Ministry> findMinistry(
        final List<Ministry> list, final String code) {

        return list.stream()
                .filter(y -> String.valueOf(y.getcode()).equals(code))
                .findFirst();
    }


    /** Constant for percentage calculations. */
    private static final int PERCENT = 100;

    /**
     * Calculates the percentage change between two amounts.
     *
     * @param base the amount in the base year
     * @param current the amount in the comparison year
     * @param code the budget code
     * @param name the name or description of the budget item
     * @param baseYear the base year
     * @param compareYear the comparison year
     * @return a ComparisonResult containing the percentage change
     * @throws IllegalArgumentException if the base amount is zero
     */
    private ComparisonResult calculatePercentageChange(
        final BigDecimal base,
        final BigDecimal current,
        final String code,
        final String name,
        final int baseYear,
        final int compareYear) {

        // Check for division by zero
        if (base.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException(
            "Η βάση σύγκρισης είναι μηδέν για τον κωδικό " + code + ".");
        }

        // Calculate percentage change
        BigDecimal percentage = current
                .subtract(base)
                .multiply(BigDecimal.valueOf(PERCENT))
                .divide(base, 2, RoundingMode.HALF_UP);


        // Create and return ComparisonResult
        return new ComparisonResult(
            code, name, baseYear, compareYear, percentage);
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
        String verb = (sign >= 0) ? "αυξήθηκε" : "μειώθηκε";

        // Print formatted result
        System.out.printf(
                "%s (%s) %s κατά %s%% το %d σε σχέση με το %d%n",
                r.getcode(),
                name,
                verb,
                r.getPercentageChange().abs(),
                year2,
                year1
        );
    }
}
