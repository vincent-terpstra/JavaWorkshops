package babyNames;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MyScene extends Scene {
	public MyScene(double x, double y){
		this(new Group(), x, y);
	}
	
	public MyScene(Group root, double x, double y) {
		super(root, x, y);
		this.group = root;
	}
	final Group group;
	
	public Text addText(String input, double x, double y){
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
	
	<T extends Node>
	T add(T node, double x, double y){
		node.setLayoutX(x);
		node.setLayoutY(y);
		group.getChildren().add(node);
		return node;
	}
}
