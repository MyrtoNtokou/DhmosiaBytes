package dhmosiabytes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ministryrequests.MinistryRequest;
import ministryrequests.RequestStatus;
import ministryrequests.RequestType;

class TestRequestsController {

    private Scanner createScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testChooseRequest_EmptyList() {
        Scanner scanner = createScanner("1\n");
        int result = RequestsController.chooseRequest(scanner, new ArrayList<>());
        assertEquals(0, result, "Αν η λίστα είναι άδεια, πρέπει να επιστρέφει 0");
    }

    @Test
    void testChooseRequest_ValidAndInvalidInput() {

        String input = "abc\n5\n0\n";
        Scanner scanner = createScanner(input);
        
        List<MinistryRequest> list = new ArrayList<>();
        list.add(new MinistryRequest(5, 100, "Test", RequestType.REGULARBUDGET, 
                 RequestStatus.PENDING, LocalDateTime.now(), "Text"));

        int result = RequestsController.chooseRequest(scanner, list);
        assertEquals(5, result);
        
        // Δεύτερη κλήση: θα διαβάσει το 0
        result = RequestsController.chooseRequest(scanner, list);
        assertEquals(0, result);
    }

    @Test
    void testCompleteOrReject_Flow() {
        Scanner scanner = createScanner("9\n1\n2\n0\n");
        
        assertEquals(1, RequestsController.completeOrReject(scanner));
        assertEquals(2, RequestsController.completeOrReject(scanner));
        assertEquals(0, RequestsController.completeOrReject(scanner));
    }

    @Test
    void testPrimeMinisterAndParlMenu() {
        Scanner scanner = createScanner("5\nxyz\n1\n0\n");
        
        assertEquals(1, RequestsController.primeMinisterAndParlMenu(scanner));
        assertEquals(0, RequestsController.primeMinisterAndParlMenu(scanner));
    }

    @Test
    void testChooseEdit_EmptyAndValid() {
        // Empty
        assertEquals(0, RequestsController.chooseEdit(createScanner("1\n"), new ArrayList<>()));

        // Valid
        List<MinistryRequest> list = List.of(new MinistryRequest(10, 1, "A", 
                RequestType.REGULARBUDGET, RequestStatus.PENDING, LocalDateTime.now(), "T"));
        assertEquals(10, RequestsController.chooseEdit(createScanner("10\n"), list));
    }

    @Test
    void testCompleteOrRejectPrimMinist() {
        Scanner scanner = createScanner("4\n1\n2\n0\n");
        
        assertEquals(1, RequestsController.completeOrRejectPrimMinist(scanner));
        assertEquals(2, RequestsController.completeOrRejectPrimMinist(scanner));
        assertEquals(0, RequestsController.completeOrRejectPrimMinist(scanner));
    }

    @Test
    void testChooseEditParl() {
        List<MinistryRequest> list = List.of(new MinistryRequest(1, 1, "A", 
                RequestType.REGULARBUDGET, RequestStatus.PENDING, LocalDateTime.now(), "T"));
        
        assertEquals(1, RequestsController.chooseEditParl(createScanner("1\n"), list));
        assertEquals(0, RequestsController.chooseEditParl(createScanner("0\n"), list));
    }

    @Test
    void testShowRequests_Exit() {
        Scanner scanner = createScanner("0\n");
        assertDoesNotThrow(() -> RequestsController.showRequests(scanner, false));
    }
}
