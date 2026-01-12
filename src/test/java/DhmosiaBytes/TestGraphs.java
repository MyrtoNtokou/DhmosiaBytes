package dhmosiabytes;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class TestGraphs {

    @Test
    void testChooseGraphValidInput() {

        String inputData = "2\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        Graphs graphs = new Graphs();
        int selected = graphs.chooseGraph(scanner);

        assertEquals(2, selected);
    }

    @Test
    void testChooseGraphZeroInput() {

        String inputData = "0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        Graphs graphs = new Graphs();
        int selected = graphs.chooseGraph(scanner);

        assertEquals(0, selected);
    }

    @Test
    void testChooseGraphInvalidThenValidInput() {

        String inputData = "abc\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        Graphs graphs = new Graphs();
        int selected = graphs.chooseGraph(scanner);

        assertEquals(5, selected);
    }

    @Test
    void testChooseGraphOutOfRangeThenValid() {

        String inputData = "9\n3\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(inputData.getBytes()));

        Graphs graphs = new Graphs();
        int selected = graphs.chooseGraph(scanner);

        assertEquals(3, selected);
    }
}
