import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    private static char currentPlayer = 'X';
    private static char computerPlayer = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic-Tac-Toe!");

        System.out.println("Choose the game mode:");
        System.out.println("1. Player vs Player");
        System.out.println("2. Player vs Computer");

        int gameMode = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (gameMode == 1) {
            playAgainstPlayer(scanner);
        } else if (gameMode == 2) {
            playAgainstComputer(scanner);
        } else {
            System.out.println("Invalid game mode. Exiting the game.");
        }

        scanner.close();
    }

    private static void playAgainstPlayer(Scanner scanner) {
        while (true) {
            printBoard();
            System.out.println("Player " + currentPlayer + ", enter row and column (e.g. 1 2), or type \"forfeit\" to quit:");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("forfeit")) {
                System.out.println("Player " + currentPlayer + " has forfeited the game.");
                break;
            }
            String[] inputParts = input.split(" ");
            if (inputParts.length != 2) {
                System.out.println("Invalid input, try again.");
                continue;
            }
            int row, col;
            try {
                row = Integer.parseInt(inputParts[0]) - 1;
                col = Integer.parseInt(inputParts[1]) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, try again.");
                continue;
            }

            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = currentPlayer;

                if (checkWin() || checkTie()) {
                    printBoard();
                    System.out.println("Game over!");
                    break;
                }

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            } else {
                System.out.println("Invalid move, try again.");
            }
        }

        if (checkWin()) {
            System.out.println("Player " + currentPlayer + " wins!");
        } else if (checkTie()) {
            System.out.println("It's a tie!");
        }
    }

    private static void playAgainstComputer(Scanner scanner) {
        while (true) {
            printBoard();

            if (currentPlayer == 'X') {
                System.out.println("Player X, enter row and column (e.g. 1 2), or type \"forfeit\" to quit:");

                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("forfeit")) {
                    System.out.println("Player X has forfeited the game.");
                    break;
                }
                String[] inputParts = input.split(" ");
                if (inputParts.length != 2) {
                    System.out.println("Invalid input, try again.");
                    continue;
                }
                int row, col;
                try {
                    row = Integer.parseInt(inputParts[0]) - 1;
                    col = Integer.parseInt(inputParts[1]) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input, try again.");
                    continue;
                }

                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                    board[row][col] = currentPlayer;

                    if (checkWin() || checkTie()) {
                        printBoard();
                        System.out.println("Game over!");
                        break;
                    }

                    currentPlayer = 'O';
                } else {
                    System.out.println("Invalid move, try again.");
                }
            } else {
                System.out.println("Computer O's turn:");

                if (!makeWinningMove() && !makeBlockingMove() && !makeRandomMove()) {
                    System.out.println("No more valid moves. It's a tie!");
                    break;
                }

                if (checkWin() || checkTie()) {
                    printBoard();
                    System.out.println("Game over!");
                    break;
                }

                currentPlayer = 'X';
            }
        }

        if (checkWin()) {
            if (currentPlayer == 'X') {
                System.out.println("Player X wins!");
            } else {
                System.out.println("Computer O wins!");
            }
        }
    }

    private static boolean makeWinningMove() {
        // Check rows for potential win
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int emptyCol = -1;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == currentPlayer) {
                    count++;
                } else if (board[i][j] == ' ') {
                    emptyCol = j;
                }
            }
            if (count == 2 && emptyCol != -1) {
                board[i][emptyCol] = computerPlayer;
                return true;
            }
        }

        // Check columns for potential win
        for (int j = 0; j < 3; j++) {
            int count = 0;
            int emptyRow = -1;
            for (int i = 0; i < 3; i++) {
                if (board[i][j] == currentPlayer) {
                    count++;
                } else if (board[i][j] == ' ') {
                    emptyRow = i;
                }
            }
            if (count == 2 && emptyRow != -1) {
                board[emptyRow][j] = computerPlayer;
                return true;
            }
        }

        // Check main diagonal for potential win
        int count = 0;
        int emptyIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == currentPlayer) {
                count++;
            } else if (board[i][i] == ' ') {
                emptyIndex = i;
            }
        }
        if (count == 2 && emptyIndex != -1) {
            board[emptyIndex][emptyIndex] = computerPlayer;
            return true;
        }

        // Check secondary diagonal for potential win
        count = 0;
        emptyIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] == currentPlayer) {
                count++;
            } else if (board[i][2 - i] == ' ') {
                emptyIndex = i;
            }
        }
        if (count == 2 && emptyIndex != -1) {
            board[emptyIndex][2 - emptyIndex] = computerPlayer;
            return true;
        }

        return false;
    }

    private static boolean makeBlockingMove() {
        char opponentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        // Check rows for potential block
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int emptyCol = -1;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == opponentPlayer) {
                    count++;
                } else if (board[i][j] == ' ') {
                    emptyCol = j;
                }
            }
            if (count == 2 && emptyCol != -1) {
                board[i][emptyCol] = computerPlayer;
                return true;
            }
        }

        // Check columns for potential block
        for (int j = 0; j < 3; j++) {
            int count = 0;
            int emptyRow = -1;
            for (int i = 0; i < 3; i++) {
                if (board[i][j] == opponentPlayer) {
                    count++;
                } else if (board[i][j] == ' ') {
                    emptyRow = i;
                }
            }
            if (count == 2 && emptyRow != -1) {
                board[emptyRow][j] = computerPlayer;
                return true;
            }
        }

        // Check main diagonal for potential block
        int count = 0;
        int emptyIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == opponentPlayer) {
                count++;
            } else if (board[i][i] == ' ') {
                emptyIndex = i;
            }
        }
        if (count == 2 && emptyIndex != -1) {
            board[emptyIndex][emptyIndex] = computerPlayer;
            return true;
        }

        // Check secondary diagonal for potential block
        count = 0;
        emptyIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] == opponentPlayer) {
                count++;
            } else if (board[i][2 - i] == ' ') {
                emptyIndex = i;
            }
        }
        if (count == 2 && emptyIndex != -1) {
            board[emptyIndex][2 - emptyIndex] = computerPlayer;
            return true;
        }

        return false;
    }

    private static boolean makeRandomMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = computerPlayer;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == currentPlayer && board[1][j] == currentPlayer && board[2][j] == currentPlayer) {
                return true;
            }
        }

        // Check main diagonal
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true;
        }

        // Check secondary diagonal
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    private static boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }
}
