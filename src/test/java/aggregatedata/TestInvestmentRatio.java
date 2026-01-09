package aggregatedata;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for InvestmentRatio class */
class TestInvestmentRatio {

    @Test
    void testConstructorAndGetters() {
        // Create a sample InvestmentRatio object
        InvestmentRatio ratio = new InvestmentRatio(
                "Ministry A",
                new BigDecimal("0.25"),
                new BigDecimal("25.00")
        );

        // Verify that the getter returns the correct ministry name
        assertEquals("Ministry A", ratio.getOnoma());

        // Verify that the getter returns the correct raw ratio
        assertEquals(new BigDecimal("0.25"), ratio.getRatio());

        // Verify that the getter returns the correct percentage
        assertEquals(new BigDecimal("25.00"), ratio.getPercentage());
    }

    @Test
    void testMultipleInstances() {
        InvestmentRatio ratio1 = new InvestmentRatio("Ministry B", new BigDecimal("0.50"), new BigDecimal("50.00"));
        InvestmentRatio ratio2 = new InvestmentRatio("Ministry C", new BigDecimal("0.10"), new BigDecimal("10.00"));

        assertEquals("Ministry B", ratio1.getOnoma());
        assertEquals(new BigDecimal("0.50"), ratio1.getRatio());
        assertEquals(new BigDecimal("50.00"), ratio1.getPercentage());

        assertEquals("Ministry C", ratio2.getOnoma());
        assertEquals(new BigDecimal("0.10"), ratio2.getRatio());
        assertEquals(new BigDecimal("10.00"), ratio2.getPercentage());
    }
}
