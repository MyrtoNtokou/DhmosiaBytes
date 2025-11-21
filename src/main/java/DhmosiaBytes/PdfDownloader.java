package dhmosiabytes;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.MalformedURLException;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Utility class that downloads the state budget pdfs.
 */
public final class PdfDownloader {

    // Constuctor
    private PdfDownloader() {
        // Utility class
    }

    /**
     * Downloads the state budget pdf of a specific year.
     *
     * @param year
     * @throws IOException for error during download or save
     */
    public static void downloadPdf(final int year) {
        // Saves the link of the pdf
        String fileUrl = BudgetUrlFinder.findUrl(year);
        // Checks that the url is not null
        if (fileUrl == null) {
            System.out.println("Δεν βρέθηκε URL για το έτος " + year);
            return;
        }
        // Saves the link in a URL object
        URL url = null;
        try {
            url = new URL(fileUrl);
        } catch (MalformedURLException e) {
            System.err.println("Σφάλμα κατά τον εντοπισμό του αρχείου");
            return;
        }
        // Creates the path of the folder where the pdfs will be saved
        Path destinationPath = Path.of("budget-" + year + ".pdf");
        try (InputStream in = url.openStream()) {
            Files.copy(in, destinationPath,
                StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την αποθήκευση του αρχείου");
        }
    }

    /**
     * Deletes the pdf of the state budget.
     *
     * @param year
     * @throws IOException for error during delete
     */
    public static void deletePdf(final int year) {
        // Creates the path of the folder with the pdfs
        Path destinationPath = Path.of("budget-" + year + ".pdf");
        // Deletes the pdf
        try {
            Files.deleteIfExists(destinationPath);
        } catch (IOException e) {
            System.err.println("Σφάλμα κατά την διαγραφή του αρχείου");
        }
    }
}
