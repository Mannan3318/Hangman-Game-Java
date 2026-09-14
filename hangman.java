import java.util.*;
import java.io.*;
public class hangman {
    public static void main(String[] args) {
        String filePath = "words.txt";
        ArrayList<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file");
            return;
        } catch (IOException e) {
            System.out.println("Something went wrong");
            return;
        }

        if (words.isEmpty()) {
            System.out.println("No words found in the file");
            return;
        }

        Random random = new Random();

        String selected = words.get(random.nextInt(words.size()));
        String[] parts = selected.split("\\|", 2);

        String word = parts[0].toLowerCase();
        String hint = parts.length > 1 ? parts[1] : "No hint available";

        Scanner sc = new Scanner(System.in);
        ArrayList<Character> wordState = new ArrayList<>();
        ArrayList<Character> guessedLetters = new ArrayList<>();

        int wrongGuesses = 0;

        for (int i = 0; i < word.length(); i++) {
            wordState.add('_');
        }

        System.out.println("Welcome to Java Hangman");

        while (wrongGuesses < 6) {
            System.out.print(getHangmanArt(wrongGuesses));

            System.out.print("Word: ");
            for (char c : wordState) {
                System.out.print(c + " ");
            }

            System.out.println();
            System.out.println("Hint: " + hint);

            System.out.print("Guessed letters: ");
            for (char c : guessedLetters) {
                System.out.print(c + " ");
            }

            System.out.println();
            System.out.println("Wrong guesses: " + wrongGuesses + "/6");

            System.out.print("Guess a letter: ");

            String input = sc.next().toLowerCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Please enter only one letter");
                continue;
            }

            char guess = input.charAt(0);

            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed that letter");
                continue;
            }

            guessedLetters.add(guess);

            if (word.indexOf(guess) >= 0) {
                System.out.println("Correct guess!");

                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess) {
                        wordState.set(i, guess);
                    }
                }

                if (!wordState.contains('_')) {
                    System.out.println(getHangmanArt(wrongGuesses));
                    System.out.println("YOU WIN!");
                    System.out.println("The word was " + word);
                    break;
                }
            } else {
                wrongGuesses++;
                System.out.println("Wrong guess");
            }
        }

        if (wrongGuesses >= 6) {
            System.out.println(getHangmanArt(wrongGuesses));
            System.out.println("GAME OVER!");
            System.out.println("The word was " + word);
        }

        sc.close();
    }

    static String getHangmanArt(int wrongGuesses) {
        return switch (wrongGuesses) {
            case 0 -> """

                    """;

            case 1 -> """
                    o
                    """;

            case 2 -> """
                    o
                    |
                    """;

            case 3 -> """
                     o
                    /|
                     """;

            case 4 -> """
                     o
                    /|\\
                     """;

            case 5 -> """
                     o
                    /|\\
                    /
                     """;

            case 6 -> """
                     o
                    /|\\
                    / \\
                     """;

            default -> "";
        };
    }
}