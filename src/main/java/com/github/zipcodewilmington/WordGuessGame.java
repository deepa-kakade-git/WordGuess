package com.github.zipcodewilmington;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class WordGuessGame {

    private static final String[] WORDS = {"bag", "mug", "tub", "tug", "rag", "cat"};  // Constant array
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public void run() {  // Shortened method name
        announceGameStart();
        boolean playAgain;
        do {
            playGame();
            playAgain = askToPlayAgain();
        } while (playAgain);
        announceGameOver();
    }

    private void announceGameOver() {
        System.out.println("Game over. Thanks for playing!");
    }

    private boolean askToPlayAgain() {
        System.out.println("Do you want to play again? (yes/no)");
        String response = scanner.next();
        return response.equalsIgnoreCase("yes");
    }

    private void announceGameStart() {
        System.out.println("Welcome to Word Guess Game!");
    }

    private void playGame() {
        String secretWord = WORDS[random.nextInt(WORDS.length)];
        char[] guesses = new char[secretWord.length()];
        Arrays.fill(guesses, '_');  // Efficiently fill with '_'
        int remainingGuesses = 4;

        while (remainingGuesses > 0 && !isWordGuessed(guesses)) {
            printCurrentState(guesses, remainingGuesses);
            char guess = getNextGuess();
            processGuess(guess, secretWord, guesses);
            if (isWordGuessed(guesses)) {
                playerWon();
                break;
            }
            remainingGuesses--;
        }

        if (!isWordGuessed(guesses)) {
            playerLost(secretWord);
        }
    }

    private void playerLost(String secretWord) {
        System.out.println("Sorry, you lost! The word was: " + secretWord);
    }

    private void playerWon() {
        System.out.println("Congratulations, you won!");
    }

    private char getNextGuess() {
        System.out.println("Enter your guess:");
        return scanner.next().charAt(0);
    }

    private void printCurrentState(char[] guesses, int remainingGuesses) {
        System.out.println("Current Guesses: " + Arrays.toString(guesses));
        System.out.println("Remaining Guesses: " + remainingGuesses);
    }

    private boolean isWordGuessed(char[] guesses) {
        return !new String(guesses).contains("_");
    }

    private void processGuess(char guess, String secretWord, char[] guesses) {
        boolean found = false;
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == guess) {
                guesses[i] = guess;
                found = true;
            }
        }
        if (!found) {
            System.out.println("Incorrect guess!");
        }
    }

    public static void main(String[] args) {
        WordGuessGame game = new WordGuessGame();
        game.run();
    }
}