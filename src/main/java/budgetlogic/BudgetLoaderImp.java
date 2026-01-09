package budgetlogic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import budgetreader.Eggrafi;
import budgetreader.Ypourgeio;

/**
 * CSV loader.
 */
public final class BudgetLoaderImp implements BudgetLoader {

    /** Minimum number of columns for general CSV rows. */
    private static final int GEN_MIN_COLS = 3;

    /** Minimum number of columns for ministry CSV rows. */
    private static final int MIN_MIN_COLS = 5;

    /** Column index for ministry code. */
    private static final int COL_KOD = 0;

    /** Column index for ministry name. */
    private static final int COL_NAME = 1;

    /** Column index for "taktikos" amount. */
    private static final int COL_TAKTIKOS = 2;

    /** Column index for "PDE" amount. */
    private static final int COL_PDE = 3;

    /** Column index for total amount ("synolo"). */
    private static final int COL_SYNOLO = 4;

    private BudgetLoaderImp() { }

    /**
     * Removes BOM from a string if present.
     *
     * @param s the input string
     * @return cleaned string
     */
    private static String stripBOM(final String s) {
        if (s != null && !s.isEmpty() && s.charAt(0) == '\uFEFF') {
            return s.substring(1);
        }
        return s;
    }

    /**
     * Loads Eggrafi entries from a CSV file.
     *
     * @param path the CSV file path
     * @return list of BasicRecord
     * @throws IOException if file read fails
     */
    public List<Eggrafi> loadGeneral(final String path)
            throws IOException {

        final List<Eggrafi> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path),
                        StandardCharsets.UTF_8
                )
        )) {
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                final String[] p = line.split(";");

                if (p.length < GEN_MIN_COLS) {
                    continue;
                }

                final String kod = stripBOM(p[COL_KOD].trim());
                final String desc = p[COL_NAME].trim();
                final BigDecimal amount = parseAmount(p[COL_TAKTIKOS]);

                list.add(new Eggrafi(kod, desc, amount));
            }
        }

        return list;
    }

    /**
     * Loads ministry entries from a CSV file.
     *
     * @param path CSV file path
     * @return list of Ministry
     * @throws IOException if reading fails
     */
    public List<Ypourgeio> loadMinistries(final String path)
            throws IOException {

        final List<Ypourgeio> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(path),
                        StandardCharsets.UTF_8
                )
        )) {
            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                final String[] p = line.split(";");

                if (p.length < MIN_MIN_COLS) {
                    continue;
                }

                final String kodStr = stripBOM(p[COL_KOD].trim());
                final int kod = Integer.parseInt(kodStr);

                final String name = p[COL_NAME].trim();
                final BigDecimal tak = parseAmount(p[COL_TAKTIKOS]);
                final BigDecimal pde = parseAmount(p[COL_PDE]);
                final BigDecimal syn = parseAmount(p[COL_SYNOLO]);

                list.add(new Ypourgeio(kod, name, tak, pde, syn));
            }
        }

        return list;
    }

    /**
     * Parses a numeric string into BigDecimal.
     *
     * @param t the numeric text
     * @return a BigDecimal value
     */
    private static BigDecimal parseAmount(final String t) {
        if (t == null || t.isEmpty()) {
            return BigDecimal.ZERO;
        }

        final String clean = t.replace(".", "").trim();
        return new BigDecimal(clean);
    }
}

