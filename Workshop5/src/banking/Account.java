package banking;

import java.io.Serializable;
import java.util.Date;

/**
 * @File	Account.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 11, 2019
 * @Purpose Workshop 5 Task 1
 * @Class Account.java 
 * 		Stores information for a Bank Account
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public final class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int pin;
	
	String firstName, lastName;
	private double balance;
	private static double annualInterestRate;
	
	private Date dateCreated;
	
	public Account(){
		setDate();
		firstName = "new";
		lastName = "account";
	}
	/*********Query and Modifier methods***********/
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public Account setBalance(double balance){
		this.balance = balance;
		return this;
	}
	public double getAnnualInterestRate(){
		return annualInterestRate;
	}
	public void setAnnualInterestRate(double rate){
		annualInterestRate = rate;
	}
	public void setDate(){
		dateCreated = Date.from(java.time.Instant.now());
	}
	public Date getDateCreated(){
		return dateCreated;
	}
	
	public double getMonthlyInterestRate(){
		return annualInterestRate / 12;
	}
	public double getMonthlyInterest(){
		return balance * getMonthlyInterestRate();
	}
	
	public void display(){
		System.out.println("Account: " + id + " Balance: " + balance +
				" Owner: " + firstName + " " + lastName + " " + pin);
	}
	
	public void deposit(double money){
		if(money > 0)
			balance += money;
	}
	
	public void withdraw(double money){
		if(money > 0 && balance > money)
			balance -= money;
	}
	
	public void setPin(int pin){
		this.pin = pin;
	}
	
	public boolean checkPin(int pin){
		return this.pin == pin;
	}
}
