package cs3500.pa05.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * Utility class for files
 */
public class FileUtils {
  /**
   * Reads a file
   *
   * @param path Path of the file
   * @return Text contents of the file
   */
  public static String readFile(Path path) {
    try {
      BufferedReader br = Files.newBufferedReader(path);
      String result = br.lines()
          .collect(Collectors.joining(System.lineSeparator()));
      br.close();
      return result;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes data to a file
   *
   * @param path Path of the file
   * @param data The data as text to write
   */
  public static void writeToFile(Path path, String data) {
    try {
      BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
      writer.write(data);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
