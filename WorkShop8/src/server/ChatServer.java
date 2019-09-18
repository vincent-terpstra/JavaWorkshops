package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Calendar;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MyScene;
import view.TextLogger;
/**
 * @File	Matrix.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	April 4, 2019
 * @Purpose Workshop 8 Task 1
 * 		Uses a serverSocket to connect multiple clients;
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class ChatServer extends Application {
	public static void main(String [] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			serverSocket = new ServerSocket(4000);
			MyScene scene = new MyScene(600, 600, primaryStage, "Multi-Threaded Server");
			logger = scene.add(new TextLogger(600, 600), 0, 0);
			logger.printLn("Multi-Threaded Server started on "+ Calendar.getInstance().getTime());
			
			new Thread(()->{
				try {	
					while(true) {
						new Echoer(serverSocket.accept(), logger).start();
					}
				} 
				catch (IOException e) {
					logger.printLn("Server Exception " + e.getMessage());
				}
			}).start();
		} catch (BindException e){ //Server already open
			
			logger.printLn("Server Exception " + e.getMessage());
			primaryStage.close();
		}
	}
	
	private ServerSocket serverSocket;
	private TextLogger logger;
	
	@Override
	public void stop(){
		try { 
			serverSocket.close();
		} catch (Exception e ) {}//Socket may be null OR not open
		System.exit(0);
	}
	
}