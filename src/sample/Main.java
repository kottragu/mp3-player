package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AnchorPane ap = (AnchorPane)FXMLLoader.load(Main.class.getResource("sample.fxml"));
        primaryStage.setTitle("mp3-player");
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
