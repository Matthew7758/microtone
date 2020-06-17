package mh.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mh.App;
import mh.database.DB;

import java.io.IOException;
import java.sql.*;
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
  Button answerBtn = new Button();
  Button nullBtn = new Button();
  DB database = new DB("admin", "password");
  Connection conn = database.connectDB("admin", "password");
  String file;
  Media sound;
  MediaPlayer player;
  HashMap<Button, String> defaultSounds = new HashMap<>();
  HashMap<Button, String> pianoSounds = new HashMap<>();
  HashMap<Integer, Button> answers = new HashMap<>();
  @FXML
  private AnchorPane anchorPane;
  private int unlockedKeys;

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
    defaultSounds.put(cNat, String.valueOf(getClass().getResource("/mh/sounds/default/01_CNatural5.mp3")));
    defaultSounds.put(cHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/02_CHalfSharp.mp3")));
    defaultSounds.put(cSharp, String.valueOf(getClass().getResource("/mh/sounds/default/03_CSharp.mp3")));
    defaultSounds.put(dHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/default/04_DHalfFlat.mp3")));
    defaultSounds.put(dNat, String.valueOf(getClass().getResource("/mh/sounds/default/05_DNatural.mp3")));
    defaultSounds.put(dHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/06_DHalfSharp.mp3")));
    defaultSounds.put(dSharp, String.valueOf(getClass().getResource("/mh/sounds/default/07_DSharp.mp3")));
    defaultSounds.put(eHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/default/08_EHalfFlat.mp3")));
    defaultSounds.put(eNat, String.valueOf(getClass().getResource("/mh/sounds/default/09_ENatural.mp3")));
    defaultSounds.put(eHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/10_EHalfSharp.mp3")));
    defaultSounds.put(fNat, String.valueOf(getClass().getResource("/mh/sounds/default/11_FNatural.mp3")));
    defaultSounds.put(fHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/12_FHalfSharp.mp3")));
    defaultSounds.put(fSharp, String.valueOf(getClass().getResource("/mh/sounds/default/13_FSharp.mp3")));
    defaultSounds.put(gHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/default/14_GHalfFlat.mp3")));
    defaultSounds.put(gNat, String.valueOf(getClass().getResource("/mh/sounds/default/15_GNatural.mp3")));
    defaultSounds.put(gHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/16_GHalfSharp.mp3")));
    defaultSounds.put(gSharp, String.valueOf(getClass().getResource("/mh/sounds/default/17_GSharp.mp3")));
    defaultSounds.put(aHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/default/18_AHalfFlat.mp3")));
    defaultSounds.put(aNat, String.valueOf(getClass().getResource("/mh/sounds/default/19_ANatural.mp3")));
    defaultSounds.put(aHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/20_AHalfSharp.mp3")));
    defaultSounds.put(aSharp, String.valueOf(getClass().getResource("/mh/sounds/default/21_ASharp.mp3")));
    defaultSounds.put(bHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/default/22_BHalfFlat.mp3")));
    defaultSounds.put(bNat, String.valueOf(getClass().getResource("/mh/sounds/default/23_BNatural.mp3")));
    defaultSounds.put(bHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/default/24_BHalfSharp.mp3")));
    pianoSounds.put(cNat, String.valueOf(getClass().getResource("/mh/sounds/piano/01piano.mp3")));
    pianoSounds.put(cHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/02piano.mp3")));
    pianoSounds.put(cSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/03piano.mp3")));
    pianoSounds.put(dHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/piano/04piano.mp3")));
    pianoSounds.put(dNat, String.valueOf(getClass().getResource("/mh/sounds/piano/05piano.mp3")));
    pianoSounds.put(dHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/06piano.mp3")));
    pianoSounds.put(dSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/07piano.mp3")));
    pianoSounds.put(eHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/piano/08piano.mp3")));
    pianoSounds.put(eNat, String.valueOf(getClass().getResource("/mh/sounds/piano/09piano.mp3")));
    pianoSounds.put(eHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/10piano.mp3")));
    pianoSounds.put(fNat, String.valueOf(getClass().getResource("/mh/sounds/piano/11piano.mp3")));
    pianoSounds.put(fHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/12piano.mp3")));
    pianoSounds.put(fSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/13piano.mp3")));
    pianoSounds.put(gHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/piano/14piano.mp3")));
    pianoSounds.put(gNat, String.valueOf(getClass().getResource("/mh/sounds/piano/15piano.mp3")));
    pianoSounds.put(gHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/16piano.mp3")));
    pianoSounds.put(gSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/17piano.mp3")));
    pianoSounds.put(aHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/piano/18piano.mp3")));
    pianoSounds.put(aNat, String.valueOf(getClass().getResource("/mh/sounds/piano/19piano.mp3")));
    pianoSounds.put(aHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/20piano.mp3")));
    pianoSounds.put(aSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/21piano.mp3")));
    pianoSounds.put(bHalfFlat, String.valueOf(getClass().getResource("/mh/sounds/piano/22piano.mp3")));
    pianoSounds.put(bNat, String.valueOf(getClass().getResource("/mh/sounds/piano/23piano.mp3")));
    pianoSounds.put(bHalfSharp, String.valueOf(getClass().getResource("/mh/sounds/piano/24piano.mp3")));
    try {
      Statement stmt = conn.createStatement();
      String query = String.format("SELECT * FROM DATA WHERE PLAYER = '%s'", "defaultUser");

      ResultSet rs = stmt.executeQuery(query);
      String tempStr = "0";
      int tempInt = 0;
      while (rs.next()) {
        tempStr = Integer.toString(rs.getInt("SCORE"));
        tempInt = rs.getInt("SELECTEDSOUND");
        unlockedKeys = rs.getInt("UNLOCKEDKEYS");
      }
      System.out.println(tempStr);
      App.setSoundType(tempInt); //Set previously selected sound type.
      System.out.println("Sound type = " + App.getSoundType());
      score.setText(tempStr);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    System.out.println("Unlocked Keys = " + unlockedKeys);
    if (unlockedKeys >= 1) {
      dHalfFlat.setDisable(false);
      dNat.setDisable(false);
      dHalfSharp.setDisable(false);
      dSharp.setDisable(false);
    }
    if (unlockedKeys >= 2) {
      eHalfFlat.setDisable(false);
      eNat.setDisable(false);
      eHalfSharp.setDisable(false);
    }
    if (unlockedKeys >= 3) {
      fNat.setDisable(false);
      fHalfSharp.setDisable(false);
      fSharp.setDisable(false);
    }
    if (unlockedKeys >= 4) {
      gHalfFlat.setDisable(false);
      gNat.setDisable(false);
      gHalfSharp.setDisable(false);
      gSharp.setDisable(false);
    }
    if (unlockedKeys >= 5) {
      aHalfFlat.setDisable(false);
      aNat.setDisable(false);
      aHalfSharp.setDisable(false);
      aSharp.setDisable(false);
    }
    if (unlockedKeys >= 6) {
      bHalfFlat.setDisable(false);
      bNat.setDisable(false);
      bHalfSharp.setDisable(false);
    }
  }

  public void generateNote() {
    int tempInt = 0;
    switch (unlockedKeys) {
      case 0:
        tempInt = 3;
        break;
      case 1:
        tempInt = 7;
        break;
      case 2:
        tempInt = 10;
        break;
      case 3:
        tempInt = 13;
      case 4:
        tempInt = 17;
        break;
      case 5:
        tempInt = 21;
        break;
      case 6:
        tempInt = 24;
        break;
      default:
        System.out.println("ERROR IN GENERATING NOTE CASE SWITCH.");
        break;
    }
    int ans = (int) ((Math.random() * ((tempInt - 1) + 1)) + 1);
    answerBtn = answers.get(ans);
    if (App.getSoundType() == 0)
      Sound(defaultSounds.get(answerBtn));
    else if (App.getSoundType() == 1)
      Sound(pianoSounds.get(answerBtn));
    replayBtn.setDisable(false);
    play();
  }

  public void replayNote(ActionEvent actionEvent) {
    if (App.getSoundType() == 0)
      Sound(defaultSounds.get(answerBtn));
    else if (App.getSoundType() == 1)
      Sound(pianoSounds.get(answerBtn));
    play();
  }

  public void openShop(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/mh/views/shop.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void checkAnswer(ActionEvent actionEvent) {
    Object source = actionEvent.getSource();
    if (App.getSoundType() == 0)
      Sound(defaultSounds.get(source));
    else if (App.getSoundType() == 1)
      Sound(pianoSounds.get(source));
    play();
    if (answerBtn.equals(source)) {
      addScore();
      answerBtn = nullBtn;
      replayBtn.setDisable(true);
    }
  }

  public void addScore() {
    System.out.println(score.getText());
    int tempScore = Integer.parseInt(score.getText());
    tempScore++;
    score.setText(Integer.toString(tempScore));
    try {
      String sql = "UPDATE DATA SET SCORE = ? WHERE PLAYER = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, tempScore);
      ps.setString(2, "defaultUser");
      ps.executeUpdate();
      ps.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
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
