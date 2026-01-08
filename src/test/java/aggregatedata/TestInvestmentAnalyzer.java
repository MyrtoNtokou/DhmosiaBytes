package aggregatedata;

import budgetreader.Ypourgeio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for InvestmentAnalyzer class */
class TestInvestmentAnalyzer {

    private List<Ypourgeio> ministries;

    @BeforeEach
    void setUp() {
        ministries = new ArrayList<>();

        // Ministries within code range (5–24)
        ministries.add(new Ypourgeio(5, "Ministry A", new BigDecimal("800"), new BigDecimal("200"), new BigDecimal("1000")));
        ministries.add(new Ypourgeio(10, "Ministry B", new BigDecimal("1000"), new BigDecimal("1000"), new BigDecimal("2000")));
        ministries.add(new Ypourgeio(24, "Ministry C", new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"))); // total=0

        // Ministries outside code range (should be ignored)
        ministries.add(new Ypourgeio(3, "Ministry D", new BigDecimal("500"), new BigDecimal("500"), new BigDecimal("1000")));
        ministries.add(new Ypourgeio(25, "Ministry E", new BigDecimal("500"), new BigDecimal("500"), new BigDecimal("1000")));
    }

    @Test
    void testCalculateRatios() {
        List<InvestmentRatio> results = InvestmentAnalyzer.calculate(ministries);

        // Only 3 ministries in code range
        assertEquals(3, results.size());

        // Ministry B has highest ratio: 1000 / 2000 = 0.50 -> 50%
        InvestmentRatio first = results.get(0);
        assertEquals("Ministry B", first.getOnoma());
        assertEquals(new BigDecimal("0.50"), first.getRatio());
        assertEquals(new BigDecimal("50.00"), first.getPercentage());

        // Ministry A: 200 / 1000 = 0.20 -> 20%
        InvestmentRatio second = results.get(1);
        assertEquals("Ministry A", second.getOnoma());
        assertEquals(new BigDecimal("0.20"), second.getRatio());
        assertEquals(new BigDecimal("20.00"), second.getPercentage());

        // Ministry C: total=0 -> ratio=0, percentage=0
        InvestmentRatio third = results.get(2);
        assertEquals("Ministry C", third.getOnoma());
        assertEquals(BigDecimal.ZERO.setScale(2), third.getRatio());
        assertEquals(BigDecimal.ZERO.setScale(2), third.getPercentage());
    }

    @Test
    void testEmptyList() {
        List<InvestmentRatio> results = InvestmentAnalyzer.calculate(new ArrayList<>());
        assertTrue(results.isEmpty());
    }

    @Test
    void testMinistriesOutsideRangeIgnored() {
        List<InvestmentRatio> results = InvestmentAnalyzer.calculate(ministries);

        // Ministries with code 3 and 25 should not appear
        assertTrue(results.stream().noneMatch(r -> r.getOnoma().equals("Ministry D")));
        assertTrue(results.stream().noneMatch(r -> r.getOnoma().equals("Ministry E")));
    }
}
