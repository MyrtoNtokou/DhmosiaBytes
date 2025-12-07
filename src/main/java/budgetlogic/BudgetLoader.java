package budgetlogic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV LOADER
 */
public final class BudgetLoader {

    private BudgetLoader() { }

    public static List<BasicRecord> loadGeneral(final String path) throws IOException {

        final List<BasicRecord> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path),
         StandardCharsets.UTF_8)
)) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                final String[] p = line.split(";");
                if (p.length < 3) {
                    continue;
                }

                final String kod = p[0].trim();
                final String desc = p[1].trim();
                final BigDecimal amount = parseAmount(p[2]);

                list.add(new BasicRecord(kod, desc, amount));
            }
        }

        return list;
    }

    public static List<Ministry> loadMinistries(final String path) throws IOException {

        final List<Ministry> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), 
        StandardCharsets.UTF_8)
)) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                final String[] p = line.split(";");
                if (p.length < 5) {
                    continue;
                }

                final int kod = Integer.parseInt(p[0].trim());
                final String name = p[1].trim();
                final BigDecimal tak = parseAmount(p[2]);
                final BigDecimal pde = parseAmount(p[3]);
                final BigDecimal syn = parseAmount(p[4]);

                list.add(new Ministry(kod, name, tak, pde, syn));
            }
        }

        return list;
    }

    private static BigDecimal parseAmount(final String t) {
        if (t == null || t.isEmpty()) {
            return BigDecimal.ZERO;
        }
        final String clean = t.replace(".", "").trim();
        return new BigDecimal(clean);
    }
}

