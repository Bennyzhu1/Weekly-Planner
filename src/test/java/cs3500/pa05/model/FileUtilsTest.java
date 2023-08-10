package cs3500.pa05.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileUtilsTest {
  File testFile;
  Path testPath;

  @BeforeEach
  void setUp() {
    try {
      this.testFile = File.createTempFile("test", ".bujo");
      this.testPath = this.testFile.toPath();
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Tests the class creation
   */
  @Test
  void testFileUtils() {
    assertDoesNotThrow(FileUtils::new);
  }

  /**
   * Tests readFile
   */
  @Test
  void testReadFile() {
    assertEquals("", FileUtils.readFile(this.testPath));
    assertThrows(RuntimeException.class,
        () -> FileUtils.readFile(Path.of(this.testPath.getFileName() + "FAIL")));
  }

  /**
   * Tests writeToFile
   */
  @Test
  void testWriteToFile() {
    assertEquals("", FileUtils.readFile(this.testPath));
    FileUtils.writeToFile(this.testPath, "TEST DATA");
    assertEquals("TEST DATA", FileUtils.readFile(this.testPath));
    boolean readOnly = this.testFile.setReadOnly();
    assertThrows(RuntimeException.class, () ->
        FileUtils.writeToFile(this.testFile.toPath(), ""));
    assertTrue(readOnly);
  }
}