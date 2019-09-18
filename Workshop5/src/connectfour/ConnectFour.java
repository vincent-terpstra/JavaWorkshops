package connectfour;

/**
 * @File	ConnectFour.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 14, 2019
 * @Purpose Workshop 5 Task 3
 * @Class ConnectFour.java 
 * 		User interface for an Connect four game
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConnectFour extends Application {
	public static void main(String[] args){ launch(args); }
	@Override
	public void start(Stage primaryStage) throws Exception {
		MyScene scene = new MyScene(600, 500);
			(indicator = scene.add(new MyCircle(), 300, 40)).player(1);
			reset = scene.add(new Button(), 200, 10);
			reset.setOnAction(event ->resetGame());
			reset.setMinWidth(200);
			
		Rectangle rect = scene.add( new Rectangle(490, 420), 55, 60);
		rect.setFill(Color.LIGHTBLUE);
		rect.setOnMouseMoved(
			event->{//align the player indicator at the top of the screen with the mouse
					if(endGame) return;
					indicatorIdx = ((int)event.getSceneX() - 55) / 70;
					indicator.setLayoutX( indicatorIdx * 70 + 90);
				}
			);
		scene.setOnMouseClicked(
			event->{
				//add a piece where the player clicked
				if(endGame) return;
				int column = indicatorIdx;
				for(int row = 0; row < 6; ++row){
					MyCircle tmp = circles[column][row];
					if(tmp.type == 0){
						tmp.player(indicator.type);
						++piecesPlayed;
						this.addColumn = column;
						this.addRow 	= row;
						this.addType = indicator.type;
						checkWin();
						indicator.player(indicator.type % 2 + 1);		
						return;
					}
				}
			}
		);
		
		circles = new MyCircle[7][6];
		for(int column = 0; column < 7; ++column){
			for(int row = 0; row < 6; ++ row){
					circles[column][5 - row] = scene.add(new MyCircle(), 90 + 70 * column, 95 + 70 * row );
			}
		}
		resetGame();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Connect Four");
		primaryStage.show();
		primaryStage.setResizable(false);	
	}
	MyCircle[][] circles;
	MyCircle indicator;
	Button reset;
	boolean endGame = false;
	private int addColumn, addRow, addType, piecesPlayed, indicatorIdx;
	
	/**
	 * Function to check if the last piece played won/ended the game
	 */
	void checkWin(){
		addType = circles[addColumn][addRow].type;
		if(checkLine( 0,  1) || //horizontal
		   checkLine( 1,  1) || //diagonal up
		   checkLine( 1, -1) || //diagonal down
		   checkLine( 1,  0)){  //vertical
			endGame = true;
			reset.setText("Player " + (addType == 1 ? "one" : "two") + " wins!");
		}
		if(piecesPlayed == 42) endGame = true;
		if(endGame){
			indicator.setVisible(false);
			reset.setVisible(true);
		}
	}
	/**
	 * Sums up the pieces in the line of the last piece
	 */
	private final boolean checkLine(int dx, int dy){
		return sum(addColumn, addRow, dx, dy) + 1 + sum( addColumn, addRow, -dx, -dy) >= 4;	
	}
	/**
	 * @return Total pieces in a line that are the same as the last piece
	 */
	private final int sum(int column, int row, int dx, int dy){
		int sum = 0;
		while(check(column+=dx, row+=dy)) sum++;
		return sum;
	}
	/**
	 * prevent the check from exiting the bounds and confirm that the piece is the same type
	 */
	private final boolean check(int column, int row){
		return !(row < 0 || row > 5 || column > 6 || column < 0) && circles[column][row].type == addType;
	}
	
	/**
	 * Reset the Game back to it's starting conditions
	 */
	private void resetGame() {
		indicator.setVisible(true);
		reset.setVisible(false);
		reset.setText("Game Over!");
		piecesPlayed = 0;
		endGame = false;
		for(int column = 0; column < 7; ++ column){
			for(int row = 0; row < 6; ++ row){
				circles[column][row].reset();
			}
		}
	}
	
	class MyCircle extends Circle {
		MyCircle(){
			super(30);
			setStroke(Color.BLACK);
		}
		int type;
		void reset(){
			type = 0;
			setFill(Color.GHOSTWHITE);
		}
		
		void player(int type){
			this.type = type;
			setFill(type == 1 ? Color.GOLDENROD : Color.CRIMSON);
		}
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
		<T extends Node>
		T add(T node, double x, double y){
			node.setLayoutX(x);
			node.setLayoutY(y);
			group.getChildren().add(node);
			return node;
		}
	}
}
