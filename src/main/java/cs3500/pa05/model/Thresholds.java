package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Thresholds for setting tasks and events
 */
public class Thresholds {
  private int maxEvents;
  private int maxTasks;

  /**
   * Constructor for the thresholds
   *
   * @param maxEvents Events threshold
   * @param maxTasks Tasks threshold
   */
  @JsonCreator
  public Thresholds(@JsonProperty("maxEvents") int maxEvents,
                    @JsonProperty("maxTasks") int maxTasks) {
    this.maxEvents = maxEvents;
    this.maxTasks = maxTasks;
  }

  /**
   * Max events
   *
   * @return The max events
   */
  public int getMaxEvents() {
    return this.maxEvents;
  }

  /**
   * Max tasks
   *
   * @return The max tasks
   */
  public int getMaxTasks() {
    return this.maxTasks;
  }

  /**
   * set max events
   *
   * @param newThreshold new Threshold for MaxEvents
   */
  public void setMaxEvents(int newThreshold) {
    this.maxEvents = newThreshold;
  }

  /**
   * set max tasks
   *
   * @param newThreshold new threshold for maxTasks
   */
  public void setMaxTasks(int newThreshold) {
    this.maxTasks = newThreshold;
  }
}
