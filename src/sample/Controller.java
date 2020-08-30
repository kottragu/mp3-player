package sample;


import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Controller implements Initializable {
    MediaPlayer mediaPlayer;
    public static int count = 0;
    private boolean flag = false;
    String Directory = "C:\\Users\\"+System.getProperty("user.name")+"\\Music";
    ArrayList<File> files = new ArrayList<>(files_output());

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView PrevSongButton;

    @FXML
    private ImageView PauseSongButton;

    @FXML
    private ImageView NextSongButton;
    
    @FXML
    private GridPane gridPane;

    @FXML
    public TextField textField;

    @FXML
    private AnchorPane anchorPane;

    private Button Button;


    @FXML
    private void actionPauseSongButton(){
        checkFlag();
    }

    private void checkFlag(){
        if(!flag){ //for play
            flag = true;
            Image image = new Image(getClass().getResourceAsStream("playButton.png"));
            PauseSongButton.setImage(image);
            mediaPlayer.play();
        }else{ //for stop
            flag = false;
            Image image = new Image(getClass().getResourceAsStream("pauseButton.png"));
            PauseSongButton.setImage(image);
            mediaPlayer.pause();
        }
    }

    @FXML
    private void actionPrevSongButton(){
        mediaPlayer.stop();
        count--;
        if(count < 0){
            count = files.size()-1;
        }

        Image image = new Image(getClass().getResourceAsStream("playButton.png"));
        PauseSongButton.setImage(image);
        flag = true;

        File soundFile = files.get(count);
        Media media = new Media(new File(Directory+"\\" + soundFile.getName()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }

    @FXML
    private void actionNextSongButton(){
        mediaPlayer.stop();
        count++;
        if(count >= files.size()){
            count = 0;
        }

        Image image = new Image(getClass().getResourceAsStream("playButton.png"));
        PauseSongButton.setImage(image);
        flag = true;

        File soundFile = files.get(count);
        Media media = new Media(new File((Directory +"\\" + soundFile.getName())).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public String removeCharAtDirectoryname(String directory){
        directory = directory.replace("file:/", "");
        directory = directory.replace("/","\\");
        directory = directory.substring(0,directory.length()-1);
        directory = directory.replace("%20"," ");
        return directory;
    }

    @FXML
    private void buttonApplyClicked() {
        mediaPlayer.stop();
        Directory = textField.getText();
        System.out.println(Directory);
        files = files_output();
        count = 0;
        flag = false;
        Image image = new Image(getClass().getResourceAsStream("pauseButton.png"));
        PauseSongButton.setImage(image);

        File soundFile = files.get(count);
        changeAnchorPane();
        Media media = new Media(new File(Directory + "\\" + soundFile.getName()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    @FXML
    public void cDBClicked(){
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) gridPane.getScene().getWindow();
        File directory = directoryChooser.showDialog(stage);
        textField.setText(removeCharAtDirectoryname(directory.toURI().toString()));
    }

    public void changeAnchorPane(){
        anchorPane.getChildren().clear();
        Double layoutXButton = 5.0;
        Double layoutYButton = 0.0;
        int id = 0;
        ArrayList<Button> arrayList = new ArrayList<>();

        for (File file : files) {
            try {
                InputStream input = new FileInputStream(Directory + "\\" + file.getName());
                ContentHandler contentHandler = new DefaultHandler();
                Metadata metadata = new Metadata();
                Parser parser = new Mp3Parser();
                ParseContext parseContext = new ParseContext();
                parser.parse(input, contentHandler, metadata, parseContext);
                input.close();

                arrayList.add(new Button( metadata.get("xmpDM:artist") + "  " + metadata.get("title") + "   "));
            } catch (TikaException | IOException | SAXException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        for (Button button: arrayList) {
            button.setId(""+id);
            button.setOnAction(MouseEvent ->{
                //тут прям лютый говнокод
                mediaPlayer.stop();
                for(int i = 0;i < files.size();i++){
                    mediaPlayer.stop();
                    String s = button.getId();
                    int num = Integer.parseInt(s);
                    if (i == num){
                        flag = false;
                        count = i;
                        File song = files.get(count);
                        Media hit = new Media(new File(Directory + "\\" + song.getName()).toURI().toString());
                        mediaPlayer = new MediaPlayer(hit);
                    }
                }
                checkFlag();
                mediaPlayer.play();
            });
            id++;
            button.setLayoutX(layoutXButton);
            button.setLayoutY(layoutYButton);
            layoutYButton+=25;
            anchorPane.getChildren().add(button);
        }
    }


    ArrayList<File> files_output(){
        File myFolder = new File(Directory);
        try {
            ArrayList<File> withRubbish = new ArrayList<>(Arrays.asList(Objects.requireNonNull(myFolder.listFiles())));
            ArrayList<File> withoutRubbish = new ArrayList<File>();
            for (File it : withRubbish) {
                if (it.getName().endsWith(".mp3")) {
                    withoutRubbish.add(it);
                }
            }
            return withoutRubbish;
        } catch (Exception exception){
            System.out.println("бан в files_output");
            System.out.println(exception);
        }
        return null;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         Image image = new Image(getClass().getResourceAsStream("pauseButton.png"));
         Image image1 = new Image(getClass().getResourceAsStream("prevSongButton.png"));
         Image image2 = new Image(getClass().getResourceAsStream("nextSongButton.png"));
         PauseSongButton.setImage(image);
         PrevSongButton.setImage(image1);
         NextSongButton.setImage(image2);

        {
            File soundFile = files.get(count);
            /*
            Double layoutXButton = 5.0;
            Double layoutYButton = 0.0;
            int id = 0;
            ArrayList<Button> arrayList = new ArrayList<>();

            for (File file : files) {
                try {
                    InputStream input = new FileInputStream(Directory + "\\" + file.getName());
                    ContentHandler contentHandler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseContext = new ParseContext();
                    parser.parse(input, contentHandler, metadata, parseContext);
                    input.close();

                    arrayList.add(new Button( metadata.get("xmpDM:artist") + "  " + metadata.get("title") + "   " + metadata.get("xmpDM:duration")));
                } catch (TikaException | IOException | SAXException e) {
                    e.printStackTrace();
                }
            }

            for (Button button: arrayList) {

                button.setId(""+id);
                button.setOnAction(MouseEvent ->{
                    //тут прям лютый говнокод
                    for(int i = 0;i < files.size();i++){
                        mediaPlayer.stop();
                        String s = button.getId();
                        int num = Integer.parseInt(s);
                        if (i == num){
                            count = i;
                            File song = files.get(count);
                            Media hit = new Media(new File(Directory + "\\" + song.getName()).toURI().toString());
                            mediaPlayer = new MediaPlayer(hit);
                        }
                    }
                });
                id++;
                button.setLayoutX(layoutXButton);
                button.setLayoutY(layoutYButton);
                layoutYButton+=25;
                anchorPane.getChildren().add(button);
            } */
            changeAnchorPane();

            Media hit = new Media(new File(Directory +"\\" + soundFile.getName()).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);
        }



    }



}