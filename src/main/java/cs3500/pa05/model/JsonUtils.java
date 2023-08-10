package cs3500.pa05.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Json utilities
 */
public class JsonUtils {
  /**
   * Serializes a record
   *
   * @param record The record
   * @return The record as a JsonNode
   */
  public static JsonNode serializeRecord(Record record) {
    return new ObjectMapper().convertValue(record, JsonNode.class);
  }

  /**
   * Deserializes JSON data into readable data for the bujo handling
   *
   * @param jsonStr Json string
   * @return BujoJson record with accessible fields
   */
  public static BujoJson deserializeToBujo(String jsonStr) {
    try {
      JsonParser parser = new ObjectMapper().getFactory().createParser(jsonStr);
      return parser.readValueAs(BujoJson.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
