package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Base {
  @FXML
  Button btn;

  public void clickBtn(ActionEvent actionEvent) {
    btn.setText("Clicked!");
  }

}
