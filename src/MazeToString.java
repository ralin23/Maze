public class MazeToString {

    private int numberOfStringRows;
    private int numberOfStringColumns;
    private String[][] mazePrint;

    MazeToString(int numberOfStringRows, int numberOfStringColumns, String[][] mazePrint) {
        this.numberOfStringRows = numberOfStringRows;
        this.numberOfStringColumns = numberOfStringColumns;
        this.mazePrint = mazePrint;
    }

    public int getNumberOfStringColumns() {
        return numberOfStringColumns;
    }

    public int getNumberOfStringRows() {
        return numberOfStringRows;
    }

    public String[][] getMazePrint() {
        return mazePrint;
    }
}
