package addressbook;

/**
 * @File	AddressBook.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 14, 2019
 * @Purpose Workshop 5 Task 2
 * @Class AddressBook.java 
 * 		User interface for an Address
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddressBook extends Application {
	public static void main(String[] args){ launch(args); }
	TextField
		firstName, lastName, city, postal;
	ComboBox<String>
		province;
	RandomAccessFile file;
	@Override
	public void stop(){
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		file = new RandomAccessFile("AddressBook.txt", "rw");
		file.setLength(0);
		MyScene scene = new MyScene(665, 200);
			scene.addText("First Name:",  20, 35);
			scene.addText("Last Name:",	  20, 77);
			scene.addText("City:", 		  20, 115);
			scene.addText("Province:",   220, 115);
			scene.addText("Postal Code:",490, 115);
				
			firstName = scene.addTextField( 100, 17, 550);
			lastName  = scene.addTextField( 100, 57, 550);
			
			city   = scene.addTextField( 55, 97, 150);
			postal = scene.addTextField(575, 97, 75);
			province = scene.add(new ComboBox<String>(
					FXCollections.observableArrayList(
							"Alberta",
							"British Columbia",
							"Manitoba",
							"New Brunswick",
							"Newfoundland and Labrador",
							"Northwest Territories",
							"Nova Scotia",
							"Nunavut",
							"Ontario",
							"Prince Edward Island",
							"Quebec",
							"Saskatchewan",
							"Yukon"
							)
				), 285, 97);
			
			province.setMaxWidth(200);
			province.setVisibleRowCount(5);
			
				double buttonY = 140;
			scene.addButton("Add"	,20 ,buttonY)
				.setOnAction(event->save(saved));;
			scene.addButton("First"	,125,buttonY)
				.setOnAction(event->load(0));
			scene.addButton("Next"	,230,buttonY)
				.setOnAction(event->load(idx + 1));
			scene.addButton("Previous",335,buttonY)
				.setOnAction(event->load(idx - 1));
			scene.addButton("Last"	,440,buttonY)
				.setOnAction(event->load(saved-1));
			scene.addButton("Update",545,buttonY)
				.setOnAction(event->save(idx));
			loadAddScreen();
		primaryStage.setTitle("Address Book");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}
	int saved = 0;
	int idx = 0;
	void save(int idx){
		try {
			String save =  province.getItems().indexOf(province.getValue()) + ":" +city.getText() +
					":" + postal.getText() + ":" + firstName.getText() + ":" + lastName.getText() + ":";
			if(save.length() > stride){
				System.out.println("To many Characters, Refusing to save");
				return;
			}
			if(idx == saved ) saved++;
			int loc = idx * stride * 4;
			file.seek(loc);
			for(int i = 0; i < stride; i++){ file.writeChar('.'); }
			file.seek(loc);
			file.writeChars(save);
		} catch (Exception ex){};
	}
	int stride = 50;
	void load(int idx){
		if(saved == 0) return;
		if(idx >= saved) idx = saved-1;
		if(idx < 0) idx = 0;
		this.idx = idx;
		try {
			file.seek(idx * stride * 4 );
			province.setValue(province.getItems().get((int)Double.parseDouble(readString())));
			city.setText(readString());
			postal.setText(readString());
			firstName.setText(readString());
			lastName.setText(readString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	String readString() throws IOException{
		String tmp = "";
		char ch;
		while((ch = file.readChar()) != ':'){
			tmp += ch;
		}
		return tmp;
	}
	void loadAddScreen(){
		province.setValue("Ontario");
		province.getValue();
		firstName.setText("");
		lastName.setText("");
		city.setText("Toronto");
		postal.setText("");
	}
	class MyScene extends Scene {
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
		public Button addButton(String input, double x, double y){
			Button button = new Button(input);
			button.setMinWidth(100);
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
}
