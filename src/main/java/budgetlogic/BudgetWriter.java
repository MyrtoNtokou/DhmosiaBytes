package budgetlogic;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Απλός writer που γράφει lists σε CSV με τη μορφή που μοιράστηκες.
 */
public final class BudgetWriter {

    private BudgetWriter() { }

    public static void writeGeneral(final String path, final List<BasicRecord> list)
            throws IOException {

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(path), 
        StandardCharsets.UTF_8)
) {
            for (final BasicRecord r : list) {
                writer.write(r.getKodikos() + ";" + r.getPerigrafi() + ";" +
                        r.getPoso().toPlainString() + ";" +
                        System.lineSeparator());
            }
        }
    }

    public static void writeMinistries(final String path, final List<Ministry> list)
            throws IOException {

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(path), 
        StandardCharsets.UTF_8)
) {
            for (final Ministry m : list) {
                writer.write(m.getKodikos() + ";" + m.getOnoma() + ";" +
                        m.getTaktikos().toPlainString() + ";" +
                        m.getPde().toPlainString() + ";" +
                        m.getSynolo().toPlainString() + ";;" +
                        System.lineSeparator());
            }
        }
    }
}
