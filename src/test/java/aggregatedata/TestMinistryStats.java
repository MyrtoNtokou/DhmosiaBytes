package aggregatedata;

import budgetreader.Ministry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for MinistryStats */
class TestMinistryStats {

    private List<Ministry> maxRegularBudget;
    private List<Ministry> maxPublicInvestments;
    private List<Ministry> maxTotalBudget;
    private List<BigDecimal> maxRegularBudgetPercentages;
    private List<BigDecimal> maxPublicInvestmentsPercentages;
    private List<BigDecimal> maxTotalBudgetPercentages;
    private MinistryStats stats;

    @BeforeEach
    void setUp() {
        maxRegularBudget = new ArrayList<>();
        maxRegularBudget.add(new Ministry(5, "Ministry A", new BigDecimal("100"), new BigDecimal("50"), new BigDecimal("150")));
        maxRegularBudget.add(new Ministry(10, "Ministry B", new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("300")));

        maxPublicInvestments = new ArrayList<>();
        maxPublicInvestments.add(new Ministry(15, "Ministry C", new BigDecimal("150"), new BigDecimal("150"), new BigDecimal("300")));

        maxTotalBudget = new ArrayList<>();
        maxTotalBudget.add(new Ministry(20, "Ministry D", new BigDecimal("50"), new BigDecimal("50"), new BigDecimal("100")));

        maxRegularBudgetPercentages = new ArrayList<>();
        maxRegularBudgetPercentages.add(new BigDecimal("33.33"));
        maxRegularBudgetPercentages.add(new BigDecimal("66.67"));

        maxPublicInvestmentsPercentages = new ArrayList<>();
        maxPublicInvestmentsPercentages.add(new BigDecimal("100.00"));

        maxTotalBudgetPercentages = new ArrayList<>();
        maxTotalBudgetPercentages.add(new BigDecimal("100.00"));

        stats = new MinistryStats(
                maxRegularBudget,
                maxPublicInvestments,
                maxTotalBudget,
                maxRegularBudgetPercentages,
                maxPublicInvestmentsPercentages,
                maxTotalBudgetPercentages
        );
    }

    @Test
    void testGettersReturnCorrectValues() {
        assertEquals(maxRegularBudget, stats.getMaxRegularBudget());
        assertEquals(maxPublicInvestments, stats.getMaxPublicInvestments());
        assertEquals(maxTotalBudget, stats.getMaxTotalBudget());

        assertEquals(maxRegularBudgetPercentages, stats.getMaxRegularBudgetPercentages());
        assertEquals(maxPublicInvestmentsPercentages, stats.getMaxPublicInvestmentsPercentages());
        assertEquals(maxTotalBudgetPercentages, stats.getMaxTotalBudgetPercentages());
    }

    @Test
    void testGettersReturnCopiesNotReferences() {
        List<Ministry> regularBudgetCopy = stats.getMaxRegularBudget();
        regularBudgetCopy.clear();
        assertFalse(stats.getMaxRegularBudget().isEmpty(), "Original list should not be affected");

        List<BigDecimal> percentagesCopy = stats.getMaxRegularBudgetPercentages();
        percentagesCopy.clear();
        assertFalse(stats.getMaxRegularBudgetPercentages().isEmpty(), "Original percentages list should not be affected");
    }

    @Test
    void testEmptyLists() {
        MinistryStats emptyStats = new MinistryStats(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertTrue(emptyStats.getMaxRegularBudget().isEmpty());
        assertTrue(emptyStats.getMaxPublicInvestments().isEmpty());
        assertTrue(emptyStats.getMaxTotalBudget().isEmpty());
        assertTrue(emptyStats.getMaxRegularBudgetPercentages().isEmpty());
        assertTrue(emptyStats.getMaxPublicInvestmentsPercentages().isEmpty());
        assertTrue(emptyStats.getMaxTotalBudgetPercentages().isEmpty());
    }
}
