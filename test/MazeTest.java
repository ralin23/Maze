import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class MazeTest {

    @Test
    public void findAllNeighbors() {
        Maze testMaze = new Maze(3, 3);
        MazeCell[] testGraph = testMaze.getMaze();
        ArrayList<MazeCell> neighborsTest = testMaze.findAllNeighbors(testGraph[0]);
        MazeCell[] neighbors = new MazeCell[neighborsTest.size()];
        for(int i = 0; i < neighborsTest.size(); i++){
            System.out.println(neighborsTest.get(i).getLocationX());
            System.out.println(neighborsTest.get(i).getLocationY());
            neighbors[i] = neighborsTest.get(i);
        }
        MazeCell[] expected = {new MazeCell(0, 1, 1), new MazeCell(1, 0, 3)};
        assertArrayEquals(expected, neighbors);
    }

    @Test
    public void generateMaze() {
        Random r = new Random();
        r.setSeed(20);
        Maze testMaze = new Maze(4,4);
        testMaze.generateMaze(r);
        System.out.println(testMaze);
    }

    @Test
    public void testToString() {
        Maze testMaze = new Maze(4, 4);
        testMaze.addEdge(3, 2);
        testMaze.addEdge(2, 1);
        testMaze.addEdge(9,13);
        String test = testMaze.toString();
        String line1 = "+ +-+-+-+" + System.lineSeparator();
        String line2 = "| |     |" + System.lineSeparator();
        String line3 = "+-+-+-+-+" + System.lineSeparator();
        String line4 = "| | | | |" + System.lineSeparator();
        String line5 = "+-+-+-+-+" + System.lineSeparator();
        String line6 = "| | | | |" + System.lineSeparator();
        String line7 = "+-+ +-+-+" + System.lineSeparator();
        String line8 = "| | | | |" + System.lineSeparator();
        String lastLine = "+-+-+-+ +" + System.lineSeparator();
        String expected = line1 + line2 + line3 + line4 + line5 + line6 + line7 + line8 + lastLine;
        System.out.println(test);
        assertEquals(expected, test);
    }
}