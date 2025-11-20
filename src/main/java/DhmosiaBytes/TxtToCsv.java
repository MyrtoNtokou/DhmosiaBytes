package dhmosiabytes;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtToCsv {

    public static void main(String[] args) {
        String inputFile = "output.txt";
        String outputFile = "output.csv";

        try (BufferedReader input = new BufferedReader(new FileReader(inputFile));
             BufferedWriter output = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            StringBuilder mergedLine = new StringBuilder();

            while ((line = input.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Ένωση πολυγραμμικών εγγραφών
                if (!line.matches(".*\\d$")) {
                    mergedLine.append(line).append(" ");
                    continue;
                } else {
                    mergedLine.append(line);
                    String fullLine = mergedLine.toString().trim();
                    mergedLine.setLength(0);

                    // Διόρθωση κόμμα ανάμεσα σε ελληνικές λέξεις
                    fullLine = fixGreekComma(fullLine);

                    // Δημιουργία CSV γραμμής
                    String csvLine = createCsvLine(fullLine);

                    output.write(csvLine);
                    output.newLine();
                }
            }

            // Επεξεργασία τυχόν υπολειπόμενης γραμμής
            if (mergedLine.length() > 0) {
                String fullLine = mergedLine.toString().trim();
                fullLine = fixGreekComma(fullLine);
                String csvLine = createCsvLine(fullLine);
                output.write(csvLine);
                output.newLine();
            }

            System.out.println("Η μετατροπή ολοκληρώθηκε ο κρατικός προϋπολογισμός είναι εκφρασμένος σε μορφή CSV");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Αφαιρεί κόμμα ανάμεσα σε ελληνικές λέξεις
    private static String fixGreekComma(String line) {
        return line.replaceAll("([\\p{IsGreek}]+),([\\p{IsGreek}]+)", "$1 $2");
    }

    // Δημιουργεί CSV γραμμή, χωρίζει περιγραφή από αριθμούς και προσθέτει quotes όπου χρειάζεται
    private static String createCsvLine(String line) {
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

        // Υπόλοιπο κείμενο μετά τον τελευταίο αριθμό
        if (lastIndex < line.length()) {
            String remaining = line.substring(lastIndex).trim();
            if (!remaining.isEmpty()) {
                csvLine.append(quoteIfNeeded(remaining));
            } 
        } 
            // Αφαίρεση τελευταίου κόμματος
            if (csvLine.length() > 0 && csvLine.charAt(csvLine.length() - 1) == ',') {
                csvLine.setLength(csvLine.length() - 1);
            }

        return csvLine.toString();
    }

    // Περικλείει το field σε quotes αν περιέχει κενό ή κόμμα
    private static String quoteIfNeeded(String field) {
        if (field.contains(",") || field.contains(" ")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}


