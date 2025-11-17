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
     * Η μέθοδος βρίσκει κατεβάζει το pdf του κρατικού προϋπολογισμού.
     *
     * @param year το έτος
     * @throws IOException αν υπάρξει σφάλμα κατά την λήψη ή αποθήκευση
     */
    public static void downloadPdf(final int year) throws IOException {
        // Καλεί την finfUrl για να κρατήσει σε μία μεταβλητή το url
        // της χρονίας που ψάχνουμε
        String fileUrl = BudgetUrlFinder.findUrl(year);
        //
        if (fileUrl == null) {
            System.out.println("Δεν βρέθηκε URL για το έτος " + year);
            return;
        }
        // Αποθήκευση του url σε ένα αντικείμενο τύπου url
        URL url = new URL(fileUrl);
        String fileName = Path.of(url.getPath()).getFileName().toString();
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
}
