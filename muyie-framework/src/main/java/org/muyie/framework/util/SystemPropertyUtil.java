package org.muyie.framework.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SystemPropertyUtil {

  private final static Logger log = LoggerFactory.getLogger(SystemPropertyUtil.class);
  private static volatile boolean loggedWriteException;
  private static volatile boolean loggedReadException;

  private static final Pattern INTEGER_PATTERN = Pattern.compile("-?[0-9]+");

  /**
   * Returns {@code true} if and only if the system property with the specified {@code key} exists.
   */
  public static boolean contains(String key) {
    return get(key) != null;
  }

  /**
   * Returns the value of the Java system property with the specified {@code key}, while falling
   * back to {@code null} if the property access fails.
   *
   * @return the property value or {@code null}
   */
  public static String get(String key) {
    return get(key, null);
  }

  /**
   * Returns the value of the Java system property with the specified {@code key}, while falling
   * back to the specified default value if the property access fails.
   *
   * @return the property value. {@code defaultValue} if there's no such property or if an access to
   *         the specified property is not allowed.
   */
  public static String get(final String key, final String defaultValue) {
    if (key == null) {
      throw new NullPointerException("key can't be null");
    }
    if (key.isEmpty()) {
      throw new IllegalArgumentException("key must not be empty.");
    }

    String value = null;
    try {
      if (System.getSecurityManager() == null) {
        value = System.getProperty(key);
      } else {
        value = AccessController.doPrivileged(new PrivilegedAction<String>() {
          @Override
          public String run() {
            return System.getProperty(key);
          }
        });
      }
    } catch (Exception e) {
      if (!loggedReadException) {
        log("Unable to retrieve a system property '" + key + "'; default values will be used.", e);
        loggedReadException = true;
      }
    }

    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  /**
   * Returns the value of the Java system property with the specified {@code key}, while falling
   * back to the specified default value if the property access fails.
   *
   * @return the property value. {@code defaultValue} if there's no such property or if an access to
   *         the specified property is not allowed.
   */
  public static boolean getBoolean(String key, boolean defaultValue) {
    String value = get(key);
    if (value == null) {
      return defaultValue;
    }

    value = value.trim().toLowerCase();
    if (value.isEmpty()) {
      return true;
    }

    if ("true".equals(value) || "yes".equals(value) || "1".equals(value)) {
      return true;
    }

    if ("false".equals(value) || "no".equals(value) || "0".equals(value)) {
      return false;
    }

    log("Unable to parse the boolean system property '" + key + "':" + value + " - "
        + "using the default value: " + defaultValue);

    return defaultValue;
  }

  /**
   * Returns the value of the Java system property with the specified {@code key}, while falling
   * back to the specified default value if the property access fails.
   *
   * @return the property value. {@code defaultValue} if there's no such property or if an access to
   *         the specified property is not allowed.
   */
  public static int getInt(String key, int defaultValue) {
    String value = get(key);
    if (value == null) {
      return defaultValue;
    }

    value = value.trim().toLowerCase();
    if (INTEGER_PATTERN.matcher(value).matches()) {
      try {
        return Integer.parseInt(value);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    log("Unable to parse the integer system property '" + key + "':" + value + " - "
        + "using the default value: " + defaultValue);

    return defaultValue;
  }

  /**
   * Returns the value of the Java system property with the specified {@code key}, while falling
   * back to the specified default value if the property access fails.
   *
   * @return the property value. {@code defaultValue} if there's no such property or if an access to
   *         the specified property is not allowed.
   */
  public static long getLong(String key, long defaultValue) {
    String value = get(key);
    if (value == null) {
      return defaultValue;
    }

    value = value.trim().toLowerCase();
    if (INTEGER_PATTERN.matcher(value).matches()) {
      try {
        return Long.parseLong(value);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    log("Unable to parse the long integer system property '" + key + "':" + value + " - "
        + "using the default value: " + defaultValue);

    return defaultValue;
  }

  private static void log(String msg) {
    log.warn(msg);
  }

  private static void log(String msg, Exception e) {
    log.warn(msg, e);
  }

  /**
   * Sets the system property indicated by the specified key.
   *
   * @param key the name of the system property.
   * @param value the value of the system property.
   * @return the previous value of the system property, or <code>null</code> if it did not have one
   *         or it cannot be set for the sake of permission.
   *
   * @exception NullPointerException if <code>key</code> or <code>value</code> is <code>null</code>.
   * @exception IllegalArgumentException if <code>key</code> is empty.
   */
  public static String set(final String key, final String value) {
    if (key == null) {
      throw new NullPointerException("key can't be null");
    }
    if (value == null) {
      throw new NullPointerException("value can't be null");
    }
    if (key.isEmpty()) {
      throw new IllegalArgumentException("key must not be empty.");
    }

    String oldVal = null;
    try {
      if (System.getSecurityManager() == null) {
        oldVal = System.setProperty(key, value);
      } else {
        oldVal = AccessController.doPrivileged(new PrivilegedAction<String>() {
          @Override
          public String run() {
            return System.setProperty(key, value);
          }
        });
      }
    } catch (Exception e) {
      if (!loggedWriteException) {
        log("Unable to set a system property '" + key + "'", e);
        loggedWriteException = true;
      }
    }

    return oldVal;
  }

  private SystemPropertyUtil() {}
}
