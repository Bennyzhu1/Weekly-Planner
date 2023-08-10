package cs3500.pa05.view;

import cs3500.pa05.controller.LoadingController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * View for the loading screen
 */
public class LoadingView extends VBox {
  private final FXMLLoader loading;

  /**
   * Journal view component with controller
   *
   * @param controller Parameterized controller
   */
  public LoadingView(LoadingController controller) {
    // look up and store the layout
    this.loading = new FXMLLoader();
    this.loading.setLocation(getClass().getClassLoader().getResource("LoadingScreen.fxml"));
    this.loading.setController(controller);
  }

  /**
   * Loads the scene and returns it
   *
   * @return Main scene
   */
  public Scene load() {
    // load the layout
    try {
      return this.loading.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
