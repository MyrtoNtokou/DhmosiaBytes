package gr.ilias;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadBudget {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Give 1 for general list or give 2 for  Ministry list");
        System.out.print("Επιλογή: ");
        int epilogh = input.nextInt();

        if (epilogh == 1) {
            List<Eggrafi> eggrafes = readGeneralBudget("/proypologismos2025.csv");
            System.out.println("\n=== ΓΕΝΙΚΟΣ ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ===");
            eggrafes.forEach(System.out::println);
        } else if (epilogh == 2) {
            List<Ypourgeio> ypourg = readByMinistry("/proypologismos2025anaypourgeio.csv");
            System.out.println("\n=== ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ ΑΝΑ ΥΠΟΥΡΓΕΙΟ ===");
            ypourg.forEach(System.out::println);
        } else {
            System.out.println("Μη έγκυρη επιλογή!");
        }
    }
        
       private static List<Eggrafi> readGeneralBudget(String path) {
        List<Eggrafi> eggrafes = new ArrayList<>();
        try {
            InputStream input = ReadBudget.class.getResourceAsStream(path);
            if (input == null) {
                System.err.println("❌ Δεν βρέθηκε το αρχείο: " + path);
                return eggrafes;
            }

            var parser = new CSVParserBuilder().withSeparator(';').build();
            var reader = new CSVReaderBuilder(new InputStreamReader(input, StandardCharsets.UTF_8))
                    .withCSVParser(parser)
                    .build();

            String[] line;
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
            InputStream input = ReadBudget.class.getResourceAsStream(path);
            if (input == null) {
                System.err.println("❌ Δεν βρέθηκε το αρχείο: " + path);
                return ypourg;
            }

            var parser = new CSVParserBuilder().withSeparator(';').build();
            var reader = new CSVReaderBuilder(new InputStreamReader(input, StandardCharsets.UTF_8))
                    .withCSVParser(parser)
                    .build();

            String[] line;
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
                    // Αγνόησε λανθασμένες γραμμές
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
