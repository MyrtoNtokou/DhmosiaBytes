package aggregatedata;

import budgetreader.Ypourgeio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for MinistryStats */
class TestMinistryStats {

    private List<Ypourgeio> maxTaktikos;
    private List<Ypourgeio> maxEpendyseis;
    private List<Ypourgeio> maxSynolo;
    private List<BigDecimal> maxTaktikosPercentages;
    private List<BigDecimal> maxEpendyseisPercentages;
    private List<BigDecimal> maxSynoloPercentages;
    private MinistryStats stats;

    @BeforeEach
    void setUp() {
        maxTaktikos = new ArrayList<>();
        maxTaktikos.add(new Ypourgeio(5, "Ministry A", new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("150")));
        maxTaktikos.add(new Ypourgeio(10, "Ministry B", new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("300")));

        maxEpendyseis = new ArrayList<>();
        maxEpendyseis.add(new Ypourgeio(15, "Ministry C", new BigDecimal("150"), new BigDecimal("150"), new BigDecimal("300")));

        maxSynolo = new ArrayList<>();
        maxSynolo.add(new Ypourgeio(20, "Ministry D", new BigDecimal("50"), new BigDecimal("50"), new BigDecimal("100")));

        maxTaktikosPercentages = new ArrayList<>();
        maxTaktikosPercentages.add(new BigDecimal("33.33"));
        maxTaktikosPercentages.add(new BigDecimal("66.67"));

        maxEpendyseisPercentages = new ArrayList<>();
        maxEpendyseisPercentages.add(new BigDecimal("100.00"));

        maxSynoloPercentages = new ArrayList<>();
        maxSynoloPercentages.add(new BigDecimal("100.00"));

        stats = new MinistryStats(
                maxTaktikos,
                maxEpendyseis,
                maxSynolo,
                maxTaktikosPercentages,
                maxEpendyseisPercentages,
                maxSynoloPercentages
        );
    }

    @Test
    void testGettersReturnCorrectValues() {
        assertEquals(maxTaktikos, stats.getMaxTaktikos());
        assertEquals(maxEpendyseis, stats.getMaxEpendyseis());
        assertEquals(maxSynolo, stats.getMaxSynolo());

        assertEquals(maxTaktikosPercentages, stats.getMaxTaktikosPercentages());
        assertEquals(maxEpendyseisPercentages, stats.getMaxEpendyseisPercentages());
        assertEquals(maxSynoloPercentages, stats.getMaxSynoloPercentages());
    }

    @Test
    void testGettersReturnCopiesNotReferences() {
        List<Ypourgeio> taktikosCopy = stats.getMaxTaktikos();
        taktikosCopy.clear();
        assertFalse(stats.getMaxTaktikos().isEmpty(), "Original list should not be affected");

        List<BigDecimal> percentagesCopy = stats.getMaxTaktikosPercentages();
        percentagesCopy.clear();
        assertFalse(stats.getMaxTaktikosPercentages().isEmpty(), "Original percentages list should not be affected");
    }

    @Test
    void testEmptyLists() {
        MinistryStats emptyStats = new MinistryStats(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertTrue(emptyStats.getMaxTaktikos().isEmpty());
        assertTrue(emptyStats.getMaxEpendyseis().isEmpty());
        assertTrue(emptyStats.getMaxSynolo().isEmpty());
        assertTrue(emptyStats.getMaxTaktikosPercentages().isEmpty());
        assertTrue(emptyStats.getMaxEpendyseisPercentages().isEmpty());
        assertTrue(emptyStats.getMaxSynoloPercentages().isEmpty());
    }
}
