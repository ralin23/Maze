import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
    /* Maze Representation 3x3 Ex:
    + +----     where 0 is entry and 8 is exit
    |0|1|2|     Using an array of adjacency lists, each number is an index of an array A
    + +----     A[index] has a linked list which contains connected paths to that node
    |3 4|5|     Example to left shows A[0] -> 3, A[3] -> 4
    -------     Legend: + corners, | or - walls
    |6|7|8|
    ----+ +
    */
    private MazeCell[] maze;
    private int row;
    private int column;

    /**
     * Creates a maze with certain amount of rows and columns
     * @param row number of rows (x)
     * @param column number of columns (y)
     */
    Maze(int row, int column) {
        this.maze = new MazeCell[row * column];
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                maze[(x * row) + y] = new MazeCell(x, y,(x * row) + y);
            }
        }
        this.row = row;
        this.column = column;
        //this.generateMaze();
    }

    /**
     * Find the north or south neighbors of a specified cell
     * @param cell to find either the north or south neighbors
     * @param direction the direction north or south using enum
     * @return MazeCell to the north or to the south of the specified cell
     */
    private MazeCell xChange(MazeCell cell, MazeCellNeighbor direction) {
        int cellLocationX = cell.getLocationX();
        int cellLocationY = cell.getLocationY();
        int newCellLocationX;
        if(direction == MazeCellNeighbor.NORTH) {
            newCellLocationX = cellLocationX - 1;
        }
        else {
            newCellLocationX = cellLocationX + 1;
        }
        MazeCell newMazeCell = null;
        if(newCellLocationX >= 0 && newCellLocationX < this.row){
            newMazeCell = maze[(newCellLocationX * this.row) + cellLocationY];
        }
        return newMazeCell;
    }

    /**
     * Find the left or right neighbors of a specified cell
     * @param cell to find either the left or right neighbors
     * @param direction the direction left or right using enum
     * @return MazeCell to the left or to the right of the specified cell
     */
    private MazeCell yChange(MazeCell cell, MazeCellNeighbor direction) {
        int cellLocationX = cell.getLocationX();
        int cellLocationY = cell.getLocationY();
        int newCellLocationY;
        if (direction == MazeCellNeighbor.LEFT) {
            newCellLocationY = cellLocationY - 1;
        }
        else {
            newCellLocationY = cellLocationY + 1;
        }
        MazeCell newMazeCell = null;
        if(newCellLocationY >= 0 && newCellLocationY < this.column) {
            newMazeCell = this.maze[(cellLocationX * this.row) + newCellLocationY];
        }
        return newMazeCell;
    }

    /**
     * Get all neighbors of the specified cell
     * @param cell the cell to find the neighbors of
     * @return an ArrayList of MazeCell that are neighbors of the cell
     */
    public ArrayList<MazeCell> findAllNeighbors(MazeCell cell){
        // Eligible neighbors are north, south, left, and right
        ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
        // Find left neighbor if there is a left neighbor
        MazeCell leftNeighbor = this.yChange(cell, MazeCellNeighbor.LEFT);
        if(leftNeighbor != null) {
            neighbors.add(leftNeighbor);
        }
        // Find right neighbor if there is a right neighbor
        MazeCell rightNeighbor = this.yChange(cell, MazeCellNeighbor.RIGHT);
        if(rightNeighbor != null) {
            neighbors.add(rightNeighbor);
        }
        // Find north neighbor if there is one
        MazeCell northNeighbor = this.xChange(cell, MazeCellNeighbor.NORTH);
        if(northNeighbor != null) {
            neighbors.add(northNeighbor);
        }
        // Find south neighbor if there is one
        MazeCell southNeighbor = this.xChange(cell, MazeCellNeighbor.SOUTH);
        if(southNeighbor != null) {
            neighbors.add(southNeighbor);
        }
        return neighbors;
    }

    // not yet tested
    public void generateMaze() {
        Random r = new Random();
        r.setSeed(20);
        Stack<MazeCell> cellStack = new Stack<>();
        int totalCells = row * column;
        MazeCell currentCell = maze[0];
        int visitedCells = 1;
        while(visitedCells < totalCells) {
            ArrayList<MazeCell> possibleNeighbors = findAllNeighbors(currentCell);
            if(possibleNeighbors.size() > 0) {
                MazeCell randomCell = possibleNeighbors.get(r.nextInt(possibleNeighbors.size()));
                // Knocking down wall
                currentCell.addAccessibleCells(randomCell);
                randomCell.addAccessibleCells(currentCell);
                cellStack.push(currentCell);
                currentCell = randomCell;
                visitedCells++;
            }
            else{
                currentCell = cellStack.pop();
            }
        }
    }

    /**
     * Used to get the maze (Used by test case)
     * @return the maze
     */
    public MazeCell[] getMaze() {
        return maze;
    }
}
