/**
 * The {@code budgetlogic} package provides classes for managing budget data.
 *
 * <p>This package contains the core classes for processing, saving, and
 * validating budget data, both at the general table level (revenues/expenses)
 * and at the ministry level.</p>
 *
 * <h2>Main Classes and Responsibilities</h2>
 *
 * <ul>
 *   <li>{@link budgetreader.ReadBudget} – loads budget data from CSV files.
 *     Supports:
 *     <ul>
 *       <li>General records ({@link budgetreader.BasicRecord})</li>
 *       <li>Ministries ({@link budgetreader.Ministry}
 *     </ul>
 *   </li>
 *
 *   <li>{@link budgetlogic.BudgetSave} – saves changes to CSV files using
 *       {@link budgetlogic.BudgetWriter}.</li>
 *
 *   <li>{@link budgetlogic.BudgetWriter} – writes budget data to CSV files
 *       for general records and ministries.</li>
 *
 *   <li>{@link budgetlogic.BudgetService} – core service for managing budget
 *       logic. Provides:
 *     <ul>
 *       <li>Updating amounts in general records or ministries</li>
 *       <li>Recomputing total/aggregate amounts</li>
 *       <li>Propagating differences to expenses using mappings or ratios</li>
 *       <li>Validating totals and consistency</li>
 *     </ul>
 *   </li>
 *
 *   <li>{@link budgetreader.Ministry} – ministry model with regular budget
 *       (regularBudget), public investment budget (PDE), total amount, and
 *       allocation by category.</li>
 * </ul>
 *
 * <h2>Data Flow</h2>
 *
 * <pre>
 * CSV files → {@link budgetreader.ReadBudget} →
 * {@link budgetlogic.Budget}
 *        ↓
 * {@link budgetlogic.BudgetService} → updates / validation
 *        ↓
 * {@link budgetlogic.BudgetSave} →
 * {@link budgetlogic.BudgetWriter} → CSV
 * </pre>
 *
 * <p>All amounts are stored as {@link java.math.BigDecimal} for precision,
 * and aggregate totals are rounded to 2 decimal places using standard rules.
 * </p>
 *
 * @since 1.0
 */
package budgetlogic;
