/**
 * Package containing classes for managing ministry budget change requests.
 *
 * <p>
 * The {@code ministryrequests} package provides functionality for submitting,
 * storing, processing and reviewing budget change requests submitted by
 * individual ministries. Each request represents a proposed modification
 * to the national budget.
 * </p>
 *
 * <h2>Main responsibilities</h2>
 * <ul>
 *   <li>
 *     Submission and validation of ministry budget change requests
 *   </li>
 *   <li>
 *     Support for different request types, including:
 *     <ul>
 *       <li>Regular budget (Taktikos)</li>
 *       <li>Public investment budget (Ependyseis)</li>
 *       <li>Combined budget changes</li>
 *     </ul>
 *   </li>
 *   <li>
 *     Parsing and extraction of budget change data from text-based sources
 *   </li>
 *   <li>
 *     Persistent storage of requests using a file-based repository
 *   </li>
 *   <li>
 *     Tracking of request lifecycle through multiple approval stages
 *   </li>
 *   <li>
 *     Detection and prevention of duplicate requests
 *   </li>
 *   <li>
 *     Formatted and colorized console output for review and auditing
 *   </li>
 * </ul>
 *
 * <h2>Package structure</h2>
 * <ul>
 *   <li>
 *     <b>Domain model classes</b>:
 *     {@link ministryrequests.MinistryRequest},
 *     {@link ministryrequests.RequestType},
 *     {@link ministryrequests.RequestStatus}
 *   </li>
 *   <li>
 *     <b>Service and repository classes</b>:
 *     {@link ministryrequests.MinistryRequestService},
 *     {@link ministryrequests.MinistryRequestRepository}
 *   </li>
 *   <li>
 *     <b>Parsing and utility classes</b>:
 *     {@link ministryrequests.BudgetRequestLoader},
 *     {@link ministryrequests.BudgetRequestParser},
 *     {@link ministryrequests.DiffColorizer},
 *     {@link ministryrequests.MinistryRequestPrinter}
 *   </li>
 * </ul>
 *
 * <p>
 * This package depends on the {@code budgetreader} package for ministry
 * identification and raw budget data. It focuses exclusively on request
 * management and does not directly apply changes to the budget datasets.
 * </p>
 *
 * @version 1.0
 */
package ministryrequests;
