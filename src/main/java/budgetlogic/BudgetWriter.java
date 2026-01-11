package budgetlogic;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import budgetreader.BasicRecord;
import budgetreader.Ministry;

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
            final List<BasicRecord> list
    ) throws IOException {

        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(path),
                StandardCharsets.UTF_8
        )) {

            for (final BasicRecord r : list) {
                writer.write(
                        r.getCode()
                        + ";"
                        + r.getDescription()
                        + ";"
                        + r.getAmount().toPlainString()
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
            final List<Ministry> list
    ) throws IOException {

        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(path),
                StandardCharsets.UTF_8
        )) {

            for (final Ministry m : list) {
                writer.write(
                        m.getcode()
                        + ";"
                        + m.getName()
                        + ";"
                        + m.getRegularBudget().toPlainString()
                        + ";"
                        + m.getPublicInvestments().toPlainString()
                        + ";"
                        + m.getTotalBudget().toPlainString()
                        + System.lineSeparator()
                );
            }
        }
    }
}
