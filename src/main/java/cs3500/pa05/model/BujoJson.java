package cs3500.pa05.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The highest level JSON for a .bujo file
 *
 * @param tasks List of tasks
 * @param events List of events
 * @param thresholds The threshold configuration
 * @param quotesAndNotes Quotes and notes
 * @param password A password
 */
public record BujoJson(@JsonProperty("title") String title,
                       @JsonProperty("tasks") List<Task> tasks,
                       @JsonProperty("events") List<Event> events,
                       @JsonProperty("thresholds") Thresholds thresholds,
                       @JsonProperty("quotesAndNotes") String quotesAndNotes,
                       @JsonProperty("password") String password) {
}
