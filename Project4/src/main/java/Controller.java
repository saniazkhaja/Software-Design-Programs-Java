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
import javafx.stage.Stage;
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
    @FXML
    private BorderPane homeScreen;
    @FXML
    private BorderPane clientScreen;
    @FXML
    private BorderPane serverScreen;
    @FXML
    private Text clientNum = new Text();
    @FXML
    private Button sendMessageButton;
    @FXML
    private TextField clientMessageField;


    @FXML
    ListView<String> listItemsServer = new ListView<>();
    @FXML
    ListView<String> listMessagesClient = new ListView<>();
    @FXML
    ListView<String> listClients = new ListView<>();
//    static ArrayList<String> allClients = new ArrayList<>();
    static ArrayList<Integer> allClients = new ArrayList<>();
    Server serverConnection;
    static Client clientConnection;
    static boolean loaded = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listClients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void startClientButtonMethod() throws IOException {
        clientConnection = new Client(data->{
            Platform.runLater(()->{
                listClients.getItems().clear();
                synchronized (data) {
                    for (int i = 0; i < data.allClientList.size(); i++) {
                        listClients.getItems().add("Client #" + data.allClientList.get(i));
//                        listClients.getItems().add(data.allClientList.get(i));
                    }
                }
                listMessagesClient.getItems().add(data.message);
            });
        });
        clientConnection.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXMLClient.fxml"));
        clientScreen = loader.load();
        Controller controllerC = loader.getController();
        controllerC.listMessagesClient.setItems(listMessagesClient.getItems());
        controllerC.listClients.setItems(listClients.getItems());
        homeScreen.getScene().setRoot(clientScreen);
        loaded = true;
    }

    public void startServerButtonMethod() throws IOException {
        serverConnection = new Server(data -> {
            Platform.runLater(() -> {
                listItemsServer.getItems().add(data.message);
            });
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MyFXMLServer.fxml"));
        serverScreen = loader.load();
        Controller controllerS = loader.getController();
        controllerS.listItemsServer.setItems(listItemsServer.getItems());
        homeScreen.getScene().setRoot(serverScreen);
    }

    public void sendMessage() throws IOException  {
        ObservableList selectedItems = listClients.getSelectionModel().getSelectedItems();
        ArrayList<Integer> clientsToMessage = new ArrayList<>();
        for (Object o : selectedItems) {
            int temp = Integer.parseInt(o.toString().substring(8));
            clientsToMessage.add(temp);
            System.out.println(temp);
        }
        MessageInfo message1 = new MessageInfo(clientMessageField.getText(), clientsToMessage);
        clientConnection.send(message1);
        clientMessageField.clear();
    }
}
