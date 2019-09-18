package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

import view.TextLogger;
/**
 * @File	Echoer.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	April 4, 2019
 * @Purpose Workshop 8 Task 1
 * 		Allows the server to host multiple clients, and clients to communicate with each other
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class Echoer extends Thread{
	private final static ArrayList<Echoer> echoList = new ArrayList<Echoer>();
	private Socket socket;
	private PrintWriter writer;
	public Echoer(Socket socket, TextLogger log) {
		this.socket = socket;
		this.log = log;
		synchronized(echoList){
			echoList.add(this);
		}
	}
	final TextLogger log;
	
	@Override
	public void run() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			log.printLn("Connection from " + socket + " at " + Calendar.getInstance().getTime());
			while(true) {
				
				String echoString = input.readLine();
				if(echoString == null) {
					synchronized(echoList){
						echoList.remove(this);
					}
					break;
				} else {
					log.printLn(echoString);
					synchronized(echoList){
						for(Echoer e : echoList){
							e.writer.println(echoString);
						}
					}
				}
				/*try {
					
					Thread.sleep(5000);
					
				}catch(InterruptedException e) {
					System.out.println("Thread Interrupted");
				}*/
				
				
			}
		}catch(IOException e) {
			System.out.println("Oooops " + e.getMessage());
		}finally {
			try {
				socket.close();
				writer.close();
			}catch(IOException e) {
				// later
			}
			
		}
		
	}

}
