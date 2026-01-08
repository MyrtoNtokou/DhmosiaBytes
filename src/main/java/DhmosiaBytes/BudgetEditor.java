package dhmosiabytes;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import budgetlogic.Budget;
import budgetlogic.BudgetAssembler;
import budgetlogic.BudgetDiffPrinter;
import budgetlogic.BudgetSave;
import budgetlogic.BudgetService;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import ministryrequests.MinistryRequestService;
import ministryrequests.RequestType;

 /**
 * Allows editing of income and expense entries in a Budget.
 */
public class BudgetEditor {
    /**  BudgetService used to perform operations on the budget. */
    private final BudgetService service;

    /** Maximum valid percentage. */
    private static final BigDecimal MAX_PERCENTAGE = BigDecimal.valueOf(100);

    /** Code that cannot be selected as an expense to be increased. */
    private static final String BANNED_CODE = "2,8";

    /** Code for number 4. */
    private static final int FOUR = 4;

    /**
     * Constructs a BudgetEditor with the specified BudgetService.
     *
     * @param serv the BudgetService used to modify and validate budget data
     */
    public BudgetEditor(final BudgetService serv) {
        this.service = serv;
    }

    /**
     * Edits the amount of a specific income entry in the general budget.
     * The user is prompted to enter a new non-negative amount.
     * The updated value is saved to the modified budget files and
     * the differences between the initial and the updated budget
     * are displayed.
     *
     * @param code the code of the income to be replaced
     * @param scanner Scanner to read user input
     */
    public void editIncome(final String code, final Scanner scanner) {
        BigDecimal newAmount = null;

        while (newAmount == null) {
            System.out.println("Παρακαλώ εισάγετε το νέο ποσό: ");
            String input = scanner.nextLine();
            try {
                newAmount = new BigDecimal(input);
                if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                newAmount = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
            }
        }

        Budget before = new Budget(service.getBudget());
        this.service.changeGeneralAmount(code, newAmount);

        try {
            BudgetSave saver = new BudgetSave();
            Budget finalBudget = service.getBudget();
            saver.saveGeneralChanges(finalBudget, "newgeneral.csv");
            System.out.println("Η αλλαγή αποθηκεύτηκε επιτυχώς.");
            BudgetDiffPrinter.printDiffRevenues(before, finalBudget);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση.");
        }
    }

    /**
     * Edits the budget amount of a specific ministry.
     * The user selects the budget type (regular or public investments),
     * enters a new amount, and the change is saved to the modified budget
     * files.
     * After saving, the differences between the initial and the updated budget
     * are printed.
     *
     * @param code the code of the expense to be replaced
     * @param scanner the Scanner to read user input
     * @param initialBudget Budget object to edit
     */
    public void editExpense(final int code, final Scanner scanner,
    final Budget initialBudget) {
        ShowEditMenuOptions editMenu = new ShowEditMenuOptions();
        int type;
        type = editMenu.selectBudgetType(scanner);
        if (type == 0) {
            return;
        }
        String column;
        if (type == 1) {
            column = "τακτικός";
        } else {
            column = "πδε";
        }
        BigDecimal newAmount = null;

        while (newAmount == null) {
            System.out.print("Παρακαλώ εισάγετε την \u001B[1mαύξηση\u001B[0m "
            + " που θα εφαρμοστεί στο επιλεγμένο Υπουργείο: ");
            String input = scanner.nextLine();
            try {
                newAmount = new BigDecimal(input);
                if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Το ποσό δεν μπορεί να είναι αρνητικό.");
                newAmount = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Μη έγκυρη τιμή.");
            }
        }

        BudgetDiffPrinter.printExpenses(initialBudget);
        Map<String, BigDecimal> distribution = distributeExpenses(scanner);
        Map<Integer, Map<String, BigDecimal>> mapping = BudgetAssembler
                .createMappingForMinistryChange(code, distribution);
        BudgetService serv = new BudgetService(initialBudget, mapping);
        Budget currentBudget = serv.getBudget();
        Budget before = new Budget(currentBudget);
        serv.changeMinistryAmount(code, column, newAmount);
        Budget after = serv.getBudget();
        Ypourgeio ministry = after.getMinistries().get(code);
        String rawDiff = BudgetDiffPrinter.captureMinistryDiff(before, after);
        MinistryRequestService reqService = new MinistryRequestService();

        BudgetDiffPrinter.printDiffMinistries(before, after);

        if (column.equals("τακτικός")) {
            reqService.submitRequest(ministry, rawDiff, RequestType.TAKTIKOS);
        } else {
            reqService.submitRequest(ministry, rawDiff,
            RequestType.EPENDYSEIS);
        }
    }

    /**
     * Interactively collects from the user the distribution of a total expense
     * increase across different state expense categories.
     *
     * @param input the Scanner used to read user input
     * @return a map where the key is the expense code and the value is the
     *         normalized percentage (0–1) of the total increase
     *         assigned to that expense
     */
    public static Map<String, BigDecimal> distributeExpenses(final Scanner
            input) {
        Map<String, BigDecimal> increases = new HashMap<>();
        CutLists cut = new CutLists();
        List<Eggrafi> exoda = cut.cutEggrafiExoda();

        while (true) {
            increases.clear();
            System.out.println();
            System.out.println("Επιλέξτε πώς θα κατανεμηθεί το ποσό στα "
            + "έξοδα.");
            System.out.println("Επιλέξτε τον κωδικό εξόδου και το ποσοστό της "
            + "συνολικής αύξησης που προορίζεται για το συγκεκριμένο έξοδο.");

            while (true) {
                System.out.println();
                System.out.print("Επιλέξτε κωδικό εξόδου: ");
                String code = input.nextLine().trim();

                boolean exists = exoda.stream().anyMatch(e -> e.getKodikos()
                        .equals(code));

                if (!exists || code.equals(BANNED_CODE)) {
                    System.out.println("Δεν υπάρχει επιλογή με αυτόν τον "
                    + "κωδικό.");
                    continue;
                }

                if (increases.containsKey(code)) {
                    System.out.println("Προσοχή: Ο κωδικός έχει ήδη δηλωθεί. "
                    + "Η τιμή θα αντικατασταθεί.");
                }

                System.out.println();
                System.out.print("Επιλέξτε το ποσοστό αύξησης (0 - "
                + MAX_PERCENTAGE + "): ");
                try {
                    BigDecimal percentage = new BigDecimal(input.nextLine());
                    if (percentage.compareTo(BigDecimal.ZERO) < 0
                    || percentage.compareTo(MAX_PERCENTAGE) > 0) {
                        System.out.println("Το ποσοστό πρέπει να είναι μεταξύ "
                        + "0 " + "και " + MAX_PERCENTAGE + ".");
                        continue;
                    }
                    BigDecimal normalized =
                    percentage.divide(MAX_PERCENTAGE, FOUR,
                            RoundingMode.HALF_UP);
                    increases.put(code, normalized);
                    BigDecimal currentSum = increases.values().stream()
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    if (currentSum.compareTo(BigDecimal.ONE) > 0) {
                        System.out.println("Σφάλμα: Το συνολικό ποσοστό "
                                + "κατανομής ξεπέρασε το 100%.");
                        System.out.println("Ξεκινήστε από την αρχή.");
                        increases.clear();
                        break;
                    } else if (currentSum.compareTo(BigDecimal.ONE) == 0) {
                        System.out.println("Η κατανομή ολοκληρώθηκε.");
                        return increases;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Μη έγκυρο ποσοστό.");
                }
            }
        }
    }
}
