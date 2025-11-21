package budgetreader;

/**Utility class that reads budget data from CSV files and processes
 * them according to the application's requirements.*/

import com.opencsv.CSVReaderBuilder;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadBudget {
    /**
    * Private constructor to prevent instantiation.
    */
    private ReadBudget() {
        throw new AssertionError("Utility class should not be instantiated.");
    }

    /** Reads output.csv from local file system
     *  and converts each row into an @Eggrafi object 
     */ 
        public static List<Eggrafi> readGeneralBudget(String filePath) {

        List<Eggrafi> eggrafes = new ArrayList<>();

        try (var reader = new CSVReaderBuilder(
            new InputStreamReader(
                new java.io.FileInputStream(filePath),
                StandardCharsets.UTF_8
            )).build()) {

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
            System.err.println("Δεν μπόρεσα να διαβάσω το αρχείο: " 
                + filePath);
            e.printStackTrace();
        }
        return eggrafes;
    }

    /** Reads output.csv from local file system
     *  and converts each row into an @Ypourgeio object 
     */ 
    public static List<Ypourgeio> readByMinistry(String filePath) {

        List<Ypourgeio> ypourg = new ArrayList<>();
        try (var reader = new CSVReaderBuilder(
        new InputStreamReader(
            new FileInputStream(filePath),
            StandardCharsets.UTF_8
        )).build()) {
            
            String[] line;

            /* Creates new instances Ypourgeio for each line */
            while ((line = reader.readNext()) != null) {
                if (line.length < 5) continue;
                try {
                    int kodikos = Integer.parseInt(line[0].trim());
                    String onoma = line[1].trim();
                    double taktikos = parseNumber(line[2]);
                    double ependyseis = parseNumber(line[3]);
                    double synolo = parseNumber(line[4]);
                    ypourg.add(new Ypourgeio(
                        kodikos, onoma, taktikos, ependyseis, synolo));
                } catch (Exception e) {
                        System.err.println("Skipped invalid CSV line: " 
                        + e.getMessage());

                }
            }
        } catch (Exception e) {
            System.err.println("Δεν μπόρεσα να διαβάσω το αρχείο: " 
                + filePath);
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
