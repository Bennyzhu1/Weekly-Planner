package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
import java.util.Date;

/**
 * Represents an event
 *
 * @param name        Name
 * @param description Description
 * @param day         The day of the week
 * @param time        The time
 * @param duration    The duration of the event
 */
public record Event(@JsonProperty("name") String name,
                    @JsonProperty("description") String description,
                    @JsonProperty("day") Day day,
                    @JsonProperty("time") String time,
                    @JsonProperty("duration") String duration) {
  /**
   * Optional constructor that doesn't have the description
   *
   * @param name     Name
   * @param day      The day of the week
   * @param time     The time
   * @param duration The duration of the event
   */
  public Event(String name, Day day, String time, String duration) {
    this(name, "", day, time, duration);
  }
}