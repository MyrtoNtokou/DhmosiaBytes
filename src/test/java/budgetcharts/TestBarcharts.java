package budgetcharts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.GraphicsEnvironment;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import budgetreader.BasicRecord;
import budgetreader.Ministry;

public class TestBarcharts {

    private List<BasicRecord> mockRecords;
    private List<Ministry> mockMinistries;

    @BeforeEach
    void setUp() {
        mockRecords = new ArrayList<>();
        mockRecords.add(new BasicRecord("1,01", "Φόροι", new BigDecimal("10000000000")));
        mockRecords.add(new BasicRecord("2,01", "Μισθοί", new BigDecimal("8000000000")));

        mockMinistries = new ArrayList<>();
        mockMinistries.add(new Ministry(
            1,
            "Υπουργείο Οικονομικών",
            new BigDecimal("10000000000"),
            new BigDecimal("5000000000"),
            new BigDecimal("15000000000")
        ));
    }

    @Test
    void chartEsoda_ShouldDisplayWithoutException() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartEsoda(mockRecords));
    }

    @Test
    void chartExoda_ShouldDisplayWithoutException() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartExoda(mockRecords));
    }

    @Test
    void chartMinistry_ShouldDisplayWithoutException() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartMinistry(mockMinistries));
    }

    @Test
    void chartEsoda_WithEmptyList_ShouldNotFail() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartEsoda(new ArrayList<>()));
    }

    @Test
    void chartExoda_WithEmptyList_ShouldNotFail() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartExoda(new ArrayList<>()));
    }

    @Test
    void chartMinistry_WithEmptyList_ShouldNotFail() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartMinistry(new ArrayList<>()));
    }

    @Test
    void chartEsodaByYear_ValidIndex_ShouldNotFail() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartEsodaByYear(1));
    }

    @Test
    void chartExodaByYear_ValidIndex_ShouldNotFail() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartExodaByYear(1));
    }

    @Test
    void chartMinistryByYear_ValidIndex_ShouldNotFail() {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        assertDoesNotThrow(() -> Barcharts.chartMinistryByYear(1));
    }

    @Test
    void chartEsodaByYear_InvalidIndex_ShouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> Barcharts.chartEsodaByYear(0));
    }

    @Test
    void chartExodaByYear_InvalidIndex_ShouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> Barcharts.chartExodaByYear(999));
    }

    @Test
    void chartMinistryByYear_InvalidIndex_ShouldThrowException() {
        assertThrows(IndexOutOfBoundsException.class,
            () -> Barcharts.chartMinistryByYear(-1));
    }
}
