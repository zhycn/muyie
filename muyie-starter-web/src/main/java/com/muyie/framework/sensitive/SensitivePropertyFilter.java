package com.muyie.framework.sensitive;

import com.alibaba.fastjson2.filter.PropertyFilter;

import cn.hutool.core.util.ArrayUtil;

/**
 * JSON序列化时忽略的字段属性，等价于：<code>@JSONField(serialize = false)</code>
 *
 * @author larry.qi
 * @since 1.2.9
 */
public class SensitivePropertyFilter implements PropertyFilter {

  /**
   * 序列化时忽略的字段列表
   */
  private static String[] ignores;

  public SensitivePropertyFilter(String... ignores) {
    SensitivePropertyFilter.ignores = ignores;
  }

  @Override
  public boolean apply(Object object, String name, Object value) {
    return !ArrayUtil.contains(ignores, name);
  }

  public static SensitivePropertyFilter of(String... ignores) {
    return new SensitivePropertyFilter(ignores);
  }

}
