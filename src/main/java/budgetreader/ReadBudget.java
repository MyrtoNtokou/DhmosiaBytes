package budgetreader;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**Utility class that reads budget data from CSV files and processes
 * them according to the application's requirements.*/

public final class ReadBudget {
    /**
    * Private constructor to prevent instantiation.
    */
    private ReadBudget() { }
    /** Minimum number of columns required for the general budget CSV. */
    private static final int MIN_GENERAL_COLUMNS = 3;

    /** Minimum number of columns required for the ministry budget CSV. */
    private static final int MIN_MINISTRY_COLUMNS = 5;

    /** CSV column index for the regular budget value. */
    private static final int COLUMN_TAKTIKOS = 2;

    /** CSV column index for the public investment budget value. */
    private static final int COLUMN_EPENDYSEIS = 3;

    /** CSV column index for the total budget value. */
    private static final int COLUMN_SYNOLO = 4;

    /** Reads the proper csv file
    * from folder resources
    * and converts each row into an {@link Eggrafi} object.
    *
    * @param resourceName the CSV filename to load
    * @return a list of Eggrafi objects read from the file
    */
        public static List<Eggrafi> readGeneralBudget(
            final String resourceName) {

        List<Eggrafi> eggrafes = new ArrayList<>();
        try {
           /* Loads CSV file from classpath (resources folder) */
            InputStream input = ReadBudget.class.getResourceAsStream(
                "/" + resourceName);

                if (input == null) {
                    System.err.println(
                        "❌ Δεν βρέθηκε το αρχείο: " + resourceName);
                    return eggrafes;
                }

            /*Converts delimiter to ";" instead of ","  */
            CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                    .build();

            CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(input, StandardCharsets.UTF_8)
                    ).withCSVParser(parser)
                        .build();

            String[] line;

            /* Creates new instances Eggrafi for each line */
            while ((line = reader.readNext()) != null) {
                if (line.length < MIN_GENERAL_COLUMNS) {
                    continue;
                }

                String kodikos = line[0].trim();
                String perigrafi = line[1].trim();
                double poso = parseNumber(line[2].trim());
                eggrafes.add(new Eggrafi(kodikos, perigrafi, poso));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eggrafes;
    }

    /** Reads cσv file from folder resources
     *  and converts each row into an @Ypourgeio object.
     *
     * @param resourceName the CSV filename to load
     * @return a list of Ypourgeio objects read from the file
     */
    public static List<Ypourgeio> readByMinistry(final String resourceName) {

        List<Ypourgeio> ypourg = new ArrayList<>();
        try {
            /* Loads  CSV file from classpath (resources folder) */
            InputStream input = ReadBudget.class.getResourceAsStream(
                "/" + resourceName);
            if (input == null) {
                System.err.println("❌ Δεν βρέθηκε το αρχείο: " + resourceName);
                return ypourg;
            }
            /*Converts delimiter to ";" instead of "," */
            CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                    .build();

            CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(input, StandardCharsets.UTF_8)
                    ).withCSVParser(parser)
                        .build();
            String[] line;

            /* Creates new instances Ypourgeio for each line */
            while ((line = reader.readNext()) != null) {
                if (line.length < MIN_MINISTRY_COLUMNS) {
                    continue;
                }

                try {
                    String kodikosStr = line[0].trim().replace("\uFEFF", "");

                /* If it's not a number  → skip */
                    if (!kodikosStr.matches("\\d+")) {
                        continue;
                    }

                    int kodikos = Integer.parseInt(kodikosStr);

                    String onoma = line[1].trim();
                    double taktikos = parseNumber(line[COLUMN_TAKTIKOS]);
                    double ependyseis = parseNumber(line[COLUMN_EPENDYSEIS]);
                    double synolo = parseNumber(line[COLUMN_SYNOLO]);
                    ypourg.add(new Ypourgeio(
                        kodikos, onoma, taktikos, ependyseis, synolo));
                } catch (Exception e) {
                        System.err.println("Προσπέραση λανθασμένης εγγραφής: "
                        + e.getMessage());

                }
            }
        } catch (Exception e) {
            System.err.println("Δεν μπόρεσα να διαβάσω το αρχείο: "
                + resourceName);
            e.printStackTrace();
        }
        return ypourg;
    }

    /** Converts a number written as a string into a double
     * by trimming the input and chainging commas to dots.
     *
     * @param s the numeric string to convert
     * @return the parsed double value, or 0.0 if conversion fails
     */
    public static double parseNumber(final String s) {

        String value = s;
    if (value == null || value.isEmpty()) {
        return 0.0;
    }

    value = value.trim();

    /* Removes the trailing thousands separator */
    value = value.replace(".", "");
    /* Converts commas to dots for proper numeric formatting. */
    value = value.replace(",", ".");

        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            System.err.println("ΣΦΑΛΜΑ ΣΤΟΝ ΑΡΙΘΜΟ: [" + s + "]");
            return 0.0;
        }
    }
}
