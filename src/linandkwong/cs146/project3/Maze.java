package linandkwong.cs146.project3;

import java.awt.Color;
import java.util.*;

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
        this.row = row;
        this.column = column;
        this.path = new ArrayList<>();
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
                // Knocking down wall with randomly selected neighbor
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
     * Used to get the maze (Used by test cases)
     *
     * @return the maze
     */
    public MazeCell[] getMaze() {
        return maze;
    }

    /**
     * Solve the maze using Depth-First Search.
     *
     * @param maze the maze to solve
     * @return an object containing Strings ready to be printed
     */
    public SolvedMazeData DFS(Maze maze) {
        clearValues();
        MazeCell[] vertices = maze.getMaze();
        Stack<MazeCell> stack = new Stack<>();
        List<MazeCell> visited = new ArrayList<>();
        stack.push(vertices[0]);
        while (!stack.isEmpty()) {
            MazeCell temp = stack.pop();
            temp.setColor(Color.GRAY);
            visited.add(temp);
            if (temp == vertices[vertices.length - 1]) {
                break;
            }

            for (MazeCell mc : temp.getAccessibleCells()) {
                if (mc.getColor() == Color.WHITE) {
                    mc.setColor(Color.GRAY);
                    mc.setParent(temp);
                    stack.push(mc);
                }
            }
            temp.setColor(Color.BLACK);
        }
        return prepDataForFileWrite(visited);
    }

    /**
     * Solve the maze using Breath-First Search
     *
     * @param maze the maze to solve
     * @return an object containing Strings ready to be printed
     */
    public SolvedMazeData BFS(Maze maze) {
        clearValues();
        MazeCell[] vertices = maze.getMaze();
        Queue<MazeCell> queue = new LinkedList<MazeCell>();
        List<MazeCell> visited = new ArrayList<>();
        queue.add(vertices[0]);
        while (!queue.isEmpty()) {
            MazeCell temp = queue.remove();
            temp.setColor(Color.GRAY);
            visited.add(temp);
            if (temp == vertices[vertices.length - 1]) {
                break;
            }

            for (MazeCell mc : temp.getAccessibleCells()) {
                if (mc.getColor() == Color.WHITE) {
                    mc.setColor(Color.GRAY);
                    mc.setParent(temp);
                    queue.add(mc);
                }
            }
            temp.setColor(Color.BLACK);
        }
        return prepDataForFileWrite(visited);
    }

    /**
     * Reset the variables that BFS and DFS uses in Maze and MazeCell to their defaults
     */
    public void clearValues() {
        for (int i = 0; i < maze.length; i++) {
            maze[i].reset();
        }
        path.clear();
    }

    /**
     * Prepares the solver's data to be written into a file
     *
     * @param visited a list of MazeCells visited by either DFS or BFS
     * @return an object containing Strings ready to be printed
     */
    public SolvedMazeData prepDataForFileWrite(List<MazeCell> visited) {
        for (int i = 0; i < visited.size(); i++) {
            visited.get(i).setVisitNumber(Integer.toString(i));
        }
        //Find path to exit and get coordinates
        MazeCell current = visited.get(visited.size() - 1);
        while (current != visited.get(0)) {
            path.add(current);
            current = current.getParent();
        }
        path.add(visited.get(0));
        // Convert path data to a string of maze cell coordinates
        StringBuilder pathCord = new StringBuilder();
        for (int i = path.size() - 1; i >= 0; i--) {
            MazeCell temp = path.get(i);
            pathCord.append("(").append(temp.getLocationX()).append(",").append(temp.getLocationY()).append(") ");
        }
        String maze = this.toString();
        String pathData = "Path: " + pathCord;
        String pathLength = "Length of path: " + path.size();
        String visitedCellsCount = "Visited cells: " + visited.size();
        return new SolvedMazeData(maze, pathData, pathLength, visitedCellsCount);
    }

    /**
     * Builds the basic maze with all applicable walls in a String[][] format while passing number of rows and number of columns
     *
     * @param findShortestPath mode that determines how the cells data will be outputted. Pass "true" to mark shortest path with '#', otherwise pass false to see how either DFS or BFS moved throughout the maze
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
        // Walls that should not exist will be removed one by one.
        // The cell space will show visited number if findShortestPath is false,
        // otherwise mark the shortest path with '#'.
        int currentNodeID = 0;
        for (int x = 1; x < numberOfStringRows; x += 2) {
            for (int y = 1; y < numberOfStringColumns; y += 2) {
                LinkedList<MazeCell> neighbors = maze[currentNodeID].getAccessibleCells();
                // Now going to remove the walls that should not exist
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
                // Time to print either '#' or visit number
                if (path.contains(maze[currentNodeID]) && findShortestPath) {
                    // If we want to find the shortest path and this maze cell is on the path print '#'
                    mazePrint[x][y] = "#";
                    if (y + 1 != numberOfStringColumns - 1) {
                        // If maze cell to the right of the current maze cell is on the path, print a '#' immediately right of this cell
                        if (path.contains(maze[currentNodeID + 1]) && maze[currentNodeID].getAccessibleCells().contains(maze[currentNodeID + 1])) {
                            mazePrint[x][y + 1] = "#";
                        }
                    }
                    if (x + 1 != numberOfStringRows - 1) {
                        // If maze cell south of the current maze cell is on the path, print a '#' immediately south of the cell
                        if (path.contains(maze[currentNodeID + column]) && maze[currentNodeID].getAccessibleCells().contains(maze[currentNodeID + this.row])) {
                            mazePrint[x + 1][y] = "#";
                        }
                    }
                } else if (!findShortestPath) {
                    // If we are not finding the shortest path, print the visit number
                    mazePrint[x][y] = maze[currentNodeID].getVisitNumber();
                } else {
                    // The catch all for all other situations
                    // Ex. trying to find the shortest path, current maze cell is not on path, and current
                    mazePrint[x][y] = " ";
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
        // Parse the maze data that is not find the shortest path
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
        // Parse the maze data that is find the shortest path
        fullMazeInLine.append(System.lineSeparator());
        mazePrint = mazePrintShortestPath.getMazePrint();
        // Take a row from 2d array and copy it into a single String line
        // and adding a new line when we reach the end of the row
        for (int x = 0; x < numberOfStringRows; x++) {
            for (int y = 0; y < numberOfStringColumns; y++) {
                fullMazeInLine.append(mazePrint[x][y]);
            }
            fullMazeInLine.append(System.lineSeparator());
        }
        return fullMazeInLine.toString();
    }
}
