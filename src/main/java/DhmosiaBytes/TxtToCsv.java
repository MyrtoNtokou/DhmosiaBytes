package dhmosiabytes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtToCsv {  
    private TxtToCsv() {
   
}
        String inputFile = "output.txt";
        String outputFile = "output.csv";

        try (BufferedReader input = new BufferedReader(
         new InputStreamReader(
             new FileInputStream(inputFile),
             StandardCharsets.UTF_8
         ));
     BufferedWriter output = new BufferedWriter(
         new OutputStreamWriter(
             new FileOutputStream(outputFile),
             StandardCharsets.UTF_8
         ))) {


            String line;
            StringBuilder mergedLine = new StringBuilder();

            while ((line = input.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                // Ένωση πολυγραμμικών εγγραφών (όσο δεν τελειώνουν σε αριθμό)
                if (!line.matches(".*\\d$")) {
                    mergedLine.append(line).append(" ");
                    continue;
                } else {
                    mergedLine.append(line);
                    String fullLine = mergedLine.toString().trim();
                    mergedLine.setLength(0);

                    fullLine = fixGreekComma(fullLine);
                    String csvLine = createCsvLine(fullLine);

                    output.write(csvLine);
                    output.newLine();
                }
            }

            // Επεξεργασία υπολειπόμενης γραμμής
            if (mergedLine.length() > 0) {
                String fullLine = mergedLine.toString().trim();
                fullLine = fixGreekComma(fullLine);
                String csvLine = createCsvLine(fullLine);
                output.write(csvLine);
                output.newLine();
            }

            System.out.println("οκ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    

    // Αφαιρεί κόμμα ανάμεσα σε ελληνικές λέξεις
    private static String fixGreekComma(final String line) {
        return line.replaceAll("([\\p{IsGreek}]+),([\\p{IsGreek}]+)", "$1 $2");
    }

    // Δημιουργεί CSV γραμμή από αναμεμειγμένο κείμενο και αριθμούς
    private static String createCsvLine(final String line) {
        Pattern pattern = Pattern.compile("(\\d[\\d\\.,]*)");
        Matcher matcher = pattern.matcher(line);

        StringBuilder csvLine = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            String textPart = line.substring(lastIndex, matcher.start()).trim();
            String numberPart = matcher.group().trim();

            if (!textPart.isEmpty()) {
                csvLine.append(quoteIfNeeded(textPart)).append(",");
            }

            csvLine.append(numberPart).append(",");
            lastIndex = matcher.end();
        }

        // Υπόλοιπο κείμενο
        if (lastIndex < line.length()) {
            String remaining = line.substring(lastIndex).trim();
            if (!remaining.isEmpty()) {
                csvLine.append(quoteIfNeeded(remaining));
            }
        }

        // Αφαίρεση τελευταίου κόμματος
        if (csvLine.length() > 0 
        && csvLine.charAt(csvLine.length() - 1) == ',') {
            csvLine.setLength(csvLine.length() - 1);
        }

        return csvLine.toString();
    }

    // Αν χρειάζεται, περικλείει field σε quotes
    private static String quoteIfNeeded(final String field) {
        if (field.contains(",") || field.contains(" ")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
