package mh.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mh.App;
import mh.database.*;

import java.io.IOException;
import java.sql.*;

public class Shop {
  @FXML Button backBtn;
  @FXML Label currentSound;
  @FXML Label score;
  @FXML Label nextUnlockLbl;
  @FXML Button unlockBtn;
  @FXML Label unlockErrorLbl;
  int unlockedKeys = 0;

  DB database = new DB("admin", "password");
  Connection conn = database.connectDB("admin", "password");

  @FXML
  public void initialize() {
    try {
      Statement stmt = conn.createStatement();
      String query = String.format("SELECT * FROM DATA WHERE PLAYER = '%s'", "defaultUser");

      ResultSet rs = stmt.executeQuery(query);
      String tempStr = "0";
      int tempInt = 0;
      int unlockedSounds = 0;
      while (rs.next()) {
        tempStr = Integer.toString(rs.getInt("SCORE"));
        tempInt = rs.getInt("SELECTEDSOUND");
        unlockedKeys = rs.getInt("UNLOCKEDKEYS");
        unlockedSounds = rs.getInt("UNLOCKEDSOUNDS");
      }
      System.out.println(tempStr);
      App.setSoundType(tempInt); //Set previously selected sound type.
      score.setText(tempStr);
      updateSoundLbl(tempInt);
      updateUnlockElements();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  //Updates the visual elements depending on the numerical value of unlockedKeys
  public void updateUnlockElements() {
    String nextUnlock = "";
    if(unlockedKeys==0)
      nextUnlock="D";
    else if(unlockedKeys==1)
      nextUnlock="E";
    else if(unlockedKeys==2)
      nextUnlock="F";
    else if(unlockedKeys==3)
      nextUnlock="G";
    else if(unlockedKeys==4)
      nextUnlock="A";
    else if(unlockedKeys==5)
      nextUnlock="B";
    if(unlockedKeys!=6) {
      nextUnlockLbl.setText(String.format("Next Unlock: %s",nextUnlock));
      unlockBtn.setText(String.format("%d Pts",(unlockedKeys+1)*3));
    }
    else {
      nextUnlockLbl.setText("All keys unlocked!");
      unlockBtn.setText("You are winner!");
      unlockBtn.setDisable(true);
    }

  }

  public void backFcn(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/mh/views/Base.fxml"));
      App.getPrimaryStage().getScene().setRoot(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void changeSound(ActionEvent actionEvent) {
    if (App.getSoundType() < 1) {//Change 1 to the highest number of sounds in the final game.
      App.setSoundType(App.getSoundType() + 1);
      updateSoundLbl(App.getSoundType());

    } else {
      App.setSoundType(0);
      updateSoundLbl(App.getSoundType());
    }
    try {
      String sql = "UPDATE DATA SET SELECTEDSOUND = ? WHERE PLAYER = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1,App.getSoundType());
      ps.setString(2,"defaultUser");
      ps.executeUpdate();
      ps.close();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Updates sound label
  public void updateSoundLbl(int tempInt) {
    String soundStr = "";
    if (tempInt == 0)
      soundStr = "Sine Wave";
    else if (tempInt == 1)
      soundStr = "Piano";
    updateUnlockElements();
    currentSound.setText(String.format("Current sound: %s", soundStr));
  }

  //TODO: Unlock key and save to database. Make buttons.
  //This unlocks a new level if there are enough points, and if not, sets opacity on unlockErrorLbl to 1.
  public void unlockKey(ActionEvent actionEvent) {
    int reqPts = (unlockedKeys+1)*3;
    if (Integer.parseInt(score.getText()) >= reqPts) {//Add 1 to unlockedKeys in database and update labels/buttons
      unlockedKeys++;
      try {//Add 1 to unlockedKeys
        String sql = "UPDATE DATA SET UNLOCKEDKEYS = ? WHERE PLAYER = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, unlockedKeys);
        ps.setString(2, "defaultUser");
        ps.executeUpdate();
        ps.close();
        String sql2 = "UPDATE DATA SET SCORE = ? WHERE PLAYER = ?";
        PreparedStatement ps2 = conn.prepareStatement(sql2);
        ps2.setInt(1,Integer.parseInt(score.getText())-reqPts);
        ps2.setString(2,"defaultUser");
        ps2.executeUpdate();
        ps2.close();
        //Update score label
        score.setText(Integer.toString(Integer.parseInt(score.getText())-reqPts));
      } catch (Exception e) {
        e.printStackTrace();
      }
      updateUnlockElements();
    }
    else {
      unlockErrorLbl.setOpacity(1.0);
      unlockBtn.setDisable(true);
    }
  }

  //TODO: Unlock Sounds
}
