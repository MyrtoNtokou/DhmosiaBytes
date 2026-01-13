package budgetlogic;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

/**
 * Writes budget data to CSV files.
 */
public final class BudgetWriter {

    private BudgetWriter() { }

    /**
     * Writes general budget records to a CSV file.
     *
     * @param path the output file path
     * @param list the list of BasicRecord items
     * @throws IOException if writing fails
     */
    public static void writeGeneral(
            final String path,
            final List<Eggrafi> list
    ) throws IOException {

        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(path),
                StandardCharsets.UTF_8
        )) {

            for (final Eggrafi r : list) {
                writer.write(
                        r.getKodikos()
                        + ";"
                        + r.getPerigrafi()
                        + ";"
                        + formatForCsv(r.getPoso())
                        + System.lineSeparator()
                );
            }
        }
    }

    /**
     * Writes ministry records to a CSV file.
     *
     * @param path the output file path
     * @param list the list of Ministry items
     * @throws IOException if writing fails
     */
    public static void writeMinistries(
            final String path,
            final List<Ypourgeio> list
    ) throws IOException {

        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(path),
                StandardCharsets.UTF_8
        )) {

            for (final Ypourgeio m : list) {
                writer.write(
                        m.getKodikos()
                        + ";"
                        + m.getOnoma()
                        + ";"
                        + formatForCsv(m.getTaktikos())
                        + ";"
                        + formatForCsv(m.getEpendyseis())
                        + ";"
                        + formatForCsv(m.getSynolo())
                        + System.lineSeparator()
                );
            }
        }
    }

    /**
     * Formats a {@link BigDecimal} value for CSV output.
     * The value is formatted without decimal places and uses the Greek locale
     * (dots as thousands separators).
     *
     * @param value the numeric value to format
     * @return a formatted string representation of the value, or "0" if null
     */
    private static String formatForCsv(final java.math.BigDecimal value) {
        if (value == null) {
            return "0";
        }
        java.text.NumberFormat nf = java.text.NumberFormat
                        .getInstance(new java.util.Locale("el", "GR"));
        nf.setMaximumFractionDigits(0);
        return nf.format(value);
    }
}
