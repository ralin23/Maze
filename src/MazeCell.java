import java.util.LinkedList;
import java.awt.Color;

public class MazeCell {
    public enum Colors{
        WHITE,GRAY,BLACK;
    }
    private int cellLocationX;
    private int cellLocationY;
    private int nodeID;
    private LinkedList<MazeCell> accessibleCells;
    public Color color = Color.WHITE;

    MazeCell(int cellLocationX, int cellLocationY, int nodeID) {
        this.cellLocationX = cellLocationX;
        this.cellLocationY = cellLocationY;
        this.nodeID = nodeID;
        accessibleCells = new LinkedList<>();

    }

    public int getLocationX() {
        return cellLocationX;
    }

    public int getLocationY() {
        return cellLocationY;
    }

    public int getNodeID() {
        return nodeID;
    }

    public LinkedList<MazeCell> getAccessibleCells() {
        return accessibleCells;
    }

    public void addAccessibleCells(MazeCell neighbor) {
        accessibleCells.add(neighbor);
    }

    public boolean equals(Object o) {
        MazeCell b = (MazeCell) o;
        return ((this.cellLocationY == b.getLocationY()) && (this.cellLocationX == b.getLocationX()) && (this.nodeID == b.nodeID));
    }
}
