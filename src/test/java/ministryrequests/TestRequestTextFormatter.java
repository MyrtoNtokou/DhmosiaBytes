package ministryrequests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRequestTextFormatter {

    @Test
    void testFormatForSavingRemovesAnsiCodes() {
        String raw = "\u001B[31mRed text\u001B[0m";
        String result = RequestTextFormatter.formatForSaving(raw, null);

        assertEquals("Red text", result);
    }

    @Test
    void testFormatForSavingRemovesTitleLine() {
        String raw = """
            TITLE LINE
            Line 1
            Line 2
            """;

        String result = RequestTextFormatter.formatForSaving(raw, "TITLE LINE");

        assertEquals("Line 1\nLine 2", result);
    }

    @Test
    void testFormatForSavingWithAnsiAndTitle() {
        String raw = """
            \u001B[36mHEADER\u001B[0m
            \u001B[32mContent line\u001B[0m
            """;

        String result = RequestTextFormatter.formatForSaving(raw, "HEADER");

        assertEquals("Content line", result);
    }

    @Test
    void testFormatForSavingTrimsWhitespace() {
        String raw = "   Some text with spaces   ";
        String result = RequestTextFormatter.formatForSaving(raw, null);

        assertEquals("Some text with spaces", result);
    }

    @Test
    void testNormalizeLineEndings() {
        String text = "Line1\r\nLine2\rLine3\nLine4";
        String result = RequestTextFormatter.normalize(text);

        assertEquals("Line1\nLine2\nLine3\nLine4", result);
    }

    @Test
    void testNormalizeMultipleSpacesAndTabs() {
        String text = "Text   with\t\tmultiple    spaces";
        String result = RequestTextFormatter.normalize(text);

        assertEquals("Text with multiple spaces", result);
    }

    @Test
    void testNormalizeMultipleLineBreaks() {
        String text = "Line1\n\n\nLine2\n\nLine3";
        String result = RequestTextFormatter.normalize(text);

        assertEquals("Line1\nLine2\nLine3", result);
    }

    @Test
    void testNormalizeTrimsWhitespace() {
        String text = "   Line with spaces   ";
        String result = RequestTextFormatter.normalize(text);

        assertEquals("Line with spaces", result);
    }
}
