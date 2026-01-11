package dhmosiabytes;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static aggregatedata.ConsoleColors.BOLD;
import static aggregatedata.ConsoleColors.RESET;
import budgetlogic.Budget;
import budgetlogic.BudgetAssembler;
import budgetlogic.BudgetDiffPrinter;
import budgetlogic.BudgetSave;
import budgetlogic.BudgetService;
import budgetlogic.BudgetServiceImpl;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import ministryrequests.MinistryRequestParser;
import ministryrequests.MinistryRequestService;
import ministryrequests.RequestLoader;
import ministryrequests.RequestType;
import revenuerequests.RevenueRequestParser;
import revenuerequests.RevenueRequestService;

 /**
 * Allows editing of income and expense entries in a Budget.
 */
public class BudgetEditor {

    /** Maximum valid percentage. */
    private static final BigDecimal MAX_PERCENTAGE = BigDecimal.valueOf(100);

    /** Code that cannot be selected as an expense to be increased. */
    private static final String BANNED_CODE = "2,8";

    /** Code for number 4. */
    private static final int FOUR = 4;

    /**
     * Constructs a BudgetEditor with the specified BudgetService.
     */
    public BudgetEditor() { }

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

        BudgetAssembler assembler = new BudgetAssembler();
        Budget initialBudget = assembler.loadBudget("newgeneral.csv",
                "newministries.csv");

        Budget before = new Budget(initialBudget);
        BudgetService serv = new BudgetServiceImpl(before, null);
        serv.changeGeneralAmount(code, newAmount);
        Budget after = serv.getBudget();

        String capturedDiff = BudgetDiffPrinter
                .captureRevenuesDiff(initialBudget, after);

        RevenueRequestService revenueService = new RevenueRequestService();
        String name = after.getRevenues().get(code).getPerigrafi();
        revenueService.submitRevenueRequest(code, name,
                capturedDiff);

        BudgetDiffPrinter.printDiffRevenues(initialBudget, after);
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
     * @param currentRole Login role
     */
    public void editExpense(final int code, final Scanner scanner,
    final Budget initialBudget, final Role currentRole) {
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

        BigDecimal increase = null;
        BigDecimal newAmount = null;
        while (increase == null) {
            System.out.print("Παρακαλώ εισάγετε την " + BOLD + "αύξηση" + RESET
            + " που θα εφαρμοστεί στο επιλεγμένο Υπουργείο: ");
            String input = scanner.nextLine();
            try {
                Ypourgeio mBefore = initialBudget.getMinistries().get(code);
                BigDecimal oldVal = column.equalsIgnoreCase("τακτικός")
                    ? mBefore.getTaktikos() : mBefore.getEpendyseis();
                increase = new BigDecimal(input);
                newAmount = oldVal.add(increase);
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
        Budget before = new Budget(initialBudget);
        BudgetService serv = new BudgetServiceImpl(before, mapping);
        serv.changeMinistryAmount(code, column, newAmount);
        Budget after = serv.getBudget();
        Ypourgeio ministry = after.getMinistries().get(code);
        String rawDiff = BudgetDiffPrinter
                        .captureMinistryDiff(initialBudget, after);
        MinistryRequestService reqService = new MinistryRequestService();

        BudgetDiffPrinter.printDiffMinistries(initialBudget, after);
        int requestId;

        if (column.equals("τακτικός")) {
            requestId = reqService.submitRequest(ministry, rawDiff,
                    RequestType.TAKTIKOS);
        } else {
            requestId = reqService.submitRequest(ministry, rawDiff,
            RequestType.EPENDYSEIS);
        }

        switch (currentRole) {
            case FINANCE_MINISTER -> {
                reqService.reveiwByFinanceMinistry(requestId);
            }
            default -> {
                // no action needed
            }
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

    /**
     * Finalize request by saving the changes.
     * @param id request id
     */
    public static void saveEdit(final int id) throws Exception {
        MinistryRequestService reqService = new MinistryRequestService();
        reqService.approveByParliament(id);

        try {
            String fileContent = Files.readString(Path
                    .of("ministryrequests.txt"));
            String requestBlock = RequestLoader
                .extractRequestBlock(fileContent, id);
            MinistryRequestParser parser =
                    new MinistryRequestParser(requestBlock);
            MinistryRequestParser.ParsedResult result = parser.parse(id);
            Integer ministry = result.getMinistryCode();
            if (ministry == null) {
                System.err.println("Σφάλμα: Δεν υπάρχει ο κωδικός Υπουργείου "
                        + "για το αίτημα " + id);
                return;
            }
            String type = result.getBudgetType();
            BigDecimal newAmount = result.getMinistryNewAmount();
            if (newAmount == null) {
                System.err.println("Σφάλμα: Δεν υπάρχει ποσό για το Υπουργείο "
                        + "στο αίτημα " + id);
                return;
            }

            Map<String, BigDecimal> expensePer = result
                    .getExpensePercentages();

            System.out.println(expensePer);

            BudgetAssembler loader = new BudgetAssembler();
            Budget initialBudget = loader.loadBudget("newgeneral.csv",
                                "newministries.csv");

            Map<Integer, Map<String, BigDecimal>> mapping =
            BudgetAssembler.createMappingForMinistryChange(ministry,
                    expensePer);

            BudgetService serv = new BudgetServiceImpl(initialBudget, mapping);
            serv.changeMinistryAmount(ministry, type, newAmount);
            Budget finalBudget = serv.getBudget();

            BudgetSave saver = new BudgetSave();
            saver.saveGeneralChanges(finalBudget, "newgeneral.csv");
            saver.saveMinistryChanges(finalBudget, "newministries.csv");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Applies a previously approved revenue modification request to the budget
     * by reading the request data from the stored request file, updating the
     * corresponding revenue entry, and saving the updated general budget.
     *
     * @param id the unique identifier of the revenue request to apply
     * @throws Exception if reading the request file, parsing the request,
     *                   or saving the updated budget fails
     */
    public static void saveEditRevenue(final int id) throws Exception {
        String filePath = "revenuerequests.txt";
        String fileContent = Files.readString(Path.of(filePath));

        String block = RequestLoader.extractRequestBlock(fileContent, id);

        RevenueRequestParser parser = new RevenueRequestParser(block);
        RevenueRequestParser.RevenueResult result = parser.parse();

        String code = result.getCode();
        BigDecimal newAmount = result.getAmount();

        BudgetAssembler loader = new BudgetAssembler();
        Budget initialBudget = loader.loadBudget("newgeneral.csv",
                "newministries.csv");

        BudgetService service = new BudgetServiceImpl(initialBudget, null);
        service.changeGeneralAmount(code, newAmount);

        BudgetSave saver = new BudgetSave();
        Budget finalBudget = service.getBudget();
        saver.saveGeneralChanges(finalBudget, "newgeneral.csv");
    }
}
