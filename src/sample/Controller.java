package sample;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javax.sound.sampled.*;


public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public Button NextSongButton;

    @FXML
    private Button PrevSongButton;

    @FXML
    private Button PauseSongButton;

    private static void handle(ActionEvent Event) {

    }

    ArrayList<File> files_output(){
        File myFolder = new File("C:\\Users\\alexe\\IdeaProjects\\AppFX\\src\\songs");
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(myFolder.listFiles())));
    }

    private MediaPlayer mediaPlayer;
    public static int count = 0;
    static int mouseEventCount = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<File> files = new ArrayList<>(files_output());

        {
            File soundFile = files.get(count);
            Media hit = new Media(new File("C:\\Users\\alexe\\IdeaProjects\\AppFX\\src\\songs\\" + soundFile.getName()).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        }
        NextSongButton.setOnAction(MouseEvent -> {
            mediaPlayer.stop();
            count++;
            if(count > files.size()-1){
                count = 0;
            }
            File soundFile = files.get(count);

            Media hit = new Media(new File("C:\\Users\\alexe\\IdeaProjects\\AppFX\\src\\songs\\" + soundFile.getName()).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        });
        PrevSongButton.setOnAction(MouseEvent ->{
            mediaPlayer.stop();
            count--;
            if(count < 0){
                count = files.size()-1;
            }
            File soundFile = files.get(count);

            Media hit = new Media(new File("C:\\Users\\alexe\\IdeaProjects\\AppFX\\src\\songs\\" + soundFile.getName()).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();
        });
        PauseSongButton.setOnAction(MouseEvent ->{
            mediaPlayer.pause();
            if(mouseEventCount%2 == 0) {
                mediaPlayer.pause();
                mouseEventCount++;
            } else {
                mouseEventCount--;
                mediaPlayer.play();
            }
        });
    }
}