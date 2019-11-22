/**
 * This class holds the solved maze data which is ready to be printed.
 */
public class SolvedMazeData {
    private String maze;
    private String pathData;
    private String pathLength;
    private String visitedCellsCount;

    /**
     * Creates a class to hold the ready to be printed solved maze data
     * @param maze the maze in one continuous String
     * @param pathData the path through the maze in String format
     * @param pathLength the length of the path in String format
     * @param visitedCellsCount number of visited cells in String format
     */
    SolvedMazeData(String maze, String pathData, String pathLength, String visitedCellsCount){
        this.maze = maze;
        this.pathData = pathData;
        this.pathLength = pathLength;
        this.visitedCellsCount = visitedCellsCount;
    }

    /**
     * Gets the maze as a String
     * @return the maze as a String
     */
    public String getMaze() {
        return maze;
    }

    /**
     * Gets the path data as a String
     * @return the path data as a String
     */
    public String getPathData() {
        return pathData;
    }

    /**
     * Gets the path's length as a String
     * @return the path's length as a String
     */
    public String getPathLength() {
        return pathLength;
    }

    /**
     * Gets the number of cells visited as a String
     * @return the number of cells visited as a String
     */
    public String getVisitedCellsCount() {
        return visitedCellsCount;
    }
}
