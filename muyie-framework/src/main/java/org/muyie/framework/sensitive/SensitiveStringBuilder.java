package org.muyie.framework.sensitive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public final class SensitiveStringBuilder {

  public static String toJSONString(final Object object, final String... ignoreFields) {
    return JSON.toJSONString(object, buildFilter(ignoreFields), SerializerFeature.WriteDateUseDateFormat);
  }

  private static SensitiveDataFilter buildFilter(final String... ignoreFields) {
    return new SensitiveDataFilter(ignoreFields);
  }

}
