package dhmosiabytes;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import budgetreader.ReadBudget;

public class TestGraphs {

    @Test
    public void testChooseGraphValid() {
        Scanner sc = new Scanner("3\n");
        Graphs g = new Graphs();

        int result = g.chooseGraph(sc);

        assertEquals(3, result);
    }

    @Test
    public void testChooseGraphInvalidThenValid() {
        Scanner sc = new Scanner("9\n2\n");
        Graphs g = new Graphs();

        int result = g.chooseGraph(sc);

        assertEquals(2, result);
    }

    @Test
    public void testChooseGraphNonInteger() {
        Scanner sc = new Scanner("abc\n4\n");
        Graphs g = new Graphs();

        int result = g.chooseGraph(sc);

        assertEquals(4, result);
    }

    @Test
    public void testRunGraphsCallsCorrectCharts() {

        try (MockedStatic<MoreCharts> mc = mockStatic(MoreCharts.class);
             MockedStatic<Barcharts> bc = mockStatic(Barcharts.class);
             MockedStatic<ReadBudget> rb = mockStatic(ReadBudget.class)) {

            // Return EMPTY lists – no mocking Eggrafi/Ypourgeio needed
            rb.when(() -> ReadBudget.readGeneralBudget(anyString()))
                    .thenReturn(List.of());
            rb.when(() -> ReadBudget.readByMinistry(anyString()))
                    .thenReturn(List.of());

            Scanner sc = new Scanner("1\n0\n");

            Graphs g = new Graphs();
            g.runGraphs(sc);

            mc.verify(() -> MoreCharts.pieChartEsodaExoda(anyList()), times(1));
        }
    }

    @Test
    public void testRunGraphsMinistryChart() {

        try (MockedStatic<MoreCharts> mc = mockStatic(MoreCharts.class);
             MockedStatic<Barcharts> bc = mockStatic(Barcharts.class);
             MockedStatic<ReadBudget> rb = mockStatic(ReadBudget.class)) {

            rb.when(() -> ReadBudget.readGeneralBudget(anyString()))
                    .thenReturn(List.of());
            rb.when(() -> ReadBudget.readByMinistry(anyString()))
                    .thenReturn(List.of());

            Scanner sc = new Scanner("5\n0\n");

            Graphs g = new Graphs();
            g.runGraphs(sc);

            bc.verify(() -> Barcharts.chartMinistry(anyList()), times(1));
        }
    }
}