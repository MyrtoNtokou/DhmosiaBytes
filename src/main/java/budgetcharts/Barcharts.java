package budgetcharts;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;
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
     * Creates and displays the expence bar chart.
     *
     * @param eggrafes List of budget records
     */
    public static void chartEsoda(final List<Eggrafi> eggrafes) {
        // Filter expence records (code starts with "1,")
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
            values.add(e.getPoso().divide(BILLION));
        }

        // Build the chart
        CategoryChart chart = new CategoryChartBuilder()
            .width(CHART_WIDTH)
            .height(CHART_HEIGHT)
            .title("Έσοδα")
            .xAxisTitle("Κατηγορία")
            .yAxisTitle("Ποσό (δις. €)")
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
            values.add(e.getPoso().divide(BILLION));
        }

        // Build the chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Έξοδα")
                .xAxisTitle("Κατηγορία")
                .yAxisTitle("Ποσό (δισ. €)")
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
            values.add(m.getSynolo().divide(BILLION));
        }

        // Build the chart
       CategoryChart chart = new CategoryChartBuilder()
                .width(CHART_WIDTH)
                .height(CHART_HEIGHT)
                .title("Δαπάνες Υπουργείων")
                .xAxisTitle("Υπουργείο")
                .yAxisTitle("Συνολική Δαπάνη (δισ. €)")
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

    /**
    * Enum representing expence categories steliou.
    */
    public enum IncomeOptions {
        /** Taxes. */
        FOROI("1,1", "Φόροι"),
        /** Social contributions. */
        KOINONIKES_EISFORES("1,2", "Κοινωνικές εισφορές"),
        /** Transfers. */
        METAVIVASEIS("1,3", "Μεταβιβάσεις"),
        /** Sales of goods and services. */
        POLHSEIS("1,4", "Πωλήσεις αγαθών και υπηρεσιών"),
        /** Other current expence. */
        LOIPA("1,5", "Λοιπά τρέχοντα έσοδα"),
        /** Fixed assets. */
        PAGIA("1,6", "Πάγια Περιουσιακά Στοιχεία"),
        /** Debt securities. */
        XREOSTIKOI("1,7", "Χρεωστικοί τίτλοι"),
        /** Loans. */
        DANEIO("1,8", "Δάνεια"),
        /** Equity securities and investment fund shares. */
        SYMMETOXIKOI(
            "1,9",
            "Συμμετοχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων"),
        /** Obligations from currency and deposits. */
        YPOXREOSEIS("1,9,1", "Υποχρεώσεις από νόμισμα και καταθέσεις"),
        /** Debt securities (liabilities). */
        XREOSTIKOI_YPOXREOSEIS("1,9,2", "Χρεωστικοί τίτλοι (υποχρεώσεις)"),
        /** Loans. */
        DANEIA("1,9,3", "Δάνεια"),
        /** Financial derivatives. */
        XRHMATOOIKONOMIKA("1,9,4", "Χρηματοοικονομικά παράγωγα");

        /** Income code. */
        private final String incomeCode;
        /** Income description. */
        private final String incomeDescription;

        IncomeOptions(final String code, final String description) {
            this.incomeCode = code;
            this.incomeDescription = description;
        }

        /**
        * Returns the expence code.
        * @return code of the expence.
        */
        public String getIncomeCode() {
            return this.incomeCode;
        }

        /**
        * Returns the expence description.
        * @return description of the expence.
        */
        public String getIncomeDescription() {
            return incomeDescription;
        }
    }

    /**
    * Creates a bar chart for the selected expence category per year.
    *
    * @param x index of the expence category (1-based).
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
            List<Eggrafi> eggrafes = ReadBudget.readGeneralBudget(filename);

            BigDecimal esoda = BigDecimal.ZERO;

            for (Eggrafi e : eggrafes) {
               if (e.getKodikos().startsWith(y)) {
                esoda = esoda.add(
                    e.getPoso());
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

        chart.addSeries("Έσοδα", years, esodaList);

        // Display chart
        new SwingWrapper<>(chart).displayChart();
        SwingWrapper<CategoryChart> sw = new SwingWrapper<>(chart);
        javax.swing.JFrame frame = sw.displayChart();
        frame.setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Enum representing expense categories.
     */
    public enum ExpenceOptions {
        /** Employee benefits. */
        EMPLOYEE_BENEFITS("2,1", "Παροχές σε εργαζομένους"),
        /** Social benefits. */
        SOCIAL_BENEFITS("2,2", "Κοινωνικές παροχές"),
        /** Transfers. */
        TRANSFERS("2,3", "Μεταβιβάσεις"),
        /** Purchases of goods and services. */
        PURCHASES_GOODS_SERVICES("2,4", "Αγορές αγαθών και υπηρεσιών"),
        /** Subsidies. */
        SUBSIDIES("2,5", "Επιδοτήσεις"),
        /** Interests. */
        INTERESTS("2,6", "Τόκοι"),
        /** Other expenses. */
        OTHER_EXPENSES("2,7", "Λοιπές δαπάνες"),
        /** Credits to allocate. */
        CREDITS_TO_ALLOCATE("2,8", "Πιστώσεις υπό κατανομή"),
        /** Fixed assets. */
        FIXED_ASSETS("2,9", "Πάγια περιουσιακά στοιχεία"),

        /** Precious metals. */
        PRECIOUS_METALS("2,9,1", "Τιμαλφή"),
        /** Loans. */
        LOANS_1("2,9,2", "Δάνεια"),
        /** Equity securities and investment fund shares. */
        EQUITY_SECURITIES(
            "2,9,3",
             "Συμμετοχικοί τίτλοι και μερίδια επενδυτικών κεφαλαίων"),
        /** Debt securities (liabilities). */
        DEBT_SECURITIES("2,9,4", "Χρεωστικοί τίτλοι (υποχρεώσεις)"),
        /** Loans. */
        LOANS_2("2,9,5", "Δάνεια"),
        /** Financial derivatives. */
        XRHMATOOIKONOMIKA("2,9,6", "Χρηματοοικονομικά παράγωγα");

        /** Expence code. */
        private final String code;
        /** Expence description. */
        private final String description;

        ExpenceOptions(final String codeParam, final String descParam) {
        this.code = codeParam;
        this.description = descParam;
        }

        /** @return the expense code. */
        public String getExpenceCode() {
            return code;
        }

        /** @return the expense description. */
        public String getExpenceDescription() {
            return description;
        }
    }

    /**
    * Creates a bar chart for the selected expence category per year.
    *
    * @param x index of the expence category (2-based).
    */
    public static void chartExodaByYear(final int x) {

        ExpenceOptions[] options = ExpenceOptions.values();
        String y = options[x - 1].getExpenceCode();

        int startYear = START_YEAR_CONST;
        int endYear = END_YEAR_CONST;
        List<Integer> years = new ArrayList<>();
        List<Double> exodaList = new ArrayList<>();

        BigDecimal scale = new BigDecimal("1000000000");

        for (int year = startYear; year <= endYear; year++) {
            years.add(year);

            String filename = "proypologismos" + year + ".csv";
            List<Eggrafi> eggrafes = ReadBudget.readGeneralBudget(filename);

            BigDecimal exoda = BigDecimal.ZERO;

            for (Eggrafi e : eggrafes) {
                if (e.getKodikos().startsWith(y)) {
                    exoda = exoda.add(e.getPoso());
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
            .title("Έξοδα: " + options[x - 1].getExpenceDescription())
            .xAxisTitle("Έτος")
            .yAxisTitle("Ποσό (δις €)")
            .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(X_AXIS_LABEL_ROTATION);
        chart.getStyler().setAvailableSpaceFill(AVAILABLE_SPACE_FILL);
        chart.getStyler().setPlotContentSize(PLOT_CONTENT_SIZE);

        chart.addSeries("Έξοδα", years, exodaList);

        // View chart
        SwingWrapper<CategoryChart> sw = new SwingWrapper<>(chart);
        javax.swing.JFrame frame = sw.displayChart();
        frame.setDefaultCloseOperation(
            javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

}

