package budgetreader;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.nio.file.Path;

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
    /**  Minimum number of columns required in the CSV file. */
    private static final int COLUMNS_REQUIRED = 5;



    /**
 * Reads general budget data from an input stream and converts
 * each row into an {@link Eggrafi} object.
 *
 * @param input the input stream of the CSV file
 * @return a list of Eggrafi objects
 */
    private static List<Eggrafi> readGeneralBudgetFromStream(
        final InputStream input) {

        List<Eggrafi> eggrafes = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(
            new InputStreamReader(input, StandardCharsets.UTF_8))
            .withCSVParser(new CSVParserBuilder()
                    .withSeparator(';')
                    .build())
            .build()) {

            String[] line;

            /* Creates new instances Eggrafi for each line */
            while ((line = reader.readNext()) != null) {
                if (line.length < MIN_GENERAL_COLUMNS) {
                    continue;
                }

                String kodikos = line[0].trim();
                String perigrafi = line[1].trim();
                BigDecimal poso = parseNumber(line[2].trim());

                eggrafes.add(new Eggrafi(kodikos, perigrafi, poso));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return eggrafes;
        }


    /** Reads the proper csv file
    * from resources folder
    * and calls the readGeneralBudgetFromStream method.
    *
    * @param resourceName the CSV filename to load
    * @return a list of {@link Eggrafi} objects parsed from the CSV file;
    *        an empty list is returned if the resource cannot be found
    */
        public static List<Eggrafi> readGeneralBudget(
            final String resourceName) {

        /* Loads CSV file from classpath (resources folder) */
        InputStream input = ReadBudget.class.getResourceAsStream(
            "/" + resourceName);

        if (input == null) {
            System.err.println(
                "Δεν βρέθηκε το αρχείο: " + resourceName);
                return new ArrayList<>();
        }

        return readGeneralBudgetFromStream(input);
    }

    /**
    * Reads a general budget CSV file from the filesystem and converts
    * each row into an {@link Eggrafi} object.
    *
    * @param path the path to the CSV file to be read
    * @return a list of {@link Eggrafi} objects parsed from the file;
    *         an empty list is returned if the file cannot be read
    */
    public static List<Eggrafi> readGeneralBudgetFromPath(
        final Path path) {

        try (InputStream input = new FileInputStream(path.toFile())) {
            return readGeneralBudgetFromStream(input);
        } catch (Exception e) {
            System.err.println("Αδυναμία ανάγνωσης αρχείου: " + path);
            return new ArrayList<>();
        }
    }


    private static List<Ypourgeio> readMinistryFromStream(
        final InputStream input) {

        List<Ypourgeio> ypourg = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(
            new InputStreamReader(input, StandardCharsets.UTF_8))
            .withCSVParser(new CSVParserBuilder()
                    .withSeparator(';')
                    .build())
            .build()) {

            String[] line;

            while ((line = reader.readNext()) != null) {
                if (line.length < MIN_MINISTRY_COLUMNS) {
                    continue;
                }

                try {
                    String kodikosStr = line[0].trim().replace(
                    "\uFEFF", "");
                    if (!kodikosStr.matches("\\d+")) {
                        continue;
                    }

                    int kodikos = Integer.parseInt(kodikosStr);
                    String onoma = line[1].trim();

                    BigDecimal taktikos = parseNumber(line[COLUMN_TAKTIKOS]);
                    BigDecimal ependyseis = parseNumber(
                        line[COLUMN_EPENDYSEIS]);
                    BigDecimal synolo = parseNumber(line[COLUMN_SYNOLO]);

                    ypourg.add(new Ypourgeio(
                        kodikos, onoma, taktikos, ependyseis, synolo));

                    } catch (Exception e) {
                        System.err.println("Προσπέραση εγγραφής: "
                            + e.getMessage());
                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ypourg;
    }

    /**
    * Reads a ministry budget CSV file from the application's resources
    * directory and converts each row into a {@link Ypourgeio} object.
    *
    * @param resourceName the name of the CSV resource file to be read
    * @return a list of {@link Ypourgeio} objects parsed from the file;
    *         an empty list is returned if the resource cannot be found
    */
    public static List<Ypourgeio> readByMinistry(final String resourceName) {

        /* Loads  CSV file from classpath (resources folder) */
        InputStream input = ReadBudget.class.getResourceAsStream(
            "/" + resourceName);

        if (input == null) {
            System.err.println("Δεν βρέθηκε το αρχείο: " + resourceName);
            return new ArrayList<>();
        }
        return readMinistryFromStream(input);
    }


    /**
    * Reads a ministry budget CSV file from the filesystem and converts
    * each row into a {@link Ypourgeio} object.
    *
    * @param path the path to the CSV file to be read
    * @return a list of {@link Ypourgeio} objects parsed from the file;
    *         an empty list is returned if the file cannot be read
    */
    public static List<Ypourgeio> readByMinistryFromPath(
        final Path path) {

        try (InputStream input = new FileInputStream(path.toFile())) {
            return readMinistryFromStream(input);

        } catch (Exception e) {
            System.err.println("Αδυναμία ανάγνωσης αρχείου: " + path);
            return new ArrayList<>();
        }
    }

    /**
    * Converts a numeric string into a {@link BigDecimal} by
    * removing thousand separators and normalizing decimal points.
    *
    * @param s the numeric string to convert
    * @return the parsed {@link BigDecimal} value, or {@code BigDecimal.ZERO}
    *         if conversion fails
    */
    private static BigDecimal parseNumber(final String s) {

        String value = s;
    if (value == null || value.isEmpty()) {
        return BigDecimal.ZERO;
    }

    value = value.trim();

    /* Removes the trailing thousands separator */
    value = value.replace(".", "");
    /* Converts commas to dots for proper numeric formatting. */
    value = value.replace(",", ".");

        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            System.err.println("ΣΦΑΛΜΑ ΣΤΟΝ ΑΡΙΘΜΟ: [" + s + "]");
            return BigDecimal.ZERO;
        }
    }

    /**
     * Reads a CSV file containing ministry budget data and returns a list
     * of {@link Ypourgeio} objects.
     * <p>
     * This method only reads the 1st column (ministry code) and the
     * 5th column (total budget) from the CSV.
     * The other fields of {@link Ypourgeio} (taktikos, ependyseis)
     * are set to {@code BigDecimal.ZERO}.
     * Rows with fewer columns than {@link #COLUMNS_REQUIRED}
     * or invalid numeric codes are skipped.
     * </p>
     *
     * @param fileName the name of the CSV file to read
     * (should be in the resources folder)
     * @return a list of {@link Ypourgeio} objects
     * containing the ministry code, name, and total budget
     */
    public static List<Ypourgeio> readCroppedByMinistry(final String fileName) {
        List<Ypourgeio> ministriesList = new ArrayList<>();
        try {
            InputStream inputStream =
            ReadBudget.class.getResourceAsStream("/" + fileName);
            if (inputStream == null) {
                System.err.println("Δεν βρέθηκε το αρχείο: " + fileName);
                return ministriesList;
            }

            CSVParser csvParser = new CSVParserBuilder()
                .withSeparator(';')
                .build();

            CSVReader csvReader = new CSVReaderBuilder(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .withCSVParser(csvParser)
                .build();

            String[] row;
            while ((row = csvReader.readNext()) != null) {
                if (row.length < COLUMNS_REQUIRED) {
                    continue;
                }

                try {
                    String codeStr = row[0].trim().replace("\uFEFF", "");
                    if (!codeStr.matches("\\d+")) {
                        continue;
                    }

                    int ministryCode = Integer.parseInt(codeStr);
                    String ministryName = row[1].trim();
                    BigDecimal totalBudget = parseNumber(row[COLUMN_SYNOLO]);

                    // Create a Ypourgeio object with empty/zero
                    // values for the other fields
                    ministriesList.add(new Ypourgeio(
                        ministryCode,
                        ministryName,
                        BigDecimal.ZERO, // taktikos
                        BigDecimal.ZERO, // ependyseis
                        totalBudget     // synolo
                    ));

                } catch (Exception e) {
                    System.err.println(
                        "Προσπέραση λανθασμένης εγγραφής: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Δεν μπόρεσα να διαβάσω το αρχείο: " + fileName);
            e.printStackTrace();
        }
        return ministriesList;
    }
}
