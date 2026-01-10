package budgetcomparison;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class TestComparisonResult {
    
    /** Tests that ComparisonResult can be created without exceptions. */
    @Test
    public void testComparisonResultCreation() {
        assertDoesNotThrow(() -> {
            new ComparisonResult("Test Result",
            "perigrafi test",
            2020, 2021, BigDecimal.valueOf(20.0));
        });
    }

    /** Tests the getters of ComparisonResult. */
    @Test
    public void testComparisonResultGetters() {
        ComparisonResult result = new ComparisonResult("Test Result",
            "perigrafi test",
            2020, 2021, BigDecimal.valueOf(20.0));

        assertDoesNotThrow(() -> {
            String kodikos = result.getKodikos();
            String perigrafi = result.getPerigrafi();
            int baseYear = result.getBaseYear();
            int compareYear = result.getCompareYear();
            BigDecimal percentageChange = result.getPercentageChange();

            assert(kodikos.equals("Test Result"));
            assert(perigrafi.equals("perigrafi test"));
            assert(baseYear == 2020);
            assert(compareYear == 2021);
            assert(percentageChange.equals(BigDecimal.valueOf(20.0)));
        });
    }

}

