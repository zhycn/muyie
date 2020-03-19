package org.muyie.framework.sensitive;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 默认数据脱敏过滤器
 */
public class DefaultValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return SensitiveDataUtil.defaultHide((String) value);
  }

}
