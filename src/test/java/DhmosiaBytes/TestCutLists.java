package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import budgetreader.Ypourgeio;

/**
 * Tests for CutLists class methods.
 */
public class TestCutLists {

    @Test
    public void testCutYpourgeioFiltersCorrectly() {

        List<Ypourgeio> allMinistries = new ArrayList<>();
        allMinistries.add(new Ypourgeio(1, "Υπουργείο Α", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ypourgeio(4, "Υπουργείο Β", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ypourgeio(10, "Υπουργείο Γ", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ypourgeio(25, "Υπουργείο Δ", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));
        allMinistries.add(new Ypourgeio(33, "Υπουργείο Ε", BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.TEN));


        CutLists cut = new CutLists() {
            @Override
            public List<Ypourgeio> cutYpourgeio() {
                List<Ypourgeio> filtered = new ArrayList<>();
                for (Ypourgeio y : allMinistries) {
                    if (y.getKodikos() != 4 && y.getKodikos() != 25 && y.getKodikos() != 33) {
                        filtered.add(y);
                    }
                }
                return filtered;
            }
        };

        List<Ypourgeio> result = cut.cutYpourgeio();


        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(y -> y.getKodikos() == 1));
        assertTrue(result.stream().anyMatch(y -> y.getKodikos() == 10));
    }
}
