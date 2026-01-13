package revenuerequests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import ministryrequests.RequestStatus;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

class TestRevenueRequestRepository {

    private RevenueRequestRepository repository;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        Path tempFile = tempDir.resolve("test_revenue_requests.txt");
        repository = new RevenueRequestRepository(tempFile);
    }

    @Test
    void testSaveAndLoadRevenue() {
        RevenueRequest request = new RevenueRequest(
                1, 
                "1,1", 
                "Άμεσοι Φόροι", 
                RequestStatus.PENDING, 
                LocalDateTime.now(), 
                "Περιεχόμενο αλλαγής"
        );

        repository.saveAll(List.of(request));

        List<RevenueRequest> all = repository.loadAll(); 
        
        assertFalse(all.isEmpty(), "Η λίστα δεν πρέπει να είναι κενή");
        assertEquals("1,1", all.get(0).getRevenueCode());
        assertEquals(RequestStatus.PENDING, all.get(0).getStatus());
    }

    @Test
    void testSaveAllOverwrites() {
        RevenueRequest req1 = new RevenueRequest(1, "1,1", "A", RequestStatus.PENDING, LocalDateTime.now(), "T1");
        RevenueRequest req2 = new RevenueRequest(2, "1,2", "B", RequestStatus.PARLIAMENT_APPROVED, LocalDateTime.now(), "T2");

        repository.saveAll(List.of(req1));
        repository.saveAll(List.of(req2)); 

        List<RevenueRequest> result = repository.loadAll();
        assertEquals(1, result.size());
        assertEquals("1,2", result.get(0).getRevenueCode());
    }
}