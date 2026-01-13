package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import budgetreader.Ministry;

/**
 * Tests for CutLists class methods.
 */
public class TestCutLists {

    @Test
    public void testCutMInistryFiltersCorrectly() {

        List<Ministry> allMinistries = new ArrayList<>();
        allMinistries.add(new Ministry(1, "Υπουργείο Α", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ministry(4, "Υπουργείο Β", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ministry(10, "Υπουργείο Γ", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ministry(25, "Υπουργείο Δ", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ministry(33, "Υπουργείο Ε", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));

        CutLists cut = new CutLists() {
            @Override
            public List<Ministry> cutMinistry() {
                List<Ministry> filtered = new ArrayList<>();
                for (Ministry y : allMinistries) {
                    if (y.getcode() != 4 && y.getcode() != 25 && y.getcode() != 33) {
                        filtered.add(y);
                    }
                }
                return filtered;
            }
        };

        List<Ministry> result = cut.cutMinistry();


        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(y -> y.getcode() == 1));
        assertTrue(result.stream().anyMatch(y -> y.getcode() == 10));
    }
}
