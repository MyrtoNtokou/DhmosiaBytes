/**
 * Package containing classes for charts related.
 * to the budget data such as income, expences
 * and expences by ministry
 * The {@code budgetcharts} package provides classes for creating and displaying
 * charts based on national budget data.
 *
 * <p>This package contains utility classes to generate bar charts, pie charts,
 * and line charts for general revenues and expenses, as well as ministry-specific
 * budget data, using the XChart library.</p>
 *
 * <h2>Main Classes and Responsibilities</h2>
 *
 * <ul>
 *   <li>{@link budgetcharts.Barcharts} – provides static methods to create bar charts for:
 *       <ul>
 *         <li>General revenues (Έσοδα)</li>
 *         <li>General expenses (Έξοδα)</li>
 *         <li>Ministry budgets</li>
 *         <li>Yearly breakdown of revenues, expenses, and ministry budgets</li>
 *       </ul>
 *       Charts are scaled to billions and formatted for readability.
 *   </li>
 *
 *   <li>{@link budgetcharts.MoreCharts} – provides additional chart types:
 *       <ul>
 *         <li>Pie charts for total revenues vs. expenses</li>
 *         <li>Pie charts for deficit coverage</li>
 *         <li>Line charts for revenues and expenses over multiple years</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <h2>Data Flow</h2>
 *
 * <pre>
 * CSV files → {@link budgetreader.ReadBudget} → List of {@link budgetreader.Eggrafi} / {@link budgetreader.Ypourgeio}
 *                ↓
 *           {@link budgetcharts.Barcharts} / {@link budgetcharts.MoreCharts} → charts displayed via Swing
 * </pre>
 *
 * <p>All monetary amounts are stored as {@link java.math.BigDecimal} for precision.
 * Charts convert values to billions for readability. Categories and years are extracted
 * from budget records to populate charts.</p>
 *

 */
package budgetcharts;
