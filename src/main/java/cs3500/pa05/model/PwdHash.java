package cs3500.pa05.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for hashing passwords, passwords are not de-hashed for security
 */
public class PwdHash {
  /**
   * Hashes a string with SHA256
   *
   * @param str The supplied string
   * @param algorithm The type of algorithm to use
   * @return Hashed string
   */
  public static String hashSha256(String str, String algorithm) {
    MessageDigest digest;
    try {
      digest = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new AssertionError("Unreachable algorithm", e);
    }
    byte[] encodedHash = digest.digest(str.getBytes(StandardCharsets.UTF_8));

    StringBuilder hexStr = new StringBuilder(2 * encodedHash.length);
    for (byte hash : encodedHash) {
      String hex = Integer.toHexString(0xff & hash);
      if (hex.length() == 1) {
        hexStr.append('0');
      }
      hexStr.append(hex);
    }
    return hexStr.toString();
  }

  /**
   * The permanent algorithm type.
   *
   * @return SHA-256
   */
  public static String sha256() {
    return "SHA-256";
  }
}
