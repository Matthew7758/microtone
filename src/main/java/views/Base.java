package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

public class Base {
  @FXML
  Button replayBtn;
  @FXML
  Button shopBtn;
  @FXML
  Button cNat;
  @FXML
  Button cHalfSharp;
  @FXML
  Button cSharp;
  @FXML
  Button dHalfFlat;
  @FXML
  Button dNat;
  @FXML
  Button dHalfSharp;
  @FXML
  Button dSharp;
  @FXML
  Button eHalfFlat;
  @FXML
  Button eNat;
  @FXML
  Button eHalfSharp;
  @FXML
  Button fNat;
  @FXML
  Button fHalfSharp;
  @FXML
  Button fSharp;
  @FXML
  Button gHalfFlat;
  @FXML
  Button gNat;
  @FXML
  Button gHalfSharp;
  @FXML
  Button gSharp;
  @FXML
  Button aHalfFlat;
  @FXML
  Button aNat;
  @FXML
  Button aHalfSharp;
  @FXML
  Button aSharp;
  @FXML
  Button bHalfFlat;
  @FXML
  Button bNat;
  @FXML
  Button bHalfSharp;
  @FXML
  Label score;
  Button answerBtn;
  Button nullBtn = new Button();

  String file;
  Media sound;
  MediaPlayer player;
  HashMap<Button, String> sounds = new HashMap<>();
  HashMap<Integer, Button> answers = new HashMap<>();

  @FXML
  public void initialize() {
    answers.put(1, cNat);
    answers.put(2, cHalfSharp);
    answers.put(3, cSharp);
    answers.put(4, dHalfFlat);
    answers.put(5, dNat);
    answers.put(6, dHalfSharp);
    answers.put(7, dSharp);
    answers.put(8, eHalfFlat);
    answers.put(9, eNat);
    answers.put(10, eHalfSharp);
    answers.put(11, fNat);
    answers.put(12, fHalfSharp);
    answers.put(13, fSharp);
    answers.put(14, gHalfFlat);
    answers.put(15, gNat);
    answers.put(16, gHalfSharp);
    answers.put(17, gSharp);
    answers.put(18, aHalfFlat);
    answers.put(19, aNat);
    answers.put(20, aHalfSharp);
    answers.put(21, aSharp);
    answers.put(22, bHalfFlat);
    answers.put(23, bNat);
    answers.put(24, bHalfSharp);
    sounds.put(cNat, String.valueOf(getClass().getResource("/sounds/01_CNatural5.mp3")));
    sounds.put(cHalfSharp, String.valueOf(getClass().getResource("/sounds/02_CHalfSharp.mp3")));
    sounds.put(cSharp, String.valueOf(getClass().getResource("/sounds/03_CSharp.mp3")));
    sounds.put(dHalfFlat, String.valueOf(getClass().getResource("/sounds/04_DHalfFlat.mp3")));
    sounds.put(dNat, String.valueOf(getClass().getResource("/sounds/05_DNatural.mp3")));
    sounds.put(dHalfSharp, String.valueOf(getClass().getResource("/sounds/06_DHalfSharp.mp3")));
    sounds.put(dSharp, String.valueOf(getClass().getResource("/sounds/07_DSharp.mp3")));
    sounds.put(eHalfFlat, String.valueOf(getClass().getResource("/sounds/08_EHalfFlat.mp3")));
    sounds.put(eNat, String.valueOf(getClass().getResource("/sounds/09_ENatural.mp3")));
    sounds.put(eHalfSharp, String.valueOf(getClass().getResource("/sounds/10_EHalfSharp.mp3")));
    sounds.put(fNat, String.valueOf(getClass().getResource("/sounds/11_FNatural.mp3")));
    sounds.put(fHalfSharp, String.valueOf(getClass().getResource("/sounds/12_FHalfSharp.mp3")));
    sounds.put(fSharp, String.valueOf(getClass().getResource("/sounds/13_FSharp.mp3")));
    sounds.put(gHalfFlat, String.valueOf(getClass().getResource("/sounds/14_GHalfFlat.mp3")));
    sounds.put(gNat, String.valueOf(getClass().getResource("/sounds/15_GNatural.mp3")));
    sounds.put(gHalfSharp, String.valueOf(getClass().getResource("/sounds/16_GHalfSharp.mp3")));
    sounds.put(gSharp, String.valueOf(getClass().getResource("/sounds/17_GSharp.mp3")));
    sounds.put(aHalfFlat, String.valueOf(getClass().getResource("/sounds/18_AHalfFlat.mp3")));
    sounds.put(aNat, String.valueOf(getClass().getResource("/sounds/19_ANatural.mp3")));
    sounds.put(aHalfSharp, String.valueOf(getClass().getResource("/sounds/20_AHalfSharp.mp3")));
    sounds.put(aSharp, String.valueOf(getClass().getResource("/sounds/21_ASharp.mp3")));
    sounds.put(bHalfFlat, String.valueOf(getClass().getResource("/sounds/22_BHalfFlat.mp3")));
    sounds.put(bNat, String.valueOf(getClass().getResource("/sounds/23_BNatural.mp3")));
    sounds.put(bHalfSharp, String.valueOf(getClass().getResource("/sounds/24_BHalfSharp.mp3")));
  }

  public void generateNote() {
    int ans = (int) ((Math.random() * ((24 - 1) + 1)) + 1);
    answerBtn = answers.get(ans);
    Sound(sounds.get(answerBtn));
    replayBtn.setDisable(false);
    play();
  }

  public void replayNote(ActionEvent actionEvent) {
    Sound(sounds.get(answerBtn));
    play();
  }

  public void openShop(ActionEvent actionEvent) {
    shopBtn.setText("Opened!");
  }

  public void checkAnswer(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    Sound(sounds.get(source));
    play();
    if (answerBtn.equals(source)) {
      addScore();
      answerBtn=nullBtn;
      replayBtn.setDisable(true);
    }
  }

  public void addScore() {
    int tempScore = Integer.parseInt(score.getText());
    tempScore++;
    score.setText(Integer.toString(tempScore));
  }

  public void Sound(String file) {
    this.file = file;
    this.sound = new Media(file);
    this.player = new MediaPlayer(sound);
  }

  public void play() {
    player.play();
  }

  public void stop() {
    player.stop();
  }

  public double getVolume() {
    return player.getVolume();
  }

  public void setVolume(double value) {
    player.setVolume(value);
  }
}
