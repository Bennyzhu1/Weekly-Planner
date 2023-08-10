package cs3500.pa05.view;

import cs3500.pa05.controller.JournalController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * View component of the journal
 */
public class JournalView {
  private final FXMLLoader loader;

  /**
   * Journal view component with controller
   *
   * @param controller Parameterized controller
   */
  public JournalView(JournalController controller) {
    // look up and store the layout
    this.loader = new FXMLLoader();
    this.loader.setLocation(getClass().getClassLoader().getResource("BUJO.fxml"));
    this.loader.setController(controller);
  }

  /**
   * Loads the scene and returns it
   *
   * @return Main scene
   */
  public Scene load() {
    // load the layout
    try {
      return this.loader.load();
    } catch (IOException exc) {
      throw new IllegalStateException("Unable to load layout.");
    }
  }
}
