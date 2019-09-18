package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.MyScene;
import view.TextLogger;

/**
 * @File	ChatClient.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	April 4, 2019
 * @Purpose Workshop 8 Task 1
 * @Class Matrix.java 
 * 		Implements Matrix addition using serial and parallel methods
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class ChatClient extends Application {

    public static void main(String[] args) {
    	launch(args);
    }
    MyScene scene;
    TextField input;
    
    Socket socket;
    PrintWriter stringToEcho;
    TextLogger logger;
	Button button;
	String name = "";
	//Thread listener;
	
	@Override
	public void start(Stage primaryStage) {
		scene = new MyScene(500, 500, primaryStage, "Chat");
		input  = scene.add(new TextField(){
			@Override
			public String toString(){
				String tmp = getText();
				setText("");
				requestFocus();
				return tmp;
			}
		}, 5, 470);
		input.setMinWidth(440);
		
		logger = scene.add(new TextLogger(300, 400), 0, 0);
		logger.print("Enter your name: ");
		
		button = scene.addButton("Enter", 450, 470, 40);
		button.setDefaultButton(true);
		button.setOnAction(event->{
			if(!(name = input.toString()).equals("")){
				logger.printLn(name);
				primaryStage.setTitle("Chat - " + name);
				connect();
			}
		});
		
		
	}
	private synchronized void connect(){
		try {
			socket = new Socket("localhost", 4000);
			stringToEcho = new PrintWriter(socket.getOutputStream(), true);
			
			button.setText("Send");
			button.setOnAction(event -> {
				if(!input.getText().equals(""))
					stringToEcho.println(name + ": "+ input);
				}
			);
			
			new Thread(()->{
				try(BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream())))
				{   String read;
					while((read = echoes.readLine()) != null)
							logger.printLn(read);	
					
					Platform.runLater(()->connect());
				} catch (IOException ex){};
			}).start();
			
			logger.printLn("Connected to Server");
			
		} catch (IOException e) {
			logger.printLn("Unable to connect");
			button.setText("Retry");
			button.setOnAction(event->connect());
		}
	}
	
	@Override
	public void stop(){
		try {
			socket.close();
			stringToEcho.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
