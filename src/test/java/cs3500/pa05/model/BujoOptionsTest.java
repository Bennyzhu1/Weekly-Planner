package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the BujoOptions enum
 */
class BujoOptionsTest {
  /**
   * Tests the general enum
   */
  @Test
  void testBujoOptions() {
    assertThrows(IllegalArgumentException.class,
        () -> BujoOptions.valueOf("..."));
    assertEquals("TASK", BujoOptions.TASK.name());
    assertEquals("EVENT", BujoOptions.EVENT.name());
    assertEquals("NONE", BujoOptions.NONE.name());
  }

  /**
   * Tests getOption
   */
  @Test
  void testGetOption() {
    assertEquals("Task", BujoOptions.TASK.getOption());
    assertEquals("Event", BujoOptions.EVENT.getOption());
    assertEquals("None", BujoOptions.NONE.getOption());
  }
}