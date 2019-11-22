public class SolvedMazeData {
    private String maze;
    private String pathData;
    private String pathLength;
    private String visitedCellsCount;

    SolvedMazeData(String maze, String pathData, String pathLength, String visitedCellsCount){
        this.maze = maze;
        this.pathData = pathData;
        this.pathLength = pathLength;
        this.visitedCellsCount = visitedCellsCount;
    }

    public String getMaze() {
        return maze;
    }

    public String getPathData() {
        return pathData;
    }

    public String getPathLength() {
        return pathLength;
    }

    public String getVisitedCellsCount() {
        return visitedCellsCount;
    }
}
