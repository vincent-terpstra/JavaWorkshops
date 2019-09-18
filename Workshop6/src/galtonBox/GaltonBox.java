package galtonBox;

import java.util.Scanner;

/**
 * @File	ATM.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 13, 2019
 * @Purpose Workshop 5 Task 1
 * @Class ATM.java 
 * 		User interface for an ATM
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class GaltonBox {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int balls = getInt("Please enter the number of balls: ", scan);
		int rows  = getInt("Please enter the number of slots in the bean machine: ", scan);
			scan.close();
		int[] slots = new int[rows];
		
		while(balls -- > 0){
			int idx = 0;
			for(int i = 1; i < rows; ++i){
				if(Math.random() >.5f){
					System.out.print('L');
				} else {
					System.out.print('R');
					++idx;
				}
			}
			slots[idx]++;
			System.out.println();
		}
		int max = 0;
		for(int i : slots)
			if(i > max) max = i;
		System.out.println();
		
		for(int r = max; r > 0; --r){
			for(int c : slots)
				System.out.print(c >= r ? 'O' : ' ');
			System.out.println();
		}
	}
	
	static final int getInt(String msg, Scanner scan){
		System.out.print(msg);
		while(true){
			try{
				int input = scan.nextInt();
				if(input > 0){
					scan.nextLine();
					return input;
				}
			} catch (Exception ex){
				System.out.println("  Unable to parse: " + scan.nextLine() );
			}
			System.out.print("  Please enter a Int greater then zero: ");
		}
	}
}
