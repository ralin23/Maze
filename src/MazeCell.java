import java.util.LinkedList;

public class MazeCell {
    private int cellLocationX;
    private int cellLocationY;
    private int nodeID;
    private LinkedList<MazeCell> accessibleCells;
//    private MazeCell northCell;
//    private MazeCell southCell;
//    private MazeCell eastCell;
//    private MazeCell westCell;

    MazeCell(int cellLocationX, int cellLocationY, int nodeID) {
        this.cellLocationX = cellLocationX;
        this.cellLocationY = cellLocationY;
        this.nodeID = nodeID;
        accessibleCells = new LinkedList<>();
    }

    public int getLocationX(){
        return cellLocationX;
    }

    public int getLocationY(){
        return cellLocationY;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void addAccessibleCells(MazeCell neighbor){
        accessibleCells.add(neighbor);
    }

    public boolean equals(Object o){
        MazeCell b = (MazeCell)o;
        return ((this.cellLocationY == b.getLocationY()) && (this.cellLocationX == b.getLocationX()) && (this.nodeID == b.nodeID));
    }
}
