import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Controller implements Initializable {
    static Client clientConnection;
    private static int totalMovesMade = -1;

//    private static boolean atFinalScreen = false;
    ListView<String> listPlays = new ListView<>();
    @FXML
    private TextField portNum;
    @FXML
    private BorderPane root;
    @FXML
    private BorderPane root2;
    @FXML
    private BorderPane root3;
    @FXML
    private BorderPane root4;

    @FXML
    private BorderPane root6;
    @FXML
    private Button newGameButton;

    private Label userTurnText = new Label();
    private Label userMoveText = new Label();
    private static GridPane gameBoard = new GridPane();
    private static int portNumber;
    static int currRoot = 0;
    private static GameBoard gameBoard1 = new GameBoard();

    private BorderPane gameBorderPane = new BorderPane();
    private VBox userTextDisplay = new VBox(userTurnText, userMoveText);
    Parent root5 = gameBorderPane;
    private static int playerCount = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static class GameBoard extends ArrayList<GameButton> {
        static ArrayList<Integer> tempWinMoves = new ArrayList<>();
        static boolean foundWin = false;
        //Constructor
        GameBoard() {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (i == 5) {
                        GameButton newButton = new GameButton(i, j,true);
                        this.add(newButton);
                        int finalI = i;
                        int finalJ = j;
                        newButton.setOnAction(e->{
                            this.get(finalI *7+ finalJ).player = 1;
                            CFourInfo move = new CFourInfo();
                            move.row = newButton.row;
                            move.col = newButton.col;
                            move.twoPlayers = true;
                            move.win = this.checkWin(finalI, finalJ);
                            if (tempWinMoves.size() >= 4) {
                                move.winPos1 = tempWinMoves.get(0);
                                move.winPos2 = tempWinMoves.get(1);
                                move.winPos3 = tempWinMoves.get(2);
                                move.winPos4 = tempWinMoves.get(3);
                            }
                            clientConnection.send(move);
                            tempWinMoves.clear();
                        });
                    } else {
                        GameButton newButton = new GameButton(i, j,false);
                        this.add(newButton);
                        int finalI = i;
                        int finalJ = j;
                        newButton.setOnAction(e->{
                            this.get(finalI *7+ finalJ).player = 1;
                            CFourInfo move = new CFourInfo();
                            move.row = newButton.row;
                            move.col = newButton.col;
                            move.twoPlayers = true;
                            move.win = this.checkWin(finalI, finalJ);
                            if (tempWinMoves.size() >= 4) {
                                move.winPos1 = tempWinMoves.get(0);
                                move.winPos2 = tempWinMoves.get(1);
                                move.winPos3 = tempWinMoves.get(2);
                                move.winPos4 = tempWinMoves.get(3);
                            }
                            clientConnection.send(move);
                            tempWinMoves.clear();
                        });
                    }
                }
            }
        }

        void newGame() {
            tempWinMoves.clear();
            totalMovesMade = -1;
            foundWin = false;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    this.get(i *7+ j).player = 0;
                }
            }
            System.out.println("finished setting up new game");
        }

        boolean checkWin(int row, int col) {
            System.out.println("checking score"+ " " + row + " " + col);
            int countH = 0, countV = 0, countD1 = 0, countD2 = 0;
            int tempRow = row, tempCol = col;

            //vertical win
            while (tempRow > -1) {
                if(this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countV++;
                    tempRow--;
                } else {
                    break;
                }
            }
            tempRow = row + 1;
            while (tempRow < 6) {
                if (this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countV++;
                    tempRow++;
                } else {
                    break;
                }
            }
            if (countV < 4 && !foundWin) {
                tempWinMoves.clear();
            } else {
                foundWin = true;
            }

            //horizontal win
            tempRow = row;
            while (tempCol > -1) {
                if (this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countH++;
                    tempCol--;
                } else {
                    break;
                }
            }
            tempCol = col + 1;
            while (tempCol < 7) {
                if (this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countH++;
                    tempCol++;
                } else {
                    break;
                }
            }
            if (countH < 4 && !foundWin) {
                tempWinMoves.clear();
            } else {
                foundWin = true;
            }

            tempCol = col;
            //diagonal 1 win left lower
            while (tempRow > -1 && tempCol > -1) {
                if (this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countD1++;
                    tempRow--;
                    tempCol--;
                } else {
                    break;
                }
            }
            tempCol = col+1;
            tempRow = row+1;
            while (tempRow < 6 && tempCol < 7) {
                if (this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countD1++;
                    tempRow++;
                    tempCol++;
                } else {
                    break;
                }
            }
            if (countD1 < 4 && !foundWin) {
                tempWinMoves.clear();
            } else {
                foundWin = true;
            }

            tempCol = col;
            tempRow = row;
            while (tempRow < 6 && tempCol > -1) {
                if (this.get(tempRow * 7 + tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countD2++;
                    tempRow++;
                    tempCol--;
                } else {
                    break;
                }
            }
            tempCol = col+1;
            tempRow = row-1;
            while (tempRow > -1 && tempCol < 7) {
                if (this.get(tempRow*7+tempCol).player == 1) {
                    if (!foundWin) {
                        tempWinMoves.add(tempRow*7+tempCol);
                    }
                    countD2++;
                    tempRow--;
                    tempCol++;
                } else {
                    break;
                }
            }
            if (countD2 < 4 && !foundWin) {
                tempWinMoves.clear();
            } else {
                foundWin = true;
            }

            if (countV >= 4 || countH >= 4 || countD1 >= 4 || countD2 >= 4) {
                System.out.println("BOARD WINNING MOVES");
                for (int i = 0; i < tempWinMoves.size(); i++) {
                    System.out.println("THHHEEEE WINNN SPOTTTTs: " + tempWinMoves.get(i));
                }
                System.out.println("Win!!!!");
                return true;
            }
            System.out.println("No win yet");
            return false;
        }
    }

    public void startNewGame() throws IOException {
        System.out.println("Went into start game. Player count: " + playerCount);
        gameBoard.getChildren().clear();
        gameBoard1.newGame();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gameBoard1.get(i*7+j).setStyle("-fx-background-color: #afafaf;");
                gameBoard1.get(i*7+j).setPrefSize(60, 60);
                gameBoard.add(gameBoard1.get(i*7+j), j, i, 1, 1);
                if (i == 5) {
                    gameBoard1.get(i*7 + j).setDisable(false);
                } else {
                    gameBoard1.get(i*7 + j).setDisable(true);
                }
            }
        }

        CFourInfo info2 = new CFourInfo(1);
        clientConnection.send(info2);
        newGameButton.setDisable(true);
//        atFinalScreen = false;
    }

    public void quitGame() throws IOException {
        playerCount = 1;
        Platform.exit();
        System.exit(0);
    }

    //Check that the port entered is valid and start connection
    public void startButtonMethod() throws IOException {
        boolean validPort = false;
        //Check if entry is valid
        if (portNum.getText().equals("")) {
            Alert errorNoInput = new Alert(Alert.AlertType.ERROR);
            errorNoInput.setHeaderText("No port input");
            errorNoInput.setContentText("Must input a port number between 4000 - 8000");
            errorNoInput.showAndWait();
        } else {
            try {
                portNumber = Integer.parseInt(portNum.getText());
                validPort = true;
            } catch (NumberFormatException e) {
                Alert invalidInput = new Alert(Alert.AlertType.ERROR);
                invalidInput.setHeaderText("Invalid input");
                invalidInput.setContentText("Must input a port number between 4000 - 8000");
                invalidInput.showAndWait();
            }

            //If the entry is integer, check number and if valid, switch scene
            if (validPort) {
                if (portNumber < 4000 || portNumber > 8000) {
                    Alert invalidInput = new Alert(Alert.AlertType.ERROR);
                    invalidInput.setHeaderText("Invalid input");
                    invalidInput.setContentText("Must input a port number between 4000 - 8000");
                    invalidInput.showAndWait();
                } else {
                    try {
                        //Start connection
                        clientConnection = new Client(portNumber, data -> {
                            Platform.runLater(() -> listPlays.getItems().add(data.toString()));
                        });
                        clientConnection.start();
                    } catch(Exception e) {
                        Alert invalidInput = new Alert(Alert.AlertType.ERROR);
                        invalidInput.setHeaderText("Not same as server");
                        invalidInput.setContentText("Server not connected or wrong port number. Please close out of program and reconnect");
                        invalidInput.showAndWait();
                    }
                }
            }
        }
    }

    public void startNewGameRoot2() {
        gameBoard.getChildren().clear();
        gameBoard1.newGame();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                gameBoard1.get(i*7+j).setStyle("-fx-background-color: #afafaf;");
                gameBoard1.get(i*7+j).setPrefSize(60, 60);
                gameBoard.add(gameBoard1.get(i*7+j), j, i, 1, 1);
                if (i == 5) {
                    gameBoard1.get(i*7 + j).setDisable(false);
                } else {
                    gameBoard1.get(i*7 + j).setDisable(true);
                }
            }
        }

        CFourInfo info2 = new CFourInfo(1);
        clientConnection.send(info2);
        newGameButton.setDisable(true);
//        atFinalScreen = false;
    }

    public class Client extends Thread {
        Socket socketClient;
        ObjectOutputStream out;
        ObjectInputStream in;
        int portNumber;
        Consumer<CFourInfo> callback;

        Client(int portNumber, Consumer<CFourInfo> call){
            this.portNumber = portNumber;
            callback = call;
            //set up the game board
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    gameBoard1.get(i*7+j).setStyle("-fx-background-color: #afafaf;");
                    gameBoard1.get(i*7+j).setPrefSize(60, 60);
                    gameBoard.add(gameBoard1.get(i*7+j), j, i, 1, 1);
                }
            }
            gameBorderPane.setCenter(gameBoard);
            gameBorderPane.setTop(userTextDisplay);
            gameBoard.setHgap(5);
            gameBoard.setVgap(5);

            gameBoard.setAlignment(Pos.CENTER);
            gameBorderPane.setStyle("-fx-background-color: #ccf0ff;");

            userTurnText.setFont(Font.font ("Arial Black", 25));
            userTurnText.setPadding(new Insets(10, 10, 10, 10));
            userTurnText.setAlignment(Pos.CENTER);

            userMoveText.setFont(Font.font ("Arial Black", 20));
            userMoveText.setPadding(new Insets(10, 10, 10, 10));
            userMoveText.setAlignment(Pos.CENTER);
        }

        public void run() {
            try {
                socketClient= new Socket(InetAddress.getLocalHost(), portNumber);
                out = new ObjectOutputStream(socketClient.getOutputStream());
                in = new ObjectInputStream(socketClient.getInputStream());
                socketClient.setTcpNoDelay(true);
            }
            catch(Exception e) {}

            while(true) {
                try {
                    CFourInfo info = (CFourInfo) in.readObject();
                    callback.accept(info);
                    System.out.println("currRoot2: " + currRoot);
                    System.out.println("received" + " " + info.twoPlayers + " " + info.turn + " " +info.row + " " + info.col + " " + info.restart);
                    System.out.println("Total Moves made before " + totalMovesMade);
                    // load scenes depending on number of players
                    // 2 players, start new game
                    if (!info.twoPlayers) {
                        System.out.println("here at root 2");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXML2.fxml"));
                        root2 = loader.load();
                        if (currRoot == 0) {
                            root.getScene().setRoot(root2);
                        } else if (currRoot == 3) {
                            root3.getScene().setRoot(root2);
                        } else if (currRoot == 4) {
                            root4.getScene().setRoot(root2);
                        } else if (currRoot == 5) {
                            root5.getScene().setRoot(root2);
                        } else if (currRoot == 6) {
                            root6.getScene().setRoot(root2);
                        }
                        currRoot = 2;
//                        atFinalScreen = false;
                        startNewGameRoot2();
                    } else if (info.win && info.turn) { //if the other player won
                        PauseTransition pause = new PauseTransition(
                                Duration.seconds(5)
                        );
                        Platform.runLater(() -> {
                            if (info.turn) {
                                userMoveText.setText("Other player made a move at row " + info.row + " and col " + info.col);

                            } else {
                                userMoveText.setText("You made a move at row " + info.row + " and col " + info.col);
                            }
                        });
                        playerCount = 2;
//                        atFinalScreen = true;
                        gameBoard1.get(info.row*7 + info.col).setStyle("-fx-background-color: #002aff;");
                        gameBoard1.get(info.row*7 + info.col).setDisable(true);
                        gameBoard.setDisable(true);

                        gameBoard1.get(info.winPos1).setStyle("-fx-background-color: #21bb24;");
                        gameBoard1.get(info.winPos2).setStyle("-fx-background-color: #21bb24;");
                        gameBoard1.get(info.winPos3).setStyle("-fx-background-color: #21bb24;");
                        gameBoard1.get(info.winPos4).setStyle("-fx-background-color: #21bb24;");
                        currRoot = 3;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXML3.fxml"));
                        root3 = loader.load();
                        pause.setOnFinished(event -> {
                            root5.getScene().setRoot(root3);
                        });
                        pause.play();
                    } else if (info.win && !info.turn) { //if this player won
                        PauseTransition pause = new PauseTransition(
                                Duration.seconds(5)
                        );
                        Platform.runLater(() -> {
                            if (!info.turn) {
                                userMoveText.setText("You made a move at row " + info.row + " and col " + info.col);
                            } else {
                                userMoveText.setText("Other player made a move at row " + info.row + " and col " + info.col);
                            }
                        });
                        playerCount = 2;
//                        atFinalScreen = true;
                        gameBoard1.get(info.row*7 + info.col).setStyle("-fx-background-color: #002aff;");
                        gameBoard1.get(info.row*7 + info.col).setDisable(true);
                        gameBoard.setDisable(true);

                        gameBoard1.get(info.winPos1).setStyle("-fx-background-color: #21bb24;");
                        gameBoard1.get(info.winPos2).setStyle("-fx-background-color: #21bb24;");
                        gameBoard1.get(info.winPos3).setStyle("-fx-background-color: #21bb24;");
                        gameBoard1.get(info.winPos4).setStyle("-fx-background-color: #21bb24;");
                        currRoot = 4;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXML4.fxml"));
                        root4 = loader.load();
                        pause.setOnFinished(event -> {
                            root5.getScene().setRoot(root4);
                        });
                        pause.play();
                    } else if (info.twoPlayers && info.turn) { //update the board with the other player's move
                        totalMovesMade++;
                        Platform.runLater(() -> {
                            if (info.turn) {
                                userTurnText.setText("Your Turn");
                                if (info.row == -1) {
                                    userMoveText.setText("");
                                } else {
                                    userMoveText.setText("Other player made a move at row " + info.row + " and col " + info.col);
                                }
                            } else {
                                userTurnText.setText("Other Player's Turn");
                                if (info.row == -1) {
                                    userMoveText.setText("");
                                } else {
                                    userMoveText.setText("You made a move at row " + info.row + " and col " + info.col);
                                }
                            }
                        });
                        if (info.row != -1) {
                            gameBoard1.get(info.row*7 + info.col).setStyle("-fx-background-color: #ff0000;");
                            gameBoard1.get(info.row*7 + info.col).setDisable(true);
                            if(info.row > 0) {
                                gameBoard1.get(info.row*7 + info.col - 7).setDisable(false);
                            }
                        }
                        gameBoard.setDisable(false);
                        if (currRoot == 0) {
                            root.getScene().setRoot(root5);
                        } else if (currRoot == 2) {
                            root2.getScene().setRoot(root5);
                        } else if (currRoot == 3) {
                            root3.getScene().setRoot(root5);
                        } else if (currRoot == 4) {
                            root4.getScene().setRoot(root5);
                        } else if (currRoot == 6) {
                            root6.getScene().setRoot(root5);
                        }
                        currRoot = 5;
                    } else { //update the board with this player's move
                        totalMovesMade++;
                        Platform.runLater(() -> {
                            if (!info.turn) {
                                userTurnText.setText("Other Player's Turn");
                                if (info.row == -1) {
                                    userMoveText.setText("");
                                } else {
                                    userMoveText.setText("You made a move at row " + info.row + " and col " + info.col);
                                }
                            } else {
                                userTurnText.setText("Your Turn");
                                if (info.row == -1) {
                                    userMoveText.setText("");
                                } else {
                                    userMoveText.setText("Other player made a move at row " + info.row + " and col " + info.col);
                                }
                            }
                        });
                        if (info.row != -1) {
                            gameBoard1.get(info.row*7 + info.col).setStyle("-fx-background-color: #002aff;");
                            gameBoard1.get(info.row*7 + info.col).setDisable(true);
                            if(info.row > 0) {
                                gameBoard1.get(info.row*7 + info.col - 7).setDisable(false);
                            }
                        }
                        gameBoard.setDisable(true);
                        if (currRoot == 0) {
                            root.getScene().setRoot(root5);
                            System.out.println("root5 - 2");
                        } else if (currRoot == 2) {
                            root2.getScene().setRoot(root5);
                        } else if (currRoot == 3) {
                            root3.getScene().setRoot(root5);
                        } else if (currRoot == 4) {
                            root4.getScene().setRoot(root5);
                        } else if (currRoot == 6) {
                            root6.getScene().setRoot(root5);
                        }
                        currRoot = 5;
//                        atFinalScreen = false;
                    }
                    if (totalMovesMade >= 42 && !info.win) { //if all spaces filled and no win yet so tie
                        PauseTransition pause = new PauseTransition(
                                Duration.seconds(3)
                        );
//                        atFinalScreen = true;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXML5.fxml"));
                        root6 = loader.load();
                        currRoot = 6;
                        pause.setOnFinished(event -> {
                            root5.getScene().setRoot(root6);
                        });
                        pause.play();
                    }
                    System.out.println("Total Moves made after " + totalMovesMade);
                }
                catch(Exception e) {}
            }
        }

        public void send(CFourInfo move) {
            try {
                out.writeObject(move);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
