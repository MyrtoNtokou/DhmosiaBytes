/**
 * Package containing classes for aggregate data, based on the general budget
 * and the ministries.
 *
 * <h2>Main responsibilities</h2>
 * <ul>
 *   <li>
 *     Calculation of TOP-N maximum and minimum revenues and expenses
 *   </li>
 *   <li>
 *     Percentage contribution of major revenue and expense categories
 *   </li>
 *   <li>
 *     Analysis of budget allocations per ministry, including:
 *     <ul>
 *       <li>Regular budget (RegularBudget)</li>
 *       <li>Public investments (Ependyseis)</li>
 *       <li>Total expenditures</li>
 *     </ul>
 *   </li>
 *   <li>
 *     Calculation of the investment-to-total expenditure ratio for each
 *     ministry
 *   </li>
 *   <li>
 *     Formatted console output of results using ANSI colors
 *   </li>
 * </ul>
 *
 * <h2>Package structure</h2>
 * <ul>
 *   <li>
 *     <b>Analyzer classes</b>:
 *     {@link aggregatedata.BudgetAnalyzer},
 *     {@link aggregatedata.MinistryAnalyzer},
 *     {@link aggregatedata.InvestmentAnalyzer}
 *   </li>
 *   <li>
 *     <b>Data holder classes</b>:
 *     {@link aggregatedata.BudgetStats},
 *     {@link aggregatedata.MinistryStats},
 *     {@link aggregatedata.InvestmentRatio}
 *   </li>
 *   <li>
 *     <b>Printer classes</b>:
 *     {@link aggregatedata.BudgetStatsPrinter},
 *     {@link aggregatedata.MinistryStatsPrinter},
 *     {@link aggregatedata.InvestmentPrinter}
 *   </li>
 * </ul>
 *
 * <p>
 * This package depends on the {@code budgetreader} package for raw budget
 * data input. It does not modify the original data, but produces derived
 * aggregated statistics for analysis and presentation purposes.
 * </p>
 *
 * @version 1.0
 */
package aggregatedata;
