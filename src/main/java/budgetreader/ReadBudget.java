package budgetreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

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
    private static final int COLUMN_REGULARBUDGET = 2;

    /** CSV column index for the public investment budget value. */
    private static final int COLUMN_PUBLIC_INVESTMENTS = 3;

    /** CSV column index for the total budget value. */
    private static final int COLUMN_SYNOLO = 4;
    /**  Minimum number of columns required in the CSV file. */
    private static final int COLUMNS_REQUIRED = 5;

    /**
     * Reads general budget data from an input stream and converts
     * each row into an {@link BasicRecord} object.
     *
     * @param input the input stream of the CSV file
     * @return a list of BasicRecord objects
     */
    private static List<BasicRecord> readGeneralBudgetFromStream(
        final InputStream input) {

        List<BasicRecord> eggrafes = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(
            new InputStreamReader(input, StandardCharsets.UTF_8))
            .withCSVParser(new CSVParserBuilder()
                    .withSeparator(';')
                    .build())
            .build()) {

            String[] line;

            /* Creates new instances BasicRecord for each line */
            while ((line = reader.readNext()) != null) {
                if (line.length < MIN_GENERAL_COLUMNS) {
                    continue;
                }

                String code = line[0]
                    .replace("\uFEFF", "")
                    .replace("\u00A0", "")
                    .trim();
                String description = line[1].trim();
                BigDecimal amount = parseNumber(line[2].trim());

                eggrafes.add(new BasicRecord(code, description, amount));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return eggrafes;
        }


    /** Reads the proper csv file
    * from resources folder
    * and calls the readGeneralBudgetFromStream method.
    *
    * @param resourceName the CSV filename to load
    * @return a list of {@link BasicRecord} objects parsed from the CSV file;
    *        an empty list is returned if the resource cannot be found
    */
        public static List<BasicRecord> readGeneralBudget(
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
    * each row into an {@link BasicRecord} object.
    *
    * @param path the path to the CSV file to be read
    * @return a list of {@link BasicRecord} objects parsed from the file;
    *         an empty list is returned if the file cannot be read
    */
    public static List<BasicRecord> readGeneralBudgetFromPath(
        final Path path) {

        try (InputStream input = new FileInputStream(path.toFile())) {
            return readGeneralBudgetFromStream(input);
        } catch (IOException e) {
            System.err.println("Αδυναμία ανάγνωσης αρχείου: " + path);
            return new ArrayList<>();
        }
    }


    private static List<Ministry> readMinistryFromStream(
        final InputStream input) {

        List<Ministry> ministry = new ArrayList<>();

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
                    String codeStr = line[0].trim().replace(
                    "\uFEFF", "");
                    if (!codeStr.matches("\\d+")) {
                        continue;
                    }

                    int code = Integer.parseInt(codeStr);
                    String name = line[1].trim();

                    BigDecimal regularBudget = parseNumber(
                        line[COLUMN_REGULARBUDGET]);
                    BigDecimal publicInvestments = parseNumber(
                        line[COLUMN_PUBLIC_INVESTMENTS]);
                    BigDecimal totalBudget = parseNumber(line[COLUMN_SYNOLO]);

                    ministry.add(new Ministry(
                        code,
                        name,
                        regularBudget,
                        publicInvestments,
                        totalBudget));

                    } catch (NumberFormatException e) {
                        System.err.println("Προσπέραση εγγραφής: "
                            + e.getMessage());
                    }
                }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return ministry;
    }

    /**
    * Reads a ministry budget CSV file from the application's resources
    * directory and converts each row into a {@link Ministry} object.
    *
    * @param resourceName the name of the CSV resource file to be read
    * @return a list of {@link Ministry} objects parsed from the file;
    *         an empty list is returned if the resource cannot be found
    */
    public static List<Ministry> readByMinistry(final String resourceName) {

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
    * each row into a {@link Ministry} object.
    *
    * @param path the path to the CSV file to be read
    * @return a list of {@link Ministry} objects parsed from the file;
    *         an empty list is returned if the file cannot be read
    */
    public static List<Ministry> readByMinistryFromPath(
        final Path path) {

        try (InputStream input = new FileInputStream(path.toFile())) {
            return readMinistryFromStream(input);
        } catch (IOException e) {
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
    static BigDecimal parseNumber(final String s)
        throws ParseException {

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
        } catch (NumberFormatException e) {
            System.err.println("ΣΦΑΛΜΑ ΣΤΟΝ ΑΡΙΘΜΟ:   " + s);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Reads a CSV file containing ministry budget data and returns a list
     * of {@link Ministry} objects.
     * <p>
     * This method only reads the 1st column (ministry code) and the
     * 5th column (total budget) from the CSV.
     * The other fields of {@link Ministry} (regularBudget, publicInvestments)
     * are set to {@code BigDecimal.ZERO}.
     * Rows with fewer columns than {@link #COLUMNS_REQUIRED}
     * or invalid numeric codes are skipped.
     * </p>
     *
     * @param fileName the name of the CSV file to read
     * (should be in the resources folder)
     * @return a list of {@link Ministry} objects
     * containing the ministry code, name, and total budget
     */
    public static List<Ministry> readCroppedByMinistry(final String fileName) {
        List<Ministry> ministriesList = new ArrayList<>();
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
            try {
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
                        BigDecimal totalBudget =
                        parseNumber(row[COLUMN_SYNOLO]);

                        // Create a Ministry object with empty/zero
                        // values for the other fields
                        ministriesList.add(new Ministry(
                            ministryCode,
                            ministryName,
                            BigDecimal.ZERO, // regularBudget
                            BigDecimal.ZERO, // publicInvestments
                            totalBudget     // totalBudget
                        ));

                    } catch (NumberFormatException e) {
                        System.err.println(
                            "Προσπέραση λανθασμένης εγγραφής: "
                            + e.getMessage());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException | CsvValidationException e) {
                System.err.println(e.getMessage());
            }
        } catch (NumberFormatException e) {
            System.err.println("Δεν μπόρεσα να διαβάσω το αρχείο: " + fileName);
            System.err.println(e.getMessage());
        }
        return ministriesList;
    }
}
