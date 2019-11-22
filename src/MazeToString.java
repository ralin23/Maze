/**
 * This class contains the maze as a 2D string and other important variables
 */
public class MazeToString {

    private int numberOfStringRows;
    private int numberOfStringColumns;
    private String[][] mazePrint;

    /**
     * Creates an object which contains various data for the maze
     *
     * @param numberOfStringRows    number of rows
     * @param numberOfStringColumns number of columns
     * @param mazePrint             2D String holding the format of the maze
     */
    MazeToString(int numberOfStringRows, int numberOfStringColumns, String[][] mazePrint) {
        this.numberOfStringRows = numberOfStringRows;
        this.numberOfStringColumns = numberOfStringColumns;
        this.mazePrint = mazePrint;
    }

    /**
     * Gets the number of columns for this maze in a 2D String format
     *
     * @return number of columns in the 2D String maze
     */
    public int getNumberOfStringColumns() {
        return numberOfStringColumns;
    }

    /**
     * Gets the number of rows for this maze in a 2D String format
     *
     * @return the number of rows in the 2D String maze
     */
    public int getNumberOfStringRows() {
        return numberOfStringRows;
    }

    /**
     * Gets the maze in a 2D String format
     *
     * @return the number of rows in the 2D String maze
     */
    public String[][] getMazePrint() {
        return mazePrint;
    }
}
