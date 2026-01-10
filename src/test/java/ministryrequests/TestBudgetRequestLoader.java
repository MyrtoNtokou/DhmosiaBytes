package ministryrequests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BudgetRequestLoader} utility class.
 * These tests verify correct extraction of request blocks
 * from a text input representing multiple budget requests.
 */
class TestBudgetRequestLoader {

    /**
     * Verifies that a specific request block is correctly found
     * when multiple requests exist in the input.
     */
    @Test
    void extractRequestBlock_foundSingleRequest() {
        String fileContent =
                "=== REQUEST ===\n" +
                "Id: 1\n" +
                "Amount: 1000\n" +
                "=== REQUEST ===\n" +
                "Id: 2\n" +
                "Amount: 2000\n";

        // Attempt to extract the request with ID 2
        String result = BudgetRequestLoader.extractRequestBlock(fileContent, 2);

        // Validate that the result is not null and contains expected data
        assertNotNull(result);
        assertTrue(result.contains("Id: 2"));
        assertTrue(result.contains("Amount: 2000"));
    }

    /**
     * Verifies that null is returned when the requested ID
     * does not exist in the input file.
     */
    @Test
    void extractRequestBlock_notFoundReturnsNull() {
        String fileContent =
                "=== REQUEST ===\n" +
                "Id: 1\n" +
                "Amount: 1000\n";

        // Attempt to extract a non-existing request
        String result = BudgetRequestLoader.extractRequestBlock(fileContent, 99);

        // Expect null since the request ID is not present
        assertNull(result);
    }

    /**
     * Verifies that the first request block can be extracted correctly.
     */
    @Test
    void extractRequestBlock_firstRequest() {
        String fileContent =
                "=== REQUEST ===\n" +
                "Id: 10\n" +
                "Amount: 500\n" +
                "=== REQUEST ===\n" +
                "Id: 20\n" +
                "Amount: 800\n";

        // Extract the first request block
        String result = BudgetRequestLoader.extractRequestBlock(fileContent, 10);

        // Ensure the correct block was returned
        assertNotNull(result);
        assertTrue(result.startsWith("Id: 10"));
    }

    /**
     * Verifies that leading and trailing whitespace
     * is removed from the extracted request block.
     */
    @Test
    void extractRequestBlock_trimsWhitespace() {
        String fileContent =
                "=== REQUEST ===\n\n" +
                "Id: 3\n" +
                "Amount: 300\n\n";

        // Extract the request and check trimming behavior
        String result = BudgetRequestLoader.extractRequestBlock(fileContent, 3);

        assertNotNull(result);
        assertEquals("Id: 3\nAmount: 300", result);
    }

    /**
     * Verifies that an empty input file
     * results in a null return value.
     */
    @Test
    void extractRequestBlock_emptyFileReturnsNull() {
        // Attempt extraction from empty content
        String result = BudgetRequestLoader.extractRequestBlock("", 1);

        assertNull(result);
    }
}
