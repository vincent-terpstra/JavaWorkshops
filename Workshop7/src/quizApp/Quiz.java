package quizApp;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @File	Quiz.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 28, 2019
 * @Purpose Workshop 7 Task 1
 * @Class Quiz.java 
 * 		Asks and checks user input for math
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class Quiz extends Application {
	public static void main(String[] args){
		launch(args);
	}
	int rand1, rand2;
	Reset[] equations;
	@Override
	public void start(Stage primaryStage) throws Exception {
		MyScene scene = new MyScene(600, 300, primaryStage, "Quiz Application");
		
		//Setup board
		equations = new Reset[]{
				new Reset(){
					@Override
					public void reset(){
						setText("Two randomly generated numbers are " + rand1 + " and " + rand2);
					}
				},
				new Equation(scene, "What is the addition of", 		(x,y)->x+y),
				new Equation(scene, "What is the subtraction of",	(x,y)->x-y),
				new Equation(scene, "What is the multiplication of",(x,y)->x*y),
				new Equation(scene, "What is the division of",		(x,y)->x/y),
				new Reset(),
				new Reset()
			};
		scene.add(equations);
		//Setup buttons
		scene.addButton("Check Answers", 98, 240, 200)
			.setOnAction(event->{
				int correct = 0;
				for(Reset eq : equations){
					if( eq instanceof Equation ){
						if(((Equation)eq).correct()) correct ++;
					}
				}
				equations[5].setText("Number of correct answers: " + correct + " !");
				equations[6].setText("Number of wrong answers  : " +(4 - correct) + " !");
			});
		scene.addButton("Reset", 302, 240, 200)
			.setOnAction(event->reset());
		
		reset();
	}
	
	/**
	 * Create a new random number set
	 * Modify all textfields
	 */
	private void reset(){
		rand1 = (int)(Math.random() * 9) + 1;
		rand2 = (int)(Math.random() * 9) + 1;
		for(Reset eq : equations){
			eq.reset();
		}
	}
	
	/**
	 *	input double double
	 *  return double
	 */
	@FunctionalInterface
	public interface Compare {
		double Check(double x, double y);
	}
	
	int deltay = 0;
	/**
	 * Text Field that resets to empty
	 */
	class Reset extends Text {
		public Reset(){
			setLayoutX(30);
			setLayoutY(deltay += 30);
		}
		
		public void reset(){ setText("");};
	}
	/**
	 *	Text field and input field combo,
	 *  Uses a Compare to check that the answer is correct
	 */
	class Equation extends Reset {
		String phrase;
		Compare check; //Equation to test
		TextField input; //Input for answer
		Equation(MyScene scene, String phrase, Compare check){
			super();
			this.phrase = phrase;
			this.check  = check;
			input = scene.addTextField(280, deltay - 20, 120);
		}
		
		@Override
		public void reset(){
			input.setText("");
			setText(phrase + " " + rand1 + " and " + rand2 + "?");
		}
		boolean correct(){
			try {
				return Math.abs(check.Check(rand1, rand2) - Double.parseDouble(input.getText())) < .001f;
			} catch(Exception ex){
				return false;
			}
		}
	}
}
