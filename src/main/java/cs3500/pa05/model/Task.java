package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a task
 *
 * @param name        Name
 * @param description Description
 * @param day         The day of the week
 * @param complete    Whether it's complete or not
 */
public record Task(@JsonProperty("name") String name,
                   @JsonProperty("description") String description,
                   @JsonProperty("day") Day day,
                   @JsonProperty("complete") boolean complete) {
  /**
   * Optional constructor that doesn't have the description
   *
   * @param name Name
   * @param day Day of the week
   * @param complete Whether it's complete or not
   */
  public Task(String name, Day day, boolean complete) {
    this(name, "", day, complete);
  }
}