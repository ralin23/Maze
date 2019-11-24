package linandkwong.cs146.project3;

import java.io.*;

public class MazeFileReader {

    /**
     * Reads and formats a maze from a file reading "|" and "-" as walls between cells
     *
     * @param file to read a properly formatted maze from.
     * @return a maze with all the proper walls removed.
     * @throws FileNotFoundException thrown when a file could not be found
     * @throws IOException           thrown when an error in file reading occurs
     */
    public Maze mazeFileReader(File file) throws FileNotFoundException, IOException {
        BufferedReader br;
        String[][] mazeRead = new String[0][0];
        int row = 0;
        int column = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            // Now reading the first line which should contain number of rows and number of columns in that order
            // Ex: 4 4
            String line = br.readLine();
            String[] firstLineArray = line.split(" ");
            int[] firstLineNumbers = new int[2];
            for (int i = 0; i < firstLineArray.length; i++) {
                firstLineNumbers[i] = Integer.parseInt(firstLineArray[i]);
            }
            row = firstLineNumbers[0];
            column = firstLineNumbers[1];
            // Now time to read the actual maze
            mazeRead = new String[(2 * row) + 1][(2 * column) + 1];
            line = br.readLine();
            for (int i = 0; i < mazeRead.length; i++) {
                for (int j = 0; j < mazeRead[i].length; j++) {
                    if (line != null) {
                        String[] lineArray = line.split("");
                        mazeRead[i][j] = lineArray[j];
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file does not exist at expected location: " + System.lineSeparator() + file.getAbsolutePath());
        } catch (IOException e) {
            throw new IOException("Something went wrong while trying to read the following file: " + System.lineSeparator() + file.getAbsolutePath() + System.lineSeparator() + "Please try again.");
        }
        Maze maze = new Maze(row, column);
        int cellNodeID = 0;
        for (int i = 1; i < mazeRead.length; i += 2) {
            for (int j = 1; j < mazeRead[i].length; j += 2) {
                // If space to the right of current cell is a space, add an edge in the maze
                if (mazeRead[i][j + 1].equals(" ")) {
                    maze.addEdge(cellNodeID, cellNodeID + 1);
                }
                // If the space south of current cell is a space, add an edge in the maze
                // Exclude last node from this line (ending point of the maze)
                if (mazeRead[i + 1][j].equals(" ") && !(i == mazeRead.length - 2 && j == mazeRead[i].length - 2)) {
                    maze.addEdge(cellNodeID, cellNodeID + column);
                }
                cellNodeID++;
            }
        }
        return maze;
    }

    public static void main(String[] args) {
        String path = "sampleInputs/";
        MazeFileReader readMazeFile = new MazeFileReader();
        try {
            MazeGeneratorSolver maze4 = new MazeGeneratorSolver(readMazeFile.mazeFileReader(new File(path + "maze4.txt")));
            System.out.println("Maze 4:");
            maze4.solveMaze("data/maze4solve.txt");
            System.out.println();
            MazeGeneratorSolver maze6 = new MazeGeneratorSolver(readMazeFile.mazeFileReader(new File(path + "maze6.txt")));
            System.out.println("Maze 6:");
            maze6.solveMaze("data/maze6solve.txt");
            System.out.println();
            MazeGeneratorSolver maze8 = new MazeGeneratorSolver(readMazeFile.mazeFileReader(new File(path + "maze8.txt")));
            System.out.println("Maze 8:");
            maze8.solveMaze("data/maze8solve.txt");
            System.out.println();
            MazeGeneratorSolver maze10 = new MazeGeneratorSolver(readMazeFile.mazeFileReader(new File(path + "maze10.txt")));
            System.out.println("Maze 10:");
            maze10.solveMaze("data/maze10solve.txt");
            System.out.println();
            MazeGeneratorSolver maze20 = new MazeGeneratorSolver(readMazeFile.mazeFileReader(new File(path + "maze20.txt")));
            System.out.println("Maze 20:");
            maze20.solveMaze("data/maze20solve.txt");
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
