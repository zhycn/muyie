package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于 FastJSON 的数据脱敏工具类
 *
 * @author larry.qi
 * @since 1.2.9
 */
@Slf4j
public class JsonSensitiveUtil extends SensitiveUtil {

  public static String toJSONString(Object object, JsonSensitiveConfig config) {
    System.out.println(Arrays.toString(config.getFilters()));
    return JSON.toJSONString(object, config.getDateFormat(), config.getFilters(), config.getFeatures());
  }

  public static String toJSONString(Object object, JSONWriter.Feature... features) {
    return JSON.toJSONString(object, features);
  }

}
