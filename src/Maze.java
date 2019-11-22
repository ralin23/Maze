import java.awt.*;
import java.util.*;
import java.util.List;

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
    private MazeCell[] maze;            // Maze in an array of MazeCell. Each MazeCell has its own LinkedList for traversing to its neighbors
    //private int[][] adjacencyList;      // Adjacency matrix
    private int row;
    private int column;
    private List<MazeCell> path;

    /**
     * Creates a maze with certain amount of rows and columns
     *
     * @param row    number of rows (x)
     * @param column number of columns (y)
     */
    Maze(int row, int column) {
        final int MAX_VERTEX_COUNT = row * column;
        this.maze = new MazeCell[MAX_VERTEX_COUNT];
        //this.adjacencyList = new int[MAX_VERTEX_COUNT][MAX_VERTEX_COUNT];
        this.row = row;
        this.column = column;
        path = new ArrayList<>();
        for (int i = 0; i < MAX_VERTEX_COUNT; i++) {
            for (int j = 0; j < MAX_VERTEX_COUNT; j++) {
                //this.adjacencyList[i][j] = 0;   // Fill adjacency matrix with 0 (no edges)
            }
        }
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                maze[calculateCellNodeID(x, y)] = new MazeCell(x, y, calculateCellNodeID(x, y));
            }
        }
    }

    /**
     * Calculates the nodeID for a MazeCell using x & y as a basis
     *
     * @param cellLocationX location on the x axis (row)
     * @param cellLocationY location on the y axis (column)
     * @return a number that is used to the MazeCell's final location in the MazeCell array
     */
    public int calculateCellNodeID(int cellLocationX, int cellLocationY) {
        return ((cellLocationX * this.column) + cellLocationY);
    }

    /**
     * Find the north or south neighbors of a specified cell
     *
     * @param cell      to find either the north or south neighbors
     * @param direction the direction north or south using enum
     * @return MazeCell to the north or to the south of the specified cell
     */
    private MazeCell findXNeighbor(MazeCell cell, MazeCellNeighbor direction) {
        int cellLocationX = cell.getLocationX();
        int cellLocationY = cell.getLocationY();
        int newCellLocationX;
        if (direction == MazeCellNeighbor.NORTH) {
            newCellLocationX = cellLocationX - 1;
        } else {
            newCellLocationX = cellLocationX + 1;
        }
        MazeCell newMazeCell = null;
        if (newCellLocationX >= 0 && newCellLocationX < this.row) {
            newMazeCell = maze[calculateCellNodeID(newCellLocationX, cellLocationY)];
        }
        return newMazeCell;
    }

    /**
     * Find the left or right neighbors of a specified cell
     *
     * @param cell      to find either the left or right neighbors
     * @param direction the direction left or right using enum
     * @return MazeCell to the left or to the right of the specified cell
     */
    private MazeCell findYNeighbor(MazeCell cell, MazeCellNeighbor direction) {
        int cellLocationX = cell.getLocationX();
        int cellLocationY = cell.getLocationY();
        int newCellLocationY;
        if (direction == MazeCellNeighbor.LEFT) {
            newCellLocationY = cellLocationY - 1;
        } else {
            newCellLocationY = cellLocationY + 1;
        }
        MazeCell newMazeCell = null;
        if (newCellLocationY >= 0 && newCellLocationY < this.column) {
            newMazeCell = this.maze[calculateCellNodeID(cellLocationX, newCellLocationY)];
        }
        return newMazeCell;
    }

    /**
     * Get all neighbors of the specified cell
     *
     * @param cell the cell to find the neighbors of
     * @return an ArrayList of MazeCell that are neighbors of the cell
     */
    public ArrayList<MazeCell> findAllNeighbors(MazeCell cell) {
        // Eligible neighbors are north, south, left, and right
        ArrayList<MazeCell> neighbors = new ArrayList<MazeCell>();
        // Find left neighbor if there is a left neighbor
        MazeCell leftNeighbor = this.findYNeighbor(cell, MazeCellNeighbor.LEFT);
        if (leftNeighbor != null) {
            neighbors.add(leftNeighbor);
        }
        // Find right neighbor if there is a right neighbor
        MazeCell rightNeighbor = this.findYNeighbor(cell, MazeCellNeighbor.RIGHT);
        if (rightNeighbor != null) {
            neighbors.add(rightNeighbor);
        }
        // Find north neighbor if there is one
        MazeCell northNeighbor = this.findXNeighbor(cell, MazeCellNeighbor.NORTH);
        if (northNeighbor != null) {
            neighbors.add(northNeighbor);
        }
        // Find south neighbor if there is one
        MazeCell southNeighbor = this.findXNeighbor(cell, MazeCellNeighbor.SOUTH);
        if (southNeighbor != null) {
            neighbors.add(southNeighbor);
        }
        return neighbors;
    }

    /**
     * Adds an bidirectional edge between two nodes (vertices)
     *
     * @param nodeA first node (vertex) to connect
     * @param nodeB second node (vertex) to connect
     */
    public void addEdge(int nodeA, int nodeB) {
        MazeCell vertexA = maze[nodeA];
        MazeCell vertexB = maze[nodeB];
        vertexA.addAccessibleCells(vertexB);
        vertexB.addAccessibleCells(vertexA);
        //adjacencyList[nodeA][nodeB] = 1;
        //adjacencyList[nodeB][nodeA] = 1;
    }

    /**
     * Creates a perfect maze (only one way through the maze) with provided seed
     *
     * @param r random seed to work with
     */
    public void generateMaze(Random r) {
        Stack<MazeCell> cellStack = new Stack<>();
        int totalCells = row * column;
        MazeCell currentCell = maze[0];
        int visitedCells = 1;
        // While not all cells were visited
        while (visitedCells < totalCells) {
            ArrayList<MazeCell> possibleNeighbors = findAllNeighbors(currentCell);
            // Remove all neighboring cells that is connected to another cell
            ArrayList<MazeCell> neighborsToRemove = new ArrayList<>();
            for (MazeCell cell : possibleNeighbors) {
                if (!cell.getAccessibleCells().isEmpty()) {
                    neighborsToRemove.add(cell);
                }
            }
            possibleNeighbors.removeAll(neighborsToRemove);
            if (possibleNeighbors.size() > 0) {
                MazeCell randomCell = possibleNeighbors.get(r.nextInt(possibleNeighbors.size()));
                // Knocking down wall
                addEdge(currentCell.getNodeID(), randomCell.getNodeID());
                cellStack.push(currentCell);
                currentCell = randomCell;
                visitedCells++;
            } else {
                currentCell = cellStack.pop();
            }
        }
    }

    /**
     * Used to get the maze (Used by test case)
     *
     * @return the maze
     */
    public MazeCell[] getMaze() {
        return maze;
    }

    /**
     * Solve the maze using Depth-First Search.
     * @param maze the maze to solve
     */
    public void DFS(Maze maze)
    {
        clearValues();
        MazeCell[] vertices = maze.getMaze();
        Stack<MazeCell> stack = new Stack<>();
        List<MazeCell> visited = new ArrayList<>();
        stack.push(vertices[0]);
        while(!stack.isEmpty()) {
            MazeCell temp = stack.pop();
            temp.setColor(Color.GRAY);
            visited.add(temp);
            if(temp == vertices[vertices.length - 1])
            {
                break;
            }

            for(MazeCell mc : temp.getAccessibleCells()) {
                if (mc.getColor() == Color.WHITE) {
                    mc.setColor(Color.GRAY);
                    mc.setParent(temp);
                    stack.push(mc);
                }
            }
            temp.setColor(Color.BLACK);

        }
        writeFile(visited);

    }

    /**
     * Solve the maze using Breath-First Search
     * @param maze the maze to solve
     */
    public void BFS(Maze maze)
    {
        clearValues();
        MazeCell[] vertices = maze.getMaze();
        Queue<MazeCell> queue = new LinkedList<MazeCell>();
        List<MazeCell> visited = new ArrayList<>();
        queue.add(vertices[0]);
        while(!queue.isEmpty())
        {
            MazeCell temp = queue.remove();
            temp.setColor(Color.GRAY);
            visited.add(temp);
            if(temp == vertices[vertices.length-1])
            {
                break;
            }

            for(MazeCell mc : temp.getAccessibleCells())
            {
                if (mc.getColor() == Color.WHITE) {
                    mc.setColor(Color.GRAY);
                    mc.setParent(temp);
                    queue.add(mc);
                }
            }
            temp.setColor(Color.BLACK);
        }
        writeFile(visited);

    }

    public void clearValues()
    {
        for(int i = 0; i < maze.length; i ++)
        {
            maze[i].reset();
        }
        path.clear();
    }
    public void writeFile(List<MazeCell> visited)
    {
        for(int i = 0; i < visited.size(); i ++)
        {
            visited.get(i).setVisitNumber(Integer.toString(i));
        }
        //Find path to exit and print coordinates
        MazeCell current = visited.get(visited.size()-1);
        while(current != visited.get(0))
        {
            path.add(current);
            current = current.getParent();
        }
        path.add(visited.get(0));
        String pathCoord = "";
        for(int i = path.size()-1; i >= 0; i --)
        {
            MazeCell temp = path.get(i);
            pathCoord += "(" + temp.getLocationX() + "," + temp.getLocationY() + ") ";
        }
        System.out.println(this);
        System.out.println("Path: " + pathCoord);
        System.out.println("Length of path: " + path.size());
        System.out.println("Visited cells: " + visited.size());
    }
    /**
     * Builds the basic maze with all applicable walls in a String[][] format while passing number of rows and number of columns
     *
     * @return MazeToString object containing a String[][], number of rows, and number of columns
     */
    public MazeToString mazeStringBuild(boolean findShortestPath) {
        int numberOfStringRows = (2 * this.row) + 1;
        int numberOfStringColumns = (2 * this.column) + 1;
        String[][] mazePrint = new String[numberOfStringRows][numberOfStringColumns];
        // Just prints out the basic skeleton of this maze
        // where the space above starting location is empty and
        // the space below the stopping location is empty
        for (int x = 0; x < numberOfStringRows; x++) {
            for (int y = 0; y < numberOfStringColumns; y++) {
                // fill in walls and corners to fully isolate cells
                // spaces where the vertices are located
                if (x % 2 == 0) {
                    if (y % 2 == 0) {
                        mazePrint[x][y] = "+";
                    } else {
                        mazePrint[x][y] = "-";
                    }
                }
                if (x % 2 == 1) {
                    if (y % 2 == 0) {
                        mazePrint[x][y] = "|";
                    } else {
                        mazePrint[x][y] = " ";
                    }
                }
                // starting point exception
                if (x == 0) {
                    if (y == 1) {
                        mazePrint[x][y] = " ";
                    }
                }
                // ending point exception
                else if (x == numberOfStringRows - 1) {
                    if (y == numberOfStringColumns - 2) {
                        mazePrint[x][y] = " ";
                    }
                }
            }
        }
        // Walls that should not exist will be removed one by one & add visit numbers
        int currentNodeID = 0;
        for (int x = 1; x < numberOfStringRows; x += 2) {
            for (int y = 1; y < numberOfStringColumns; y += 2) {
                if(path.contains(maze[currentNodeID]) && findShortestPath)
                {
                    mazePrint[x][y] = "#";
                }
                else if(!findShortestPath){
                    mazePrint[x][y] = maze[currentNodeID].getVisitNumber();
                }
                else{
                    mazePrint[x][y] = " ";
                }
                LinkedList<MazeCell> neighbors = maze[currentNodeID].getAccessibleCells();
                for (MazeCell neighbor : neighbors) {
                    int neighborNodeID = neighbor.getNodeID();
                    // Neighbor to the right
                    if (currentNodeID + 1 == neighborNodeID) {
                        mazePrint[x][y + 1] = " ";
                    }
                    // Neighbor to the south
                    else if (currentNodeID + this.column == neighborNodeID) {
                        mazePrint[x + 1][y] = " ";
                    }
                }
                currentNodeID++;
            }
        }
        return new MazeToString(numberOfStringRows, numberOfStringColumns, mazePrint);
    }

    /**
     * Prints out the maze using data from mazeToString
     *
     * @return a String containing maze data
     */
    public String toString() {
        MazeToString mazePrintData = mazeStringBuild(false);
        MazeToString mazePrintShortestPath = mazeStringBuild(true);
        int numberOfStringRows = mazePrintData.getNumberOfStringRows();
        int numberOfStringColumns = mazePrintData.getNumberOfStringColumns();
        String[][] mazePrint = mazePrintData.getMazePrint();
        // Take a row from 2d array and copy it into a single String line
        // and adding a new line when we reach the end of the row
        StringBuilder fullMazeInLine = new StringBuilder();
        for (int x = 0; x < numberOfStringRows; x++) {
            for (int y = 0; y < numberOfStringColumns; y++) {
                fullMazeInLine.append(mazePrint[x][y]);
            }
            fullMazeInLine.append(System.lineSeparator());
        }
        mazePrint = mazePrintShortestPath.getMazePrint();
        fullMazeInLine.append(System.lineSeparator());
        for (int x = 0; x < numberOfStringRows; x++) {
            for (int y = 0; y < numberOfStringColumns; y++) {
                fullMazeInLine.append(mazePrint[x][y]);
            }
            fullMazeInLine.append(System.lineSeparator());
        }
        return fullMazeInLine.toString();
    }
}
