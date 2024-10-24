import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private final String question;
    private final String[] options;
    private final int correctAnswerIndex;

    public Question(String question, String[] options, int correctAnswerIndex) {
        this.question = question;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswerIndex;
    }

    public String getCorrectAnswer() {
        return options[correctAnswerIndex]; // Provide a method to get the correct answer text
    }
}

class Quiz {
    private final Question[] questions;
    private int score;
    private final int timeLimit; // time limit in seconds

    public Quiz(Question[] questions, int timeLimit) {
        this.questions = questions;
        this.timeLimit = timeLimit;
        this.score = 0;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 0; i < questions.length; i++) {
                Question question = questions[i];
                System.out.println("\nQuestion " + (i + 1) + ": " + question.getQuestion());
                String[] options = question.getOptions();

                for (int j = 0; j < options.length; j++) {
                    System.out.println((j + 1) + ". " + options[j]);
                }

                int answerIndex = getUserAnswer(scanner);
                if (question.isCorrect(answerIndex)) {
                    score++;
                    System.out.println("Correct!");
                } else {
                    System.out.println("Wrong! The correct answer was: " + question.getCorrectAnswer());
                }
            }

            // Display the final score
            System.out.println("\nYour final score: " + score + " out of " + questions.length);
        }
    }

    private int getUserAnswer(Scanner scanner) {
        Timer timer = new Timer();
        final int[] answerIndex = {-1}; // Store user's answer
        final boolean[] answered = {false}; // Check if answered

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!answered[0]) {
                    System.out.println("\nTime's up!");
                    answered[0] = true; // Mark as answered
                }
            }
        }, timeLimit * 1000); // Convert seconds to milliseconds

        while (!answered[0]) {
            System.out.print("Enter your answer (1-" + (questions[0].getOptions().length) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 1 && input <= questions[0].getOptions().length) {
                    answerIndex[0] = input - 1; // Convert to 0-based index
                    answered[0] = true; // Mark as answered
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else {
                scanner.next(); // Clear invalid input
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        timer.cancel(); // Cancel the timer
        return answerIndex[0];
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        Question[] questions = new Question[]{
            new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2),
            new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1),
            new Question("What is the largest ocean on Earth?", new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 3),
            new Question("Who wrote 'Hamlet'?", new String[]{"Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"}, 2),
            new Question("What is the chemical symbol for water?", new String[]{"O2", "H2O", "CO2", "NaCl"}, 1)
        };

        Quiz quiz = new Quiz(questions, 10); // 10 seconds time limit for each question
        quiz.start();
    }
}

