package budgetlogic;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import budgetreader.*;

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
                        + r.getPoso().toPlainString()
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
                        + m.getTaktikos().toPlainString()
                        + ";"
                        + m.getEpendyseis().toPlainString()
                        + ";"
                        + m.getSynolo().toPlainString()
                        + System.lineSeparator()
                );
            }
        }
    }
}

