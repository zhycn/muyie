package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.filter.PropertyFilter;
import com.alibaba.fastjson2.filter.ValueFilter;

import cn.hutool.core.date.DatePattern;

import static com.alibaba.fastjson2.JSONWriter.Feature.IgnoreErrorGetter;

public class JsonSensitiveConfig {

  /**
   * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
   */
  private static final String DEFAULT_DATE_FORMAT = DatePattern.NORM_DATETIME_PATTERN;

  /**
   * JSON序列化时，忽略错误的属性
   */
  private static final JSONWriter.Feature[] DEFAULT_FEATURE = new JSONWriter.Feature[]{IgnoreErrorGetter};

  /**
   * 序列化的日期格式
   */
  private String dateFormat = DEFAULT_DATE_FORMAT;

  /**
   * 序列化设置
   */
  private JSONWriter.Feature[] features = DEFAULT_FEATURE;

  /**
   * 序列化时忽略的字段属性
   */
  private PropertyFilter propertyFilter;

  /**
   * 序列化时数据脱敏策略
   */
  private ValueFilter valueFilter;

  private JsonSensitiveConfig(SensitiveStrategy sensitiveStrategy) {
    this.propertyFilter = SensitivePropertyFilter.of(sensitiveStrategy.getIgnores());
    this.valueFilter = SensitiveValueFilter.of(sensitiveStrategy);
  }

  public static JsonSensitiveConfig of(SensitiveStrategy sensitiveStrategy) {
    return new JsonSensitiveConfig(sensitiveStrategy);
  }

  public static JsonSensitiveConfig of(SensitiveStrategy sensitiveStrategy, String dateFormat) {
    return new JsonSensitiveConfig(sensitiveStrategy).dateFormat(dateFormat);
  }

  public static JsonSensitiveConfig of(SensitiveStrategy sensitiveStrategy, String dateFormat, JSONWriter.Feature... features) {
    return new JsonSensitiveConfig(sensitiveStrategy).dateFormat(dateFormat).features(features);
  }

  public static JsonSensitiveConfig of(SensitiveStrategy sensitiveStrategy, JSONWriter.Feature... features) {
    return new JsonSensitiveConfig(sensitiveStrategy).features(features);
  }

  public Filter[] getFilters() {
    return new Filter[]{propertyFilter, valueFilter};
  }

  public String getDateFormat() {
    return dateFormat;
  }

  public JSONWriter.Feature[] getFeatures() {
    return features;
  }

  public JsonSensitiveConfig dateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
    return this;
  }

  public JsonSensitiveConfig features(JSONWriter.Feature... features) {
    this.features = features;
    return this;
  }

}
