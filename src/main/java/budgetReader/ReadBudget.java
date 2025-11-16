package budgetReader;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadBudget {
    public static void showBudget() {

        Scanner input = new Scanner(System.in);
        /* Ζητά από τον χρήστη να επιλέξει ποια μορφή προϋπολογισμού θέλει να εμφανιστεί. */
        System.out.println("Give 1 for general list or give 2 for  Ministry list");
        System.out.print("Επιλογή: ");
        /* Διαβάζει την επιλογή του χρήστη (1 ή 2) */
        int epilogh = input.nextInt();

        if (epilogh == 1) {

            /*Δημιουργεί λίστα eggrafes από το proypologismos2025.csv και τις εμφανίζει*/
            List<Eggrafi> eggrafes = readGeneralBudget("/proypologismos2025.csv");
            System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===");
            eggrafes.forEach(System.out::println);

        } else if (epilogh == 2) {

            /* Δημιουργεί λίστα Ypourgeio από το proypologismos2025anaypourgeio.csv και τις εμφανίζει*/
            List<Ypourgeio> ypourg = readByMinistry("/proypologismos2025anaypourgeio.csv");
            System.out.println("\n=== ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ΑΝΑ ΥΠΟΥΡΓΕΙΟ ===");
            ypourg.forEach(System.out::println);

        } else {

            System.out.println("Μη έγκυρη επιλογή!");
        }
    }

        /*
 * Διαβάζει το αρχείο γενικού προϋπολογισμού (proypologismos2025.csv)
 * από τον φάκελο resources και επιστρέφει όλες τις εγγραφές σε λίστα Eggrafi.
 */
       private static List<Eggrafi> readGeneralBudget(String path) {

        List<Eggrafi> eggrafes = new ArrayList<>();
        try {
            /* Φορτώνει το αρχείο CSV από το classpath (resources folder) */ 
            InputStream input = ReadBudget.class.getResourceAsStream(path);

            if (input == null) {
                System.err.println("❌ Δεν βρέθηκε το αρχείο: " + path);
                return eggrafes;
            }
            
            /* Καθορίζει το διαχωριστικό σε ; αντί για , */
            var parser = new CSVParserBuilder().withSeparator(';').build();
            var reader = new CSVReaderBuilder(new InputStreamReader(input, StandardCharsets.UTF_8))
                    .withCSVParser(parser)
                    .build();

            String[] line;
            
            /* Διαβάζει κάθε γραμμή του αρχείου και δημιουργεί ένα νέο αντικείμενο Eggrafi για κάθε γραμμή */
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

    /* Διαβάζει το proypologismos2025anaypourgeio.csv (ανά υπουργείο) */ 
    private static List<Ypourgeio> readByMinistry(String path) {
        List<Ypourgeio> ypourg = new ArrayList<>();
        try {
            /* Φορτώνει το αρχείο CSV από το classpath (resources folder) */
            InputStream input = ReadBudget.class.getResourceAsStream(path);
            if (input == null) {
                System.err.println("❌ Δεν βρέθηκε το αρχείο: " + path);
                return ypourg;
            }

            /* Καθορίζει το διαχωριστικό σε ; αντί για , */
            var parser = new CSVParserBuilder().withSeparator(';').build();
            var reader = new CSVReaderBuilder(new InputStreamReader(input, StandardCharsets.UTF_8))
                    .withCSVParser(parser)
                    .build();

            String[] line;

            /* Διαβάζει κάθε γραμμή του αρχείου και δημιουργεί ένα νέο αντικείμενο Ypourgeio για κάθε γραμμή */
            while ((line = reader.readNext()) != null) {
                if (line.length < 5) continue;
                try {
                    int kodikos = Integer.parseInt(line[0].trim());
                    String onoma = line[1].trim();
                    double taktikos = parseNumber(line[2]);
                    double ependyseis = parseNumber(line[3]);
                    double synolo = parseNumber(line[4]);
                    ypourg.add(new Ypourgeio(kodikos, onoma, taktikos, ependyseis, synolo));
                } catch (Exception e) {
                        System.err.println("Skipped invalid CSV line: " + e.getMessage());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ypourg;
    }
    private static double parseNumber(String s) {
    if (s == null || s.isEmpty()) return 0.0;

    s = s.trim();

    // Αφαίρεση τελειών χιλιάδων
    s = s.replace(".", "");

    // Αν υπάρχει κόμμα για δεκαδικά, το κάνουμε τελεία
    s = s.replace(",", ".");

    try {
        return Double.parseDouble(s);
    } catch (Exception e) {
        System.err.println("ΣΦΑΛΜΑ ΣΤΟΝ ΑΡΙΘΜΟ: [" + s + "]");
        return 0.0;
    }
}
}
