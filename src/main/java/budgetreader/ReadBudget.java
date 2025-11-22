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

    /** Reads the proper csv file
    * from folder resources and converts each row into an @Eggrafi object
     */ 
        public static List<Eggrafi> readGeneralBudget(String resourceName) {

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
                
            /*Converts delimiter to ";" instead of "," */    
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
                if (line.length < 3) continue;
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
     *  and converts each row into an @Ypourgeio object 
     */ 
    public static List<Ypourgeio> readByMinistry(String resourceName) {

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
                if (line.length < 5) continue;
                try {
                    String kodikosStr = line[0].trim().replace("\uFEFF", "");

                /* If it's not a number  → skip */
                    if (!kodikosStr.matches("\\d+")) {
                        continue;
                    }

                    int kodikos = Integer.parseInt(kodikosStr);

                    String onoma = line[1].trim();
                    double taktikos = parseNumber(line[2]);
                    double ependyseis = parseNumber(line[3]);
                    double synolo = parseNumber(line[4]);
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

    /** Converts a number written as a string into a double. 
     * by trimming the input and chainging commas to dots
     */
    public static double parseNumber(String s) {
    if (s == null || s.isEmpty()) return 0.0;

    s = s.trim();

    /* Removes the trailing thousands separator */
    s = s.replace(".", "");
    /* Converts commas to dots for proper numeric formatting. */
    s = s.replace(",", ".");

        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            System.err.println("ΣΦΑΛΜΑ ΣΤΟΝ ΑΡΙΘΜΟ: [" + s + "]");
            return 0.0;
        }
    }
}
