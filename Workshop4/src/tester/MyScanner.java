package tester;

import java.util.Scanner;

/**
 * @File MyScanner.java
 * @Course JAC 444 SAB
 * @Author Vincent Terpstra
 * @ID     140665175
 * @Date   Jan 28, 2019
 * @Purpose Workshop X Task X
 * 		encapsulate the scanner Object for Exception Handling
 * @Class MyScanner 
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class MyScanner implements ScannerInterface {
	final Scanner scan = new Scanner(System.in);
	
	@Override
	public void finalize(){
		scan.close();
	}
	
	@Override
	public int getInt(int min){
		int input;
		while((input = getInt()) < min){
			System.out.print("Please enter a int greater then " + min + ": ");
		}
		return input;
	}
	@Override
	public int getInt(){
		while(true){
			try{
				int input = scan.nextInt();
				scan.nextLine(); //clear buffer
				return input;
			} catch (Exception ex){
				System.out.println("Unable to parse: "+ scan.nextLine());
			}
			System.out.print("Please enter an int: ");
		}
	}
	@Override
	public double getDouble(double min){
		double input;
		while((input = getDouble())< min){
			System.out.print("Please enter a double greater then " + min +": " );
		}
		return input;
	}
	
	@Override
	public double getDouble(){
		while(true){
			try{
			double input = scan.nextDouble(); 
				scan.nextLine(); //clear buffer
				return input;
			} catch (Exception ex){
				System.out.println("Unable to parse: "+ scan.nextLine());
			}
		}
	}
	/**
	 * Continually asks the User for a y or n answer
	 * @param request
	 * @return
	 */
	public boolean ask(String request){
		System.out.print(request + " (y or n)? ");
		while(true){	
			String input = scan.nextLine().toLowerCase();
			if(input.equals("y") || input.equals("yes")) 
				return true;
			else if(input.equals("n") || input.equals("no")) 
				return false;
			System.out.print("Please enter (y or n) ");
		}
	}
}
