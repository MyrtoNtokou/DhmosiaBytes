package dhmosiabytes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Files;

import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;

public class TestPdfDownloader {

    private static final int TEST_YEAR = 2025;
    private static Path budgetsDir;

    @AfterEach
    // Delete the file after every test
    public void cleanUp() throws IOException {
        Path pdfFile = Path.of("budget-" + TEST_YEAR + ".pdf");
        Files.deleteIfExists(pdfFile);
    }

    @Test
    public void testDownloadPdfCreatesFile() throws IOException {
        // Mock of the static method findUrl
        try (MockedStatic<BudgetUrlFinder> mocked = mockStatic(BudgetUrlFinder.class)) {
            mocked.when(() -> BudgetUrlFinder.findUrl(TEST_YEAR))
                  .thenReturn("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
            PdfDownloader.downloadPdf(TEST_YEAR);
            Path pdfFile = Path.of("budget-" + TEST_YEAR + ".pdf");
            assertTrue(Files.exists(pdfFile), "Το PDF δεν έχει δημιουργηθεί");
        }
    }

    @Test
    public void testDeletePdfRemovesFile() throws IOException {
        try (MockedStatic<BudgetUrlFinder> mocked = mockStatic(BudgetUrlFinder.class)) {
            mocked.when(() -> BudgetUrlFinder.findUrl(TEST_YEAR))
              .thenReturn("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf");
            PdfDownloader.downloadPdf(TEST_YEAR);
            Path pdfFile = Path.of("budget-" + TEST_YEAR + ".pdf");
            assertTrue(Files.exists(pdfFile));
            PdfDownloader.deletePdf(TEST_YEAR);
            assertFalse(Files.exists(pdfFile), "Το PDF δεν έχει διαγραφεί");
        }
    }

    @Test
    public void testDownloadPdfWithNullUrlDoesNotCreateFile() throws IOException {
        try (MockedStatic<BudgetUrlFinder> mocked = mockStatic(BudgetUrlFinder.class)) {
            mocked.when(() -> BudgetUrlFinder.findUrl(TEST_YEAR)).thenReturn(null);
            PdfDownloader.downloadPdf(TEST_YEAR);
            Path pdfFile = Path.of("budget-" + TEST_YEAR + ".pdf");
            assertFalse(Files.exists(pdfFile), "Δημιουργήθηκε αρχείο ενώ το URL είναι null");
        }
    }
}
