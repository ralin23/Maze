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
    private MazeCell[][] maze;
    private int row;
    private int column;

    Maze(int row, int column) {
        this.maze = new MazeCell[row][column];
        for (int x = 0; x < row; x++){
            for (int y = 0; y < column; y++){
                maze[x][y] = new MazeCell(x, y);
            }
        }
        this.row = row;
        this.column = column;
    }

    public void generateMaze() {
        Stack<MazeCell> cellStack = new Stack<>();
        int totalCells = row * column;
        MazeCell startingCell = maze[0][0];
        int visitedCells = 1;
        while(visitedCells < totalCells) {

        }
    }
}
