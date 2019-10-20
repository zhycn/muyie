package org.muyie.framework.domain.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class UrlShortenerUtilTest {

  @Test
  public void test() {
    final String url = "http://localhost:8080?lang=zh_CN";
    List<String> list = UrlShortenerUtil.create(url);
    assertThat(list).asList().size().isEqualTo(4);
  }

}
