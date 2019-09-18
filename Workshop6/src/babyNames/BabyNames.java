package babyNames;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @File	BabyNames.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 13, 2019
 * @Purpose Workshop 6 Task 2
 * @Class BabyNames.java 
 * 		Works with an file of baby names to display the popular names
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class BabyNames extends Application {
	public static void main(String[] args){
		launch(args);
	}
	Text output;
	ComboBox<String> year;
	RadioButton male, female;
	TextField name;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MyScene scene = new MyScene(600, 250);
		scene.addText("Enter the Year:",   30,  50);
		scene.addText("Enter the Gender:", 30,  90);
		scene.addText("Enter the Name:",   30, 130);
		output = scene.addText("", 30, 170);
		year   = scene.add(new ComboBox<String>( FXCollections.observableArrayList(
					"2001", "2002",  "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010")),
				145, 32 );
		year.setValue("2001");
		year.setVisibleRowCount(5);
		
		final ToggleGroup gender = new ToggleGroup();
		male = scene.add(new RadioButton("Male"), 145, 78);
			male.setToggleGroup(gender);
			male.setSelected(true);
		female = scene.add(new RadioButton("Female"), 210, 78);
			female.setToggleGroup(gender);
			
		name = scene.add(new TextField(){
			@Override
			public void replaceText(int start, int end, String text){
				if(text.matches("[A-Za-z]") || text.equals("")) 
					super.replaceText(start, end, text);
			}
			
		}, 145, 110);
			
		scene.addButton("Exit", 305, 190, 200)
			.setOnAction(event->primaryStage.close());
		Button query = scene.addButton("Submit Query", 95, 190, 200);
			query.setOnAction(event->loadFromFile());
			query.setDefaultButton(true);
	
		primaryStage.show();
		primaryStage.setTitle("Search Name Ranking Application");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
	}
	
	private void loadFromFile(){
		if(name.getText().equals("")) 
			return;
		boolean isMale = male.isSelected();
		String gen = isMale ? "Boy" : "Girl";
		String fName = "babynamesranking"+ year.getValue() +".txt";
		String check = name.getText();
		
		try(BufferedReader file = new BufferedReader(new FileReader(fName))){
			int idx = isMale ? 1 : 3;
			String line;
			while((line = file.readLine()) != null){
				String[] split = line.split("\\s+");
				if(split[idx].equals(check)){
					output.setText(gen + " name " +
						split[idx] + " is ranked #" + split[0] +
						" in " + year.getValue() + " year" );
					return;
				}
			}
			//name not found
			output.setText(gen + " name not found " + name.getText());
		} catch (FileNotFoundException e) {
			output.setText("File not Found: "+ fName);
		} catch (IOException e) {
			output.setText("");
			e.printStackTrace();
		}
		
	}

}
