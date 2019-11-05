import java.util.Stack;

public class Maze {
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
