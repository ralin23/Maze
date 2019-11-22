import java.util.LinkedList;
import java.awt.Color;

public class MazeCell {

    private int cellLocationX;
    private int cellLocationY;
    private int nodeID;
    private LinkedList<MazeCell> accessibleCells;
    private Color color;
    private MazeCell parent;
    private String visitNumber;

    MazeCell(int cellLocationX, int cellLocationY, int nodeID) {
        this.cellLocationX = cellLocationX;
        this.cellLocationY = cellLocationY;
        this.nodeID = nodeID;
        accessibleCells = new LinkedList<>();
        color = Color.WHITE;
        visitNumber = " ";
        parent = null;
    }

    public Color getColor(){
        return color;
    }
    public void setColor(Color col){
        color = col;
    }

    public MazeCell getParent(){
        return parent;
    }
    public void setParent(MazeCell par){
        parent = par;
    }
    public String getVisitNumber() {
        if(visitNumber.length() >= 1) {
            visitNumber = visitNumber.substring(visitNumber.length() - 1);
        }
        return visitNumber;
    }

    public void setVisitNumber(String x) {
        visitNumber = x;
    }

    public void reset()
    {
        visitNumber = " ";
        parent = null;
        color = Color.WHITE;
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
