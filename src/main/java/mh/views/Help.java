package mh.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mh.App;

import java.io.IOException;

public class Help {
  @FXML
  Button homeBtn;
  @FXML
  Button tetPlayBtn;
  @FXML
  Button micPlayBtn;
  String file;
  Media sound;
  MediaPlayer player;

  //Goes back home
  public void backFcn(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/mh/views/base.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Plays 12tet tone
  public void play12Tet(ActionEvent actionEvent) {
    Sound(String.valueOf(getClass().getResource("/mh/sounds/help/12tet.mp3")));
    play();
  }

  //plays microtone progression
  public void playMicrotone(ActionEvent actionEvent) {
    Sound(String.valueOf(getClass().getResource("/mh/sounds/help/microtone.mp3")));
    play();
  }
  public void Sound(String file) {
    this.file = file;
    this.sound = new Media(file);
    this.player = new MediaPlayer(sound);
  }

  public void play() {
    player.play();
  }
}
