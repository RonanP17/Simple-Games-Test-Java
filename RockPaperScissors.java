import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to Rock, Paper, Scissors!");
        System.out.println("Enter your choice: 1 for Rock, 2 for Paper, 3 for Scissors.");
        int userChoice = scanner.nextInt();

        if (userChoice < 1 || userChoice > 3) {
            System.out.println("Invalid choice. Please select a number between 1 and 3.");
            return;
        }

        String[] choices = { "Rock", "Paper", "Scissors" };
        int computerChoice = random.nextInt(3) + 1;

        System.out.println("You chose: " + choices[userChoice - 1]);
        System.out.println("Computer chose: " + choices[computerChoice - 1]);

        System.out.println("Your choice:");
        displayChoice(userChoice);
        System.out.println("Computer's choice:");
        displayChoice(computerChoice);

        if (userChoice == computerChoice) {
            System.out.println("It's a tie!");
        } else if ((userChoice == 1 && computerChoice == 3) || (userChoice == 2 && computerChoice == 1)
                || (userChoice == 3 && computerChoice == 2)) {
            System.out.println("Congratulations! You win!");
        } else {
            System.out.println("Sorry, you lose. Better luck next time!");
        }
    }

    public static void displayChoice(int choice) {
        if (choice == 1) {
            System.out.println("    _______\n" +
                    "---'   ____)\n" +
                    "      (_____)    \n" +
                    "      (_____)    \n" +
                    "      (____)     \n" +
                    "---.__(___)     ");
        } else if (choice == 2) {
            System.out.println("     _______\n" +
                    "---'    ____)____\n" +
                    "           ______)\n" +
                    "          _______)\n" +
                    "         _______)\n" +
                    "---.__________)   ");
        } else if (choice == 3) {
            System.out.println("    _______\n" +
                    "---'   ____)____\n" +
                    "          ______)\n" +
                    "       __________)\n" +
                    "      (____)\n" +
                    "---.__(___)    ");
        }
    }
}
