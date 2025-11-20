package dhmosiabytes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
/**
 * This class takes a pdf file and converts it into a txt file
 */
final class PdfToTxt {
    private PdfToTxt() {
}
       public static void convertPdfToTxt() {
        String pdfPath = "pr1.pdf";
        String txtPath = "output.txt";

        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            Files.writeString(new File(txtPath).toPath(), text);
            System.out.println("Conversion complete: " + txtPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
}
