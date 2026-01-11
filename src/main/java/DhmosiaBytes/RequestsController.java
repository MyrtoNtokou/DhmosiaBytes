package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.CYAN;
import static aggregatedata.ConsoleColors.RESET;
import ministryrequests.MinistryRequest;
import ministryrequests.MinistryRequestService;
import ministryrequests.Request;
import ministryrequests.RequestPrinter;
import ministryrequests.RequestStatus;
import revenuerequests.RevenueRequest;
import revenuerequests.RevenueRequestService;

/**
 * Utility class that handles user interactions related to reviewing,
 * approving, or rejecting ministry requests.
 */
public final class RequestsController {

    /** Code for option 2: rejected. */
    private static final int REJECT = 2;

    /** Min choice of ministries. */
    private static final int MIN_CHOICE_PRIME_MINISTER = 1;
    /** Max choice of ministries. */
    private static final int MAX_CHOICE_PRIME_MINISTER = 3;

    /** Constructor. */
    private RequestsController() { }

    /**
     * Asks user to enter the code of the request to be completed or rejected.
     *
     * @param input the Scanner for user input
     * @param requests the list of available requests to choose from
     * @return the user's choice
     */
    public static int chooseRequest(final Scanner input,
            final List<? extends Request> requests) {

        if (requests.isEmpty()) {
            return 0;
        }

        int choice;
        while (true) {
            if (!requests.isEmpty()) {
                System.out.println();
                System.out.println("Έχετε την δυνατότητα να σημειώσετε τα "
                + "αιτήματα που έχετε ολοκληρώσει ή απορρίψει.");
                System.out.println("Επιλέξτε 0 για επιστροφή στο "
                + "προηγούμενο μενού.");
                System.out.print("Επιλέξτε το " + BOLD + CYAN
                + "ID Αιτήματος " + RESET
                + "για θετική ή αρνητική αξιολόγηση: ");

                try {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 0) {
                        return choice;
                    }
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    input.nextLine();
                }
            }
        }
    }

    /**
     * Asks user if the selected request is going to be completed or rejected.
     *
     * @param input the Scanner for user input
     * @return the user's choice
     */
    public static int completeOrReject(final Scanner input) {
        boolean valid = false;
        int choice = -1;
        do {
            System.out.println();
            System.out.println("1. Έγκριση αυτούσιου αιτήματος");
            System.out.println("2. Απόρριψη");
            System.out.println("3. Το αίτημα θα υποβληθεί τροποποιημένο");
            System.out.println("0. Εξοδος");
            System.out.print("Επιλογή: ");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 0) {
                    break;
                }
                if (choice < 1 || choice > MAX_CHOICE_PRIME_MINISTER) {
                    System.out.println("Μη έγκυρος κωδικός.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
            }
        } while (!valid);
        return choice;
    }

    /**
     * Displays the Prime Minister / Parliament menu
     * and reads the user's choice.
     *
     * @param input the Scanner used for user input
     * @return the selected option (0–3)
     */
    public static int primeMinisterAndParlMenu(final Scanner input) {
        int choice;
        while (true) {
            System.out.println("\n1. Ιστορικό Αλλαγών");
            System.out.println("2. Σύγκριση Δημοσιευμένου και "
                    + "Τροποποιημένου");
            System.out.println("3. Προβολή Τροποποιήσεων");
            System.out.println("0. Εξοδος");
            System.out.print("Επιλογή: ");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 0 || (choice >= MIN_CHOICE_PRIME_MINISTER
                                    && choice <= MAX_CHOICE_PRIME_MINISTER)) {
                    return choice;
                } else {
                    System.out.println("Μη έγκυρος κωδικός.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
            }
        }
    }

    /**
     * Asks the user to select the ID of a request to review.
     * Returns 0 if the list is empty or the user chooses to exit.
     *
     * @param input the Scanner used for user input
     * @param financeMinistry the list of requests awaiting review
     * @return the selected request ID or 0 to exit
     */
    public static int chooseEdit(final Scanner input,
            final List<MinistryRequest> financeMinistry) {

        if (financeMinistry.isEmpty()) {
            return 0;
        }

        int choice;
        while (true) {
            if (!financeMinistry.isEmpty()) {
                System.out.println();
                System.out.println("Αξιολόγηση Τροποποιήσεων "
                        + "από Υπουργείο Οικονομικών");
                System.out.println("Επιλέξτε 0 για επιστροφή στο "
                + "προηγούμενο μενού.");
                System.out.print("Επιλέξτε το " + BOLD + CYAN
                + "ID " + RESET + "για έγκριση ή απόρριψη: ");

                try {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 0) {
                        return choice;
                    }
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    input.nextLine();
                }
            }
        }
    }

    /**
     * Asks the Prime Minister to approve or reject a specific request.
     *
     * @param input the Scanner used for user input
     * @return 1 for approval, 2 for rejection, or 0 to exit
     */
    public static int completeOrRejectPrimMinist(final Scanner input) {
        boolean valid = false;
        int choice = -1;
        do {
            System.out.println();
            System.out.println("1. Έγκριση");
            System.out.println("2. Απόρριψη");
            System.out.println("0. Εξοδος");
            System.out.print("Επιλογή: ");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 0) {
                    break;
                }
                if (choice != 1 && choice != REJECT) {
                    System.out.println("Μη έγκυρος κωδικός.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.nextLine();
            }
        } while (!valid);
        return choice;
    }

    /**
     * Asks the Parliament to select a government‑approved request
     * for final evaluation. Returns 0 if the list is empty or the
     * user chooses to exit.
     *
     * @param input the Scanner used for user input
     * @param govApproved the list of government‑approved requests
     * @return the selected request ID or 0 to exit
     */
    public static int chooseEditParl(final Scanner input,
            final List<MinistryRequest> govApproved) {

        if (govApproved.isEmpty()) {
            return 0;
        }

        int choice;
        while (true) {
            if (!govApproved.isEmpty()) {
                System.out.println(" Τελική Αξιολόγηση Τροποποιήσεων");
                System.out.println("Επιλέξτε 0 για επιστροφή στο "
                + "προηγούμενο μενού.");
                System.out.print("Επιλέξτε το " + BOLD + CYAN
                + "ID " + RESET + "για έγκριση ή απόρριψη: ");

                try {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 0) {
                        return choice;
                    }
                    return choice;
                } catch (InputMismatchException e) {
                    System.out.println("Παρακαλώ εισάγετε αριθμό.");
                    input.nextLine();
                }
            }
        }
    }

    /**
     * Allows the user to evaluate all requests with a specific status, either
     * from the Finance Ministry or for final approval by the Parliament.
     *
     * @param input Scanner used to read the user's input
     * @param reqService The service that handles ministry requests
     * @param statusToCheck The status of the requests that need evaluation
     * @param approveByParliament If true, approval will be done by the
     * Parliament; otherwise, it will be done by the government
     */
    public static void evaluateRequests(
            final Scanner input,
            final MinistryRequestService reqService,
            final RequestStatus statusToCheck,
            final boolean approveByParliament) {
        List<MinistryRequest> requests =
                reqService.getByStatusAndType(statusToCheck, null);
        if (requests.isEmpty()) {
            System.out.println("Δεν υπάρχουν άλλες τροποποιήσεις "
                    + "για αξιολόγηση.");
            return;
        }
        RequestPrinter.printRequests(requests);

        while (true) {
            int id = RequestsController.chooseEdit(input, requests);
            if (id == 0) {
                return;
            }

            boolean valid = requests.stream().anyMatch(r -> r.getId() == id);
            if (!valid) {
                System.out.println("Μη έγκυρος κωδικός.");
                System.out.println("Επιλέξτε ένα από τα εμφανιζόμενα "
                        + "αιτήματα.");
                continue;
            }

            int complOrRej = RequestsController
                    .completeOrRejectPrimMinist(input);
            if (complOrRej == 1) {
                if (approveByParliament) {
                    try {
                        BudgetEditor.saveEdit(id);
                    } catch (Exception e) {
                        System.err.println("Σφάλμα κατά την αποθήκευση του "
                                + "αιτήματος." + e.getMessage());
                    }
                } else {
                    reqService.approveByGovernment(id);
                }
            } else if (complOrRej == REJECT) {
                reqService.markRejected(id);
            }

            requests = reqService.getByStatusAndType(statusToCheck, null);
            if (requests.isEmpty()) {
                System.out.println("Δεν υπάρχουν άλλες τροποποιήσεις "
                    + "για αξιολόγηση.");
                return;
            }
        }
    }

    /**
     * Evaluates pending revenue modification requests by displaying them,
     * allowing the user to select a request, and approving or rejecting it.
     *
     * @param input the Scanner used for user interaction
     * @param revenueService service providing access to revenue requests
     * @param statusToCheck the request status used to filter pending items
     * @param approvedByGovernment true if the evaluation is performed at the
     *                             parliamentary stage, false if at the
     *                             government review stage
     */
    public static void evaluateRevenueRequests(
            final Scanner input,
            final RevenueRequestService revenueService,
            final RequestStatus statusToCheck,
            final boolean approvedByGovernment) {

        List<RevenueRequest> requests =
                revenueService.getByStatus(statusToCheck);

        if (requests.isEmpty()) {
            System.out.println("Δεν υπάρχουν άλλες τροποποιήσεις "
                    + "για αξιολόγηση.");
            return;
        }

        RequestPrinter.printRequests(requests);

        while (true) {
            int id = chooseRequest(input, requests);
            if (id == 0) {
                return;
            }

            boolean valid = requests.stream().anyMatch(r -> r.getId() == id);
            if (!valid) {
                System.out.println("Μη έγκυρος κωδικός.");
                System.out.println("Επιλέξτε ένα από τα εμφανιζόμενα "
                        + "αιτήματα.");
                continue;
            }

            int decision = completeOrRejectPrimMinist(input);
            if (decision == 1) {
                if (approvedByGovernment) {
                    revenueService.approveByParliament(id);
                    try {
                        BudgetEditor.saveEditRevenue(id);
                    } catch (Exception e) {
                        System.err.println("Σφάλμα κατά την αποθήκευση του "
                                + "αιτήματος." + e.getMessage());
                    }
                } else {
                    revenueService.approveByGovernment(id);
                }
            } else if (decision == 2) {
                revenueService.rejectRequest(id);
            }

            requests = revenueService.getByStatus(statusToCheck);
            if (requests.isEmpty()) {
                System.out.println("Δεν υπάρχουν άλλες τροποποιήσεις "
                    + "για αξιολόγηση.");
                return;
            }
        }
    }

    /**
     * Displays and processes pending revenue or expense modification requests
     * based on user selection and the current approval stage.
     *
     * @param input the Scanner used for user interaction
     * @param approvedByGovernment true if requests should be evaluated at the
     *                             parliamentary stage, false if at the
     *                             government review stage
     */
    public static void showRequests(final Scanner input,
            final boolean approvedByGovernment) {
        ShowEditMenuOptions edit = new ShowEditMenuOptions();
        RevenueOrExpense choice = edit.chooseRevenueOrExpense(input);
        if (choice == null) {
            return;
        }
        switch (choice) {
            case INCOME -> {
                RevenueRequestService revenueService =
                        new RevenueRequestService();

                if (approvedByGovernment) {
                    evaluateRevenueRequests(input, revenueService,
                            RequestStatus.GOVERNMENT_APPROVED, true);
                } else {
                    evaluateRevenueRequests(input, revenueService,
                            RequestStatus.REVIEWED_BY_FINANCE_MINISTRY, false);
                }
            }
            case EXPENSE -> {
                MinistryRequestService reqService =
                        new MinistryRequestService();
                if (approvedByGovernment) {
                    evaluateRequests(input, reqService,
                            RequestStatus.GOVERNMENT_APPROVED, true);
                } else {
                    evaluateRequests(input, reqService,
                            RequestStatus.REVIEWED_BY_FINANCE_MINISTRY, false);
                }
            }
            default -> {
                // not needed
            }
        }
    }
}
