package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

import static com.alibaba.fastjson2.JSONWriter.Feature.IgnoreErrorGetter;

/**
 * 基于 FastJSON 的数据脱敏工具类
 *
 * @author larry.qi
 * @since 1.2.9
 */
@Slf4j
public class JsonSensitiveUtil extends SensitiveUtil {

  /**
   * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
   */
  private static String dateFormat = DatePattern.NORM_DATETIME_PATTERN;

  /**
   * JSON序列化时，忽略错误的属性
   */
  private static JSONWriter.Feature[] features = new JSONWriter.Feature[]{IgnoreErrorGetter};

  public static String toJson(Object object) {
    return JSON.toJSONString(object, dateFormat, chooseFilter(), features);
  }

  public static String toJson(Object object, JSONWriter.Feature... features) {
    return JSON.toJSONString(object, dateFormat, chooseFilter(), features);
  }

  public static String toJson(Object object, String format, JSONWriter.Feature... features) {
    return JSON.toJSONString(object, format, chooseFilter(), features);
  }

  public static void dateFormat(String dateFormat) {
    if (StringUtils.isNotBlank(dateFormat)) {
      JsonSensitiveUtil.dateFormat = dateFormat;
    }
  }

  public static void features(JSONWriter.Feature... features) {
    if (ArrayUtil.isNotEmpty(features)) {
      JsonSensitiveUtil.features = features;
    }
  }

  private static Filter[] chooseFilter() {
    return JsonSensitiveConfig.of(SensitiveStrategy.builder("createdTime").phones("mobile").build()).getFilters();
  }

}
