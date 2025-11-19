package dhmosiabytes;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Utility class για αποθήκευση των pdf των κρατικών προϋπολογισμών.
 */
public final class PdfDownloader {

    // Constuctor
    private PdfDownloader() {
        // Utility class
    }

    /**
     * Η μέθοδος κατεβάζει το pdf του κρατικού προϋπολογισμού.
     *
     * @param year το έτος
     * @throws IOException αν υπάρξει σφάλμα κατά την λήψη ή αποθήκευση
     */
    public static void downloadPdf(final int year) throws IOException {
        // Καλεί την findUrl για να κρατήσει σε μία μεταβλητή το link
        // της χρονίας που ψάχνουμε
        String fileUrl = BudgetUrlFinder.findUrl(year);
        // Έλεγχος ότι το url δεν είναι κενό
        if (fileUrl == null) {
            System.out.println("Δεν βρέθηκε URL για το έτος " + year);
            return;
        }
        // Αποθήκευση του url σε ένα αντικείμενο τύπου url
        URL url = new URL(fileUrl);
        // Ορισμός του ονόματος του αρχείου που θα κατεβάσουμε
        String fileName = "budget-" + year + ".pdf";
        // Δημιουργία του path του φακέλου που θα αποθηκευτούν τα pdf
        Path budgetsDir = Path.of("budgetsInPdf");
        // Δημιουργία φακέλου για αποθήκευση των pdf αν αυτός δεν υπαρχεί
        if (!Files.exists(budgetsDir)) {
            Files.createDirectories(budgetsDir);
        }
        // Δημιουργία του τελικού path με το αρχείο
        Path destinationPath = budgetsDir.resolve(fileName);

        try (InputStream in = url.openStream()) {
            Files.copy(in, destinationPath,
                StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Η μέθοδος διαγράφει το pdf του κρατικού προϋπολογισμού.
     * Όταν αυτό δεν χρειάζεται πια
     *
     * @param year το έτος
     * @throws IOException αν υπάρξει σφάλμα κατά την διαγραφή
     */
    public static void deletePdf(final int year) throws IOException {
        // Δημιουργία του path όπου είναι αποθηκευμένα τα pdf
        Path budgetsDir = Path.of("budgetsInPdf");
        // Δημιουργία του τελικού path με το αρχείο που θα διαγραφεί
        Path pdfFile = budgetsDir.resolve("budget-" + year + ".pdf");
        // Διαγραφή του αρχείου
        Files.deleteIfExists(pdfFile);
    }
}
