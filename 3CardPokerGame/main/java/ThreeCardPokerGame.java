import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreeCardPokerGame extends Application {
    Scene scene1;
    private MenuBar menu = new MenuBar();
    Menu optionsMenu = new Menu("Options");
    MenuItem exitMenuOption = new MenuItem("Exit");
    MenuItem freshStartMenuOption = new MenuItem("Fresh Start");
    MenuItem newLookMenuOption = new MenuItem("New Look");

    private Button startButton = new Button("Start Game");;
    private Button saveWagersAndStart;
    private Button goToSummary;
    private Button startNewGame;
    private TextField player1AnteWager;
    private TextField player1PairWager;

    private TextField player2AnteWager;
    private TextField player2PairWager;
    private boolean dealerQueenHigh;
    private boolean player1Tie;
    private boolean player2Tie;
    AtomicBoolean player1Play = new AtomicBoolean(false);
    AtomicBoolean player2Play = new AtomicBoolean(false);
    AtomicBoolean player1Fold = new AtomicBoolean(false);
    AtomicBoolean player2Fold = new AtomicBoolean(false);

    private Player player1;
    private Player player2;
    private Dealer theDealer;
    private BorderPane borderPane;

    private Label dealerQueenLabel;
    private Label player1WinLossLabel;
    private Label player2WinLossLabel;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    //Start game
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Project 2");

        //Font
        Font font = Font.font("Courier New", FontWeight.BOLD, 18);
        Font menuFont = Font.font("Courier New", FontWeight.BOLD, 20);

        //Initialize game
        theDealer = new Dealer();
        player1 = new Player();
        player2 = new Player();
        dealerQueenHigh = true;
        player1Tie = false;
        player2Tie = false;
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #F0F8FF;");
        startButton.setStyle("-fx-background-color: #87CEFA;");
        startButton.setFont(font);
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);

        //Menu set up
        optionsMenu.getItems().add(exitMenuOption);
        optionsMenu.getItems().add(freshStartMenuOption);
        optionsMenu.getItems().add(newLookMenuOption);
        ImageView menuImg = new ImageView("menu.png");
        menuImg.setFitHeight(30);
        menuImg.setPreserveRatio(true);
        optionsMenu.setGraphic(menuImg);
        menu.getMenus().addAll(optionsMenu);
        menu.setStyle("-fx-background-color: #F0F8FF;");
        menu.setPadding(new Insets(0, 15, 0, 15));
        borderPane.setTop(menu);

        //Set up start
        Image startImg = new Image("startImg.png");
        ImageView startImgView = new ImageView(startImg);
        startImgView.setFitHeight(400);
        startImgView.setPreserveRatio(true);
        VBox middle = new VBox(startImgView, startButton);
        middle.setAlignment(Pos.CENTER);
        borderPane.setCenter(middle);

        //Players hit play button to start game
        startButton.setOnAction(e-> {
            play();
        });

        //Closes game
        exitMenuOption.setOnAction(e-> {
            primaryStage.close();
        });

        //New look
        newLookMenuOption.setOnAction(e-> {
            Random random = new Random();
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);
            borderPane.setStyle("-fx-background-color: rgb(" + r + "," + g + ", " + b + ");");
            menu.setStyle("-fx-background-color: rgb(" + r + "," + g + ", " + b + ");");
        });

        //Players want an fresh start, reset everything
        freshStartMenuOption.setOnAction(e-> {
            theDealer = null;
            player1 = null;
            player2 = null;
            theDealer = new Dealer();
            player1 = new Player();
            player2 = new Player();
            dealerQueenHigh = true;
            player1Tie = false;
            player2Tie = false;
            goToStartPage();
        });

        scene1 = new Scene(borderPane, 700,700);

        // sets scene and displays it
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setScene(scene1);
        primaryStage.setMaximized(true); // makes scene be the screen size
        primaryStage.show();
    }

    //Go back to start game page
    public void goToStartPage() {
        //Reset everything
        borderPane.setLeft(null);
        borderPane.setRight(null);
        borderPane.setCenter(null);

        //Put image and button back in
        Image startImg = new Image("startImg.png");
        ImageView startImgView = new ImageView(startImg);
        startImgView.setFitHeight(400);
        startImgView.setPreserveRatio(true);
        VBox middle = new VBox(startImgView, startButton);
        middle.setAlignment(Pos.CENTER);
        borderPane.setCenter(middle);
    }

    //Start game and ask players for bets
    public void play() {
        //Deal cards
        theDealer.dealersHand = theDealer.dealHand();
        player1.hand = theDealer.dealHand();
        player2.hand = theDealer.dealHand();
        player1.playBet = 0;
        player2.playBet = 0;
        borderPane.setPadding(new Insets(0, 20, 0, 20));

        //Players wager boxes
        player1AnteWager = new TextField();
        player1AnteWager.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 15));
        player1AnteWager.setFocusTraversable(false);
        player1AnteWager.setPrefWidth(400);
        player1AnteWager.setAlignment(Pos.CENTER);
        player1AnteWager.setPadding(new Insets(10, 10, 10, 10));

        player1PairWager = new TextField();
        player1PairWager.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 15));
        player1PairWager.setFocusTraversable(false);
        player1PairWager.setPrefWidth(400);
        player1PairWager.setAlignment(Pos.CENTER);
        player1PairWager.setPadding(new Insets(10, 10, 10, 10));

        player2AnteWager = new TextField();
        player2AnteWager.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 15));
        player2AnteWager.setFocusTraversable(false);
        player2AnteWager.setPrefWidth(400);
        player2AnteWager.setAlignment(Pos.CENTER);
        player2AnteWager.setPadding(new Insets(10, 10, 10, 10));

        player2PairWager = new TextField();
        player2PairWager.setFont(Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 15));
        player2PairWager.setFocusTraversable(false);
        player2PairWager.setPrefWidth(400);
        player2PairWager.setAlignment(Pos.CENTER);
        player2PairWager.setPadding(new Insets(10, 10, 10, 10));

        //If there wasn't a dealer queen high in previous game
        if (!dealerQueenHigh) {
            player1AnteWager.setText(Integer.toString(player1.anteBet));
            player1AnteWager.setDisable(true);
            player1PairWager.setPromptText("Player 1 - Enter Pair Plus Wager ($5 - $25)");
            player2AnteWager.setText(Integer.toString(player2.anteBet));
            player2AnteWager.setDisable(true);
            player2PairWager.setPromptText("Player 1 - Enter Pair Plus Wager ($5 - $25)");
        } else {
            if (player1Tie && player1Play.get()) { //If player 1 didn't fold and tied with the dealer the previous game, push previous ante bet
                player1AnteWager.setText(Integer.toString(player1.anteBet));
                player1AnteWager.setDisable(true);
                player1PairWager.setPromptText("Player 1 - Enter Pair Plus Wager ($5 - $25)");
            } else {
                player1AnteWager.setPromptText("Player 1 - Enter Ante Wager ($5 - $25)");
                player1PairWager.setPromptText("Player 1 - Enter Pair Plus Wager ($5 - $25)");
            }
            if (player2Tie && player2Play.get()) { //If player 2 didn't fold and tied with the dealer the previous game, push previous ante bet
                player2AnteWager.setText(Integer.toString(player2.anteBet));
                player2AnteWager.setDisable(true);
                player2PairWager.setPromptText("Player 2 - Enter Pair Plus Wager ($5 - $25)");
            } else {
                player2AnteWager.setPromptText("Player 2 - Enter Ante Wager ($5 - $25)");
                player2PairWager.setPromptText("Player 2 - Enter Pair Plus Wager ($5 - $25)");
            }
        }
        saveWagersAndStart = new Button("Confirm wagers and start game");
        saveWagersAndStart.setStyle("-fx-background-color: #67f58f");
        saveWagersAndStart.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        saveWagersAndStart.setPadding((new Insets(10, 15, 10, 15)));

        //Reset play and fold boolean values
        player1Play.set(false);
        player1Fold.set(false);
        player2Play.set(false);
        player2Fold.set(false);

        //Error message if no ante wagers
        Alert errorAnte = new Alert(Alert.AlertType.ERROR);
        errorAnte.setHeaderText("No input");
        errorAnte.setContentText("Each player must enter an ante wager between 5 and 25");

        //Error message if wagers are invalid values
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Input not valid");
        error.setContentText("Ante wager must be between 5 and 25 \n Entered pair plus wager must be between 5 and 25");

        //Error message if values are not integers
        Alert errorValues = new Alert(Alert.AlertType.ERROR);
        errorValues.setHeaderText("Input not valid");
        errorValues.setContentText("Wagers must be integer values between 5 and 25");

//		Players start the game -> shows textboxes for players to enter ante bets
        borderPane.setCenter(null);
        Text textPlayer1 = new Text("Player 1 Wagers");
        textPlayer1.setFont((Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 20)));
        textPlayer1.setTextAlignment(TextAlignment.CENTER);
        VBox player1Wagers = new VBox(textPlayer1, player1AnteWager, player1PairWager);
        player1Wagers.setSpacing(20);
        player1Wagers.setPadding(new Insets(15, 0, 5, 15));
        borderPane.setLeft(player1Wagers);
        Text textPlayer2 = new Text("Player 2 Wagers");
        textPlayer2.setFont((Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 20)));
        textPlayer2.setTextAlignment(TextAlignment.CENTER);
        VBox player2Wagers = new VBox(textPlayer2, player2AnteWager, player2PairWager);
        player2Wagers.setSpacing(20);
        player2Wagers.setPadding(new Insets(15, 0, 5, 15));
        borderPane.setRight(player2Wagers);
        borderPane.setCenter(saveWagersAndStart);


//		Players want entered their bets and start the game
        saveWagersAndStart.setOnAction(e-> {
            boolean validAntes = false, antesNumsInRange = false, pairPlusValid1 = false, pairPlusValid2 = false;
            int player1AnteWagerAmount = 0, player2AnteWagerAmount = 0, player1PairPlusWagerAmount = 0, player2PairPlusWagerAmount = 0;
            if (player1AnteWager.getText().equals("") || player2AnteWager.getText().equals("")) {
                errorAnte.showAndWait();
            } else {
                try {
                    player1AnteWagerAmount = Integer.parseInt(player1AnteWager.getText());
                    player2AnteWagerAmount = Integer.parseInt(player2AnteWager.getText());
                    validAntes = true;
                } catch (NumberFormatException a) {
                    errorValues.showAndWait();
                }

                if (validAntes) {
                    if (player1AnteWagerAmount < 5 || player1AnteWagerAmount > 25 || player2AnteWagerAmount < 5 || player2AnteWagerAmount > 25) {
                        error.showAndWait();
                    } else {
                        antesNumsInRange = true;
                    }
                }

                boolean notValidNums = false, noParse = false;
                if (player1PairWager.getText().equals("")) {
                    pairPlusValid1 = true;
                } else {
                    try {
                        player1PairPlusWagerAmount = Integer.parseInt(player1PairWager.getText());
                        if (player1PairPlusWagerAmount < 5 || player1PairPlusWagerAmount > 25) {
                            notValidNums = true;
                        } else {
                            pairPlusValid1 = true;
                        }
                    } catch (NumberFormatException a) {
                        noParse = true;
                    }
                }

                if (player2PairWager.getText().equals("")) {
                    pairPlusValid2 = true;
                } else {
                    try {
                        player2PairPlusWagerAmount = Integer.parseInt(player2PairWager.getText());
                        if (player2PairPlusWagerAmount < 5 || player2PairPlusWagerAmount > 25) {
                            notValidNums = true;
                        } else {
                            pairPlusValid2 = true;
                        }
                    } catch (NumberFormatException a) {
                        noParse = true;
                    }
                }
                if (notValidNums){
                    error.showAndWait();
                }
                if (noParse) {
                    errorValues.showAndWait();
                }
            }

            //Move to game if all user inputs are valid
            if (validAntes && antesNumsInRange && pairPlusValid1 && pairPlusValid2) {
                borderPane.setCenter(null);
                borderPane.setLeft(null);
                borderPane.setRight(null);
                player1.anteBet = player1AnteWagerAmount;
                player2.anteBet = player2AnteWagerAmount;
                player1.pairPlusBet = player1PairPlusWagerAmount;
                player2.pairPlusBet = player2PairPlusWagerAmount;

                //Set dealer card images
                Image dealerCard1 = new Image("backOfCard.png");
                ImageView dealerCard1View = new ImageView(dealerCard1);
                dealerCard1View.setFitHeight(125);
                dealerCard1View.setPreserveRatio(true);
                ImageView dealerCard2View = new ImageView(dealerCard1);
                dealerCard2View.setFitHeight(125);
                dealerCard2View.setPreserveRatio(true);
                ImageView dealerCard3View = new ImageView(dealerCard1);
                dealerCard3View.setFitHeight(125);
                dealerCard3View.setPreserveRatio(true);
                Text textCardDealer = new Text("Dealer's Cards");
                textCardDealer.setFont((Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 20)));
                textCardDealer.setTextAlignment(TextAlignment.CENTER);
                HBox dealerCards = new HBox(dealerCard1View, dealerCard2View, dealerCard3View);
                dealerCards.setAlignment(Pos.TOP_CENTER);
                dealerCards.setPadding(new Insets(15, 15, 15, 15));
                dealerCards.setSpacing(20);
                VBox dealerSide = new VBox(textCardDealer, dealerCards);
                dealerSide.setAlignment(Pos.TOP_CENTER);
                borderPane.setCenter(dealerSide);

                //Buttons for players to choose play or fold
                Button player1play = new Button("Play");
                Button player1fold = new Button("Fold");
                Button player2play = new Button("Play");
                Button player2fold = new Button("Fold");

                //Set player 1 card images
                StringBuilder player1Card1 = new StringBuilder();
                player1Card1.append(String.valueOf(player1.hand.get(0).suit));
                player1Card1.append(String.valueOf(player1.hand.get(0).value));
                player1Card1.append(".png");
                String player1Card1Str = player1Card1.toString();
                Image player1Card1Img = new Image(player1Card1Str);
                ImageView player1Card1View = new ImageView(player1Card1Img);
                player1Card1View.setFitHeight(125);
                player1Card1View.setPreserveRatio(true);

                StringBuilder player1Card2 = new StringBuilder();
                player1Card2.append(String.valueOf(player1.hand.get(1).suit));
                player1Card2.append(String.valueOf(player1.hand.get(1).value));
                player1Card2.append(".png");
                String player1Card2Str = player1Card2.toString();
                Image player1Card2Img = new Image(player1Card2Str);
                ImageView player1Card2View = new ImageView(player1Card2Img);
                player1Card2View.setFitHeight(125);
                player1Card2View.setPreserveRatio(true);

                StringBuilder player1Card3 = new StringBuilder();
                player1Card3.append(String.valueOf(player1.hand.get(2).suit));
                player1Card3.append(String.valueOf(player1.hand.get(2).value));
                player1Card3.append(".png");
                String player1Card3Str = player1Card3.toString();
                Image player1Card3Img = new Image(player1Card3Str);
                ImageView player1Card3View = new ImageView(player1Card3Img);
                player1Card3View.setFitHeight(125);
                player1Card3View.setPreserveRatio(true);

                HBox player1Cards = new HBox(player1Card1View, player1Card2View, player1Card3View);
                player1Cards.setSpacing(20);
                player1Cards.setPadding(new Insets(15, 15, 15, 15));
                HBox player1Buttons = new HBox(player1play, player1fold);
                player1Buttons.setSpacing(20);
                player1Buttons.setAlignment(Pos.CENTER);
                Label player1AnteWagerLabel = new Label("Player 1 Ante Wager: $" + player1.anteBet);
                Label player1PairPlusWagerLabel = new Label("Player 1 Pair Plus Wager: $" + player1.pairPlusBet);
                Label player1PlayWagerLabel = new Label("Player 1 Play Wager: $" + player1.playBet);
                Label player1Winnings = new Label("Player 1 Total Winnings: $" + player1.totalWinnings);
                player1AnteWagerLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14)));
                player1PairPlusWagerLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14)));
                player1PlayWagerLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14)));
                player1Winnings.setFont((Font.font("Times New Roman", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 16)));
                Text textCardPlayer1 = new Text("Player 1 Cards");
                textCardPlayer1.setFont((Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 20)));
                textCardPlayer1.setTextAlignment(TextAlignment.CENTER);
                VBox player1Stats = new VBox(textCardPlayer1, player1Cards, player1Buttons, player1AnteWagerLabel, player1PairPlusWagerLabel, player1PlayWagerLabel, player1Winnings);
                player1Stats.setAlignment(Pos.CENTER);
                player1Stats.setSpacing(10);
                borderPane.setLeft(player1Stats);

                //Set player 2 card images
                StringBuilder player2Card1 = new StringBuilder();
                player2Card1.append(String.valueOf(player2.hand.get(0).suit));
                player2Card1.append(String.valueOf(player2.hand.get(0).value));
                player2Card1.append(".png");
                String player2Card1Str = player2Card1.toString();
                Image player2Card1Img = new Image(player2Card1Str);
                ImageView player2Card1View = new ImageView(player2Card1Img);
                player2Card1View.setFitHeight(125);
                player2Card1View.setPreserveRatio(true);

                StringBuilder player2Card2 = new StringBuilder();
                player2Card2.append(String.valueOf(player2.hand.get(1).suit));
                player2Card2.append(String.valueOf(player2.hand.get(1).value));
                player2Card2.append(".png");
                String player2Card2Str = player2Card2.toString();
                Image player2Card2Img = new Image(player2Card2Str);
                ImageView player2Card2View = new ImageView(player2Card2Img);
                player2Card2View.setFitHeight(125);
                player2Card2View.setPreserveRatio(true);

                StringBuilder player2Card3 = new StringBuilder();
                player2Card3.append(String.valueOf(player2.hand.get(2).suit));
                player2Card3.append(String.valueOf(player2.hand.get(2).value));
                player2Card3.append(".png");
                String player2Card3Str = player2Card3.toString();
                Image player2Card3Img = new Image(player2Card3Str);
                ImageView player2Card3View = new ImageView(player2Card3Img);
                player2Card3View.setFitHeight(125);
                player2Card3View.setPreserveRatio(true);

                HBox player2Cards = new HBox(player2Card1View, player2Card2View, player2Card3View);
                player2Cards.setPadding(new Insets(15, 15, 15, 15));
                player2Cards.setSpacing(20);
                HBox player2Buttons = new HBox(player2play, player2fold);
                player2Buttons.setSpacing(20);
                player2Buttons.setAlignment(Pos.CENTER);
                Label player2AnteWagerLabel = new Label("Player 2 Ante Wager: $" + player2.anteBet);
                Label player2PairPlusWagerLabel = new Label("Player 2 Pair Plus Wager: $" + player2.pairPlusBet);
                Label player2PlayWagerLabel = new Label("Player 2 Play Wager: $" + player2.playBet);
                Label player2Winnings = new Label("Player 2 Total Winnings: $" + player2.totalWinnings);
                player2AnteWagerLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14)));
                player2PairPlusWagerLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14)));
                player2PlayWagerLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 14)));
                player2Winnings.setFont((Font.font("Times New Roman", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 16)));
                Text textCardPlayer2 = new Text("Player 2 Cards");
                textCardPlayer2.setFont((Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 20)));
                textCardPlayer2.setTextAlignment(TextAlignment.CENTER);
                VBox player2Stats = new VBox(textCardPlayer2, player2Cards, player2Buttons, player2AnteWagerLabel, player2PairPlusWagerLabel, player2PlayWagerLabel, player2Winnings);
                player2Stats.setAlignment(Pos.CENTER);
                player2Stats.setSpacing(10);
                borderPane.setRight(player2Stats);

                player1play.setStyle("-fx-background-color: #90f8d0");
                player1play.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                player1play.setPadding(new Insets(10, 10, 10, 10));
                player1play.setFocusTraversable(false);

                player1fold.setStyle("-fx-background-color: #f890ac");
                player1fold.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                player1fold.setPadding(new Insets(10, 10, 10, 10));
                player1fold.setFocusTraversable(false);

                player2play.setStyle("-fx-background-color: #90f8d0");
                player2play.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                player2play.setPadding(new Insets(10, 10, 10, 10));
                player2play.setFocusTraversable(false);

                player2fold.setStyle("-fx-background-color: #f890ac");
                player2fold.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
                player2fold.setPadding(new Insets(10, 10, 10, 10));
                player2fold.setFocusTraversable(false);

                //Player chooses to fold or play
                player1play.setOnAction(a-> {
                    player1fold.setDisable(true);
                    player1play.setDisable(true);
                    player1fold.setVisible(false);
                    player1play.setVisible(false);
                    player1.playBet = player1.anteBet;
                    player1PlayWagerLabel.setText("Player 1 Play Wager: $" + player1.playBet);
                    player1Play.set(true);
                    if (player1Play.get() && (player2Play.get() || player2Fold.get())) {
                        playFold(dealerCard1View, dealerCard2View, dealerCard3View, player1Winnings, player2Winnings);
                    }
                });
                player1fold.setOnAction(a-> {
                    player1fold.setDisable(true);
                    player1play.setDisable(true);
                    player1fold.setVisible(false);
                    player1play.setVisible(false);
                    player1Fold.set(true);
                    if (player1Fold.get() && (player2Play.get() || player2Fold.get())) {
                        playFold(dealerCard1View, dealerCard2View, dealerCard3View, player1Winnings, player2Winnings);
                    }
                });
                player2play.setOnAction(a-> {
                    player2fold.setDisable(true);
                    player2play.setDisable(true);
                    player2fold.setVisible(false);
                    player2play.setVisible(false);
                    player2.playBet = player2.anteBet;
                    player2PlayWagerLabel.setText("Player 2 Play Wager: $" + player2.playBet);
                    player2Play.set(true);
                    if (player2Play.get() && (player1Play.get() || player1Fold.get())) {
                        playFold(dealerCard1View, dealerCard2View, dealerCard3View, player1Winnings, player2Winnings);
                    }
                });
                player2fold.setOnAction(a-> {
                    player2fold.setDisable(true);
                    player2play.setDisable(true);
                    player2fold.setVisible(false);
                    player2play.setVisible(false);
                    player2Fold.set(true);
                    if (player2Fold.get() && (player1Play.get() || player1Fold.get())) {
                        playFold(dealerCard1View, dealerCard2View, dealerCard3View, player1Winnings, player2Winnings);
                    }
                });
            }
        });
    }

    //Flip dealer cards and finish game
    public void playFold(ImageView dealerCard1View, ImageView dealerCard2View, ImageView dealerCard3View, Label player1Winnings, Label player2Winnings) {
        //Flip dealer cards
        borderPane.setCenter(null);
        StringBuilder dealerCard1 = new StringBuilder();
        dealerCard1.append(String.valueOf(theDealer.dealersHand.get(0).suit));
        dealerCard1.append(String.valueOf(theDealer.dealersHand.get(0).value));
        dealerCard1.append(".png");
        String dealerCard1Str = dealerCard1.toString();
        dealerCard1View.setImage(new Image(dealerCard1Str));
        dealerCard1View.setFitHeight(125);
        dealerCard1View.setPreserveRatio(true);

        StringBuilder dealerCard2 = new StringBuilder();
        dealerCard2.append(String.valueOf(theDealer.dealersHand.get(1).suit));
        dealerCard2.append(String.valueOf(theDealer.dealersHand.get(1).value));
        dealerCard2.append(".png");
        String dealerCard2Str = dealerCard2.toString();
        dealerCard2View.setImage(new Image(dealerCard2Str));
        dealerCard2View.setFitHeight(125);
        dealerCard2View.setPreserveRatio(true);

        StringBuilder dealerCard3 = new StringBuilder();
        dealerCard3.append(String.valueOf(theDealer.dealersHand.get(2).suit));
        dealerCard3.append(String.valueOf(theDealer.dealersHand.get(2).value));
        dealerCard3.append(".png");
        String dealerCard3Str = dealerCard3.toString();
        dealerCard3View.setImage(new Image(dealerCard3Str));
        dealerCard3View.setFitHeight(125);
        dealerCard3View.setPreserveRatio(true);

        Text textCardDealer = new Text("Dealer's Cards");
        textCardDealer.setFont((Font.font("Times New Roman", FontWeight.BLACK, FontPosture.REGULAR, 20)));
        textCardDealer.setTextAlignment(TextAlignment.CENTER);
        HBox dealerCards = new HBox(textCardDealer, dealerCard1View, dealerCard2View, dealerCard3View);
        dealerCards.setAlignment(Pos.TOP_CENTER);
        dealerCards.setPadding(new Insets(15, 15, 15, 15));
        dealerCards.setSpacing(20);

        //Winnings from current round
        int player1DollarWinnings = 0, player2DollarWinnings = 0;

        //Initialize labels for display messages
        dealerQueenLabel = new Label();
        player1WinLossLabel = new Label();
        player2WinLossLabel = new Label();
        dealerQueenLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16)));
        player1WinLossLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16)));
        player2WinLossLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16)));

        //Evaluate dealer cards and see if there is at least a queen high
        int dealerCardVal = ThreeCardLogic.evalHand(theDealer.dealersHand);
        if (dealerCardVal > 0) {
            dealerQueenHigh = true;
        } else {
            ArrayList<Integer> dealerCardNums = new ArrayList<>();
            for (Card i:theDealer.dealersHand) {
                dealerCardNums.add(i.value);
            }
            Collections.sort(dealerCardNums);
            if (dealerCardNums.get(2) < 12) {
                dealerQueenHigh = false;
                player1.totalWinnings += player1.anteBet;
                player2.totalWinnings += player2.anteBet;
                dealerQueenLabel.setText("Dealer does not have at least Queen high; ante wager is pushed");
            } else {
                dealerQueenHigh = true;
            }
        }

        //Players folds
        if (player1Fold.get()) {
            player1.totalWinnings = player1.totalWinnings - player1.anteBet - player1.pairPlusBet;
            player1WinLossLabel.setText("Player 1 folded. Lost Ante and Pair Plus Wagers");
        }
        if (player2Fold.get()) {
            player2.totalWinnings = player2.totalWinnings - player2.anteBet - player2.pairPlusBet;
            player2WinLossLabel.setText("Player 2 folded. Lost Ante and Pair Plus Wagers");
        }

        //Players play
        if (dealerQueenHigh) {
            //If queen high then evaluate the winnings
            if (player1Play.get()) {
                player1.playBet = player1.anteBet;
                int win = ThreeCardLogic.compareHands(theDealer.dealersHand, player1.hand);
                player1Tie = false;
                //Dealer wins
                if (win == 1) {
                    player1DollarWinnings = 0 - player1.anteBet - player1.playBet;
                    player1.totalWinnings = player1.totalWinnings - player1.anteBet - player1.playBet;
                    player1WinLossLabel.setText("Player 1 loses to dealer");
                } else if (win == 2) { //Player wins
                    player1DollarWinnings = 2*player1.anteBet + 2*player1.playBet;
                    player1.totalWinnings = player1.totalWinnings + 2*player1.anteBet + 2*player1.playBet;
                    player1WinLossLabel.setText("Player 1 beats dealer");
                } else { //Tie
                    player1Tie = true;
                    player1WinLossLabel.setText("Player 1 ties with dealer; ante wager is pushed");
                }
            }
            if (player2Play.get()) {
                player2.playBet = player2.anteBet;
                int win = ThreeCardLogic.compareHands(theDealer.dealersHand, player2.hand);
                player2Tie = false;
                //Dealer wins
                if (win == 1) {
                    player2DollarWinnings = 0 - player2.anteBet - player2.playBet;
                    player2.totalWinnings = player2.totalWinnings - player2.anteBet - player2.playBet;
                    player2WinLossLabel.setText("Player 2 loses to dealer");
                } else if (win == 2) { //Player wins
                    player2DollarWinnings = 2*player2.anteBet + 2*player2.playBet;
                    player2.totalWinnings = player2.totalWinnings + 2*player2.anteBet + 2*player2.playBet;
                    player2WinLossLabel.setText("Player 2 beats dealer");
                } else { //Tie
                    player2Tie = true;
                    player2WinLossLabel.setText("Player 2 ties with dealer; ante wager is pushed");
                }
            }
        }

        //Calculate and update pair plus bet
        int player1ppWinning = ThreeCardLogic.evalPPWinnings(player1.hand, player1.pairPlusBet);
        Label player1PPWinLossLabel = new Label();
        Label player2PPWinLossLabel = new Label();
        player1PPWinLossLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16)));
        player2PPWinLossLabel.setFont((Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16)));

        if (player1.pairPlusBet == 0) {
            player1PPWinLossLabel.setText("Player 1 did not make a Pair Plus Wager");
        } else if (player1ppWinning == 0) {
            player1PPWinLossLabel.setText("Player 1 lost Pair Plus");
            if (player1Play.get()) {
                player1.totalWinnings -= player1.pairPlusBet;
            }
        } else if (player1ppWinning > 0) {
            player1PPWinLossLabel.setText("Player 1 won Pair Plus");
        }
        int player2ppWinning = ThreeCardLogic.evalPPWinnings(player2.hand, player2.pairPlusBet);
        if (player2.pairPlusBet == 0) {
            player2PPWinLossLabel.setText("Player 2 did not make a Pair Plus Wager");
        } else if (player2ppWinning == 0) {
            player2PPWinLossLabel.setText("Player 2 lost Pair Plus");
            if (player2Play.get()) {
                player2.totalWinnings -= player2.pairPlusBet;
            }
        } else if (player2ppWinning > 0) {
            player2PPWinLossLabel.setText("Player 2 won Pair Plus");
        }
        //Update players' winnings
        player1.totalWinnings += player1ppWinning;
        player2.totalWinnings += player2ppWinning;


        //Allow players to start new game
        startNewGame = new Button("Start new game");
        startNewGame.setStyle("-fx-background-color: #84ffcf");
        startNewGame.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        startNewGame.setPadding(new Insets(10, 10, 10, 10));
        startNewGame.setFocusTraversable(false);
        VBox cardsAndButton = new VBox(textCardDealer, dealerCards, dealerQueenLabel, player1WinLossLabel, player1PPWinLossLabel, player2WinLossLabel, player2PPWinLossLabel, startNewGame);
        cardsAndButton.setSpacing(10);
        cardsAndButton.setAlignment(Pos.TOP_CENTER);
        borderPane.setCenter(cardsAndButton);
        player1Winnings.setText("Player 1 Total Winnings: $" + player1.totalWinnings);
        player2Winnings.setText("Player 2 Total Winnings: $" + player2.totalWinnings);
        startNewGame.setOnAction(e-> {
            play();
        });
    }
}
