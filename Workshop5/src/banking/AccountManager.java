package banking;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * @File	AccountManager.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 11, 2019
 * @Purpose Workshop 5 Task 1
 * @Class AccountManager.java 
 * 		Manages Bank accounts
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class AccountManager {
	static final String filename = "Accounts.dat";
	static final int MAXACCOUNTS = 10;
	static Account[] accounts = new Account[MAXACCOUNTS];
	public static void main(String[] args){
		/*Reset Accounts*
		for(int i = 0; i < MAXACCOUNTS; i++){
			//create 10 accounts with a balance of 100
			accounts[i] = new Account().setBalance(100);
		}
		/*Save Accounts*
			createSerializedAccounts();
		/*Load Accounts*/
			loadSerializedAccounts();
		/*Display Accounts*/
		for(Account account : accounts){
			account.display();
		}
	}
	public static Account getAccount(int accountNum){
		if(accounts[0] == null){
			loadSerializedAccounts();
		}
		int idx = 0;
		do {
			int ID = accounts[idx].getID();
			if(ID == 0 || ID == accountNum) return accounts[idx];
		} while(++idx < MAXACCOUNTS);
		return accounts[0];
		//always override accounts[0] if accounts are full and a new account is made
	}
	public static void createSerializedAccounts(){
		if(accounts[0] == null) return; //Don't save null accounts
		//Serialize the accounts
		try(ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream(filename))){
			for(Account account : accounts){
				output.writeObject(account);
			}
		} catch (IOException ex){
			System.out.println(ex);
		}
	}
	
	public static void loadSerializedAccounts(){
		try(ObjectInputStream input = new ObjectInputStream(
				new FileInputStream(filename))){
			for(int i = 0; i < accounts.length; i++){
				accounts[i] = (Account)input.readObject();
			}
		} catch (IOException ex){
			System.out.println(ex);
		} catch (ClassNotFoundException e) {
			System.out.println(e);;
		}
	}
}
