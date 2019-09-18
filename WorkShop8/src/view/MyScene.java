package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @File	MyScene.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 28, 2019
 * @Purpose Workshop X Task X
 * @Class MyScene.java 
 * 		Encapsulates a Scene and Group object
 * 		Allows use of declarative syntax in other tasks
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class MyScene extends Scene {
	public MyScene(double x, double y, Stage stage, String title){
		this(new Group(), x, y, stage, title);
	}
	public MyScene(Group root, double x, double y, Stage primaryStage, String title) {
		super(root, x, y);
		this.group = root;
		primaryStage.show();
		primaryStage.setTitle(title);
		primaryStage.setScene(this);
		primaryStage.setResizable(false);
	}
	final Group group;
	
	public Text addText(String input, double x, double y){
		System.out.println( input);
		Text text = new Text(input);
		return add(text, x, y);
	}
	public TextField addTextField(double x, double y, double length){
		TextField box = new TextField();
			box.setMaxWidth(length);
			box.setMinWidth(length);
		return add(box, x, y);
	}
	public Button addButton(String input, double x, double y, double width){
		Button button = new Button(input);
		button.setMinWidth(width);
		return add(button, x, y);
	}
	
	public <T extends Node>
	T add(T node, double x, double y){
		node.setLayoutX(x);
		node.setLayoutY(y);
		add(node);
		return node;
	}
	
	public void add(Node... array){
		for(Node n : array)
			try{ group.getChildren().add(n); } 
			catch(Exception ex){ ex.printStackTrace();}
	}
}
