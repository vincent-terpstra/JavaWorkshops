package hangmangame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @File HangMan.java
 * @Course JAC 444 SAB
 * @Author Vincent Terpstra
 * @ID     140665175
 * @Date   Jan 20, 2019
 * @Purpose Workshop 4 Task 1
 * @Class HangMan 
 * 		allows the user to play a game of hangman
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class HangManGame {
	private static final String[] words = {
			"hello", "world", "write", "that", "program", "guess", "enter", "letter", "word", "secret", "hangman"
	};
	
	private final String secret;
	private String guess, tries = "";
	private int misses = 0;
	
	public static final HangManGame load(String filename){
		try( BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			return new HangManGame(buffer.readLine().split(" "));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to load file " + filename);	
		} catch (IOException e){
			System.out.println(e);
		}
		return new HangManGame();
	}
	
	public HangManGame(){
		this(words);
	}
	
	public HangManGame(String[] words){
		secret = words[(int)(Math.random() * words.length)].toLowerCase();
		guess = secret.replaceAll("[A-Za-z]", "*");
	}
	
	/**
	 * @param scan Scanner for user input
	 * @return if the word has been solved
	 */
	public void guess(Scanner scan){
		System.out.print("(Guess) Enter a letter in word " + guess + " > ");
		String check = scan.nextLine();
		if(check.length() > 1){
			System.out.println("    Too many characters!");
		} else if(guess.contains(check = check.toLowerCase())){
			System.out.println("    " + check + " is already in the word!");
		} else if(secret.contains(check)){
			reveal(check.charAt(0));
		} else if(tries.contains(check)){
			System.out.println("    " + check + " has already been guessed!");
		} else {
			tries = tries + check;
			System.out.println("    " + check + " is not in the word!" );
			misses++;
		}
		
	}
	/**
	 * Function to reveal a char in the guess
	 * @param char check, the actual guess
	 */
	public void reveal(char ch){
		String sum = "";
		for(int idx = 0; idx < secret.length(); idx++){
			sum = sum + (secret.charAt(idx) == ch ? ch : guess.charAt(idx));
		}
		guess = sum;
	}
	
	/**
	 * Function to check if the game has been won
	 * @return true if the word has been solved
	 */
	public boolean gameOver(){
		if(guess.equals(secret)){
			System.out.print  ("The word is " + guess + ". "); 
			System.out.println("You missed " + misses + " time"+(misses == 1 ? "" : "s")+"!");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Function to loop over guesses
	 * @param scan For user input
	 */
	public void run(Scanner scan){
		do{
			guess(scan);
		} while(!gameOver());
	}
}
