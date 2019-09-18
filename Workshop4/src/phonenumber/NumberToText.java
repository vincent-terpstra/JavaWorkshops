package phonenumber;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * @File NumberToText.java
 * @Course JAC 444 SAB
 * @Author Vincent Terpstra
 * @ID     140665175
 * @Date   Feb 14, 2019
 * @Purpose Workshop 4 Task 3
 * @Class NumberToText.java 
 * 		Creates a file with all
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class NumberToText {
	public NumberToText(final int number){
		if(number <= 0){
			System.out.println("Input must be greater then zero: " + number);
			return;
		}
		int max = 0, tmp = number;
		while(tmp > 0){
			if(tmp %10 <= 1){ //Check for zeros or one's
				System.out.println("Input must not contain zero or one: " + number);
				return;
			}
			tmp /= 10;
			max++;
		}
		max = (int)Math.pow(3, max);
		
		try(FileOutputStream output = new FileOutputStream(number + ".txt")){
			for(int seed = 0; seed < max; seed++){
				recursion(output, number, seed);
				output.write((int)'\n');
			}
			System.out.println("File " + number +".txt created");
		} catch (FileNotFoundException e) {
			System.out.println("Unable to create file: " + number + ".txt");
		} catch (IOException e) {
			System.out.println("Unable to write to file: " + number + ".txt");
		}
	}
	
	/**
	 * Function to parse input number and seed number into text
	 * @param number
	 * @param seed
	 * @throws IOException 
	 */
	private void recursion(FileOutputStream output, int number, int seed) throws IOException{
		if(number > 9) recursion(output, number / 10, seed / 3);
		int result = ((number % 10 - 2) * 3) + seed % 3 + 'A';
		if(result >= 'Q') result ++; //ignore Q
		output.write(result);
	}
}
