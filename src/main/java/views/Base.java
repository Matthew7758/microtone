package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Base {
  @FXML Button replayBtn;
  @FXML Button shopBtn;
  public void replayNote(ActionEvent actionEvent) {
    replayBtn.setText("Clicked!");
  }
  public void openShop(ActionEvent actionEvent) {
    shopBtn.setText("Opened!");
  }

}
