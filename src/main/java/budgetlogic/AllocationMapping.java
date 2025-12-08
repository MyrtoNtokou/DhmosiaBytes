package budgetlogic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * provides a predefined allocation mapping used to split ministry totals
 * into specific expense categories. Each ministry has its own mapping.
 */
public final class AllocationMapping {

    /** prevent instantiation for utility class. */
    private AllocationMapping() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Builds the full allocation mapping for ministries.
     *
     * @return a map: ministryName → (expenseCategory → percentage)
     */
    public static Map<String, Map<String, BigDecimal>> build() {

        final Map<String, Map<String, BigDecimal>> all =
                new LinkedHashMap<>();

        // Helper for BigDecimal percentages
        final Function<String, BigDecimal> p =
                s -> new BigDecimal(s).setScale(2);

                putGovermentBodies(all, p);
                putMinistries(all, p);
                putRegions(all, p);

                return all;
    }

    private static void putGovermentBodies(
       final Map<String, Map<String, BigDecimal>> all,
       final Function<String, BigDecimal> p) {

        all.put("Προεδρία της Δημοκρατίας", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.60"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.40")
        ));

        all.put("Βουλή των Ελλήνων", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.70"),
            "Αγορές αγαθών και υπηρεσισιών", p.apply("0.25"),
            "Λοιπές δαπάνες", p.apply("0.05")
        ));

        all.put("Προεδρία της Κυβέρνησης", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.50"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.40"),
            "Λοιπές δαπάνες", p.apply("0.10")
        ));
    }

    private static void putMinistries(
        final Map<String, Map<String, BigDecimal>> all,
        final Function<String, BigDecimal> p) {

            all.put("Υπουργείο Εσωτερικών", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.45"),
            "Μεταβιβάσεις", p.apply("0.35"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.15"),
            "Λοιπές δαπάνες", p.apply("0.05")
        ));

        all.put("Υπουργείο Εξωτερικών", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.55"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.35"),
            "Μεταβιβάσεις", p.apply("0.10")
        ));

        all.put("Υπουργείο Εθνικής Άμυνας", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.30"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.60"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.10")
        ));

        all.put("Υπουργείο Υγείας", Map.of(
            "Κοινωνικές παροχές", p.apply("0.50"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.35"),
            "Παροχές σε εργαζόμενους", p.apply("0.15")
        ));

        all.put("Υπουργείο Δικαιοσύνης", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.60"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.30"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.10")
        ));

        all.put("Υπουργείο Παιδείας, Θρησκευμάτων και Αθλητισμού", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.70"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.20"),
            "Μεταβιβάσεις", p.apply("0.10")
        ));

        all.put("Υπουργείο Πολιτισμού", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.45"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.45"),
            "Μεταβιβάσεις", p.apply("0.10")
        ));

        all.put("Υπουργείο Εθνικής Οικονομίας και Οικονομικών", Map.of(
            "Μεταβιβάσεις", p.apply("0.60"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.25"),
            "Παροχές σε εργαζόμενους", p.apply("0.15")
        ));

        all.put("Υπουργείο Αγροτικής Ανάπτυξης και Τροφίμων", Map.of(
            "Μεταβιβάσεις", p.apply("0.70"),
            "Λοιπές δαπάνες", p.apply("0.20"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.10")
        ));

        all.put("Υπουργείο Περιβάλλοντος και Ενέργειας", Map.of(
            "Μεταβιβάσεις", p.apply("0.50"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.35"),
            "Παροχές σε εργαζόμενους", p.apply("0.15")
        ));

        all.put("Υπουργείο Εργασίας και Κοινωνικής Ασφάλισης", Map.of(
            "Κοινωνικές παροχές", p.apply("0.80"),
            "Μεταβιβάσεις", p.apply("0.15"),
            "Παροχές σε εργαζόμενους", p.apply("0.05")
        ));

        all.put("Υπουργείο Κοινωνικής Συνοχής και Οικογένειας", Map.of(
            "Κοινωνικές παροχές", p.apply("0.75"),
            "Μεταβιβάσεις", p.apply("0.20"),
            "Λοιπές δαπάνες", p.apply("0.05")
        ));

        all.put("Υπουργείο Ανάπτυξης", Map.of(
            "Μεταβιβάσεις", p.apply("0.40"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.40"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.20")
        ));

        all.put("Υπουργείο Υποδομών και Μεταφορών", Map.of(
            "Πάγια περιουσιακά στοιχεία", p.apply("0.60"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.35"),
            "Παροχές σε εργαζόμενους", p.apply("0.05")
        ));

        all.put("Υπουργείο Ναυτιλίας και Νησιωτικής Πολιτικής", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.50"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.40"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.10")
        ));

        all.put("Υπουργείο Τουρισμού", Map.of(
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.50"),
            "Μεταβιβάσεις", p.apply("0.40"),
            "Λοιπές δαπάνες", p.apply("0.10")
        ));

        all.put("Υπουργείο Ψηφιακής Διακυβέρνησης", Map.of(
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.60"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.30"),
            "Παροχές σε εργαζόμενους", p.apply("0.10")
        ));

        all.put("Υπουργείο Μετανάστευσης και Ασύλου", Map.of(
            "Κοινωνικές παροχές", p.apply("0.40"),
            "Μεταβιβάσεις", p.apply("0.40"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.20")
        ));

        all.put("Υπουργείο Προστασίας του Πολίτη", Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.65"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.25"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.10")
        ));

        all.put("Υπουργείο Κλιματικής Κρίσης και Πολιτικής Προστασίας", Map.of(
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.50"),
            "Λοιπές δαπάνες", p.apply("0.25"),
            "Πάγια περιουσιακά στοιχεία", p.apply("0.25")
        ));
    }

    private static void putRegions(
        final Map<String, Map<String, BigDecimal>> all,
        final Function<String, BigDecimal> p) {
        final String[] regions = {
            "Αποκεντρωμένη Διοίκηση Αιγαίου",
            "Αποκεντρωμένη Διοίκηση Θεσσαλίας – Στερεάς Ελλάδας",
            "Αποκεντρωμένη Διοίκηση Ηπείρου – Δυτικής",
            "Αποκεντρωμένη Διοίκηση Κρήτης",
            "Αποκεντρωμένη Διοίκηση Μακεδονίας – Θράκης",
            "Αποκεντρωμένη Διοίκηση Πελοποννήσου – Δυτικής",
            "Αποκεντρωμένη Διοίκηση Αττικής",
            "Αποκεντρωμένη Διοίκηση Ανατολικής Μακεδονίας – Θράκης"
        };

        final Map<String, BigDecimal> regionMap = Map.of(
            "Παροχές σε εργαζόμενους", p.apply("0.60"),
            "Αγορές αγαθών και υπηρεσιών", p.apply("0.35"),
            "Λοιπές δαπάνες", p.apply("0.05")
        );

        for (String r : regions) {
            all.put(r, new HashMap<>(regionMap));
        }
    }
}
