package charcount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @File NumberToText.java
 * @Course JAC 444 SAB
 * @Author Vincent Terpstra
 * @ID     140665175
 * @Date   Feb 14, 2019
 * @Purpose Workshop 4 Task 3
 * @Class CharCounter.java 
 * 		Counts all alphabetic characters in a file
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class CharCounter {
	public CharCounter(String filename){
		try (FileInputStream input = new FileInputStream(filename)){
			int c;
			while((c = input.read()) != -1){
				if(c >= 'A' && c <= 'Z'){
					count[c - 'A']++;
				} else if (c >= 'a' && c <= 'z'){
					count[c - 'a']++;
				}
			}
			display();
		} catch (FileNotFoundException fnf){
			System.out.println("Unable to find "+filename);
		} catch (IOException ioe){
			System.out.println(ioe);
		}
	}
	int[] count = new int[26];
	public void display(){
		for(int i = 0; i < count.length; i++){
			System.out.println("Number of " +(char)('A' + i) + "'s: " + count[i]);
		}
	}
}
