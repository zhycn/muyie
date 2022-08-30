package com.muyie.security;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

/**
 * Utility class for generating random Strings.
 *
 * @author larry.qi
 */
public final class RandomUtil {

  private static final int DEF_COUNT = 20;

  private static final SecureRandom SECURE_RANDOM;

  static {
    SECURE_RANDOM = new SecureRandom();
    SECURE_RANDOM.nextBytes(new byte[64]);
  }

  private RandomUtil() {
  }

  /**
   * <p>generateRandomAlphanumericString.</p>
   *
   * @return a {@link String} object.
   */
  public static String generateRandomAlphanumericString() {
    return RandomStringUtils.random(DEF_COUNT, 0, 0, true, true, null, SECURE_RANDOM);
  }

  /**
   * Generate a password.
   *
   * @return the generated password.
   */
  public static String generatePassword() {
    return generateRandomAlphanumericString();
  }

  /**
   * Generate an activation key.
   *
   * @return the generated activation key.
   */
  public static String generateActivationKey() {
    return generateRandomAlphanumericString();
  }

  /**
   * Generate a reset key.
   *
   * @return the generated reset key.
   */
  public static String generateResetKey() {
    return generateRandomAlphanumericString();
  }
}
