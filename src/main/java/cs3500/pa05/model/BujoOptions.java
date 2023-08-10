package cs3500.pa05.model;

/**
 * Represents possible items in a bulleted journal application
 */
public enum BujoOptions {
  /**
   * Task ENUM
   */
  TASK("Task"),
  /**
   * Event ENUM
   */
  EVENT("Event"),
  /**
   * When no BUJOoption is required
   */
  NONE("None");

  private final String option;

  /**
   * Options for tasks or events
   *
   * @param option The option
   */
  BujoOptions(String option) {
    this.option = option;
  }

  /**
   * Reflected option
   *
   * @return The reflected option
   */
  public String getOption() {
    return this.option;
  }
}
