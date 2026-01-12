package ministryrequests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestRequestLoader {

    @Test
    void testExtractExistingRequestBlock() {
        String fileContent = """
            === REQUEST ===
            Id: 1
            Name: Ministry Request 1
            Details: Some details here
            === REQUEST ===
            Id: 2
            Name: Ministry Request 2
            Details: Other details
            """;

        String result = RequestLoader.extractRequestBlock(fileContent, 2);

        assertNotNull(result);
        assertTrue(result.contains("Id: 2"));
        assertTrue(result.contains("Name: Ministry Request 2"));
        assertTrue(result.contains("Details: Other details"));
    }

    @Test
    void testExtractNonExistingRequestBlock() {
        String fileContent = """
            === REQUEST ===
            Id: 1
            Name: Ministry Request 1
            """;

        String result = RequestLoader.extractRequestBlock(fileContent, 99);

        // Should return null because ID 99 does not exist
        assertNull(result);
    }

    @Test
    void testTrimmedBlock() {
        String fileContent = "=== REQUEST ===\n   Id: 5  \nDetails: test  ";
        String result = RequestLoader.extractRequestBlock(fileContent, 5);

        // Leading/trailing whitespace should be removed
        assertNotNull(result);
        assertFalse(result.startsWith(" "));
        assertFalse(result.endsWith(" "));
    }

    @Test
    void testEmptyFile() {
        String fileContent = "";
        String result = RequestLoader.extractRequestBlock(fileContent, 1);

        // Should return null on empty content
        assertNull(result);
    }
}
