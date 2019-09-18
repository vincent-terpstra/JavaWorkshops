package tester;

import charcount.CharCounter;
import hangmangame.HangManGame;
import phonenumber.NumberToText;

/**
 * @File TesterClass.java
 * @Course JAC 444 SAB
 * @Author Vincent Terpstra
 * @ID     140665175
 * @Date   Feb 13, 2019
 * @Purpose Workshop 4 Task Tester
 * @Class TesterClass
 * 	implements a main method for testing
 * 		HangManGame.java
 * 		CharCounter.java
 * 		NumberToText.java
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class TesterClass {
	public static void main(String[] args){
		MyScanner scan = new MyScanner();
		
		//TASK 1
		while(scan.ask("Run Hangman Game")){
			HangManGame.load("hangman.txt")
				.run(scan.scan);
		}
		
		//TASK 2
		while(scan.ask("Run CharCounter")){
			System.out.print("Please enter a filename ");
			new CharCounter(scan.scan.nextLine());
		}
		
		//TASK 3
		while(scan.ask("Run NumberToText")){
			System.out.print("Please enter a number ");
			new NumberToText(scan.getInt());
		}
	}
	
}
