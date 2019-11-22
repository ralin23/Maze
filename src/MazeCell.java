import java.util.LinkedList;
import java.awt.Color;

/**
 * This class holds the data for each cell in the maze
 */
public class MazeCell {

    private int cellLocationX;
    private int cellLocationY;
    private int nodeID;
    private LinkedList<MazeCell> accessibleCells;
    private Color color;
    private MazeCell parent;
    private String visitNumber;

    /**
     * Creates a cell for the maze with the required data
     *
     * @param cellLocationX location of cell on x axis (row)
     * @param cellLocationY location of cell on y axis (column)
     * @param nodeID        unique number that corresponds to x and y
     */
    MazeCell(int cellLocationX, int cellLocationY, int nodeID) {
        this.cellLocationX = cellLocationX;
        this.cellLocationY = cellLocationY;
        this.nodeID = nodeID;
        accessibleCells = new LinkedList<>();
        color = Color.WHITE;
        visitNumber = " ";
        parent = null;
    }

    /**
     * Gets the color of the cell
     *
     * @return the color of the cell
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the cell
     *
     * @param col new color to set
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Gets the cell's parent
     *
     * @return the cell's parent
     */
    public MazeCell getParent() {
        return parent;
    }

    /**
     * Sets the cell's parent
     *
     * @param par the cell's parent to set
     */
    public void setParent(MazeCell par) {
        parent = par;
    }

    /**
     * Gets the visit number of the cell
     * If the cell's visit number is greater than 9, we only transmit the last digit of the number
     *
     * @return the cell's visit number with the above restriction
     */
    public String getVisitNumber() {
        if (visitNumber.length() >= 1) {
            visitNumber = visitNumber.substring(visitNumber.length() - 1);
        }
        return visitNumber;
    }

    /**
     * Sets the celll's visit number
     *
     * @param x the cell's visit number to set
     */
    public void setVisitNumber(String x) {
        visitNumber = x;
    }

    /**
     * Resets the cell's data to its default
     */
    public void reset() {
        visitNumber = " ";
        parent = null;
        color = Color.WHITE;
    }

    /**
     * Gets the cells x value on the x-axis
     *
     * @return the cell's x value on the x-axis
     */
    public int getLocationX() {
        return cellLocationX;
    }

    /**
     * Gets the cells y value on the y-axis
     *
     * @return the cell's y-value on the y-axis
     */
    public int getLocationY() {
        return cellLocationY;
    }

    /**
     * Gets the cell's nodeID number
     *
     * @return the cell's nodeID number
     */
    public int getNodeID() {
        return nodeID;
    }

    /**
     * Gets the list of MazeCells accessible from this cell
     *
     * @return the list of MazeCells accessible from this cell
     */
    public LinkedList<MazeCell> getAccessibleCells() {
        return accessibleCells;
    }

    /**
     * Adds a MazeCell to make it accessible from this cell
     *
     * @param neighbor the MazeCell to add to the list of accessible cells
     */
    public void addAccessibleCells(MazeCell neighbor) {
        accessibleCells.add(neighbor);
    }

    /**
     * Checks if two MazeCells are the same
     *
     * @param o the MazeCell to check against
     * @return true if the two cells are the same, false if not
     */
    public boolean equals(Object o) {
        MazeCell b = (MazeCell) o;
        return ((this.cellLocationY == b.getLocationY()) && (this.cellLocationX == b.getLocationX()) && (this.nodeID == b.nodeID));
    }
}
