package budgetlogic;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

class TestAllocationMapping {

    @Test
    void testBuildReturnsNonNullAndNotEmpty() {
        Map<String, Map<String, BigDecimal>> map = AllocationMapping.build();
        assertNotNull(map, "The returned map should not be null");
        assertFalse(map.isEmpty(), "The returned map should not be empty");
    }

    @Test
    void testContainsSpecificMinistry() {
        Map<String, Map<String, BigDecimal>> map = AllocationMapping.build();

        assertTrue(map.containsKey("Υπουργείο Υγείας"),
                "Υπουργείο Υγείας should exist in the mapping");
    }

    @Test
    void testMinistryPercentages() {
        Map<String, Map<String, BigDecimal>> map = AllocationMapping.build();

        Map<String, BigDecimal> health = map.get("Υπουργείο Υγείας");

        assertEquals(new BigDecimal("0.50").setScale(2), health.get("Κοινωνικές παροχές"));
        assertEquals(new BigDecimal("0.35").setScale(2), health.get("Αγορές αγαθών και υπηρεσιών"));
        assertEquals(new BigDecimal("0.15").setScale(2), health.get("Παροχές σε εργαζόμενους"));
    }

    @Test
    void testPercentScaleIsAlwaysTwo() {
        Map<String, Map<String, BigDecimal>> map = AllocationMapping.build();

        for (Map<String, BigDecimal> inner : map.values()) {
            for (BigDecimal percent : inner.values()) {
                assertEquals(2, percent.scale(),
                        "Percent values must have scale 2");
            }
        }
    }

    @Test
    void testRegionsHaveCorrectTemplate() {
        Map<String, Map<String, BigDecimal>> map = AllocationMapping.build();

        String region = "Αποκεντρωμένη Διοίκηση Αιγαίου";
        assertTrue(map.containsKey(region));

        Map<String, BigDecimal> regionValues = map.get(region);

        assertEquals(new BigDecimal("0.60").setScale(2), regionValues.get("Παροχές σε εργαζόμενους"));
        assertEquals(new BigDecimal("0.35").setScale(2), regionValues.get("Αγορές αγαθών και υπηρεσιών"));
        assertEquals(new BigDecimal("0.05").setScale(2), regionValues.get("Λοιπές δαπάνες"));
    }
}

