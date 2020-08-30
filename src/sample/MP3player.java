package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.net.URL;

public class MP3player extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridPane ap = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("mp3-player");
        Scene scene = new Scene(ap);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
       launch(args);
    }
}
