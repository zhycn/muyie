package org.muyie.framework.config.logback;

import org.muyie.framework.sensitive.SensitiveDataFilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class LogUtil {

  public static String toJSONString(Object object) {
    return JSON.toJSONString(object, new SensitiveDataFilter(),
        SerializerFeature.WriteDateUseDateFormat);
  }

  public static String toJSONString(Object object, String... ignoreFields) {
    return JSON.toJSONString(object, new SensitiveDataFilter(ignoreFields),
        SerializerFeature.WriteDateUseDateFormat);
  }

}
