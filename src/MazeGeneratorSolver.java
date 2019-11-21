import java.util.*;
import java.awt.Color;

/**
 * This class will completely generate a maze from scratch then solve it
 */
public class MazeGeneratorSolver {

    private Maze maze;

    public MazeGeneratorSolver(int row, int column, Random r) {
        maze = new Maze(row, column);
        maze.generateMaze(r);
    }

    public void solveMaze() {
        maze.BFS(maze);
    }

    public void DFS(Maze maze)
    {
        MazeCell[] vertices = maze.getMaze();
        Stack<MazeCell> stack = new Stack<>();
        List<MazeCell> visited = new ArrayList<>();
        stack.push(vertices[0]);
        //int pos = 0;
        while(!stack.isEmpty()) {
            MazeCell temp = stack.pop();
            if(temp == vertices[vertices.length - 1])
            {
                break;
            }
            if (temp.color == Color.WHITE) {
                temp.color = Color.GRAY;
                //temp.time = pos;
                visited.add(temp);
                //pos++;
                for(MazeCell mc : temp.getAccessibleCells()) {
                    if (mc.color == Color.WHITE) {
                        mc.color = Color.GRAY;
                        stack.push(mc);
                    }
                }
                temp.color = Color.BLACK;
            }
        }
        for(MazeCell cell : visited)
        {
            System.out.println(cell.getNodeID());
        }
    }

    public void BFS(Maze maze)
    {
        MazeCell[] vertices = maze.getMaze();
        Queue<MazeCell> queue = new LinkedList<MazeCell>();
        queue.add(vertices[0]);
        int pos = 0;
        while(!queue.isEmpty())
        {
            MazeCell temp = queue.remove();
            if(temp == vertices[vertices.length-1])
            {
                break;
            }
            if(temp.color == Color.WHITE)
            {
                //temp.time = pos;
                pos ++;
                for(MazeCell mc : temp.getAccessibleCells())
                {
                    if(mc.color == Color.WHITE)
                    {
                        mc.color = Color.GRAY;
                        queue.add(mc);
                    }
                }
                temp.color = Color.BLACK;
            }
        }
    }

    public static void main(String[] args) {

    }
}
