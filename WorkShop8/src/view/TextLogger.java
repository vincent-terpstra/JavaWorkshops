package view;

import javafx.application.Platform;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
/**
 * @File	TextLogger.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	April 4, 2019
 * @Purpose Workshop 8 Task 1 
 * 		Allows printing of text to the screen
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class TextLogger extends TextFlow {
	public TextLogger(double width, double height){
		this.setPrefSize(width, height);
		this.setMaxHeight(height);
		this.setMaxWidth(width);
	}
	
	public final void print(String text){
		Platform.runLater(()->getChildren().add(new Text(text)));
	}
	public final void printLn(String text){
		print(text + '\n');
	}
}
