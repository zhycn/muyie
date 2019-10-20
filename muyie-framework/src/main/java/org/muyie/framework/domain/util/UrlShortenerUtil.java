package org.muyie.framework.domain.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

public final class UrlShortenerUtil {

  private static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  public static List<String> create(String url) {
    return create(url, 6);
  }

  public static List<String> create(String url, String secret) {
    return create(url, secret, 6);
  }

  public static List<String> create(String url, int length) {
    return create(url, null, length);
  }

  public static List<String> create(String url, String secret, int length) {
    return create(url, secret, length, ALPHABET.toCharArray());
  }

  private static List<String> create(String url, String secret, int length, char[] chars) {
    Assert.notNull(url, "url must be not null");

    if (length > 6 || length < 3) {
      throw new IllegalArgumentException(String.format("Length must be between %d and %d.", 3, 6));
    }

    if (StringUtils.isNotBlank(secret)) {
      url = UriComponentsBuilder.fromPath(url).queryParam("secret", secret).build().toString();
    }

    String md5 = DigestUtils.md5DigestAsHex(url.getBytes());
    List<String> list = Lists.newArrayList();

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
