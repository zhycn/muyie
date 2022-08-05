package org.muyie.framework.logback;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * TraceId生成工具类
 */
public final class TraceIdUtil {

  /**
   * 获取一个随机TraceId字符串
   *
   * @return TraceId字符串
   */
  public static String get() {
    return get(8);
  }

  /**
   * 获取一个随机TraceId字符串
   *
   * @param length 字符串长度（8-32位）
   * @return TraceId字符串
   */
  public static String get(int length) {
    if (length > 32 || length < 6) {
      throw new IllegalArgumentException(String.format("Length must be between %d and %d.", 6, 32));
    }
    return RandomStringUtils.randomAlphanumeric(length);
  }

}
