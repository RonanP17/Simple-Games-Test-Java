import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
  
        Scanner scanner = new Scanner(System.in);
        System.out.println(" _  _  ____  __     ___  __   _  _  ____");
        System.out.println("/ )( \\(  __)(  )   / __)/  \\ ( \\/ )(  __)");
        System.out.println("\\ /\\ / ) _) / (_/\\( (__(  O )/ \\/ \\ ) _)");
        System.out.println("(_/\\_)(____)\\____/ \\___)\\__/ \\_)(_/(____)");
        System.out.println(" ____  __    ____  _  _  ____");
        System.out.println("(_  _)/  \\  (_  _)/ )( \\(  __)");
        System.out.println("  )( (  O )   )(  ) __ ( ) _)");
        System.out.println(" (__) \\__/   (__) \\_)(_/(____)");
        System.out.println(" ___   __   _  _  ____    _  _  _  _  ____  _");
        System.out.println("/ __) /  \\ ( \\/ )(  __)  / )( \\/ )( \\(  _ \\/ \\      ");
        System.out.println("( (_\\/ <> \\/ \\/ \\ ) _)   ) __ () \\/ ( ) _ (\\_/      ");
        System.out.println("\\___/\\_/\\_/\\_)(_/(____)  \\_)(_/(____/(____/(_)       ");
        System.out.println("");
        System.out.println("Select a game to play:");
        System.out.println("1. Tic Tac Toe");
        System.out.println("2. Rock Paper Scissors");
        System.out.println("3. Minesweeper");
        System.out.println("4. Snake");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
System.out.println("");                System.out.println("Starting Tic Tac Toe...");
                TicTacToe.main(args);
                break;
            case 2:
System.out.println("");                System.out.println("Starting Rock Paper Scissors...");
                RockPaperScissors.main(args);
                break;
            case 3:
System.out.println("");                System.out.println("Starting Minesweeper...");
                Minesweeper.main(args);
                break;
            case 4:
System.out.println("");                System.out.println("Starting Snake...");
                SnakeGame.main(args);
                break;
            
            default:
                System.out.println("Invalid choice. Please select a valid game.");
                break;
        }
    }
}
