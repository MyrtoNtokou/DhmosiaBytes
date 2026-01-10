/**
 * The {@code budgetcomparison} package provides classes for comparing budget data across years.
 *
 * <p>This package contains utility, controller, and service classes to compare
 * both general and ministry-specific budget data and display percentage changes.</p>
 *
 * <h2>Main Classes and Responsibilities</h2>
 *
 * <ul>
 *   <li>{@link budgetcomparison.BudgetFileResolver} – utility class for resolving
 *       file names for general or ministry budget CSV files based on a given year.</li>
 *
 *   <li>{@link budgetcomparison.ComparisonController} – handles user interaction,
 *       prompts for years and budget codes, and triggers budget comparisons
 *       using {@link budgetcomparison.ComparisonService}.</li>
 *
 *   <li>{@link budgetcomparison.ComparisonService} – service class that performs
 *       the actual comparison of budget entries between two years. Supports:
 *       <ul>
 *         <li>General budget entries via {@link budgetreader.Eggrafi}</li>
 *         <li>Ministry budget entries via {@link budgetreader.Ypourgeio}</li>
 *         <li>Percentage change calculation and formatted console output</li>
 *       </ul>
 *   </li>
 *
 *   <li>{@link budgetcomparison.ComparisonResult} – model representing the result
 *       of a budget comparison, including code, description, base year, comparison year,
 *       and percentage change.</li>
 * </ul>
 *
 * <h2>Data Flow</h2>
 *
 * <pre>
 * CSV files → {@link budgetcomparison.BudgetFileResolver} → resolved file names
 *      ↓
 * {@link budgetreader.ReadBudget} → List of {@link budgetreader.Eggrafi} / {@link budgetreader.Ypourgeio}
 *      ↓
 * {@link budgetcomparison.ComparisonService} → {@link budgetcomparison.ComparisonResult}
 *      ↓
 * {@link budgetcomparison.ComparisonController} → printed output to console
 * </pre>
 *
 * <p>Percentage changes are calculated as {@link java.math.BigDecimal} with
 * rounding to 2 decimal places to ensure precision.</p>
 *
 * @since 1.0
 */
package budgetcomparison;
