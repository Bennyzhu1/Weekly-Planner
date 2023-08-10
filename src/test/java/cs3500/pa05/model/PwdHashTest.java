package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests PwdHash for hashing strings
 */
class PwdHashTest {
  /**
   * Tests hashSha256
   */
  @Test
  void testHashSha256() {
    assertDoesNotThrow(PwdHash::new);

    assertEquals("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
        PwdHash.hashSha256("", PwdHash.sha256()));
    assertEquals("de942f110d62564b42f1be1786cf740f414d4c7d16fe2520207c7cdaadc36374",
        PwdHash.hashSha256("A B C D E F G", PwdHash.sha256()));
    assertEquals(PwdHash.hashSha256("str", PwdHash.sha256()),
        PwdHash.hashSha256("str", PwdHash.sha256()));

    assertThrows(AssertionError.class, () -> PwdHash.hashSha256("", ""));
  }
}