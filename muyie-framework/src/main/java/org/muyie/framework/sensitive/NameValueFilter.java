package org.muyie.framework.sensitive;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * 中文名称、用户姓名、昵称等数据脱敏
 */
public class NameValueFilter implements ValueFilter {

  @Override
  public Object process(Object object, String name, Object value) {
    return SensitiveDataUtil.nameHide((String) value);
  }

}
