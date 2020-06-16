package mh;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class App extends Application {
  @Getter
  @Setter
  private static Stage primaryStage;
  @Getter
  @Setter
  private static Parent root;
  @Getter
  @Setter
  private static Scene scene;
  @Getter
  @Setter
  private static int soundType = 0;


  @Override
  public void start(Stage primaryStage) {
    App.primaryStage = primaryStage;
    try {
      root =
          FXMLLoader.load(getClass().getResource("/mh/views/base.fxml"));
      scene = new Scene(root);

      primaryStage.setScene(scene);
      primaryStage.setTitle("Microtone Heaven");
      primaryStage
          .getIcons()
          .add(
              new Image(
                  String.valueOf(
                      getClass().getResource("/mh/images/favicon.png"))));

      /*Screen screen = Screen.getPrimary();
      Rectangle2D bounds = screen.getVisualBounds();
      App.getPrimaryStage().setWidth(bounds.getWidth());
      App.getPrimaryStage().setHeight(bounds.getHeight());*/
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
      Platform.exit();
    }
  }
}
