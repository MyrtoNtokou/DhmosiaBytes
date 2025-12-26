package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import budgetcharts.Barcharts;
import budgetcharts.MoreCharts;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;

/**
 * Displays a menu of graph options and
 * receives a validated user selection from the console.
 */
public class Graphs {
    /** The maximum valid option number. */
    private static final int MAX_CODE = 9;

    /** The minimum valid option number. */
    private static final int MIN_CODE = 1;

    /** Numerical code for pie chart of revenues and expenditures. */
    private static final int PIE_ESODA_EXODA = 1;

    /** Numerical code for pie chart of deficit. */
    private static final int PIE_ELLEIMMA = 2;

    /** Numerical code for chart of revenue. */
    private static final int CHART_ESODA = 3;

    /** Numerical code for chart of expenditure. */
    private static final int CHART_EXODA = 4;

    /** Numerical code for chart per ministry. */
    private static final int CHART_MINISTRY = 5;

    /** Line chart index for revenues and expenses. */
    private static final int LINE_CHART_ESODA_EXODA = 6;

    /** Chart index for yearly revenues. */
    private static final int CHART_ESODA_YEAR = 7;

    /** Chart index for yearly expenses. */
    private static final int CHART_EXODA_YEAR = 8;

    /** Chart index for yearly ministry data. */
    private static final int CHART_MINISTRY_YEAR = 9;

    /**
     * Creates a Graph object.
     */
    public Graphs() { }

    /**
     * Displays a menu of graph options until a valid
     * numeric choice within the allowed range is provided.
     *
     * @param input the Scanner for user input
     * @return the validated selected option
     */
    public int chooseGraph(final Scanner input) {
        int code = -1;
        boolean valid = false;

        while (!valid) {
            System.out.println("\n1. Πίτα Συνολικών Εσόδων - Εξόδων");
            System.out.println("2. Πίτα Χρηματοδότησης Εξόδων");
            System.out.println("3. Ιστόγραμμα Εσόδων");
            System.out.println("4. Ιστόγραμμα Εξόδων");
            System.out.println("5. Ιστόγραμμα Προϋπολογισμού ανά Υπουργείο");
            System.out.println("6. Γραμμικό διάγραμμα εσόδων εξόδων "
            + "για τα έτη 2020-2026");
            System.out.println("7. Ιστόγραμμα για συγκεκριμένο έσοδο "
            + "2020-2026");
            System.out.println("8. Ιστόγραμμα για συγκεκριμένο έξοδο "
            + "2020-2026 ");
            System.out.println("9. Ιστόγραμμα ανά Υπουργείο "
            + "για τα έτη 2020-2026");
            System.out.println("0. Έξοδος");
            System.out.print("Επιλογή: ");

            try {
                code = input.nextInt();
                input.nextLine();
                if (code == 0) {
                    break;
                } else if (code < MIN_CODE || code > MAX_CODE) {
                    System.out.println("Παρακαλώ επιλέξτε αριθμό από το "
                    + MIN_CODE + " έως το " + MAX_CODE);
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Παρακαλώ εισάγετε αριθμό.");
                input.next();
            }
        }
        return code;
    }

    /**
     * Displays the correct graph depending on the given code.
     *
     * @param input the Scanner for user input
     */
    public void runGraphs(final Scanner input) {
        List<Eggrafi> eggra =
        ReadBudget.readGeneralBudget("proypologismos2025.csv");
        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");
        CutLists cut = new CutLists();

        int code;
        do {
            code = chooseGraph(input);
            switch (code) {
                case PIE_ESODA_EXODA -> MoreCharts.pieChartEsodaExoda(eggra);
                case PIE_ELLEIMMA -> MoreCharts.pieChartElleimma(eggra);
                case CHART_ESODA -> Barcharts.chartEsoda(eggra);
                case CHART_EXODA -> Barcharts.chartExoda(eggra);
                case CHART_MINISTRY -> Barcharts.chartMinistry(y);
                case LINE_CHART_ESODA_EXODA -> MoreCharts
                .lineChartEsodaExoda();
                case CHART_ESODA_YEAR -> {
                    List<Eggrafi> esoda = cut.cutEggrafiEsoda();
                    int revenueCode = cut.selectRevenueByNumber(input,
                    esoda);
                    Barcharts.chartEsodaByYear(revenueCode);
                }
                case CHART_EXODA_YEAR -> {
                    List<Eggrafi> exoda = cut.cutEggrafiExoda();
                    int expenseCode = cut.selectExpenseByNumber(input,
                    exoda);
                    Barcharts.chartExodaByYear(expenseCode);
                }
                case CHART_MINISTRY_YEAR -> {
                    List<Ypourgeio> ministries = cut.cutYpourgeio();
                    int ministryCode = cut.selectMinistryByNumber(input,
                    ministries);
                    Barcharts.chartMinistryByYear(ministryCode);
                }
                case 0 -> System.out.println("Επιστροφή στο μενού επιλογών");
                default -> System.out.println("Μη έγκυρη επιλογή.");
            }
        } while (code != 0);
    }
}
