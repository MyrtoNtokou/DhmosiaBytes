package budgetcharts;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import budgetreader.Eggrafi;
import budgetreader.ReadBudget;

/**
* Utility class.
* Creates extra charts like pies.
*/
public final class MoreCharts {
    /** Window width for chart window. */
    private static final int CHART_WIDTH = 800;
    /** Window height for chart window. */
    private static final int CHART_HEIGHT = 600;
    /** Marker size for the chart points in pixels. */
    private static final int MARKER_SIZE = 6;
    /** Color for pie chart slice (RGB). */
    private static final Color BLUE = new Color(0, 128, 255);
    /** Color for pie chart slice (RGB). */
    private static final Color RED = new Color(255, 99, 71);
    /** First year. */
    private static final int START_YEAR_CONST = 2020;
    /** Last year. */
    private static final int END_YEAR_CONST = 2026;

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

    /**
    * Creates and displays a line chart showing
    * Income and Expenses per year
    * from CSV budget files.
    *
    */
    public static void lineChartEsodaExoda() {
        int startYear = START_YEAR_CONST;
        int endYear = END_YEAR_CONST;

        List<Integer> years = new ArrayList<>();
        List<Double> esodaList = new ArrayList<>();
        List<Double> exodaList = new ArrayList<>();

        BigDecimal scale = new BigDecimal("1000000000");

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);

            String filename = "proypologismos" + year + ".csv";
            List<Eggrafi> eggrafes = ReadBudget.readGeneralBudget(filename);

            BigDecimal esoda = BigDecimal.ZERO;
            BigDecimal exoda = BigDecimal.ZERO;

            for (Eggrafi e : eggrafes) {
                String perigrafi = e.getPerigrafi().trim();

                if (perigrafi.equalsIgnoreCase("ΕΣΟΔΑ")) {
                    esoda = e.getPoso();
                } else if (perigrafi.equalsIgnoreCase("ΕΞΟΔΑ")) {
                    exoda = e.getPoso();
                }
            }

            // Convert to billions and double for chart display
            esodaList.add(
                esoda.divide(scale, 2, RoundingMode.HALF_UP).doubleValue());
            exodaList.add(
                exoda.divide(scale, 2, RoundingMode.HALF_UP).doubleValue());
        }

        // Create chart
        XYChart chart = new XYChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Έσοδα και Έξοδα ανά Έτος")
                .xAxisTitle("Έτος")
                .yAxisTitle("Ποσό (σε δισ.)")
                .build();

        chart.getStyler()
            .setLegendPosition(
            org.knowm.xchart.style.Styler.LegendPosition.InsideNE);
        chart.getStyler().setMarkerSize(MARKER_SIZE);

        // Add chart series with specific colors
        chart.addSeries("Έσοδα", years, esodaList).setLineColor(Color.GREEN);
        chart.addSeries("Έξοδα", years, exodaList).setLineColor(Color.RED);

        // Display LineChart
        SwingWrapper<XYChart> window = new SwingWrapper<>(chart);
        window.displayChart();
    }


}
