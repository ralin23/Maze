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
        SolvedMazeData solvedBFS = maze.BFS(maze);
        SolvedMazeData solvedDFS = maze.DFS(maze);
        System.out.println("BFS: ");
        System.out.println(solvedBFS.getMaze());
        System.out.println(solvedBFS.getPathData());
        System.out.println(solvedBFS.getPathLength());
        System.out.println(solvedBFS.getVisitedCellsCount());
        System.out.println();
        System.out.println("DFS: ");
        System.out.println(solvedDFS.getMaze());
        System.out.println(solvedDFS.getPathData());
        System.out.println(solvedDFS.getPathLength());
        System.out.println(solvedDFS.getVisitedCellsCount());
    }

    public static void main(String[] args) {
        Random r = new Random();
        r.setSeed(100);
        MazeGeneratorSolver randomMaze = new MazeGeneratorSolver(4,4, r);
        randomMaze.solveMaze();
    }
}
