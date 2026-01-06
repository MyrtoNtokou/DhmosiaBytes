package aggregatedata;

import budgetreader.Ypourgeio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for MinistryAnalyzer */
class TestMinistryAnalyzer {

    private List<Ypourgeio> ministries;

    @BeforeEach
    void setUp() {
        ministries = new ArrayList<>();

        // Ministries inside the code range (5-24)
        ministries.add(new Ypourgeio(5, "Ministry A", new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("150")));
        ministries.add(new Ypourgeio(10, "Ministry B", new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("300")));
        ministries.add(new Ypourgeio(15, "Ministry C", new BigDecimal("150"), new BigDecimal("150"), new BigDecimal("300")));
        ministries.add(new Ypourgeio(20, "Ministry D", new BigDecimal("50"), new BigDecimal("50"), new BigDecimal("100")));

        // Ministries outside code range (should be ignored)
        ministries.add(new Ypourgeio(3, "Ministry X", new BigDecimal("500"), new BigDecimal("500"), new BigDecimal("1000")));
        ministries.add(new Ypourgeio(25, "Ministry Y", new BigDecimal("400"), new BigDecimal("400"), new BigDecimal("800")));
    }

    @Test
    void testAnalyzeReturnsCorrectTopCountAndPercentages() {
        MinistryStats stats = MinistryAnalyzer.analyze(ministries);

        // Each list should have at most 3 elements
        assertTrue(stats.getMaxTaktikos().size() <= 3);
        assertTrue(stats.getMaxEpendyseis().size() <= 3);
        assertTrue(stats.getMaxSynolo().size() <= 3);

        // Max Taktikos should be Ministry B (200), C (150), A (100)
        assertEquals("Ministry B", stats.getMaxTaktikos().get(0).getOnoma());
        assertEquals("Ministry C", stats.getMaxTaktikos().get(1).getOnoma());
        assertEquals("Ministry A", stats.getMaxTaktikos().get(2).getOnoma());

        // Max Ependyseis should be Ministry C (150), B (100), A (50)
        assertEquals("Ministry C", stats.getMaxEpendyseis().get(0).getOnoma());
        assertEquals("Ministry B", stats.getMaxEpendyseis().get(1).getOnoma());
        assertEquals("Ministry A", stats.getMaxEpendyseis().get(2).getOnoma());

        // Max Synolo should be Ministry B (300), C (300), A (150)
        assertEquals("Ministry B", stats.getMaxSynolo().get(0).getOnoma());
        assertEquals("Ministry C", stats.getMaxSynolo().get(1).getOnoma());
        assertEquals("Ministry A", stats.getMaxSynolo().get(2).getOnoma());

        // Check percentages (sum of taktikos = 100+200+150+50=500)
        List<BigDecimal> taktikosPercent = stats.getMaxTaktikosPercentages();
        assertEquals(new BigDecimal("40.00"), taktikosPercent.get(0).setScale(2));
        assertEquals(new BigDecimal("30.00"), taktikosPercent.get(1).setScale(2));
        assertEquals(new BigDecimal("20.00"), taktikosPercent.get(2).setScale(2));

        // Ependyseis sum = 50+100+150+50=350
        List<BigDecimal> ependyseisPercent = stats.getMaxEpendyseisPercentages();
        assertEquals(new BigDecimal("42.86"), ependyseisPercent.get(0).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertEquals(new BigDecimal("28.57"), ependyseisPercent.get(1).setScale(2, BigDecimal.ROUND_HALF_UP));
        assertEquals(new BigDecimal("14.29"), ependyseisPercent.get(2).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    void testMinistriesOutsideCodeRangeIgnored() {
        MinistryStats stats = MinistryAnalyzer.analyze(ministries);

        // Ministries X and Y should not appear in any max lists
        stats.getMaxTaktikos().forEach(y -> assertFalse(y.getOnoma().equals("Ministry X")));
        stats.getMaxTaktikos().forEach(y -> assertFalse(y.getOnoma().equals("Ministry Y")));
        stats.getMaxEpendyseis().forEach(y -> assertFalse(y.getOnoma().equals("Ministry X")));
        stats.getMaxEpendyseis().forEach(y -> assertFalse(y.getOnoma().equals("Ministry Y")));
        stats.getMaxSynolo().forEach(y -> assertFalse(y.getOnoma().equals("Ministry X")));
        stats.getMaxSynolo().forEach(y -> assertFalse(y.getOnoma().equals("Ministry Y")));
    }

    @Test
    void testEmptyList() {
        MinistryStats stats = MinistryAnalyzer.analyze(new ArrayList<>());
        assertTrue(stats.getMaxTaktikos().isEmpty());
        assertTrue(stats.getMaxEpendyseis().isEmpty());
        assertTrue(stats.getMaxSynolo().isEmpty());
        assertTrue(stats.getMaxTaktikosPercentages().isEmpty());
        assertTrue(stats.getMaxEpendyseisPercentages().isEmpty());
        assertTrue(stats.getMaxSynoloPercentages().isEmpty());
    }
}
