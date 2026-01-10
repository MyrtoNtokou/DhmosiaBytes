/**
 * The {@code budgetreader} package provides classes for reading and displaying
 * budget data.
 *
 * <p>This package contains utility and model classes to load, parse, and show
 * both general and ministry-specific budget data from CSV files.</p>
 *
 * <h2>Main Classes and Responsibilities</h2>
 *
 * <ul>
 *   <li>{@link budgetreader.ReadBudget} – reads budget CSV files and converts
 *       each row into {@link budgetreader.Eggrafi} or
 *       {@link budgetreader.Ypourgeio} objects. Supports:
 *     <ul>
 *       <li>General budget (Eggrafi) CSVs</li>
 *       <li>Ministry budget (Ypourgeio) CSVs</li>
 *       <li>Cropped ministry CSVs (only code and total budget)</li>
 *     </ul>
 *   </li>
 *
 *   <li>{@link budgetreader.DisplayBudget} – utility class to display budget
 *       lists on the console, formatted for readability.</li>
 *
 *   <li>{@link budgetreader.Eggrafi} – model for a general budget record
 *       containing code, description, and amount.</li>
 *
 *   <li>{@link budgetreader.Ypourgeio} – model for a ministry budget record
 *       with code, name, regular budget, public investment budget, total,
 *       and allocations.</li>
 * </ul>
 *
 * <h2>Data Flow</h2>
 *
 * <pre>
 * CSV files → {@link budgetreader.ReadBudget} →
 * List of {@link Eggrafi} / {@link Ypourgeio}
 *        ↓
 * {@link budgetreader.DisplayBudget} → printed output to console
 * </pre>
 *
 * <p>All monetary amounts are stored as {@link java.math.BigDecimal} for
 * precision. Thousands separators are removed and decimal points normalized
 * during CSV parsing.</p>
 *
 * @since 1.0
 */
package budgetreader;
