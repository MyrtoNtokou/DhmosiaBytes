/**
 * The {@code dhmosiabytes} package contains all core classes and enums
 * for managing the public budget application.
 *
 * <p>The package covers the following functionalities:</p>
 *
 * <ul>
 *   <li>User and role management
 *     <ul>
 *       <li>{@link dhmosiabytes.User} - represents a user with
 *           username, password, and role.</li>
 *       <li>{@link dhmosiabytes.UserDatabase} - singleton for storing,
 *           retrieving, and managing users with JSON persistence.</li>
 *       <li>{@link dhmosiabytes.Role} - enum defining user types
 *           (Prime Minister, Parliament, Finance Ministry, Other
 *           Ministries) and their access rights.</li>
 *     </ul>
 *   </li>
 *
 *   <li>Menus and user interaction
 *     <ul>
 *       <li>{@link dhmosiabytes.MenuOptions} - enum for main menu
 *           options of the application.</li>
 *       <li>{@link dhmosiabytes.ShowMenuOptions} - main class
 *           displaying the menu and executing actions according to
 *           the user's role.</li>
 *       <li>{@link dhmosiabytes.ShowEditMenuOptions} - submenu for
 *           editing budget entries (income/expenses, budget type).</li>
 *       <li>{@link dhmosiabytes.RequestsController} - helper class for
 *           evaluating, approving, or rejecting ministry requests.</li>
 *       <li>{@link dhmosiabytes.NotCorrectPassword} - exception for
 *           invalid passwords.</li>
 *     </ul>
 *   </li>
 *
 *   <li>Options and data types
 *     <ul>
 *       <li>{@link dhmosiabytes.MinistryOptions} - enum containing all
 *           ministries and decentralized administrations, each with a
 *           code and description.</li>
 *       <li>{@link dhmosiabytes.RevenueOrExpense} - enum for budget
 *           type: income or expenses.</li>
 *     </ul>
 *   </li>
 *
 *   <li>Comparison and charts support
 *     <ul>
 *       <li>{@link dhmosiabytes.ShowMenuOptions} calls
 *         <ul>
 *           <li>{@link budgetlogic.BudgetDiffPrinter} for budget
 *               comparisons.</li>
 *           <li>{@link budgetcomparison.ComparisonController} for
 *               multi-year or general comparisons.</li>
 *           <li>{@link dhmosiabytes.Graphs} for chart display.</li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <p>The package structure focuses on:</p>
 * <ul>
 *   <li>Users and roles</li>
 *   <li>Application menus and options (role-based access)</li>
 *   <li>Budget and ministry request management</li>
 *   <li>Data comparison and presentation</li>
 * </ul>
 *
 * <p>The package interacts with other packages:</p>
 * <ul>
 *   <li>{@code budgetlogic} - for loading, assembling, and printing
 *       budgets</li>
 *   <li>{@code budgetreader} - for reading budget files and
 *       displaying data</li>
 *   <li>{@code ministryrequests} - for managing and displaying
 *       ministry requests</li>
 * </ul>
 */
package dhmosiabytes;
