package cs3500.pa05.controller;

import cs3500.pa05.view.JournalView;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Implementation of the LoadingController
 */
public class LoadingControllerImpl implements LoadingController {
  @FXML
  private Button loadingButton;
  @FXML
  private Text loadingText;
  @FXML
  private ImageView loadingGif;
  private final Stage primaryStage;
  private final JournalController jci;
  private final JournalView jv;
  private final PauseTransition pause = new PauseTransition(Duration.seconds(2));

  /**
   * Constructor for LoadingController implementation
   *
   * @param primaryStage Stage
   * @param jci          JournalController object
   * @param jv           The JournalView
   */
  public LoadingControllerImpl(Stage primaryStage, JournalController jci, JournalView jv) {
    this.primaryStage = primaryStage;
    this.jci = jci;
    this.jv = jv;
  }

  /**
   * Runs the loading controller
   */
  @Override
  public void run() {
    this.loadingButton.setOnAction(event -> {
      this.loadingButton.setVisible(false);
      this.loadingGif.setVisible(true);
      this.loadingText.setVisible(true);
      this.pause.play();
      this.pause.setOnFinished(e -> waitToLoad());
    });
  }

  /**
   * Waits to load
   */
  @Override
  public void waitToLoad() {
    Scene scene = jv.load();
    scene.setOnKeyPressed(jci::handleInteraction);
    this.primaryStage.setScene(scene);
    this.jci.run();
  }
}
