//Standard javafx imports.
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


//Imports for this application.
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
//Menus and menuitems.
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


//Image support.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Pos and insets
import javafx.geometry.Pos;
import javafx.geometry.Insets;


//Imports for layout.
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


//Import to support application exit
import javafx.application.Platform;

/**
 *                   CEM BUGEY 3014176  && ELVIS LESI 3011081
 */
public class CardsHiLoGUI extends Application {
	
	private int winCounter = 0;  
	// Labels
	private Label lblFirstCard, lblSecondCard, lblPrompt, lblResult, lblProgress;
	// Menu bar
	private MenuBar mBar;
	
	private ImageView imvFirstCard, imvSecondCard;
	private Image imgFirstCard, imgSecondCard;
	
	private Button btnDealFirst, btnDealSecond;
	
	// Radio buttons for higher/lower
	private RadioButton rdHigher, rdLower;
	
	// Create a deck of cards
	private DeckOfCards deck;
	
	// Cards
	private Card leftCard;
	private Card rightCard;
	
	// Progress bar and indicator
	private ProgressBar progBar;                
	private ProgressIndicator progInd;          
	
               
	
	// Default constructor
	public CardsHiLoGUI() {
		
	}//constructor()
	
	@Override
	public void init() {
		
		// instantiate a new deck of cards
		deck = new DeckOfCards();
		
		// Shuffle the deck at the start
		deck.shuffle();        
	
		// instantiate labels
		lblFirstCard = new Label("First Card Dealt:");
		lblSecondCard = new Label("Second Card Dealt:");	
		lblResult = new Label("");     
		lblPrompt = new Label("Next card will be:");
		
		// instantiate a menu bar
		mBar = new MenuBar();
		
		// File menu
		Menu fileMenu = new Menu("File");
		// Menu item to add to File menu
		MenuItem fileNewGameItem = new MenuItem("New Game");
		fileMenu.getItems().add(fileNewGameItem);
		// Handle click events on the New Game menu item 
		fileNewGameItem.setOnAction(ae -> {            
			// Shuffle the deck
			deck.shuffle();  
			// Update default cards
			dealFirstCard();   
			dealSecondCard(); 
			lblResult.setText(""); 
			// Reset all progress when you start a new game
	        double progValue = 0;                 
	        progBar.setProgress(progValue);       
			progInd.setProgress(progValue); 
			
			});   // fileNewGameItem.setOnAction()
		
		// Shuffle item
		MenuItem fileShuffleItem = new MenuItem("Shuffle");
		fileMenu.getItems().add(fileShuffleItem);
		// Handle click events on the shuffle menu item
		fileShuffleItem.setOnAction(ae -> {  
			// Shuffle the deck
			deck.shuffle();   
			// Update left and right cards
			// Update the left card
			dealFirstCard(); 
			
			// Update the right card
			// if the deck is empty(no card left on the deck), display an error message 
			if (deck.isEmpty()) {        
				// Create an alert
				Alert alert = new Alert(AlertType.INFORMATION);		 		
				
				// Add a heading
				alert.setHeaderText("WARNING!");      
				
				// Print an error message
				alert.setContentText("Deck is empty! Please start a new game or shuffle the deck." );          
										
				alert.showAndWait();           
			}// deck.isEmpty()
			
			//Deal the card on the right hand side in response to a button click	
			rightCard = deck.dealTopCard();
			
			// Create an image for the right card	
			imgSecondCard = new Image("./images/cards/" + rightCard.toString() + ".png");
			// Set the image into the image view
			imvSecondCard.setImage(imgSecondCard);
			
			
		}); // fileShuffleItem.setOnAction()
		
		// Exit item
		MenuItem fileExitItem = new MenuItem("Exit");
		fileMenu.getItems().add(fileExitItem);
		//Handle click events on the exit menu item.
		fileExitItem.setOnAction(ae -> Platform.exit());   
		
		mBar.getMenus().add(fileMenu);
		
		// Help menu
		Menu fileHelp = new Menu("Help");
		// Menu item to add to Help menu
		MenuItem fileAboutItem = new MenuItem("About");
		fileHelp.getItems().add(fileAboutItem);
		

		//Handle click events on the About menu item
		fileAboutItem.setOnAction(ae -> {
			// show name and student number in a dialog
			showDialog();					
		});  // fileAboutItem.setOnAction()
		
		mBar.getMenus().add(fileHelp);
		
		// Default random cards to be shown at the start
		leftCard = deck.dealTopCard();
		rightCard = deck.dealTopCard();
		
		// Show left and right card images
		imgFirstCard = new Image("file:./images/cards" + leftCard.toString());
		imvFirstCard = new ImageView();
		imvFirstCard.setImage(imgFirstCard);
		
		imgSecondCard = new Image("file:./images/cards" + rightCard.toString());
		imvSecondCard = new ImageView();
		imvSecondCard.setImage(imgSecondCard);
		 		
		
		// Buttons 
		btnDealFirst = new Button("<- Deal First Card");
		btnDealSecond = new Button("Deal Second Card ->");
		
		// Set button widths
		btnDealFirst.setMinWidth(148);
		btnDealSecond.setMinWidth(120);
		
		// Radio buttons
		rdLower = new RadioButton("Lower");
		rdHigher = new RadioButton("Higher");
		
		// Create a toggle group for the radio buttons
		ToggleGroup hiloGroup = new ToggleGroup();
		
		// Add the radio buttons to the toggle group. Make them mutually exclusive
		rdLower.setToggleGroup(hiloGroup);
		rdHigher.setToggleGroup(hiloGroup);
				
		
		/* When the program runs first time, "Dead Second Card" button must be disabled, 
		because radio buttons have not been selected yet
		*/
		btnDealSecond.setDisable(true);
		
		// Handle click events on buttons
		btnDealFirst.setOnAction(ae -> {
			// Deal left card
			dealFirstCard();
			// When 'Deal First Card' button clicked, change its size slightly as a click effect
			btnDealFirst.setMinWidth(150);   
			// Turn 'Deal Second Card' button to normal size
			btnDealSecond.setMinWidth(120);  
			// Enable 'Deal Second Card' button
			btnDealSecond.setDisable(false);   
		});
		btnDealSecond.setOnAction(ae -> {
			// Deal second card
			dealSecondCard();
			// Turn 'Deal First Card' button to normal size
			btnDealFirst.setMinWidth(148);
			// When 'Deal Second Card' button clicked, change its size slightly as a click effect
			btnDealSecond.setMinWidth(130);
		});
		
		// Handle events on the radio buttons. Enable 2nd. card dealing
		rdLower.setOnAction(ae -> btnDealSecond.setDisable(false));
		rdHigher.setOnAction(ae -> btnDealSecond.setDisable(false));
		
		
		
		// Deal a card to show a random default card on the left
		dealFirstCard(); 
		// Deal a card to show a random default card on the right
		rightCard = deck.dealTopCard();        
		imgSecondCard = new Image("./images/cards/" + rightCard.toString() + ".png");    
		//Set the image into the image view
		imvSecondCard.setImage(imgSecondCard);      
		
		// Set initial progress to zero
		progBar = new ProgressBar(0);  
		// Set the width of the progress bar
		progBar.setMinWidth(148);                     
		// Set initial progress to zero
		progInd = new ProgressIndicator(0);            
		// Set the size of the progress bar			   
		progInd.setMinSize(60, 60);	                   
		
		// initialize the Progress label
		lblProgress = new Label("Progress");    
		
		// 'Higher' button is selected by default to make sure a radio button is selected before guessing 
		rdHigher.setSelected(true);
		
		// Disable radio buttons at the start (they will be enabled after 1st card dealt)
		rdLower.setDisable(true);             
		rdHigher.setDisable(true);            
			
	}//init()
	
	// Deal a card to show on the left 
	public void dealFirstCard() {
		rdLower.setDisable(false);
		rdHigher.setDisable(false);
	
		// if the deck is empty(no card left on the deck), display an error message 
		if (deck.isEmpty()) {          
			// Create an alert
			Alert alert = new Alert(AlertType.INFORMATION);		 				
			
			// Add a heading 
			alert.setHeaderText("WARNING!");              
			
			// Print an error message 
			alert.setContentText("Deck is empty! Please start a new game or shuffle the deck." );          
									
			alert.showAndWait();          
		}// deck.isEmpty()
		
		//Deal the card on the left hand side in response to a button click
		leftCard = deck.dealTopCard();
		// Create an image for the left card	
		imgFirstCard = new Image(getClass().getResource("./images/cards/" + leftCard.toString() + ".png").toString());
		// Set the image into the image view
		imvFirstCard.setImage(imgFirstCard);
				
		
	}// dealFirstCard()
	
	// Deal a card to show on the right
	public void dealSecondCard() {
		// Disable radio buttons after 2nd card dealt, they will be enabled again after 1st card dealt 
		rdLower.setDisable(true);             
		rdHigher.setDisable(true);            
		
		// if the deck is empty(no card left on the deck), display an error message 
		if (deck.isEmpty()) {        
			// Create an alert
			Alert alert = new Alert(AlertType.INFORMATION);		
			
			// Add a heading
			alert.setHeaderText("WARNING!");      
			
			// Print an error message
			alert.setContentText("Deck is empty! Please start a new game or shuffle the deck." );          
									
			alert.showAndWait();           
		}// deck.isEmpty()
		
		//Deal the card on the right hand side in response to a button click	
		rightCard = deck.dealTopCard();
		
		// Create an image for the right card	
		imgSecondCard = new Image("./images/cards/" + rightCard.toString() + ".png");
		// Set the image into the image view
		imvSecondCard.setImage(imgSecondCard);

		//Get the card result. Result is either 0, 1, 2 (same, lower, higher)
		int cardResult = getSecondCardResult(leftCard, rightCard);
		
		//Additional information for the user
		String cardValue = "";
		
		// if the cards have equal rank
		if(rightCard.isEqual(leftCard)) {
			cardValue = "Card dealt is the same.\n";
		}
		// if the rank of the right card is lower than that of the left card
		else if (rightCard.isLower(leftCard)) {
			cardValue = "Card dealt is lower.\n";
		}
		// if the rank of the right card is higher than that of the left card
		else {
			cardValue = "Card dealt is higher.\n";
		}
		
		// if user guessed correctly, update the label, increase the win counter, update the progress
		if(isUserGuessCorrect(cardResult)) {
			// Update the label
			lblResult.setText( cardValue + "You win!");
			// increase the win counter
			winCounter++;
			
			
			// Get the current value of the progress
			double progValue = progBar.getProgress();
				
			// increase the progress by 0.2
			progValue = progValue + 0.2;
				
			// Set color for the progress bar and indicator
			if(progValue <= 0.2) {
				// Set a style to show light grey color
				progBar.setStyle("-fx-accent:rgb(201, 191, 191);");
				progInd.setStyle("-fx-accent:rgb(201, 191, 191);");
				
			}// if
			else if(progValue <= 0.4) {
				// Set a style to show light grey-red color
				progBar.setStyle("-fx-accent:rgb(201, 145, 145);");
				progInd.setStyle("-fx-accent:rgb(201, 145, 145);");
					
			}// else if
			else if(progValue <= 0.6) {
				// Set a style to show light grey-red color
				progBar.setStyle("-fx-accent:rgb(199, 93, 93);");
				progInd.setStyle("-fx-accent:rgb(199, 93, 93);");
				
			}// else if
			else if(progValue <= 0.8) {
				// Set a style to show red color
				progBar.setStyle("-fx-accent:rgb(196, 51, 51);");
				progInd.setStyle("-fx-accent:rgb(196, 51, 51);");
				
			}// else if
			else {
				//Set a style to show red color
				progBar.setStyle("-fx-accent:rgb(194, 17, 17);");
				progInd.setStyle("-fx-accent:rgb(194, 17, 17);");
			}// else

				
			// Keep the progress bar in range. increasing, so should not pass 1.
			if(progValue <= 1) {
				progBar.setProgress(progValue);
				progInd.setProgress(progValue);
			}// if
			else {
				// Set the progress to 1
				progValue = 1;
				progBar.setProgress(progValue);
				progInd.setProgress(progValue);
			}// else
				
			
			// Check if there is an overall win (5+)
			if(winCounter >= 5) {
				// Update the label
				lblResult.setText(cardValue + "You won " +
			winCounter + " times.\n" + "Overall win!\n");
			}
		}// if(isUserGuessCorrect())
		
		// if the user guess isn't correct
		else {
			// Update the label
			lblResult.setText(cardValue + "You lose!");		
			// Reset the win counter
			winCounter = 0;
			// Reset the progress
	        double progValue = 0;                 
	        progBar.setProgress(progValue);      
			progInd.setProgress(progValue);       
			
		}// else
		
		// Finally, disable the deal second card button
		btnDealSecond.setDisable(true);
	} // dealSecondCard()
	
	// A method that returns true if the user guesses correctly, otherwise returns false
	private boolean isUserGuessCorrect(int res) {
		// An integer variable to store the result
		int result = res;
		boolean win = false;
		
		//The second card is either:
		//1: Equal (0)
		//2: Lower (1)
		//3: Higher (2)
		
		switch (result) {
		case (0):
			win = false;
		    break;
		case (1):
			if(rdLower.isSelected()) {
				win = true;
			}
			else {
				win = false;
			}
			break;
		
		case (2):
			if(rdHigher.isSelected()) {
				win = true;
			}
			else {
				win = false;
			}
			break;
			
		default:
			win = false;
			break;
		}
		
		return win;
				
	}// isUserGuessCorrect()
	
	// A method that returns the result after comparing two cards
	private int getSecondCardResult(Card leftC, Card rightC) {
		
		//The second (right) card is either:
		//1: Equal (0)
		//2: Lower (1)
		//3: Higher (2)
		
		// An integer variable to store the result
		int result = 0;
		
		if(rightC.isEqual(leftC)) {
			result = 0;
		}
		else if(rightC.isLower(leftC)) {
			result = 1;
		}
		else if(rightC.isHigher(leftC)) {
			result = 2;
		}
		
		return result;
	}// getSecondCardResult()
	
	// A methods that opens a dialog(after clicking 'About')
	public void showDialog(){
		// Create a new stage
		Stage dialog = new Stage();
		// Set the width and height
		dialog.setWidth(240);
		dialog.setHeight(210);
		// Set the title
		dialog.setTitle("About");
		
		dialog.setMaximized(false);
		dialog.setAlwaysOnTop(true);
							
		// Student name and number to show on the dialog
		Label lblName1 = new Label("Cem Bugey");
		Label lblNumber1 = new Label("3014176");
		Label lblName2 = new Label("Elvis Lesi");
		Label lblNumber2 = new Label("3011081");
		
		
		// Button of the dialog
		Button btnOK = new Button("Got it!");  
		// Set the width of the button
		btnOK.setMinWidth(70);
		
		
		//Handle clicks on the button.
		btnOK.setOnAction(ae -> {
			// Close the dialog
			dialog.close();		
		}); //btnOK
		
		// Create a layout
		GridPane dgp = new GridPane();
		// Add labels and the button to the layout
		dgp.add(lblName1, 0, 0);
		dgp.add(lblNumber1, 0, 1);
		dgp.add(lblName2, 0, 2);
		dgp.add(lblNumber2, 0, 3);
		dgp.add(btnOK, 6, 4);
		
			
		// Extra configuration for the layout
		dgp.setPadding(new Insets(15));
		dgp.setHgap(10);
		dgp.setVgap(10);

				
		// Create a scene
		Scene s = new Scene(dgp,400,250);
		// Add a style sheet
		s.getStylesheets().add("style_CardsHiLoGUI.css"); 
		// Set the scene
		dialog.setScene(s);
		dialog.show();
			
	}//showDialog()

	@Override
	public void start(Stage pStage) throws Exception {
		// Set the width and height
		pStage.setWidth(480);
		pStage.setHeight(400);
		
		//Set the title
		pStage.setTitle("Cards HiLo 2020 V 1.0.0");
		
		// Create a layout. The vbox is the main container
		VBox mainBox = new VBox();
		mainBox.getChildren().add(mBar);
		
		GridPane gp = new GridPane();
		
		// Configure the grid pane
		gp.setPadding(new Insets(10));
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.BASELINE_CENTER);
		
		// Add components
		gp.add(lblFirstCard, 0, 0);
		gp.add(lblSecondCard, 2, 0);
		gp.add(imvFirstCard, 0, 1, 1, 8);
		gp.add(imvSecondCard, 2, 1, 1, 8);
		gp.add(lblResult, 2, 9);
		gp.add(lblPrompt, 1, 2);
		gp.add(rdHigher, 1, 3);
		gp.add(rdLower, 1, 4);
		gp.add(btnDealFirst, 1, 5);
		gp.add(btnDealSecond, 1, 6);
		gp.add(lblProgress, 1, 7);
		gp.add(progBar, 1, 8);
		gp.add(progInd, 1, 9);
		
				
		//Add the gp to the VBox
		mainBox.getChildren().add(gp);
		
		//Create a scene
		Scene scene = new Scene(mainBox, 478, 380);
		// Add a style sheet
		scene.getStylesheets().add("style_CardsHiLoGUI.css");    
		//Set the scene
		pStage.setScene(scene);
		
		//Show the stage.
		pStage.show();
				
	}

    // main method
	public static void main(String[] args) {
		
		//Launch the application.
		launch();

	} // main()

} // CardsHiLoGUI class
