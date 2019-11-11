import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {
    /* Maze Representation 3x3 Ex:
    + +-+-+     where 0 is entry and 8 is exit
    |0|1|2|     Using an array of adjacency lists, each number is an index of an array A
    + +-+-+     A[index] has a linked list which contains connected paths to that node
    |3 4|5|     Example to left shows A[0] -> 3, A[3] -> 4
    +-+-+-+     Legend: + corners, | or - walls
    |6|7|8|
    +-+-+ +
    */
    private MazeCell[] maze;
    private int[][] adjacencyList;      // Adjacency matrix
    private int row;
    private int column;

    /**
     * Creates a maze with certain amount of rows and columns
     * @param row number of rows (x)
     * @param column number of columns (y)
     */
    Maze(int row, int column) {
        final int MAX_VERTEX_COUNT = row * column;
        this.maze = new MazeCell[MAX_VERTEX_COUNT];
        this.adjacencyList = new int[MAX_VERTEX_COUNT][MAX_VERTEX_COUNT];
        this.row = row;
        this.column = column;
        for(int i = 0; i < MAX_VERTEX_COUNT; i++) {
            for(int j = 0; j < MAX_VERTEX_COUNT; j++) {
                this.adjacencyList[i][j] = 0;   // Fill adjacency matrix with 0 (no edges)
            }
        }
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                maze[(x * row) + y] = new MazeCell(x, y,(x * row) + y);
            }
        }
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

    /**
     * Adds an bidirectional edge between two nodes (vertices)
     * @param nodeA first node (vertex) to connect
     * @param nodeB second node (vertex) to connect
     */
    public void addEdge(int nodeA, int nodeB) {
        MazeCell vertexA = maze[nodeA];
        MazeCell vertexB = maze[nodeB];
        vertexA.addAccessibleCells(vertexB);
        vertexB.addAccessibleCells(vertexA);
        adjacencyList[nodeA][nodeB] = 1;
        adjacencyList[nodeB][nodeA] = 1;
    }

    // not yet tested
    public void generateMaze() {
        Random r = new Random();
        r.setSeed(20);
        Stack<MazeCell> cellStack = new Stack<>();
        int totalCells = row * column;
        MazeCell currentCell = maze[0];
        int visitedCells = 1;
        // While not all cells were visited
        while(visitedCells < totalCells) {
            ArrayList<MazeCell> possibleNeighbors = findAllNeighbors(currentCell);
            // Remove all neighboring cells that is connected to another cell
            ArrayList<MazeCell> neighborsToRemove = new ArrayList<>();
            for(MazeCell cell: possibleNeighbors) {
                if(!cell.getAccessibleCells().isEmpty()) {
                    neighborsToRemove.add(cell);
                }
            }
            possibleNeighbors.removeAll(neighborsToRemove);
            if(possibleNeighbors.size() > 0) {
                MazeCell randomCell = possibleNeighbors.get(r.nextInt(possibleNeighbors.size()));
                // Knocking down wall
                addEdge(currentCell.getNodeID(), randomCell.getNodeID());
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

    public String toString() {
        int numberOfStringRows = (2* this.row) + 1;
        int numberOfStringColumns = (2 * this.column) + 1;
        String[][] mazePrint = new String[numberOfStringRows][numberOfStringColumns];
        for(int x = 0; x < numberOfStringRows; x++) {
            for(int y = 0; y < numberOfStringColumns; y++) {
                if(x % 2 == 0) {
                    if(y % 2 == 0){
                        mazePrint[x][y] = "+";
                    }
                    else {
                        mazePrint[x][y] = "-";
                    }
                }
                if(x % 2 == 1) {
                    if(y % 2 == 0){
                        mazePrint[x][y] = "|";
                    }
                    else {
                        mazePrint[x][y] = " ";
                    }
                }
                if(x == 0) {
                    if(y == 1) {
                        mazePrint[x][y] = " ";
                    }
                }
                else if(x == numberOfStringRows - 1) {
                    if(y == numberOfStringColumns - 2) {
                        mazePrint[x][y] = " ";
                    }
                }
            }
        }
        String fullMazeInLine = "";
        for(int x = 0; x < numberOfStringRows; x++){
            for(int y = 0; y < numberOfStringColumns; y++) {
                fullMazeInLine = fullMazeInLine + mazePrint[x][y];
            }
            fullMazeInLine = fullMazeInLine + System.lineSeparator();
        }
        return fullMazeInLine;
    }
}
