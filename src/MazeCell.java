import java.util.LinkedList;

public class MazeCell {
    private int cellLocationX;
    private int cellLocationY;
    private LinkedList<MazeCell> adjacentCells;
//    private MazeCell northCell;
//    private MazeCell southCell;
//    private MazeCell eastCell;
//    private MazeCell westCell;

    MazeCell(int cellLocationX, int cellLocationY) {
        this.cellLocationX = cellLocationX;
        this.cellLocationY = cellLocationY;
        adjacentCells = new LinkedList<>();
    }


}
