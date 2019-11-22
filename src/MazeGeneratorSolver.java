import java.util.*;

/**
 * This class will completely generate a maze from scratch then solve it or will accept o prebuilt maze to solve
 */
public class MazeGeneratorSolver {

    private Maze maze;

    /**
     * Creates a new random maze to solve
     *
     * @param row    the number of rows
     * @param column the number of columns
     * @param r      a random seed
     */
    public MazeGeneratorSolver(int row, int column, Random r) {
        maze = new Maze(row, column);
        maze.generateMaze(r);
    }

    /**
     * Creates MazeGeneratorSolver using prebuilt maze
     *
     * @param maze the maze that needs to be solved
     */
    public MazeGeneratorSolver(Maze maze) {
        this.maze = maze;
    }

    /**
     * Solves the maze using BFS and DFS and prints out the result
     */
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
        MazeGeneratorSolver randomMaze = new MazeGeneratorSolver(4, 4, r);
        randomMaze.solveMaze();
    }
}
