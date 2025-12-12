package dhmosiabytes;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.List;
import budgetreader.Eggrafi;

/**
* Utility class.
* Creates extra charts like pies.
*/
public final class MoreCharts {
    /** Window width for chart window. */
    private static final int CHART_WIDTH = 800;
    /** Window height for chart window. */
    private static final int CHART_HEIGHT = 600;
    /** Color for pie chart slice (RGB). */
    private static final Color BLUE = new Color(0, 128, 255);
    /** Color for pie chart slice (RGB). */
    private static final Color RED = new Color(255, 99, 71);

    private MoreCharts() {
        // private Constructor
    }

    /**
    * Pie chart for total revenue and total expenses.
    *
    * @param eggrafes the list with revenue and expenses
    */
    public static void pieChartEsodaExoda(final List<Eggrafi> eggrafes) {
        // Initialisation
        BigDecimal esoda = BigDecimal.ZERO;
        BigDecimal exoda = BigDecimal.ZERO;

        // Find and save the totals of revenue and expenses
        for (Eggrafi e : eggrafes) {
            if (e.getPerigrafi().equalsIgnoreCase("ΕΣΟΔΑ")) {
                esoda = e.getPoso();
            } else if (e.getPerigrafi().equalsIgnoreCase("ΕΞΟΔΑ")) {
                exoda = e.getPoso();
            }
        }

        // Create PieChart
        PieChartBuilder builder = new PieChartBuilder();
        builder.width(CHART_WIDTH);
        builder.height(CHART_HEIGHT);
        builder.title("Συνολικά Έσοδα - Έξοδα");
        PieChart chart = builder.build();

        // Add contents
        chart.addSeries("Έσοδα", esoda);
        chart.addSeries("Έξοδα", exoda);

        // Set colors for pie slices
        Color[] sliceColors = new Color[] {
            BLUE,
            RED };
        chart.getStyler().setSeriesColors(sliceColors);

        // Display PieChart in a swing window
        SwingWrapper<PieChart> window = new SwingWrapper<>(chart);
        JFrame frame = window.displayChart();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
    * Pie chart for revenue coverage of expenses.
    *
    * @param eggrafes the list with revenue and expenses
    */
    public static void pieChartElleimma(final List<Eggrafi> eggrafes) {
        // Initialisation
        BigDecimal esoda = BigDecimal.ZERO;
        BigDecimal elleimma = BigDecimal.ZERO;

        // Find and save the total expenses and the revenue-expesnes difference
        for (Eggrafi e : eggrafes) {
            if (e.getPerigrafi().equalsIgnoreCase("ΕΣΟΔΑ")) {
                esoda = e.getPoso();
            } else if (e.getPerigrafi().equalsIgnoreCase("ΑΠΟΤΕΛΕΣΜΑ ΚΡΑΤΙΚΟΥ"
                + "ΠΡΟΫΠΟΛΟΓΙΣΜΟΥ (ΕΣΟΔΑ - ΕΞΟΔΑ)")) {
                elleimma = e.getPoso();
            }
        }

        // Create PieChart
        PieChartBuilder builder = new PieChartBuilder();
        builder.width(CHART_WIDTH);
        builder.height(CHART_HEIGHT);
        builder.title("Χρηματοδότηση Εξόδων");
        PieChart chart = builder.build();

        // Add contents
        chart.addSeries("Έσοδα", esoda);
        chart.addSeries("Έλλειμμα", elleimma);

         // Set colors for pie slices
        Color[] sliceColors = new Color[] {
            BLUE,
            RED };
        chart.getStyler().setSeriesColors(sliceColors);

        // Display PieChart in a swing window
        SwingWrapper<PieChart> window = new SwingWrapper<>(chart);
        JFrame frame = window.displayChart();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
