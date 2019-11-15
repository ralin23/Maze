import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MazeFileReaderTest {

    @Test
    public void mazeFileReader() {
        MazeFileReader readFile = new MazeFileReader();
        Maze actualMaze = readFile.mazeFileReader(new File("maze4.txt"));
        Maze expectedMaze = new Maze(4,4);
        // Manually adding edges using data from maze4.txt
        expectedMaze.addEdge(0,1);
        expectedMaze.addEdge(1,5);
        expectedMaze.addEdge(5,6);
        expectedMaze.addEdge(6,7);
        expectedMaze.addEdge(3,7);
        expectedMaze.addEdge(2,3);
        expectedMaze.addEdge(7,11);
        expectedMaze.addEdge(10,11);
        expectedMaze.addEdge(9,10);
        expectedMaze.addEdge(8,9);
        expectedMaze.addEdge(4,8);
        expectedMaze.addEdge(8,12);
        expectedMaze.addEdge(12,13);
        expectedMaze.addEdge(13,14);
        expectedMaze.addEdge(14,15);
        System.out.println("Expected Maze: ");
        System.out.println(expectedMaze);
        System.out.println("Actual Maze: ");
        System.out.println(actualMaze);
        assertEquals(expectedMaze.toString(), actualMaze.toString());
    }
}