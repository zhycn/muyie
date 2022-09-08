package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.filter.PropertyFilter;
import com.alibaba.fastjson2.filter.ValueFilter;
import com.muyie.framework.sensitive3.SensitivePropertyFilter;
import com.muyie.framework.sensitive3.SensitiveValueFilter;

import cn.hutool.core.util.ArrayUtil;

/**
 * 敏感数据脱敏过滤器
 *
 * @author larry.qi
 * @since 1.2.9
 */
public class SensitiveDataFilter {

  /**
   * 序列化时忽略的字段属性
   */
  private final PropertyFilter propertyFilter;

  /**
   * 序列化时数据脱敏策略
   */
  private final ValueFilter valueFilter;

  private SensitiveDataFilter(SensitiveStrategy sensitiveStrategy, String... ignores) {
    String[] ignoredFields = ArrayUtil.defaultIfEmpty(ignores, sensitiveStrategy.getIgnores());
    this.propertyFilter = SensitivePropertyFilter.of(ignoredFields);
    this.valueFilter = SensitiveValueFilter.of(sensitiveStrategy);
  }

  public static SensitiveDataFilter of(SensitiveStrategy sensitiveStrategy, String... ignores) {
    return new SensitiveDataFilter(sensitiveStrategy, ignores);
  }

  public Filter[] get() {
    return new Filter[]{propertyFilter, valueFilter};
  }

}
