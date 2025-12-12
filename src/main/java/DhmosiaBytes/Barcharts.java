package dhmosiabytes;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

public final class Barcharts {
     /** Width of the chart in pixels. */
    private static final int CHART_WIDTH = 1200;

    /** Height of the chart in pixels. */
    private static final int CHART_HEIGHT = 800;

     /** X-axis label rotation angle in degrees. */
    private static final int X_AXIS_LABEL_ROTATION = 45;

     /** Percentage of available space used in the chart. */
    private static final double AVAILABLE_SPACE_FILL = 0.9;

    /** Plot content size (percentage of chart space). */
    private static final double PLOT_CONTENT_SIZE = 0.9;

     /** Private constructor to prevent instantiation of this utility class. */
    private Barcharts() {
    }
     /**
     * Creates and displays the income bar chart.
     *
     * @param eggrafes List of budget records
     */
    public static void chartEsoda(final List<Eggrafi> eggrafes) {
        // Filter income records (code starts with "1,")
        List<Eggrafi> esoda = new ArrayList<>();
        for (Eggrafi e : eggrafes) {
            if (e.getKodikos().startsWith("1,")) {
                esoda.add(e);
            }
        }

        // Prepare data for the chart
        List<String> categories = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        for (Eggrafi e : esoda) {
            categories.add(e.getPerigrafi());
            values.add(e.getPoso());
        }

        // Build the chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Έσοδα")
                .xAxisTitle("Κατηγορία")
                .yAxisTitle("Ποσό")
                .build();

        // Styling
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setToolTipsEnabled(true);

        chart.getStyler().setXAxisLabelRotation(X_AXIS_LABEL_ROTATION);


        chart.getStyler().setAvailableSpaceFill(AVAILABLE_SPACE_FILL);
        chart.getStyler().setPlotContentSize(PLOT_CONTENT_SIZE);

    // Add series and display chart
        chart.addSeries("Έσοδα", categories, values);
        SwingWrapper<CategoryChart> sw = new SwingWrapper<>(chart);
        javax.swing.JFrame frame = sw.displayChart();
        frame.setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }


    /**
     * Creates and displays the expenses bar chart.
     *
     * @param eggrafes List of budget records
     */
     public static void chartExoda(final List<Eggrafi> eggrafes) {
        // Filter expense records (code starts with "2,")
        List<Eggrafi> exoda = new ArrayList<>();
        for (Eggrafi e : eggrafes) {
            if (e.getKodikos().startsWith("2,")) {
                exoda.add(e);
            }
        }

        // Prepare data for the chart
        List<String> categories = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        for (Eggrafi e : exoda) {
            categories.add(e.getPerigrafi());
            values.add(e.getPoso());
        }

        // Build the chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Έξοδα")
                .xAxisTitle("Κατηγορία")
                .yAxisTitle("Ποσό")
                .build();

        // Styling
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setToolTipsEnabled(true);


        chart.getStyler().setXAxisLabelRotation(X_AXIS_LABEL_ROTATION);


        chart.getStyler().setAvailableSpaceFill(AVAILABLE_SPACE_FILL);
        chart.getStyler().setPlotContentSize(PLOT_CONTENT_SIZE);

        // Add series and display chart
        chart.addSeries("Έξοδα", categories, values);

        SwingWrapper<CategoryChart> sw = new SwingWrapper<>(chart);
        javax.swing.JFrame frame = sw.displayChart();
        frame.setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Creates and displays a bar chart for ministries showing their budget.
     *
     * @param ministry List of Ypourgeio objects containing ministry data
     */
    public static void chartMinistry(final List<Ypourgeio> ministry) {
        // Filter only ministries (exclude other entries)
        List<Ypourgeio> ministries = new ArrayList<>();
        for (Ypourgeio m: ministry) {
            if (m.getOnoma().contains("Υπουργείο")) {
                ministries.add(m);
            }
        }

        // Prepare data for the chart
        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        for (Ypourgeio m : ministries) {
            names.add(m.getOnoma());
            values.add(m.getSynolo());
        }

        // Build the chart
       CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Δαπάνες Υπουργείων")
                .xAxisTitle("Υπουργείο")
                .yAxisTitle("Συνολική Δαπάνη")
                .build();

        // Styling
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(X_AXIS_LABEL_ROTATION);

        // Add series and display chart
        chart.addSeries("Δαπάνες", names, values);
        SwingWrapper<CategoryChart> sw = new SwingWrapper<>(chart);
        javax.swing.JFrame frame = sw.displayChart();
        frame.setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}
