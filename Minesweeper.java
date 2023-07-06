import java.util.Scanner;

public class Minesweeper {
    private int rows;
    private int columns;
    private int numMines;
    private int[][] board;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private boolean gameWon;
    private boolean gameOver;

    public Minesweeper(int rows, int columns, int numMines) {
        this.rows = rows;
        this.columns = columns;
        this.numMines = numMines;
        this.board = new int[rows][columns];
        this.revealed = new boolean[rows][columns];
        this.flagged = new boolean[rows][columns];
        this.gameWon = false;
        this.gameOver = false;
    }
    public void initialize() {
        // Check for valid board size
        if (rows <= 0 || columns <= 0 || rows > 20 || columns > 20) {
            System.out.println("Invalid board size! The number of rows and columns should be between 1 and 20.");
            return;
        }

        // Check for valid number of mines
        if (numMines >= rows * columns) {
            System.out.println("Invalid number of mines! The number of mines should be less than the total number of cells.");
            return;
        }

        // Initialize the board with empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = 0;
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }

        // Randomly place mines on the board
        int count = 0;
        while (count < numMines) {
            int randRow = (int) (Math.random() * rows);
            int randCol = (int) (Math.random() * columns);
            if (board[randRow][randCol] != -1) {
                board[randRow][randCol] = -1;
                count++;
            }
        }

        // Calculate the number of adjacent mines for each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == -1)
                    continue;

                int numAdjacentMines = 0;
                for (int r = -1; r <= 1; r++) {
                    for (int c = -1; c <= 1; c++) {
                        if (r == 0 && c == 0)
                            continue;

                        int newRow = i + r;
                        int newCol = j + c;
                        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
                            if (board[newRow][newCol] == -1)
                                numAdjacentMines++;
                        }
                    }
                }

                board[i][j] = numAdjacentMines;
            }
        }
    }

    public void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns) {
            System.out.println("Invalid cell!");
            return;
        }

        if (revealed[row][col]) {
            System.out.println("Cell already revealed!");
            return;
        }

        if (flagged[row][col]) {
            System.out.println("Cell is flagged! Unflag the cell before revealing.");
            return;
        }

        if (gameOver) {
            System.out.println("Game Over! Please start a new game.");
            return;
        }

        revealed[row][col] = true;

        if (board[row][col] == -1) {
            gameOver = true;
            revealAllMines();
            System.out.println("Oops! You hit a mine! Game Over!");
        } else if (board[row][col] == 0) {
            // Reveal neighboring cells recursively if they are empty
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (r == 0 && c == 0)
                        continue;

                    int newRow = row + r;
                    int newCol = col + c;
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns && !revealed[newRow][newCol]) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        }

        checkGameStatus();
    }

    public void flagCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns) {
            System.out.println("Invalid cell!");
            return;
        }

        if (revealed[row][col]) {
            System.out.println("Cell already revealed!");
            return;
        }

        if (gameOver) {
            System.out.println("Game Over! Please start a new game.");
            return;
        }

        if (flagged[row][col]) {
            flagged[row][col] = false;
            System.out.println("Cell unflagged.");
        } else {
            flagged[row][col] = true;
            System.out.println("Cell flagged.");
        }

        checkGameStatus();
    }

    public void checkGameStatus() {
        int numUnrevealedCells = rows * columns - numMines;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (revealed[i][j])
                    count++;
            }
        }

        if (count == numUnrevealedCells) {
            gameWon = true;
            gameOver = true;
        }
    }

    public void revealAllMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == -1)
                    revealed[i][j] = true;
            }
        }
    }

    public void printBoard() {
        System.out.print("   ");
        for (int j = 0; j < columns; j++) {
            if (j < 10)
                System.out.print(j + "  ");
            else
                System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            if (i < 10)
                System.out.print(i + " |");
            else
                System.out.print(i + "|");

            for (int j = 0; j < columns; j++) {
                if (revealed[i][j]) {
                    if (board[i][j] == -1) {
                        System.out.print("*  ");
                    } else {
                        System.out.print(board[i][j] + "  ");
                    }
                } else if (flagged[i][j]) {
                    System.out.print("F  ");
                } else {
                    System.out.print(".  ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = 0;
        int columns = 0;
        int numMines = 0;

        while (true) {
            System.out.println("Welcome to Minesweeper!");
            System.out.println("");
            System.out.print("Enter the number of rows: ");
            rows = scanner.nextInt();

            System.out.print("Enter the number of columns: ");
            columns = scanner.nextInt();

            if (rows <= 0 || columns <= 0 || rows > 20 || columns > 20) {
                System.out.println("Invalid board size! The number of rows and columns should be between 1 and 20.");
                continue;
            }

            System.out.print("Enter the number of mines: ");
            numMines = scanner.nextInt();

            if (numMines >= rows * columns) {
                System.out.println("Invalid number of mines! The number of mines should be less than the total number of cells.");
                continue;
            }

            break;
        }

        Minesweeper minesweeper = new Minesweeper(rows, columns, numMines);
        minesweeper.initialize();

        while (!minesweeper.gameOver) {
            minesweeper.printBoard();

            System.out.print("Enter 'R' to reveal a cell or 'F' to flag/unflag a cell: ");
            String action = scanner.next().toUpperCase();

            if (action.equals("R")) {
                System.out.print("Enter the row to reveal: ");
                int row = scanner.nextInt();

                System.out.print("Enter the column to reveal: ");
                int col = scanner.nextInt();

                minesweeper.revealCell(row, col);
            } else if (action.equals("F")) {
                System.out.print("Enter the row to flag/unflag: ");
                int row = scanner.nextInt();

                System.out.print("Enter the column to flag/unflag: ");
                int col = scanner.nextInt();

                minesweeper.flagCell(row, col);
            } else {
                System.out.println("Invalid action! Try again.");
            }
        }

        minesweeper.printBoard();

        if (minesweeper.gameWon) {
            System.out.println("Congratulations! You won the game!");
        }

        scanner.close();
    }
}
