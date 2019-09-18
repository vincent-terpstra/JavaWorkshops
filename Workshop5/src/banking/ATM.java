package banking;

/**
 * @File	ATM.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 13, 2019
 * @Purpose Workshop 5 Task 1
 * @Class ATM.java 
 * 		User interface for an ATM
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;


public class ATM extends javafx.application.Application {
	public static void main(String[] args){ launch(args); }
		private Stage primaryStage;
		private RegexTextField 
			accNumInput;
		private	Button	
			submit;
		private Text 
			accNum,
			title;
		private Account
			account;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		accNum = new Text("");
			accNum.setLayoutX(300);
			accNum.setLayoutY( 68);
		accNumInput = new RegexTextField("[0-9]*");
			accNumInput.setLayoutX(300);
			accNumInput.setLayoutY(50);
		loginScene = new MyScene();
		submit = loginScene.addButton("Submit", 200, 200);
		title  = loginScene.addText("", 120, 68);
			accNumInput.setOnKeyPressed(event->{ if(event.getCode() == KeyCode.ENTER) submit.fire();});
			submit.setOnKeyPressed(event->{ if(event.getCode() == KeyCode.ENTER) submit.fire();});
		setLoginScreen();
		primaryStage.setTitle("ATM");
		primaryStage.show();
		primaryStage.setResizable(false);
	}
	@Override
	public void stop(){
		//save the accounts
		AccountManager.createSerializedAccounts();
	}
	
	MyScene loginScene;
	private void setLoginScreen(){
		accNumInput.setLayoutY(50);
		accNumInput.setText("");
		title.setText("Enter an account number:");
		accNum.setText("");
		submit.setOnAction(
			event -> {
				if(!accNumInput.getText().equals("")){
					accNum.setText(accNumInput.getText());
					int ID = Integer.parseInt(accNumInput.getText());
					account = AccountManager.getAccount(ID);
					if(account.getID() == 0){
						account.setID(ID);
						setCreateScreen("Creating ");
					} else {
						setPinScreen();
					}
				} else {
					accNumInput.requestFocus();
				}
			}
		);
		loginScene.add(submit, accNumInput, title);
		primaryStage.setScene(loginScene);
		accNumInput.requestFocus();
	}
	
	MyScene createScreen;
	RegexTextField firstname;
	RegexTextField lastname;
	Text message;
	private void setCreateScreen(String type){
		if(createScreen == null){
				String alpha = "[A-Za-z- ]*";
				createScreen = new MyScene();
				message = createScreen.addText("", 120,  240);
				createScreen.addText("First Name:", 160,  98);
				createScreen.addText("Last  Name:", 160, 128);
				createScreen.addText("Change Pin:", 160, 158);
				firstname = createScreen.add(new RegexTextField(alpha), 300, 80);
				lastname  = createScreen.add(new RegexTextField(alpha), 300, 110);
				firstname.setOnKeyPressed(event->{ if(event.getCode() == KeyCode.ENTER) submit.fire();});
				lastname .setOnKeyPressed(event->{ if(event.getCode() == KeyCode.ENTER) submit.fire();});
			}
		accNumInput.setLayoutY(140);
		firstname.setText(account.firstName);
		lastname .setText(account.lastName);
		message  .setText("");
		accNumInput.setText("");
		title.setText(type + " Account:");
		submit.setOnAction(
				event ->{
					if(firstname.getText().equals("") || lastname.getText().equals("")){
						message.setText("ERROR: First name and last name cannot be empty");
					} else  {
						account.firstName = firstname.getText();
						account.lastName  = lastname.getText();
						if(!accNumInput.getText().equals(""))
							account.setPin(Integer.parseInt(accNumInput.getText()));
						if(!account.checkPin(0)){
							setAccountScreen();
						} else {
							message.setText("ERROR: Pin cannot be 0");
						}
					}
				}
			);
		createScreen.add(accNum,  title, accNumInput, submit);
		primaryStage.setScene(createScreen);
		firstname.requestFocus();
	}
	
	MyScene pinScreen;
	Text invalid;
	int attempts;
	private void setPinScreen(){
		if(pinScreen == null){
			pinScreen = new MyScene();
			invalid = pinScreen.addText("",  120,  108);
			pinScreen.addText("Enter your pin:", 120, 88);
		}
		invalid.setText("");
		title.setText("Enter an account number:");
		accNumInput.setText("");
		accNumInput.setLayoutY(70);
		attempts = 0;
		submit.setOnAction(
			event ->{
				String input = accNumInput.getText();
				if(input.equals("")){
					accNumInput.requestFocus();
				} else if(account.checkPin(Integer.parseInt(input))){
					setAccountScreen();
				} else if(attempts ++ < 3) {
					invalid.setText("Invalid attempts: " + attempts);
					accNumInput.requestFocus();
				} else {
					setLoginScreen();
				}
			}
		);
		pinScreen.add(accNum, submit, accNumInput, title);
		primaryStage.setScene(pinScreen);
		accNumInput.requestFocus();
	}
	
	MyScene accountScreen;
	Text user;
	private void setAccountScreen(){
		if(accountScreen == null){
			accountScreen = new MyScene();
			user = accountScreen.addText("", 120, 88);
			accountScreen.addButton("Check Balance", 200, 100)
				.setOnAction(event->{setBalanceScreen();});
			accountScreen.addButton("Withdraw", 200, 130)
				.setOnAction(event->{setDepositScreen("Withdraw");});;
			accountScreen.addButton("Deposit", 200, 160)
				.setOnAction(event->{setDepositScreen("Deposit");});
			accountScreen.addButton("Edit Account", 200, 190)
				.setOnAction(event->{ setCreateScreen("Editing ");});
			accountScreen.addButton("Signoff", 200, 220)
				.setOnAction(event->{ setLoginScreen();});
			balance = new Text("");
				balance.setLayoutX(300);
				balance.setLayoutY(118);
		}
		balance.setText(String.format("$%20.2f", account.getBalance()));
		user.setText("User: " + account.firstName + " " + account.lastName);
		title.setText("Welcome Account:");
		
		accountScreen.add(title, user, accNum);
		primaryStage.setScene(accountScreen);
	}
	
	MyScene balanceScreen;
	Text balance;
	
	private void setBalanceScreen(){
		if(balanceScreen == null){
			balanceScreen = new MyScene();
			balanceScreen.addText("Balance:", 160, 118);
			balanceScreen.addButton("Confirm", 200, 190)
				.setOnAction(event->{setAccountScreen();});
		}
		balance.setText(String.format("$%20.2f", account.getBalance()));
		balanceScreen.add(user, balance);
		primaryStage.setScene(balanceScreen);
	}
	
	MyScene depositScreen;
	Text type;
	TextField amount;
	private void setDepositScreen(String transaction){
		if(depositScreen == null){
			depositScreen = new MyScene();
			depositScreen.addText("Balance:", 160, 118);
			type = depositScreen.addText(transaction, 160, 148);
			depositScreen.addButton("Confirm", 200, 190)
				.setOnAction(event->{
					String txt = amount.getText();
					amount.setText("");
					double money = (txt.equals("") ? 0 : Double.parseDouble(txt));
					if(type.getText().startsWith("D")){ 
						account.deposit(money); 
					} else {
						account.withdraw(money);
					}
					setBalanceScreen();
				});
			depositScreen.addButton("Cancel", 200, 220)
				.setOnAction(event->{setAccountScreen();});
			amount = depositScreen.add(new RegexTextField("^[0-9]*.[0-9]{0,2}$"), 300, 138);
		}
		type.setText(transaction);
		depositScreen.add(user, balance);
		primaryStage.setScene(depositScreen);
		amount.requestFocus();
	}
	
	class MyScene extends Scene {
		public MyScene(){
			this(new Group());
		}
		public MyScene(Group group) {
			super(group, 600, 300);
			this.group = group;
		}
		final Group group;
		Button addButton(String input, double x, double y){
			Button button = new Button( input );
				button.setMinWidth(200);
				add(button, x, y);
				return button;
		}
		Text addText(String input, double x, double y){
			return add(new Text(input), x, y);
		}
		<T extends Node>
		T add(T node, double x, double y){
			node.setLayoutX(x);
			node.setLayoutY(y);
			add(node);
			return node;
		}
		
		void add(Node... nodes){
			for(Node node : nodes){
				try { group.getChildren().add(node);
				} catch(Exception ex){} //throw out scene already has element error
			}
		}
	}
	
	private static final TextField field = new TextField();
	static class RegexTextField extends TextField {
		final String regex;
		RegexTextField(String regex){
			this.regex = regex;
		}
		
		@Override
		public void replaceText(int start, int end, String text){
			field.setText(getText());
			field.replaceText(start, end, text);
			if(field.getText().matches(regex))
				super.replaceText(start, end, text);
		}
	}
}
