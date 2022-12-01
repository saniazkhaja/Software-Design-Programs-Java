import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ServerGui extends Application{

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Project 3 Server");
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/MyFXML.fxml"));
        Scene s1 = new Scene(root, 500, 500);

        // basically for full screen view
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setMaximized(true); // makes scene be the screen size
        s1.getStylesheets().add("/styles/style1.css");
        primaryStage.setScene(s1);
        primaryStage.show();
    }
}
