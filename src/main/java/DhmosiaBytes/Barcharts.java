package dhmosiabytes;

import static org.mockito.Answers.values;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

import budgetreader.Eggrafi;
import budgetreader.ReadBudget;

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
     * Main method of the application.
     *
     * @param args Command-line arguments
     */
    public static void main(final String[] args) {
        List<Eggrafi> eggrafes =
        ReadBudget.readGeneralBudget("proypologismos2025.csv");

        // Εκτύπωση μόνο των εσόδων
        chartesoda(eggrafes);

        chartexoda(eggrafes);
    }

     /**
     * Creates and displays the income bar chart.
     *
     * @param eggrafes List of budget records
     */
    public static void chartesoda(final List<Eggrafi> eggrafes) {
    // Φιλτράρισμα εσόδων
    List<Eggrafi> esoda = new ArrayList<>();
    for (Eggrafi e : eggrafes) {
        if (e.getKodikos().startsWith("1,")) {
            esoda.add(e);
        }
    }

    // Prepare data for the chart
    List<String> categories = new ArrayList<>();
    List<Double> values = new ArrayList<>();
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
    new SwingWrapper<>(chart).displayChart();
}


    /**
     * Creates and displays the expenses bar chart.
     *
     * @param eggrafes List of budget records
     */
     public static void chartexoda(final List<Eggrafi> eggrafes) {
    // Filter expense records (code starts with "2,")
    List<Eggrafi> exoda = new ArrayList<>();
    for (Eggrafi e : eggrafes) {
        if (e.getKodikos().startsWith("2,")) {
            exoda.add(e);
        }
    }

    // Prepare data for the chart
    List<String> categories = new ArrayList<>();
    List<Double> values = new ArrayList<>();
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


    new SwingWrapper<>(chart).displayChart();
}



    //dorotheas
    // public static void summary() {
     //   List<Ypourgeio> y =
     //   ReadBudget.readByMinistry("proypologismos2025anaypourgeio.csv");
     //   DisplayBudget.showMinistry(y);
   // }
//}
}
