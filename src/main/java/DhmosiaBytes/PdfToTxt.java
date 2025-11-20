package dhmosiabytes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfToTxt {
    public static void main(String[] args) {
        String pdfPath = "pr1.pdf";
        String txtPath = "output.txt";

        try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            File file = new File(txtPath);
            Path path = file.toPath();
            Files.writeString(path, text);

            System.out.println("Conversion complete: " + txtPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
