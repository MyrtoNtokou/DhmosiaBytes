package dhmosiabytes;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Utility class για εύρεση URLs κρατικών προϋπολογισμών.
 */
public final class BudgetUrlFinder {

    /** Map με τα γνωστά URLs κρατικών προϋπολογισμών. */
    private static final Map<Integer, String> KNOWN_URL = Map.of(
        2019, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2019.pdf",
        2020, "https://minfin.gov.gr/wp-content/uploads/2019/11/21-11-2019-"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2020.pdf",
        2021, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2021.pdf",
        2022, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ_2022.pdf",
        2023, "https://minfin.gov.gr/wp-content/uploads/2023/11/21-11-2022-"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΫΠΟΛΟΓΙΣΜΟΣ-2023.pdf",
        2024, "https://minfin.gov.gr/wp-content/uploads/2023/11/"
                + "ΚΡΑΤΙΚΟΣ-ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ-2024.pdf",
        2025, "https://minfin.gov.gr/wp-content/uploads/2024/11/"
                + "Κρατικός-Προϋπολογισμός-2025_ΟΕ.pdf"
    );

    // Constructor
    private BudgetUrlFinder() {
        // Utility class
    }

    /**
     * Η μέθοδος βρίσκει το url που έχει το pdf του κρατικού προϋπολογισμού.
     *
     * @param year το έτος
     * @return το URL ή null αν δεν βρεθεί
     */
    public static String findUrl(final int year) {
        // Έλεγχος αν ζητείται προηγούμενο έτος και βρίσκεται
        // στην λίστα των γνωστών url
        if (KNOWN_URL.containsKey(year)) {
            return KNOWN_URL.get(year);
        } else { // Αναζήτηση στην ιστοσελίδα του υπουργείου για εντοπισμό url
        // κρατικού προϋπολογισμού νέου έτους
            try {
                // Σύνδεση στην ιστοσελίδα του υπουργείου
                Document doc = Jsoup.connect("https://minfin.gov.gr/"
                + "kratikos-proypologismos/").get();
                // Επιλογή των links που περιλαμβάνουν την χρονιά
                // και τελειώνουν με .pdf
                Elements links = doc.select("a[href*=" + year + "]"
                    + "[href$=.pdf]");
                // Επιστροφή μόνο του link που περιέχει και την φράση
                // Κρατικός Προϋπολογισμός
                for (Element link : links) {
                    String url = link.absUrl("href");
                    String upperUrl = url.toUpperCase();
                    if (upperUrl.contains("ΚΡΑΤΙΚΟΣ")
                        && upperUrl.contains("ΠΡΟΥΠΟΛΟΓΙΣΜΟΣ")) {
                        return url;
                    }
                }
            } catch (IOException e) {
                System.out.println("Σφάλμα κατά την ανάγνωση της σελίδας");
            }
        }
        // Επιστροφή null αν δεν βρεθεί τίποτα
        return null;
    }
}
