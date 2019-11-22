import java.util.*;
import java.awt.Color;

/**
 * This class will completely generate a maze from scratch then solve it
 */
public class MazeGeneratorSolver {

    private Maze maze;

    public MazeGeneratorSolver(int row, int column, Random r) {
        maze = new Maze(row, column);
        maze.generateMaze(r);
    }

    public void solveMaze() {
        maze.BFS(maze);
        //maze.findShortestPath(maze);
        maze.DFS(maze);
    }

    public static void main(String[] args) {

    }
}
