package budgetcharts;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import budgetreader.BasicRecord;
import budgetreader.Ministry;
import budgetreader.ReadBudget;
import dhmosiabytes.IncomeOptions;
import dhmosiabytes.MinistryOptions;
import dhmosiabytes.ExpenseOptions;

/**
 * Utility class for creating and displaying bar charts related to
 * national budget data using the XChart library.
 */
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
    /** First year. */
    private static final int START_YEAR_CONST = 2020;
    /** Last year. */
    private static final int END_YEAR_CONST = 2026;
    /** Divider for billion scaling. */
    private static final BigDecimal BILLION =
            new BigDecimal("1000000000");
     /** Private constructor to prevent instantiation of this utility class. */
    private Barcharts() {
    }

     /**
     * Creates and displays the Expense bar chart.
     *
     * @param basicRecords List of budget records
     */
    public static void chartEsoda(final List<BasicRecord> basicRecords) {
        // Filter Expense records (code starts with "1,")
        List<BasicRecord> esoda = new ArrayList<>();
        for (BasicRecord e : basicRecords) {
            if (e.getCode().startsWith("1,")) {
                esoda.add(e);
            }
        }

        // Prepare data for the chart
        List<String> categories = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();

        for (BasicRecord e : esoda) {
            categories.add(e.getDescription());
            values.add(e.getAmount().divide(BILLION));
        }

        // Build the chart
        CategoryChart chart = new CategoryChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Έσοδα")
            .xAxisTitle("Κατηγορία")
            .yAxisTitle("Ποσό (δις. €)")
            .build();

        configureAndShowChart(chart, "Έσοδα", categories, values);
    }


    /**
     * Creates and displays the expenses bar chart.
     *
     * @param basicRecords List of budget records
     */
     public static void chartExoda(final List<BasicRecord> basicRecords) {
        // Filter expense records (code starts with "2,")
        List<BasicRecord> exoda = new ArrayList<>();
        for (BasicRecord e : basicRecords) {
            if (e.getCode().startsWith("2,")) {
                exoda.add(e);
            }
        }

        // Prepare data for the chart
        List<String> categories = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        for (BasicRecord e : exoda) {
            categories.add(e.getDescription());
            values.add(e.getAmount().divide(BILLION));
        }

        // Build the chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Έξοδα")
                .xAxisTitle("Κατηγορία")
                .yAxisTitle("Ποσό (δισ. €)")
                .build();

        configureAndShowChart(chart, "Έξοδα", categories, values);
    }

    /**
     * Creates and displays a bar chart for ministries showing their budget.
     *
     * @param ministry List of Ministry objects containing ministry data
     */
    public static void chartMinistry(final List<Ministry> ministry) {
        // Filter only ministries (exclude other entries)
        List<Ministry> ministries = new ArrayList<>();
        for (Ministry m: ministry) {
            if (m.getName().contains("Υπουργείο")) {
                ministries.add(m);
            }
        }

        // Prepare data for the chart
        List<String> names = new ArrayList<>();
        List<BigDecimal> values = new ArrayList<>();
        for (Ministry m : ministries) {
            names.add(m.getName());
            values.add(m.getTotalBudget().divide(BILLION));
        }

        // Build the chart
       CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Δαπάνες Υπουργείων")
                .xAxisTitle("Υπουργείο")
                .yAxisTitle("Συνολική Δαπάνη (δισ. €)")
                .build();

        configureAndShowChart(chart, "Δαπάνες", names, values);
    }

    /**
    * Creates a bar chart for the selected Expense category per year.
    *
    * @param x index of the Expense category (1-based).
    */
    public static void chartEsodaByYear(final int x) {
        IncomeOptions[] options = IncomeOptions.values();
        String y = options[x - 1].getIncomeCode();

        int startYear = START_YEAR_CONST;
        int endYear = END_YEAR_CONST;
        List<Integer> years = new ArrayList<>();
        List<Double> esodaList = new ArrayList<>();

        BigDecimal scale = new BigDecimal("1000000000");

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);

            String filename = "proypologismos" + year + ".csv";
            List<BasicRecord> basicRecords =
                ReadBudget.readGeneralBudget(filename);

            BigDecimal esoda = BigDecimal.ZERO;

            for (BasicRecord e : basicRecords) {
               if (e.getCode().startsWith(y)) {
                esoda = esoda.add(
                    e.getAmount());
             }
            }
            BigDecimal scaledEsoda = esoda.divide(
                scale, 2, RoundingMode.HALF_UP);
            esodaList.add(scaledEsoda.doubleValue());
        }
        // Create histogram/bar chart
        CategoryChart chart = new CategoryChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Έσοδα: " + options[x - 1].getIncomeDescription())
            .xAxisTitle("Έτος")
            .yAxisTitle("Ποσό (δις €)")
            .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(X_AXIS_LABEL_ROTATION);
        chart.getStyler().setAvailableSpaceFill(AVAILABLE_SPACE_FILL);
        chart.getStyler().setPlotContentSize(PLOT_CONTENT_SIZE);

        configureAndShowChart(chart, "Έσοδα", years, esodaList);
    }

    /**
    * Creates a bar chart for the selected Expense category per year.
    *
    * @param x index of the Expense category (2-based).
    */
    public static void chartExodaByYear(final int x) {

        ExpenseOptions[] options = ExpenseOptions.values();
        String y = options[x - 1].getExpenseCode();

        int startYear = START_YEAR_CONST;
        int endYear = END_YEAR_CONST;
        List<Integer> years = new ArrayList<>();
        List<Double> exodaList = new ArrayList<>();

        BigDecimal scale = new BigDecimal("1000000000");

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);

            String filename = "proypologismos" + year + ".csv";
            List<BasicRecord> basicRecords =
                ReadBudget.readGeneralBudget(filename);

            BigDecimal exoda = BigDecimal.ZERO;

            for (BasicRecord e : basicRecords) {
                if (e.getCode().startsWith(y)) {
                    exoda = exoda.add(e.getAmount());
                }
            }

            BigDecimal scaledExoda = exoda.divide(
                scale, 2, RoundingMode.HALF_UP);
            exodaList.add(scaledExoda.doubleValue());
        }

        // Create chart
        CategoryChart chart = new CategoryChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Έξοδα: " + options[x - 1].getExpenseDescription())
            .xAxisTitle("Έτος")
            .yAxisTitle("Ποσό (δις €)")
            .build();

        configureAndShowChart(chart, "Έξοδα", years, exodaList);
    }

    /**
     * Displays a bar chart showing the total budget of a specific ministry
     * across all available years.
     *
     * @param x the index of the selected ministry
     */
    public static void chartMinistryByYear(final int x) {

        MinistryOptions[] options = MinistryOptions.values();
        String ministryName = options[x - 1].getMinistryDescription();

        int startYear = START_YEAR_CONST;
        int endYear = END_YEAR_CONST;

        List<Integer> years = new ArrayList<>();
        List<Double> budgetValues = new ArrayList<>();

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);

            String filename = "proypologismos" + year + "anaypourgeio.csv";

            List<Ministry> ministries =
                ReadBudget.readCroppedByMinistry(filename);

            BigDecimal totalBudget = BigDecimal.ZERO;

            for (Ministry m : ministries) {
                if (m.getcode() == options[x - 1].getMinistryCode()) {
                    totalBudget = m.getTotalBudget();
                    break;
                }
            }

            BigDecimal scaledBudget = totalBudget.divide(
                BILLION, 2, RoundingMode.HALF_UP);

            budgetValues.add(scaledBudget.doubleValue());

        }


        // Build chart
        CategoryChart chart = new CategoryChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Κατηγοριοποιημένο έξοδο: " + ministryName)
            .xAxisTitle("Έτος")
            .yAxisTitle("Ποσό (δις €)")
            .build();

        configureAndShowChart(chart, "Κατηγορία", years, budgetValues);
    }

    private static void configureAndShowChart(
        final CategoryChart chart, final String seriesName,
        final List<?> xData, final List<? extends Number> yData) {
        //Stilling
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setXAxisLabelRotation(X_AXIS_LABEL_ROTATION);
        chart.getStyler().setAvailableSpaceFill(AVAILABLE_SPACE_FILL);
        chart.getStyler().setPlotContentSize(PLOT_CONTENT_SIZE);

        //Add series
        chart.addSeries(seriesName, xData, yData);

        // Display
        SwingWrapper<CategoryChart> sw = new SwingWrapper<>(chart);
        javax.swing.JFrame frame = sw.displayChart();
        frame.setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(false);
    }
}
