package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test JsonUtils, this also tests all observing records
 */
class JsonUtilsTest {
  private BujoJson bujo;

  /**
   * Set up for easy access
   */
  @BeforeEach
  void setup() {
    this.bujo =
        new BujoJson("",
            new ArrayList<>(),
            new ArrayList<>(),
            new Thresholds(10, 10),
            "",
            PwdHash.hashSha256("", PwdHash.sha256()));
  }

  /**
   * Tests the class instance
   */
  @Test
  void testClassInstance() {
    assertDoesNotThrow(JsonUtils::new);
  }

  /**
   * Test serializeRecord
   */
  @Test
  void testSerializeRecord() {
    Thresholds thresholds = new Thresholds(10, 10);
    BujoJson bujoJson = bujo;
    final BujoJson finalBujoJson1 = bujoJson;
    assertDoesNotThrow(() -> JsonUtils.serializeRecord(finalBujoJson1));

    List<Task> tasks = new ArrayList<>();
    List<Event> events = new ArrayList<>();
    this.addSampleTasksAndEvents(tasks, events);

    thresholds.setMaxEvents(20);
    thresholds.setMaxTasks(20);
    bujoJson = new BujoJson("Test with Tasks & Events", tasks, events, thresholds, "Quote this",
        bujoJson.password());
    final BujoJson finalBujoJson2 = bujoJson;
    assertDoesNotThrow(() -> JsonUtils.serializeRecord(finalBujoJson2));
  }

  /**
   * Test deserializeToBujo
   */
  @Test
  void testDeserializeToBujo() {
    String bujoData = JsonUtils.serializeRecord(this.bujo).toString();
    assertDoesNotThrow(() -> JsonUtils.deserializeToBujo(bujoData));
    List<Task> tasks = new ArrayList<>();
    List<Event> events = new ArrayList<>();
    this.addSampleTasksAndEvents(tasks, events);

    this.bujo = new BujoJson(this.bujo.title(), tasks, events, this.bujo.thresholds(),
        this.bujo.quotesAndNotes(), this.bujo.password());

    String newBujoData = JsonUtils.serializeRecord(this.bujo).toString();
    assertDoesNotThrow(() -> JsonUtils.deserializeToBujo(newBujoData));

    assertThrows(RuntimeException.class, () -> JsonUtils.deserializeToBujo("{"));
    assertThrows(RuntimeException.class,
        () -> JsonUtils.deserializeToBujo("A string is invalid JSON without its notation"));
  }

  /**
   * Abstraction to add sample tasks and events for testing
   *
   * @param tasks  List of tasks
   * @param events List of events
   */
  void addSampleTasksAndEvents(List<Task> tasks, List<Event> events) {
    Task task1 = new Task("task 1", "desc", Day.SUNDAY, false);
    Task task2 = new Task("task 2", Day.MONDAY, true);
    Task task3 = new Task("task 3", "desc", Day.TUESDAY, false);
    Task task4 = new Task("task 4", Day.WEDNESDAY, true);
    Task task5 = new Task("task 5", "desc", Day.THURSDAY, false);
    Task task6 = new Task("task 6", Day.FRIDAY, true);
    Task task7 = new Task("task 7", "desc", Day.SATURDAY, false);
    tasks.add(task1);
    tasks.add(task2);
    tasks.add(task3);
    tasks.add(task4);
    tasks.add(task5);
    tasks.add(task6);
    tasks.add(task7);

    Event event1 = new Event("event 1", "desc", Day.SUNDAY, "00:00", "1 minute");
    Event event2 = new Event("event 2", Day.MONDAY, "01:00", "2 minutes");
    Event event3 = new Event("event 3", "desc", Day.TUESDAY, "02:00", "3 minutes");
    Event event4 = new Event("event 4", Day.WEDNESDAY, "03:00", "4 minutes");
    Event event5 = new Event("event 5", "desc", Day.THURSDAY, "04:00", "5 minutes");
    Event event6 = new Event("event 6", Day.FRIDAY, "05:00", "6 minutes");
    Event event7 = new Event("event 7", "desc", Day.SATURDAY, "06:00", "7 minutes");
    events.add(event1);
    events.add(event2);
    events.add(event3);
    events.add(event4);
    events.add(event5);
    events.add(event6);
    events.add(event7);
  }
}