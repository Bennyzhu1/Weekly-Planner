package cs3500.pa05.controller;

import javafx.scene.input.KeyEvent;

/**
 * represents a controller for a java journal
 */
public interface JournalController {
  /**
   * Runs the controller
   */
  void run();

  /**
   * Handles the interaction
   *
   * @param code The KeyEvent
   */
  void handleInteraction(KeyEvent code);
}
