package cs3500.pa05;

import cs3500.pa05.controller.JournalController;
import cs3500.pa05.controller.JournalControllerImpl;
import cs3500.pa05.controller.LoadingController;
import cs3500.pa05.controller.LoadingControllerImpl;
import cs3500.pa05.view.JournalView;
import cs3500.pa05.view.LoadingView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Driver class
 */
public class JournalApplication extends Application {

  /**
   * The start method
   *
   * @param primaryStage the primary stage for this application, onto which
   *                     the application scene can be set.
   *                     Applications may create other stages, if needed, but they will not be
   *                     primary stages.
   */
  @Override
  public void start(Stage primaryStage) {
    // instantiate a simple welcome GUI view

    JournalController jci = new JournalControllerImpl(primaryStage);
    JournalView jv = new JournalView(jci);
    LoadingController lc = new LoadingControllerImpl(primaryStage, jci, jv);
    LoadingView lv = new LoadingView(lc);
    try {
      // load and place the view's scene onto the stage

      Scene scene = lv.load();
      primaryStage.setScene(scene);
      primaryStage.setTitle("Your BUJO Journal");
      // render the stage
      primaryStage.show();
      lc.run();
    } catch (IllegalStateException exc) {
      System.err.println("Unable to load GUI.");
    }
  }

  /**
   * The entry point
   *
   * @param args Arguments, not required
   */
  public static void main(String[] args) {
    launch();
  }
}
