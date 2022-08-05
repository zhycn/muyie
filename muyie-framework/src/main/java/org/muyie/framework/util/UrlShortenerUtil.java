package org.muyie.framework.util;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * URL短链接生成工具类
 */
public final class UrlShortenerUtil {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  /**
   * 获取短链接地址
   *
   * @param url URL链接地址
   * @return 4个长度的短链接数组
   */
  public static List<String> get(String url) {
    return get(url, 6);
  }

  /**
   * 获取短链接地址
   *
   * @param url    URL链接地址
   * @param secret 安全码
   * @return 4个长度的短链接数组
   */
  public static List<String> get(String url, String secret) {
    return get(url, secret, 6);
  }

  /**
   * 获取短链接地址
   *
   * @param url    URL链接地址
   * @param length 3~6位长度
   * @return 4个长度的短链接数组
   */
  public static List<String> get(String url, int length) {
    return get(url, null, length);
  }

  /**
   * 获取短链接地址
   *
   * @param url    URL链接地址
   * @param secret 安全码
   * @param length 3~6位长度
   * @return 4个长度的短链接数组
   */
  public static List<String> get(String url, String secret, int length) {
    return get(url, secret, length, ALPHABET.toCharArray());
  }

  /**
   * 获取短链接地址
   *
   * @param url    URL链接地址
   * @param secret 安全码
   * @param length 3~6位长度
   * @param chars  支持的字符串
   * @return 4个长度的短链接数组
   */
  private static List<String> get(String url, String secret, int length, char[] chars) {
    Assert.notNull(url, "url must be not null");

    if (length > 6 || length < 3) {
      throw new IllegalArgumentException(String.format("Length must be between %d and %d.", 3, 6));
    }

    if (StringUtils.isNotBlank(secret)) {
      url = UriComponentsBuilder.fromPath(url).queryParam("secret", secret).build().toString();
    }

    String md5 = DigestUtils.md5DigestAsHex(url.getBytes());
    List<String> list = Lists.newArrayList();

    // 算法
    for (int i = 0; i < 4; i++) {
      String substring = md5.substring(i * 8, i * 8 + 8);
      long hex = 0x3FFFFFFF & Long.parseLong(substring, 16);
      String outChars = "";
      for (int j = 0; j < length; j++) {
        long index = 0x0000003D & hex;
        outChars += chars[(int) index];
        hex = hex >> 5;
      }
      list.add(outChars);
    }
    return list;
  }

}
