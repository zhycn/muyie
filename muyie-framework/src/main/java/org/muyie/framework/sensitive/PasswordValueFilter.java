package org.muyie.framework.sensitive;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 密码数据安全过滤器
 */
public class PasswordValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return SensitiveDataUtil.customizeHide((String) value, 0, 0, 6);
  }

}
