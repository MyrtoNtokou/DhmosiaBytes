package dhmosiabytes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.List;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
import budgetreader.ReadBudget;

import budgetreader.Eggrafi;
import budgetreader.ReadBudget;
import budgetreader.Ypourgeio;

/**
 * Displays a menu of graph options and
 * receives a validated user selection from the console.
 */
public class Graphs {
    /** The maximum valid option number. */
    private static final int MAX_CODE = 5;

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
            System.out.println("1. Πίτα Συνολικών Εσόδων - Εξόδων");
            System.out.println("2. Πίτα Χρηματοδότησης Εξόδων");
            System.out.println("3. Ιστόγραμμα Εσόδων");
            System.out.println("4. Ιστόγραμμα Εξόδων");
            System.out.println("5. Ιστόγραμμα Προϋπολογισμού ανά Υπουργείο");
            System.out.println("0. Έξοδος");
            System.out.print("Επιλογή: ");

            try {
                code = input.nextInt();
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
<<<<<<< HEAD
     * @param input the Scanner for user input
     */
    public void runGraphs(final Scanner input) {
        List<Eggrafi> eggra =
=======
     * @param code the numeric code referring to the graph
     */
    public void runGraphs(final int code) {
        List<Eggrafi> eggrafes =
>>>>>>> a3c6744 (Add runGraphs)
        ReadBudget.readGeneralBudget("proypologismos2025.csv");

        List<Ypourgeio> y =
        ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");

<<<<<<< HEAD
        int code;
        do {
            code = chooseGraph(input);
            switch (code) {
                case PIE_ESODA_EXODA -> MoreCharts.pieChartEsodaExoda(eggra);
                case PIE_ELLEIMMA -> MoreCharts.pieChartElleimma(eggra);
                case CHART_ESODA -> Barcharts.chartEsoda(eggra);
                case CHART_EXODA -> Barcharts.chartExoda(eggra);
                case CHART_MINISTRY -> Barcharts.chartMinistry(y);
                case 0 -> System.out.println("Επιστροφή στο μενού επιλογών");
                default -> System.out.println("Μη έγκυρη επιλογή.");
            }
        } while (code != 0);
=======
        switch (code) {
            case 1 -> MoreCharts.pieChartEsodaExoda(eggrafes);
            case 2 -> MoreCharts.pieChartElleimma(eggrafes);
            case 3 -> Barcharts.chartEsoda(eggrafes);
            case 4 -> Barcharts.chartExoda(eggrafes);
            case 5 -> Barcharts.chartMinistry(y);
            default -> System.out.println("Μη έγκυρη επιλογή.");
        }
>>>>>>> a3c6744 (Add runGraphs)
    }
}
