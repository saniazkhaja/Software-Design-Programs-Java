import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class Controller implements Initializable {
   private static int portNumber;
    @FXML
    private TextField portNum;
    @FXML
    private BorderPane root;
    @FXML
    private Text message;

    static int clientCount = 0;

    Server serverConnection;
    @FXML
    ListView<String> listItems = new ListView<>();
    private static boolean turn = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }


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
                    //Start connection
                    serverConnection = new Server(portNumber, data -> {
                        Platform.runLater(() -> {
                          //  listItems.getItems().add(data.toString());
                            String output = "";
                            String won = "";
                            if (!turn) {
                                turn = true;
                                output = "Player 1 played at row " + data.row + " and column " + data.col;
                                if (data.win) {
                                    won = "Player 1 won!";
                                }
                            } else {
                                turn = false;
                                output = "Player 2 played at row " + data.row + " and column " + data.col;
                                if (data.win) {
                                    won = "Player 2 won!";
                                }
                            }
                            listItems.getItems().add(output);
                            if (!won.equals("")) {
                                listItems.getItems().add(won);
                            }
                        });
                    });
                    //Load scene 2
                    updateDisplay();
                }
            }
        }
    }

    public class Server {
        private int clientNum = 0;
        int turn = 0;
        int wait = 0;
        int restarts = 0;
        boolean twoPlayers;
        ArrayList<ClientThread> clients = new ArrayList<>();
        TheServer server;
        private Consumer<CFourInfo> callback;
        private int portNumber;

        Server() {
        }

        Server(int portNumber, Consumer<CFourInfo> call) throws IOException {
            this.portNumber = portNumber;
            callback = call;
            twoPlayers = false;
            server = new TheServer();
            server.start();
        }

        public class TheServer extends Thread {
            public void run() {
                try(ServerSocket mysocket = new ServerSocket(portNumber);){
                    System.out.println("Server is waiting for a client!");
                    while(true) {
                        ClientThread c = new ClientThread(mysocket.accept(), clientNum+1);
                        clients.add(c);
                        c.start();
                        clientNum++;
                        Platform.runLater(() -> {
                            listItems.getItems().add("Client #" + clientNum + " joined");
                        });
                        //determine who goes first
                        if(turn == 0) {
                            turn = clientNum;
                        } else {
                            wait = clientNum;
                        }
                        clientCount++;
                        if (clientCount > 1) {
                            twoPlayers = true;
                        }
                        System.out.println("client has connected to server: " + "client #" + clientNum);
                    }
                }//end of try
                catch(Exception e) {
                    System.out.println("Server socket did not launch");
                }
            }//end of while
        }

        class ClientThread extends Thread{
            Socket connection;
            int count;
            ObjectInputStream in;
            ObjectOutputStream out;

            ClientThread(Socket s, int clientNum){
                this.connection = s;
                this.count = clientNum;
            }

            public void updateClients(CFourInfo move) {
                for(int i = 0; i < clients.size(); i++) {
                    ClientThread t = clients.get(i);
                    if(clients.get(i).count == turn) {
                        move.turn = false;
                    } else {
                        move.turn = true;
                    }
                    try {
                        t.out.writeObject(move);
                    }
                    catch(Exception e) {}
                }
                int temp = turn;
                turn = wait;
                wait = temp;
            }

            public void run(){
                try {
                    in = new ObjectInputStream(connection.getInputStream());
                    out = new ObjectOutputStream(connection.getOutputStream());
                    connection.setTcpNoDelay(true);
                }
                catch(Exception e) {
                    System.out.println("Streams not open");
                }

                CFourInfo info = new CFourInfo(twoPlayers);
                updateClients(info);

                while(true) {
                    try {
                        CFourInfo move = (CFourInfo) in.readObject();
                        if (move.restart == 1) {
                            restarts += move.restart;
                            System.out.println(restarts);
                            Platform.runLater(() -> {
                                listItems.getItems().add("Client #" + count + " is playing again");
                            });
                            if(restarts == 2) {
                                System.out.println("restarting");
                                CFourInfo info2 = new CFourInfo(twoPlayers);
                                updateClients(info2);
                                restarts = 0;
                            }
                        } else {
                            callback.accept(move);
                            updateClients(move);
                            restarts = 0;
                        }
                    }
                    catch(Exception e) {
                        Platform.runLater(() -> {
                            listItems.getItems().add("Client #" + count + " left");
                        });
                        System.out.println("Client #"+count+" has left the server!");
                        if(this.count == turn) {
                            turn = 0;
                        } else {
                            wait = 0;
                        }
                        clients.remove(this);
                        twoPlayers = false;
                        CFourInfo info2 = new CFourInfo(false);
                        updateClients(info2);
                        restarts = 2;
                        clientCount--;
                        break;
                    }
                }
            }//end of run

        }//end of client thread
    }

    public void updateDisplay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXML2.fxml"));
        Parent root2 = loader.load();
        root2.getStylesheets().add("/styles/style2.css");
        Controller controller2 = loader.getController();
        controller2.message.setText("Server started on port " + portNumber + " : Game Info Below");
        controller2.listItems.setItems(listItems.getItems());
        root.getScene().setRoot(root2);
    }
}
