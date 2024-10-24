import java.util.Random;
import java.util.Scanner;

public class Numberguessing {
    public static void main(String[] args) { 
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        
        // Generate a random number between 1 and 100
        int randomNumber = random.nextInt(100) + 1;
        int userGuess = 0;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have generated a random number between 1 and 100. Can you guess it?");

        // Loop until the user guesses the correct number
        while (userGuess != randomNumber) {
            System.out.print("Enter your guess: ");
            userGuess = scanner.nextInt();

            if (userGuess < randomNumber) {
                System.out.println("Too low! Try again.");
            } else if (userGuess > randomNumber) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("Congratulations! You guessed the correct number: " + randomNumber);
            }
        }

        scanner.close();
    }
}